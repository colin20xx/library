import {InitializerPopup} from "./initializer-popup.js";

document.addEventListener("DOMContentLoaded", function () {
let currentRequest;
    document.querySelectorAll(".open-popup").forEach(function(button) {

            button.addEventListener("click", function(e) {
                e.preventDefault();

                let url = this.dataset.url;
                fetch(url, {method:"POST"})
                    .then(response => response.text())
                    .then(html => {
                        document.getElementById("globalModalContent").innerHTML = html;

                        let modal = new bootstrap.Modal( document.getElementById("globalModal"));

                        InitializerPopup();

                        modal.show();

                    });

            });

    });

    document.querySelectorAll(".open-simple-popup").forEach(

        function (button){
            button.addEventListener("click", function (e) {
                e.preventDefault();
                let url = this.dataset.url;

                fetch(url, {method:"POST"})
                    .then(response => response.text())
                    .then(html => {
                        let smallLogPanel = null;
                        const globalModalContent = document.getElementById("globalModalContent");
                        if (globalModalContent){
                            globalModalContent.innerHTML = html;
                            smallLogPanel = document.getElementById("small_log_bar");
                        }else{
                            alert('На цій сторінці неможливо створення іншого документу.');
                            return;
                        }
                        // document.getElementById("globalModalContent").innerHTML = html;

                        let modal = new bootstrap.Modal(document.getElementById("globalModal"));

                        let nomenclatureSelect = $('#nomenclature_select').select2( { dropdownParent: $("#globalModalContent"), width:'100%' } );

                        $('#denomination_select').select2(
                            {
                                dropdownParent: $("#globalModalContent"),
                                width:'100%'
                            }
                        ).on('change', function ()
                        {

                            nomenclatureSelect
                                .empty()
                                .append(new Option('виберіть номенклатуру','',true,true))
                                .trigger('change');

                                fetch('/getNomenclatureListByIdDenomination?idDenom=' + this.value, {method:'GET'})
                                    .then(res => { return res.json() })
                                    .then(
                                        dataArray =>{
                                            if (dataArray.length > 0){
                                                nomenclatureSelect.prop('disabled', false);
                                                dataArray.forEach(nomenclature =>{
                                                    nomenclatureSelect.append(new Option(nomenclature.title, nomenclature.id))
                                                });

                                                nomenclatureSelect.trigger('change');
                                            }else{
                                                nomenclatureSelect
                                                    .empty()
                                                    .append(new Option('номенклатури відсутні','',true,true))
                                                    .prop('disabled', true)
                                                    .trigger('change');
                                            }
                                        })
                                    .catch(error =>{
                                        console.error('error loading nomenclatures', error);
                                    });
                        });

                        modal.show();
                    });//end .then block

            });
        }
    );

    const button = document.querySelector(".open-popup-add-numeric-units");
    if (button){
        button.addEventListener("click", async function (){
            try{

                const res = await fetch(button.dataset.url, {method:'POST'})

                if(!res.ok){ throw new Error(`HTTP error: ${res.status}`); }

                const html = await res.text();

                document.getElementById("globalModalContent").innerHTML = html;

                let modal = new bootstrap.Modal( document.getElementById("globalModal"));

                //----------------------------------------------------------------------------------[ init elements ]---

                    const searchInputField = document.querySelector('.field-input-serial-number');
                    const targetTBody = $('#tableBodyList');
                    const tempTableSearch = $('#tempTableSearch');

                    if(searchInputField){
                        searchInputField.addEventListener("input", async function(){
                            targetTBody.empty();

                            if(currentRequest){currentRequest.abort()}
                            currentRequest = new AbortController();

                            const response = await fetch(
                                this.dataset.searchUrl + '?serialNumber=' + encodeURIComponent(this.value),
                                {signal: currentRequest.signal, method: 'GET'},
                            );
                            if(response.ok){
                                const unitList = await response.json();
                                console.log(unitList);
                                targetTBody.empty();

                                const tRow = unitList.map(unit =>`
                                    <tr>
                                            <td class="small"> ${unit.id} </td>
                                            <td class="small"> ${unit.serialNumber} </td>
                                            <td class="text-center small"> ${unit.unitYear ?? ''} </td>
                                            <td class="text-center small"> ${unit.category ?? ''} </td>
                                            <td class="py-1 small"> 
                                                <button type="button" class="btn btn-outline-success py-0 add-found-unit-to-temp-table" 
                                                        data-unit-id = "${unit.id}"
                                                        data-url = "/unit/add-unit-to-document"> 
                                                    &#8680; 
                                                </button> 
                                            </td>
                                        </tr>
                                `).join('');

                                targetTBody.html(tRow);

                            }else{ throw new Error('error loading data:' + response.status); }

                        });

                    }//if end
                //------------------------------------------------------------------[ .add-found-unit-to-temp-table ]---
                    targetTBody.on('click', '.add-found-unit-to-temp-table', function () {
                        // url = /unit/add-unit-to-document
                        fetch(this.dataset.url, {
                            method:'POST', body: new URLSearchParams({ unitId: this.dataset.unitId })
                        });

                        const row = $(this).closest('tr');
                        const cells = row.find('td');
                        tempTableSearch.prepend(`
                            <tr>
                                <td>-</td>
                                <td> ${cells.eq(0).text().trim()} </td>
                                <td class="text-center"> ${cells.eq(1).text().trim()} </td>
                                <td class="text-center"> ${cells.eq(2).text().trim() ?? ''} </td>
                                <td class="text-center"> ${cells.eq(3).text().trim() ?? ''} </td>
                                <td class="py-1"> 
                                    <button type="button" value="${ $(this).data('unit-id')}" 
                                            class="btn btn-outline-danger py-0 remove-unit-from-temp-table"> 
                                    &#8678; </button> 
                                </td>
                            </tr>
                        `);
                        row.remove();
                    });

                    tempTableSearch.on('click','.remove-unit-from-temp-table', function (){
                        const row = $(this).closest('tr').remove();
                    });

                //-----------------------------------------------------------------------------[ edn init elements ]----

                modal.show();

            } catch(error){
                console.error("Failed to load modal content:", error);
            }

        });
    }//if end


}); //end document ready