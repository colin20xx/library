const DENOMINATION = "denomination";
const NOMENCLATURE = "nomenclature";
const CARD = "card";
let buttonObj;
let id_doc_type;
let docID, tempURL;
let tableCardBody;
let tempHTML ="";
let tempNumber;

const formatterPrice = new Intl.NumberFormat('uk-UA',{minimumFractionDigits: 2, maximumFractionDigits: 2});

/**
 * @param {object} e  - The first number.
 *
 */
function findDenomOrCard(e) {

    let newRow01;

    switch (e.name) {
        case DENOMINATION:
            console.log('search for ' + DENOMINATION);
            break;
        case NOMENCLATURE:
            console.log('search for ' + NOMENCLATURE);
            break;
        case CARD:
            console.log('search for ' + CARD);
            break;
        default:
            alert('wrong name on input tag name!')
            break;
    }

    var tableDenomCards = document.getElementById('denomTable');
    var rowsCount = tableDenomCards.getElementsByTagName('tr').length - 1;
    let tableBody = tableDenomCards.getElementsByTagName('tbody')[0];
    const rows = Array.from(tableBody.getElementsByTagName('tr'));


    //-----------------------------------------------------------------[ event listener for table body ]----------------

    const tableDenomBody = document.getElementById('table-denom-body');
    tableDenomBody.addEventListener('click', function (event) {
        let clickedRow = event.target.closest('tr');
        if (clickedRow) {
            const allRows = tableDenomBody.querySelectorAll('tr');

            allRows.forEach(row => {
                if (row !== clickedRow) {
                    row.style.backgroundColor = 'white'
                }
            });

            clickedRow.style.backgroundColor = '#c0c0c0';
            document.getElementById('span-denom-title').textContent = clickedRow.cells[0].innerHTML;
            document.getElementById('span-nomenc-title').textContent = clickedRow.cells[1].innerHTML;
            document.getElementById('field_denom_id').value = clickedRow.cells[4].innerHTML; //denom_id
            document.getElementById('field_nonencl_id').value = clickedRow.cells[3].innerHTML; //nomencl_id

            $('#tableCardBody').empty();
            //-----------------------------------------------------------------------------[ populate table cards ]-----
            $.ajax({
                url: '/cards/ajax/list',
                type: 'GET',
                dataType: 'json',
                data: {
                    denom_id: clickedRow.cells[4].innerHTML,
                    nomencl_id: clickedRow.cells[3].innerHTML
                },

                success: function (response) {
                    $.each(response, function (index, card) {
                        var $row = $('<tr data-id="' + card.id + '">')
                            .append($('<td>').text(card.id).hide())
                            .append($('<td>').text(String(card.cardNumber).padStart(5, '0')))
                            .append($('<td>').text(card.category))
                            .append($('<td>').text(card.qtyPS))
                            .append($('<td>').text(card.qtyBTS))
                            .append($('<td>').text(card.qtyPS + card.qtyBTS))
                            .append($('<td>').text(card.price));
                        $('#tableCardBody').append($row);

                    });
                    // console.log(response);
                },
                error: function (xhr, status, err) {
                    console.log(err.value);
                }
            });

        }
    });//end event listener

    //-----------------------------------------------------------------[ function load to table all denominations ]-----
    // TODO
    $.ajax({
        url: '/denom-and-nomenclature-list?denomTitle=' + e.value,
        type: 'GET',
        dataType: 'json',
        success: function (myDenoms) {
            // console.log(myDenoms);
            if (rowsCount > 1) { rows.forEach(row => row.remove()); }
            $.each(myDenoms, function (index, denom) {
                newRow01 = tableBody.insertRow(-1);
                let cell0 = newRow01.insertCell(0); cell0.innerHTML = denom[1];
                let cell1 = newRow01.insertCell(1); cell1.innerHTML = denom[10];
                let cell2 = newRow01.insertCell(2); cell2.innerHTML = denom[19];
                let cell3 = newRow01.insertCell(3); cell3.hidden = true; cell3.innerText = denom[0];
                let cell4 = newRow01.insertCell(3); cell4.hidden = true; cell4.innerText = denom[9];
            });
        },
        error: function (xhr, status, error) {
            console.error('error update table', error)
        }
    });

}//end function

var myGlobalArray = [];

$('#input-denom').on('input', function () {
    // console.log('denom val: ' + $(this).val());
});

function countTotals() {
    let qtyPS = parseFloat($('#qty_pls').val()) || 0;
    let qtyBTS = parseFloat($('#qty_bts').val()) || 0;
    let qtyDeps = parseFloat($('#qty_deps').val()) || 0;

    let price = parseFloat($('#price').val()) || 0;
    $('#qty_tot').val((qtyPS + qtyBTS + qtyDeps).toFixed(5));
    let totalAmount = Intl.NumberFormat('fr-FR', { minimumFractionDigits: 5, maximumFractionDigits: 5 }).format((qtyPS + qtyBTS + qtyDeps).toFixed(5) * price);

    $('#qty_amount_to_show').val(totalAmount);
    $('#qty_amount_to_send').val((qtyPS + qtyBTS + qtyDeps).toFixed(5) * price);
} //end function


function formatDateToFormatUA(dateStr){
    const date = new Date(dateStr);
    return [
        String(date.getDate()).padStart(2, "0"),
        String(date.getMonth() + 1).padStart(2, "0"),
        date.getFullYear(),
    ].join(".");
}
// ---------------------------------------------------------------------------------[ click on btn_last_invoice_num ]---

$(document).ready(function () {
    $('#btn_last_invoice_num').click(function () {
        console.log('click on btn');
        let doc_title = document.getElementById('field_doc_title');

        $.ajax({
            url: '/invoice/ajax/last-num',
            type: 'GET',

            success: function (data) {
                if (data.length < 1) {
                    $('#doc_num').val('1/' + String(new Date().getFullYear().slice(-2)));
                    $('#doc_num').css('background-color', '#e6ffee');
                    $('#inputDocSubmit').prop('disabled', false);
                } else {
                    $('#doc_num').val(data);
                    $('#doc_num').css('background-color', '#e6ffee');
                    $('#inputDocSubmit').prop('disabled', false);
                }
            },
            error: function (err, status) {
                console.log(err + ' > ' + status)
            }
        });
    });
});

function letsDebounce(fn,d){
    let timer;
    return function(){
        clearTimeout(timer);
        timer=setTimeout(fn,d);
    }
}//end function

const PAGE_APP_COMMON_LIST = 'app-list-page';
const PAGE_CARDS_COMMON_LIST = 'cards-common-list';
const PAGE_INVOICES_COMMON_LIST = 'invoices-common-list';
const PAGE_ATC_COMMON_LIST = 'atc-common-list';

const PAGE_NEW_CARD = 'new-card-page';

//--------------------------------------------------------------------------------------------------------[ populateInvoiceCommonTable ]---
function populateInvoiceCommonTable(listDocs, tblList) {
    listDocs.forEach((doc, index) => {
        const tempRow = `
            <tr ${doc.commitedAt ? '' : 'class=table-warning'}>
                <td> ${index + 1} </td>
                <td> - </td>
                <td> <a href="/invoice/${doc.id}">${doc.number}</a> </td>
                <td> ${formatDateToFormatUA(doc.documentDate)} </td>
                <td> ${doc.sender} </td>
                <td> ${doc.receiver} </td>
                <td> ${doc.comments} </td>
                <td style="text-align: center"> ${doc.commitedAt ? '<i class="bi bi-bookmark-plus-fill m-0 fs-5"></i>' : '<i class="bi bi-bookmark m-0 fs-5"></i>'} </td>
                <td>

                    <div class="input-group gap-1">
                        <button class="btn btn-outline-info btn-sm flex-fill"
                            data-url="@{/invoice/{id}(id=${doc.id})}"
                            data-redir-url="@{/docs}"
                            onclick="">&#9998;
                        </button>

                        <button class="btn btn-outline-danger btn-sm flex-fill"
                            data-url="@{/invoice/{id}(id=${doc.id})}"
                            data-redir-url="@{/docs}"
                             onclick="deleteItem(this)">DEL
                        </button>

                        <a href="${doc.firstPdf ? '/uploads/' + doc.fileId + '/download' : ''}"
                           class="${doc.firstPdf ? 'btn btn-outline-danger btn-sm flex-fill' : 'btn btn-outline-secondary btn-sm flex-fill disabled'}"
                           title="${doc.firstPdf ? 'завантажити pdf документу' : 'документ відсутній'}"> ${doc.firstPdf ? '&#8595; PDF' : 'X PDF'}
                        </a>

                        <button class="btn btn-outline-success btn-sm flex-fill" th:data-url="@{/invoice/{id}(id=${doc.id})}"
                            data-redir-url="/docs"
                            onclick="">XLS</button>
                    </div>
                </td>
            </tr>
        `;
        tblList.append(tempRow);
    });
}//end function

//--------------------------------------------------------------------------------------------------------[ populateCardsCommonTable ]---
function populateCardsCommonTable(listDocs, tblList) {

    listDocs.forEach((doc, index) =>{
        const tempRow = `
            <tr>
                <td> ${index + 1} </td>
                <td> <a href="/cards/${doc.id}/details" class="link-primary link-offset-2 p-0 m-0 text-center">${doc.cardNumber}</a> </td>
                <td> --- </td>
                <td> --- </td>
                <td> --- </td>
                <td> ${doc.category} </td>
                <td style="text-align: right"> ${doc.price.toLocaleString('uk-UA',{minimumFractionDigits:2, maximumFractionDigits:2}) } </td>
                <td> ${doc.qtyPS} </td>
                <td> ${doc.qtyBTC} </td>
                <td> --- </td>
                <td> ${doc.qtyBTC + doc.qtyBTC} </td>
                <td> ${(doc.qtyBTC + doc.qtyBTC) * doc.price} </td>
                <td> --- </td>
                <td>
                     <div class="input-group input-group-sm">
                            <button class="btn btn-outline-info btn-sm w-50 m-0 p-0"
                                    th:attr="data-id=${doc.id}"
                                    th:data-url="@{/cards/{id}(id=${doc.id})}"
                                    onclick="openEditFromForCard(this)">&#9998;</button>
                            <button class="btn btn-outline-danger btn-sm w-50 m-0 p-0" th:data-url="@{/cards/{id}(id=${doc.id})}" onclick="deleteItem(this)">X</button>
                        </div>
                </td>
            </tr>`;
        tblList.append(tempRow);
    });
}//end function

//--------------------------------------------------------------------------------------------------------[ populateAppCommonTable ]---
function populateAppCommonTable(listDocs, tblList) {

    listDocs.forEach((doc, index) =>{
        const tempRow = `
            <tr>
                <td> ${index + 1} </td>
                <td> <a href="/akt-accept-transfer/${doc.id}" class="link-primary link-offset-2 p-0 m-0 text-center">${doc.number}</a> </td>
                <td> ${doc.date} </td>
                <td> ${doc.sender} </td>
                <td> ${doc.receiver} </td>
                <td> ${doc.comments} </td>
                <td style="text-align: center; font-size: 1em"> ${doc.commitedDate !== null ? doc.commitedDate : '&#11198;'} </td>
                <td class="m-0 p-0 text-center align-middle" style="font-size: 1.5em;"> ${doc.firstPdf === null ? '&#11198;' : '&#11015;'} </td>
                <td class="m-0 p-0 text-center align-middle" style="font-size: 1.5em;">  &#11015; </td>
            </tr>`;
        tblList.append(tempRow);
    });
}//end function
// ----------------------------------------------------------------------------------------------------[ makeParams ]---
/**
 *
 * @param searchFldList
 * @param tblList - html table to populate
 * @param url
 * @param pageData - web page body attribute
 * @returns {Promise<void>}
 */
async function makeParamsURL(searchFldList, tblList, url, pageData){
    tblList.empty();
    const params = new URLSearchParams();
    searchFldList.forEach(param =>{ params.append(param.id, param.value) });

    const response01 = await fetch(url + '?' + params.toString());
    const listDocs01 = await response01.json();

    switch (pageData){
        case PAGE_APP_COMMON_LIST:
            populateAppCommonTable(listDocs01, tblList);
            break;
        case PAGE_INVOICES_COMMON_LIST:
            populateInvoiceCommonTable(listDocs01, tblList);
            break;
        case PAGE_CARDS_COMMON_LIST:
            populateCardsCommonTable(listDocs01,tblList);
            break;
        case PAGE_ATC_COMMON_LIST:
            console.log(PAGE_ATC_COMMON_LIST);
            break;
        default: console.log('wrong page-data id');
    }//end switch

}//end function

// ---------------------------------------------------------------------------------------------------------------------------------[ $(document).ready() ]---
$(document).ready(function () {
    const pageData = $('body').data('page');
    const searchFieldsList = document.querySelectorAll('.searchField');
    // -----------------------------------------------------------------------------------------------[ app list page ]---

    if (searchFieldsList.length){

        const tableList = $('#tableBodyList');
        const searchURL = $('#field_search_url').val();

        const debouncedSearch = letsDebounce(() => { makeParamsURL(searchFieldsList, tableList, searchURL, pageData); }, 500);

        searchFieldsList.forEach(fld =>{

            fld.addEventListener('input', debouncedSearch);
        });
    }
    // -----------------------------------------------------------------------------------------------[ new card page ]---
    if(pageData === PAGE_NEW_CARD){

        $('#input-card').css('background-color', '#ffffff')
        $('#input_card_next_num').click(function () {

            let card_field_01 = $('#card_number');
            let bgColor = '#f0f0f0';

            $.ajax({
                url: '/cards/ajax/last_card_number',
                type: 'GET',
                success: function (res) {
                    $('#card_number').val((parseInt(res) + 1).toString().padStart(5, '0'));
                    $('#field_card_id').val('');
                    $('#field_price').val('');
                    $('#field_category').val('');
                },
                error: function (err, status) {
                    console.log(err.value + ' > ' + status.value)
                }
            });
        });

        // ---------------------------------------------------------------------------------[ click on TR tableCardBody ]---
        $('#tableCardBody').on('click', 'tr', function () {
            const tds = $(this).find('td');
            // console.log(tds);
            $('#field_card_id').val($(tds[0]).text())
            $('#card_number').val($(tds[1]).text().trim());
            $('#field_category').val($(tds[2]).text().trim());

            $('#cardTotalCurrentQty').val($(tds[5]).text().trim());
            $('#card_PS_CurrentQty').val($(tds[3]).text().trim());
            $('#card_BTS_CurrentQty').val($(tds[4]).text().trim());

            $('#field_price').val($(tds[6]).text().trim());
            $('#card_id').val($(tds[0]).text().trim());
        });

    }//end if(pageData === 'new-card-page')

}); //end document.ready

function clearSearchFields(){
    document.querySelectorAll('.searchField').forEach(fld =>{
        fld.value = "";
    });
    document.location.reload();
}//end function

document.addEventListener("DOMContentLoaded", () => {
    //TODO console.log('document.addEventListener("DOMContentLoaded":225'); - find page and put <body data-page="....">
    document.querySelectorAll(".btn_del_denomination").forEach(button => {

        button.addEventListener("click", async function () {

            const id = this.dataset.id;
            // console.log("this.dataset.id=" + this.dataset.id);
            const response = await fetch("/denomination/delete/" + id, {method: "DELETE"});
            if (response.ok) {
                this.closest("tr").remove();
            } else {
                const msg = await response.text();
                alert(msg);
            }
        });
    });
});

//TODO clickSelect(e)
// function clickSelect(e) {
//     e.addEventListener("click", function (event) {
//         let nomenclatureSelectList  =  $('#select-nomenclature-list')
//         nomenclatureSelectList.empty();
//
//         let row = event.target.closest("tr");
//         if (!row) return;
//         const denom_id = Number(row.dataset.id);
//
//         $.ajax({
//             url: '/nomenclatures_list',
//             type: 'GET',
//             dataType: 'json',
//             data: {idDenom: denom_id},
//             success: function (response) {
//                 console.log(response);
//                 response.forEach(function (nomenclature) {
//                     nomenclatureSelectList.append(`<option value="${nomenclature.id}">${nomenclature.title}</option>`)
//                 });
//
//             },
//             error: function (xhr, status, error) {
//                 console.log("Error:", error);
//             }
//         });
//     });
// }// end function

function findAllByName(e) {
    let selectDenominations = $('#demonSelectList');
    let tbodyDemonList = $('#denominationList');
    let htmlRow='';
    // console.log(e.value);
    $.ajax({
        url: '/denomination-list',
        type: 'GET',
        data: {denomTitle: e.value},
        dataType: 'json',

        success: function (response) {

            // console.log(response);
            tbodyDemonList.empty();

            response[0].forEach(function (item){
                htmlRow += `<tr data-id="${item.denominationId}"> 
                                <td> ${item.denominationName} </td> 
                                <td> ${item.nomenclatures} </td> 
                                <td> ${item.objectName} </td> 
                            </tr>`;
            });
            tbodyDemonList.append(htmlRow);
        },
        error: function (xhr, status, error) {
            console.log("Error:", error);
        }

    });

}//end function


function addPropertyToTempTable() {
    let tBodyTemp = document.getElementById('body-temp')
}

//TODO
function appendNewRow(myObject, newRow) {

}

/**
 * @param {Number} element button object.
 * @returns {Number} The total of the two numbers.
 */
function executeDepAction(element) {
    if (window.confirm("Підтвердити операцію?")) {
        $.ajax({
            url: "/settings/dep/exec-cmd",
            type: "GET",
            data: {cmdType: element.name, iddep: element.id},
            success: function (response) {
                location.reload();
            },
            error: function (xhr, status, error) {
                console.error("Error:", error);
            }
        });
    }
}

function checkIfDepExist(element) {
    // console.log(element.value);
    if (element.value.length > 1) {
        $.ajax({
            url: "/settings/deps/dep-exists",
            type: "GET",
            data: {depTitle: element.value.toUpperCase()},
            success: function (response) {
                // console.log(response)
                element.style.backgroundColor = "#ffcccc";
                $('#submit_new_dep').prop('disabled', true);
            },
            error: function (xhr, status, error) {
                element.style.backgroundColor = "#ccffcc";
                $('#submit_new_dep').prop('disabled', false);
                console.error("Error:", error);
            }
        });
    } else {
        element.style.backgroundColor = "";
    }
}// end func

function checkDocNumber(element) {
    buttonObj = document.getElementById('inputDocSubmit');

    let numberStr = element.value;

    if (numberStr.length > 0){
        id_doc_type = document.getElementById('id_doc_type');
        //doc_title = document.getElementById('field_doc_title');

        $.ajax({

            url: "/doc/checkDocNumberAndDocType",
            type: "GET",
            data: {id_doc_type: id_doc_type.value, doc_number: element.value},

            success: function (response) {
                if (response === true) {
                    element.style.backgroundColor = "red"
                    buttonObj.disabled = true;
                } else {
                    element.style.backgroundColor = "#e6ffee"
                    buttonObj.disabled = false;
                }
            },
            error: function (xhr, status, error) { console.error("Error: " + error) }
        });

    } else {
        console.log('element:', element.value);
        element.style.backgroundColor = "white";
        buttonObj.disabled = true;
    }
}// end function

function checkDocNumberAndDocTypeSelected(element) {
    docType = document.getElementById('selectDocType')
    inputDocSubmit = document.getElementById('inputDocSubmit')
    docNumber = document.getElementById('doc_num')

    if (docType.value <= 0) {
        docType.style.backgroundColor = '#ffe6e6';
        inputDocSubmit.disabled = true;
    } else {
        docType.style.backgroundColor = '#ffffff';
        inputDocSubmit.disabled = false;
    }

}//end function


$(document).on('click', '.add-unit', function () {
    let quantity = $('#field_qty_needed_' + this.dataset.cardId).val();
    if(quantity<0) {return}
    let price = this.dataset.cardPrice;
    let atcUnitID = 0;
    let cardID = this.dataset.cardId;
    let cardNumber = this.dataset.cardNumber;
    let cardPrice = this.closest('tr').cells[6].textContent;
    let denomTitle = $('#denominationList001 option:selected').text()
    let nID = this.dataset.nomenclatureId;
    let nTitle = this.dataset.nomenclatureTitle;

    fetch('/atc/units/add-single-unit',{
        method: 'POST',
        headers:{'Content-Type': 'application/json'},
        body: JSON.stringify({
            category: this.closest('tr').cells[7].textContent ,
            quantity: quantity,
            price: price,
            atcID: $('#field_atc_id').val(),
            denominationID: $('#field_denom_id').val(),
            nomenclatureID: nID,
            cardID: cardID
        })
    })
        .then(response => response.text())
        .then(data => { console.log('data:' + data); atcUnitID = data })
        .catch(
            error => {
                console.log("------------------------------------------------------------------\n");
                console.log(error);
            }
        );


    var tempTableBody002 = $('#tempTableBody001');
    var $row = $('<tr>')
        .append( $('<td style="height: 30px; text-align: center;">'))
        .append( $('<td>').text( denomTitle ))
        .append( $('<td>').text( nTitle ))
        .append( $('<td>').text(quantity))
        .append( $('<td>').text(cardPrice))
        .append( $('<td style="text-align: center">').text("2"))
        .append( $('<td style="text-align: center">').append(
                $('<a>').text(String(cardNumber).padStart(5,0) ).attr('href','/cards/' + cardNumber + '/details').addClass('link-opacity-50-hover')
            )
        )
        .append( $('<td>').append(
            $('<button>').text('X').addClass('btn btn-outline-danger btn-sm m-1').on('click', function () {
                fetch('/atc/units/delete/' + atcUnitID, {method: 'DELETE'})
                        .then(res => {
                            if(!res.ok){ throw new Error('delete field')}
                            return res.ok

                        })
                        .then(data => {console.log(data)} )
                        .catch(err => {console.log(err)} )
                $(this).closest('tr').remove();

            })
        ));

    tempTableBody002.prepend($row);

    tempTableBody002.find('tr').each(function(index) {
        $(this).find('td:first').text(index + 1);
    });
}); //end function

// /**
//  * @param element
//  * @param targetTbody
//  * @description onclick="addUnitsToTempTable(this, ${tempTableBody001})"
//  * */
// function addUnitsToTempTable(element, targetTbody){
//     //let tempTableBody = $('#tempTableBody');
//     console.log('targetTbody:' + targetTbody);
//     tempHTML +=`<tr>
//                     <td> ${targetTbody.find('tr').length + 1} </td>
//                     <td> ${element.dataset.card-id} </td>
//                 </tr>`;
//     console.log('tempHTML:' + tempHTML);
//     targetTbody.insertAdjacentHTML('afterbegin',tempHTML);
// }

/**
 * @description ajax function
 */
function addGoodToTempTable() {

    let tableBody = document.getElementById('tempTableBody');
    // let btPlus = $('#field_qty_bts_needed_plus').val().trim();
    // let btMinus = $('#field_qty_bts_needed_minus').val().trim();
    // let psPlus = $('#field_qty_ps_needed_plus').val().trim();
    // let psMinus = $('#field_qty_ps_needed_minus').val().trim();

    let quantity_plus = $('#field_quantity_plus').val().trim();
    let quantity_minus = $('#field_quantity_minus').val().trim();
    let nomenclatureID =document.getElementById('field_nonencl_id').value;

    const invoiceMov = {
        idDemonination: document.getElementById('field_denom_id').value,
        idNomenclature: (nomenclatureID === '' ? null : nomenclatureID),
        idCard: $('#field_card_id').val(),
        quantity: quantity_plus === '' ? -Number(quantity_minus) : Number(quantity_plus),
        price: $('#field_price').val() === '' ? 0 : $('#field_price').val(),
        cardNumber: $('#card_number').val(),
        category: $('#field_category').val()
    }

    invoiceMov.invoice = {
        id: $('#field_invoice_id').text().trim()
    }

    console.log('invoiceMov: -------------------------\n' + JSON.stringify(invoiceMov, null, 2));
    let rows = null;

    $.ajax({
        url: '/invmov/ajax/save_single_movement',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(invoiceMov),

        success: function (response) {
            let row = tableBody.insertRow(0);
            let cell0 = row.insertCell(0);
            cell0.innerText = tableBody.rows.length;
            cell0.style.textAlign = 'center';
            let cell1 = row.insertCell(1);
            cell1.innerText = document.getElementById('span-denom-title').innerText;
            let cell2 = row.insertCell(2);
            cell2.innerText = document.getElementById('span-nomenc-title').innerText;
            let cell3 = row.insertCell(3);
            cell3.innerText = invoiceMov.quantity;
            cell3.style.textAlign = 'center';
            let cell4 = row.insertCell(4);
            cell4.innerText = document.getElementById('field_price').value;
            cell4.style.textAlign = 'center';
            let cell5 = row.insertCell(5);
            cell5.innerText = document.getElementById('field_category').value;
            cell5.style.textAlign = 'center';
            let cell6 = row.insertCell(6);
            cell6.innerText = document.getElementById('card_number').value;
            cell6.style.textAlign = 'center';
            let cell7 = row.insertCell(7);
            cell7.style.textAlign = 'center';
            let btn = document.createElement('input');
            btn.type = 'button';
            btn.value = 'X';
            btn.style.backgroundColor = '#ffe6e6';
            cell7.appendChild(btn);
            btn.onclick = function () {
                row.remove();
                //TODO remove movement
            };

            rows = document.querySelectorAll('#tempTableBody tr');

            document.getElementById('totalSumQty').textContent = '' + countTotalsForTempTable(rows, 3);
            document.getElementById('totalSumPrice').textContent = countTotalsForTempTable(rows, 4).toLocaleString('ua-UA', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            }).replace(/\u00A0/g, ' ');
        },
        error: function (err, status) {
            console.log(err + ':' + status);

        }
    });

}//end function

/**
 * @param{data} rowsList - from tableBody
 * @param{number} cellNumber - column index
 * @return{number} total - sum for column index cellNumber
 */
function countTotalsForTempTable(rowsList, cellNumber) {

    let total = 0;
    rowsList.forEach(row => {
        const tot = parseFloat(row.cells[cellNumber].textContent) || 0;
        total += tot;
    });
    return total.toFixed(2);
}//end function

function getRespPersone(element, target, dataArray) {

    // console.log(element.value + " target:" + target + " dataArray.lenght:" + dataArray.length);

    let dep = dataArray.find(item =>{
        if(element.value === item.idDep + "_" + item.idSubDep){
            target.value = item.responsiblePersone != null ? item.responsiblePersone : "-";
        }
    });
//---------------------------------------------------------------------------------------------------------
//     for (let i = 0; i < dataArray.length; i++) {
//         console.log(dataArray[i]['responsiblePersone']);
//         if (dataArray[i]['idDep'] + "_" + dataArray[i]['idSubDep'] === element.value) {
//
//
//             target.value = dataArray[i]['responsiblePersone'];
//             return;
//         }
//     }
}//end function

function changeDepPosition(element) {
    // console.log(element.id + ' > ' + element.value);
    $.ajax({
        url: "/settings/sub-deps/change_dep_position",
        type: 'POST',
        data: {iddep: element.id, cmd_type: element.value},
        success: function (response) {
        },
        error: function (xhr, status, error) {
            console.error("Error: " + error)
        }
    });
}//end function

function registerRespPersone(element) {
    $.ajax({
        url: "/settings/registerRespPersone",
        type: 'GET',
        data: {idSubDep: element.id, respPersone: element.value},

        success: function (response) {
            element.style.backgroundColor = '#0dff00';
            // console.log(response);
        },
        error: function (xhr, status, error) {
            console.error("Error: " + error)
            element.style.backgroundColor = '#ee9494';
        }
    });

}//end function

function objectMilitaryAjaxCRUD(e) {
    $.ajax({
        url: '/settings/objectMilitaryCRUD',
        type: 'GET',
        data: {idObject: e.name, cmdType: e.id},
        success: function (response) {
            location.reload();
        },
        error: function (xhr, status, err) {
            console.error("Error: " + err)
        }

    });
}

/**
 *
 * @param btn_element
 * @returns {Promise<void>}
 * @description fetch function whitch deletes pdf document of my document
 */
async function deletePDFofDocument(btn_element){
    if (!confirm("Видалити цей документ?")) {
        return;
    }

    let res = await fetch('/uploads/ajax/' + btn_element.value, {method: 'DELETE'});
    if (res.ok){
        let row = btn_element.closest('tr');
        row.cells[1].innerText = "";
        row.cells[2].innerText = "";
        row.cells[3].innerText = "";
        row.cells[4].innerText = "";
    } else { alert('Помилка видалення!') }

}

async function deleteItem(btn_element) {
    if (!confirm("Видалити цей документ?")) {
        return;
    }
    const myUrl = btn_element.dataset.url;
    const redirUrl = btn_element.dataset.redirUrl;

    let res = await fetch(myUrl, {method: 'DELETE'});
    if (res.ok){
        btn_element.closest('tr').remove();

        if(redirUrl !== "") {
            location.href = redirUrl;
        }
    } else { alert('Помилка видалення!') }
}

function deleteAPPUnitAndUpdateTable(btn) {
    fetch('/aatu/ajax/delete/' + btn.dataset.rowId , {method: 'DELETE'})
        .then(response =>{
            if(response.ok) {
                btn.closest('tr').remove();
            }
            else { alert('помилка видалення')}
        });
}

function deleteAPPUnit(id) {
    fetch('/aatu/ajax/delete/' + id, {method: 'DELETE'})
        .then(response =>{
            if(response.ok) location.reload();
            else { alert('помилка видалення')}
        });
}

function deleteATCUnit(id) {
    fetch('/atc/units/delete/' + id, {method: 'DELETE'}).then(response =>{
       if(response.ok) location.reload();
       else { alert('помилка видалення') }
    });
}



function selectSklad(element, target, target_value) {
    // const target = element.parentElement.querySelector('select');
    target.value = target_value;
    // target.trigger('change');
    target.dispatchEvent(new Event('change'));
}

// ---------------------------------------------------------------------------------------------[ div modal popup ]-----
function showModalCommonDivNewForm(element) {
    element.disabled = true;
    let modal = document.getElementById('modal-div-01');
    let span = document.getElementsByClassName('close-div')[0];

    modal.style.display = 'block';
    span.onclick = function() {modal.style.display = 'none'; location.reload(); element.disabled = false}

    window.onclick = function(event) {
        if (event.target === modal) {modal.style.display = 'none'; location.reload();  element.disabled = false}
    }
}//end function

// ---------------------------------------------------------------------------------------------[ div modal popup ]-----
async function showModalDiv(btn) {
    btn.disabled = true;
    let modal = document.getElementById('myModal');
    let span = document.getElementsByClassName('close-div')[0];

    const res = await fetch(btn.dataset.url, {method:"POST"});
    if (res.ok) {

        addListToTempTableApp(await res.json(), $('#tempTableBodyAPP'))

        modal.style.display = 'block';
        span.onclick = function () {
            modal.style.display = 'none';
            location.reload()
        }
    }
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
            location.reload()
        }
    }

    btn.disabled = false;
}//end function


// ------------------------------------------------------------------------------------[ div modal popup card edit]-----
function openEditFromForCard(element) {
    console.log(element.dataset.id);
    tempURL = element.dataset.url;
    docID = element.dataset.id;

    $('#docID').text(docID);

    const modalElement = document.getElementById('editAtcModal');
    const modal = bootstrap.Modal.getOrCreateInstance(modalElement);
    modal.show();
}//end function



function closeEditModal() {
    document.getElementById('editModal')
        .classList.remove('show');
}

/**
 *
 * @param e - input text tag
 */

// TODO
function findDenomNomenclatureCard(e) {
    let tableDenominations = document.getElementById('denominationTable');
    let tableBody = document.getElementById('table-denomination-body-app');
    let rowsCount = tableDenominations.getElementsByTagName('tr').length - 1;

    const rows = Array.from(tableBody.getElementsByTagName('tr'));

    $.ajax({
        url: '/denom-and-nomenclature-list?denomTitle=' + e.value,
        type: 'GET',
        dataType: 'json',
        success: function (myDenoms) {
            if (rowsCount > 1) { rows.forEach(row => row.remove()); }

            const grouped = {};

            myDenoms.forEach(item =>{

                const denom_key = item[1];

                if(!grouped[denom_key]){
                    grouped[denom_key] = {
                        id: item[0], title: item[1], om: item[19], nomenclatures:[]
                    };
                }
                if (item[9] != null) grouped[denom_key].nomenclatures.push({ id: item[9], name: item[10] });

            });

            let html="";
            for (const denom in grouped) {
                const group = grouped[denom];
                //${group.id}
                html += `<tr> <td> <strong>${group.title}</strong></td> <td>${group.om}</td> <td hidden>${group.title}:-:0:${group.id}</td> </tr>`;

                group.nomenclatures.forEach(nom => { html += `<tr> 
                                                            <td colspan="2" style="padding-left: 25px">${nom.name}</td> 
                                                            <td hidden>${group.title}:${nom.name}:${nom.id}:${group.id}</td> </tr>`; });
            }

            tableBody.innerHTML = html;
        },
        error: function (xhr, status, error) {
            console.error('error update table', error)
        }
    });
}//end function

//--------------------------------------------------------------------------------------------------[ document.addEventListener("DOMContentLoaded ]
/**
 * TODO document.addEventListener("DOMContentLoaded")
 */
document.addEventListener("DOMContentLoaded", () => {
    const selectedNomenclatureID = document.getElementById("select-nomenclature-list");
    const tbodyDenomination = document.getElementById("denominationList");
    const tbody = document.getElementById("table-denomination-body-app");
    const btnDeleteValue = document.getElementById("");


    document.querySelectorAll(".deleteUnitFromDocument").forEach(btn_delete => {
        btn_delete.addEventListener('click', async function (event) {

            if(!confirm("Видалити позицію?")) return;
                console.log('delete:' + this.dataset.movId + " > " + this.dataset.docType);
                await fetch(this.dataset.url, {method: 'DELETE'}).then( (response) =>{
                    if (!response.ok) {
                        throw new Error(`http error, status = ${response.status}`);
                    }
                    this.closest('tr').remove();
                });
        })
    });

    document.querySelectorAll(".flatpickr-datetime").forEach(input => {
            if (input._flatpickr) { return; }

            flatpickr(input, {
                enableTime: true,
                time_24hr: true,
                dateFormat: "d.m.Y H:i"
            });
    });

    if(selectedNomenclatureID){
        selectedNomenclatureID.addEventListener('click', function (event) {
           $('#field_nomenclature_id').val(selectedNomenclatureID.value);
           $('#td_nomenclature_selected').text(selectedNomenclatureID.options[selectedNomenclatureID.selectedIndex].text);
        });
    }

    if(tbodyDenomination){
        tbodyDenomination.addEventListener('click', function (event) {
            let nomenclatureSelectList  =  $('#select-nomenclature-list')
            nomenclatureSelectList.empty();

            let row = event.target.closest("tr");
            if (!row) return;
            const denom_id = Number(row.dataset.id);
            $('#td_om_selected').text(row.cells[2].textContent);
            $('#td_denomination_selected').text(row.cells[0].textContent);
            $('#td_nomenclature_selected').text('');

            $.ajax({
                url: '/nomenclatures_list',
                type: 'GET',
                dataType: 'json',
                data: {idDenom: denom_id},
                success: function (response) {
                    $('#field_denomination_id').val(denom_id);
                    $('#field_nomenclature_id').val('');
                    response.forEach(function (nomenclature) {
                        nomenclatureSelectList.append(`<option value="${nomenclature.id}">${nomenclature.title}</option>`)
                    });
                },
                error: function (xhr, status, error) {
                    console.log("Error:", error);
                }
            });
        });
    }

    if(tbody) {
        let tempStr = "";
        tbody.addEventListener("click", function (event) {
            $('#tableCardBody').empty();
            const row = event.target.closest("tr");
            if (!row) return;
            tempStr = row.cells[row.cells.length - 1].innerText;
            document.getElementById("span-denom-title").innerText = tempStr.split(":")[0];
            document.getElementById("span-nomenc-title").innerText = tempStr.split(":").length > 1 ? tempStr.split(":")[1] : "";
            document.getElementById("field_denomination_id").value = tempStr.split(":")[3]
            document.getElementById("field_nomenclature_id").value = tempStr.split(":")[2]
            // console.log("Clicked:", row.cells[1].textContent.trim());

            //------------------------------------[ populate table cards ]-----
            $.ajax({
                url: '/cards/ajax/list',
                type: 'GET',
                dataType: 'json',
                data: {
                    denom_id: document.getElementById("field_denomination_id").value,
                    nomencl_id: document.getElementById("field_nomenclature_id").value
                },

                success: function (response) {
                    $.each(response, function (index, card) {
                        var $row = $('<tr data-id="' + card.id + '">')
                            .append($('<td>').text(card.id).hide())
                            .append($('<td>').text(String(card.cardNumber).padStart(5, '0')))
                            .append($('<td>').text(card.category))
                            .append($('<td>').text(card.qtyPS))
                            .append($('<td>').text(card.qtyBTS))
                            .append($('<td>').text(card.qtyPS + card.qtyBTS))
                            .append(
                                $('<td>').text(
                                                    Number(card.price).toLocaleString('uk-UA',{minimumFractionDigits:2, maximumFractionDigits:2})
                                )
                            )
                            .append(
                                $('<td>')
                                    .append(`
                                            <td>
                                                <div class="input-group input-group-sm">
                                                    <input id="field_quantity_needed" type="number" class="form-control text-center" max="${card.qtyPS + card.qtyBTS}" value="1"><button type="button" class="btn btn-secondary" 
                                                            data-max-value="${card.qtyPS + card.qtyBTS}"
                                                            data-card-id = "${card.id}"
                                                            data-category = "${card.category}"
                                                            data-price = "${card.price}"
                                                            data-card-number = "${card.cardNumber}"
                                                            onclick="addGoodsToTempTableAPP(this, this.previousSibling)"> > </button>
                                                </div>
                                            </td>
                                    `)
                            );
                        $('#tableCardBody').append($row);
                    });
                },
                error: function (xhr, status, err) {
                    console.log(err.value);
                }
            });
        });
    }
}); // document.addEventListener("DOMContentLoaded")

function addGoodsToTempTableAPP(btn, fld) {

    if (fld.value === '') {
        fld.style.backgroundColor = '#ffe6e6';
        return;
    }

    const quantity = Number(fld.value);
    const maxQty = Number(btn.dataset.maxValue);
    if (quantity <= 0){ //wrong quantity
        fld.style.backgroundColor = '#ffe6e6';
        return;
    }
    fld.style.backgroundColor = 'white';
    const new_unit = {
        idDenomination: $('#field_denomination_id').val(),
        denominationName: $('#span-denom-title').text(),
        idNomenclature: $('#field_nomenclature_id').val() === '0' ? null : $('#field_nomenclature_id').val(),
        nomenclatureName: $('#span-nomenc-title').text() === '' ? null : $('#span-nomenc-title').text(),
        category: btn.dataset.category,
        quantity: Number(quantity),
        price: Number(btn.dataset.price),
        idApp: $('#field_app_id').data('id'),
        cardId: btn.dataset.cardId,
        cardNumber: btn.dataset.cardNumber
    }

    $.ajax({
        url: '/akt-accept-transfer/ajax/addUnitToApp',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(new_unit),
        success: function (response) {
            //addRowToTempTable(new_unit);
            addSimpleUnitToTable($('#tempTableBodyAPP'), new_unit, true);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(xhr + "\n" + textStatus + "\n" + errorThrown)
        }
    });

}//end function

/**
 *
 * @param tbodyObject - table body passed as jquery object
 * @param unit_mov - json object
 * @param countRowNumber countRowNumber = false - boolean, if true the first TD will have a row number like 1,2,3...
 * @return {void}
 */
function addSimpleUnitToTable(tbodyObject, unit_mov, countRowNumber = false){

    tbodyObject.prepend(`<tr>
                        <td style="height: 20px"></td>
                        <td style="text-align: left"> ${unit_mov.denominationName}</td>
                        <td style="text-align: ${unit_mov.idNomenclature === null ? 'center' : 'left'} "> ${unit_mov.idNomenclature === null ? '-' : unit_mov.nomenclatureName} </td>
                        <td class="quantityCol" > ${unit_mov.quantity} </td>
                        <td class="priceCol"> ${unit_mov.price} </td>
                        <td> ${unit_mov.category} </td>
                        <td> <a href="/cards/${unit_mov.cardId}/details" > ${unit_mov.cardNumber} </a> </td>
                        <td> <button data-url="" 
                                data-row-id="${unit_mov.idAppUnit}" 
                                class="btn btn-sm btn-outline-danger" 
                                onclick="deleteAPPUnitAndUpdateTable(this)"> <i class="bi bi-trash3"></i> </button> </td> 
                    </tr>`);

    if (countRowNumber) renumberRows(tbodyObject);
}//end function

function renumberRows(tbodyObject) {
    tbodyObject.find('tr').each(function(index) {
        $(this).find('td:first').text(index + 1);
    });
}

function addListToTempTableApp(unitsList, tbl) {
    unitsList.forEach(unit =>{ addSimpleUnitToTable(tbl, unit, true) });
}//end function

function addRowToTempTable(unit_mov){
    let totalQuantity = 0;
    let totalSum = 0;
    addSimpleUnitToTable($('#tempTableBodyAPP'), unit_mov, true)

    const tempTableRows = document.querySelectorAll('#tempTableBodyAPP tr');
    tempTableRows.forEach(row =>{
        totalQuantity += Number(row.querySelector('.quantityCol')?.textContent) || 0;
        totalSum += Number(row.querySelector('.priceCol')?.textContent) || 0
    });
    document.getElementById('totalSumQty').textContent = totalQuantity;
    document.getElementById('totalSumPrice').innerHTML = totalSum.toLocaleString('uk-UA',{minimumFractionDigits:2, maximumFractionDigits:2}) + ' &#8372;';
}//end function

function getDocsLastNumber(element) {
    const fieldDocNumber  = $('#doc_num');
    if(!fieldDocNumber.length) {
        alert('Button object without text field!');
        return;
    }
    $.ajax({
        url: '/docs/get-last-number',
        type: 'GET',
        data:{doc_type: Number($('#id_doc_type').val())},
        success: function (res) {
            $('#field_new_document_number').val(res);
            fieldDocNumber.val(res);
            fieldDocNumber.css({backgroundColor: '#e6ffee'}); //.style.backgroundColor = "#e6ffee";
        },
        error: function (err){
            console.log(err);
        }
    });
}//end function

async function loadFilePDF(element) {
    let form = document.getElementById("documentForm01");
    let formData = new FormData(form);
    let pdfURL = element.dataset.url;

    let response = await fetch(pdfURL, { method: "POST", body: formData });
    if(response.ok){

        let pdfID = await response.text();

        console.log('pdfID:>' + pdfID);
        $('#span-msg-panel').text('pdf loaded');
        $('#span-msg-panel').css('color', 'green')

        const row = element.closest('tr');
        const cells = row.querySelectorAll('td');

        cells[0].innerText='file name';
        cells[1].innerHTML=`<button class="btn btn-sm btn-outline-secondary" value="${pdfID}" onclick="">перегляд</button>`;
        cells[2].innerHTML=`<button class="btn btn-sm btn-outline-secondary" value="${pdfID}" onclick="">завантажити</button>`;
        cells[3].innerHTML=`<button class="btn btn-sm btn-outline-danger" value="${pdfID}" onclick="deletePDFofDocument(this)">видалити</button>`;
    }
}//end function

function addRowToTable(element){
    const tempTBody = $('#tbody-temp-for-docs');
    tempTBody.append(`
        <tr>
            <td>${tempTBody.find('tr').length + 1}</td>
            <td>3</td>
            <td>5</td>
            <td>6</td>
            <td>7</td>
            <td class="text-center"> 
                <form>
                    <input type="hidden" th:field="2">
                    <input type="hidden" th:field="2">
                    <input type="hidden" th:field="28">
                    <input type="hidden" name="first_pdf" value="false">
                    <input type="file" name="file-name01" accept="application/pdf" id="file-name01" aria-label="виберіть файл">
                    <button class="btn btn-sm btn-outline-secondary" type="button" data-url="/uploads/ajax/uploadPDF" onclick="loadFilePDF(this)">завантажити</button>
                </form>
            </td>
        </tr>
    `);
}//end function

async function openPdfModal(button) {
    button.disabled = true;
    const docId = button.dataset.docId;
    const docType = button.dataset.docType;
    const firstPdf = button.dataset.firstPdf;
    const firstPdfId = firstPdf === 'true' ? '&firstPdfId=' + button.dataset.firstPdfId : '';


    const response = await fetch(`/uploads/getFragmentPdfForm?doc-id=` + docId + `&documentType=` + docType + `&firstPdf=` + firstPdf + firstPdfId);

    if (!response.ok) {
        console.error('Failed to load PDF form');
        return;
    }

    tempHTML = await response.text();
    document.getElementById('pdfModalContent').innerHTML = tempHTML;

    const modalElement = document.getElementById('pdfModal');
    const modal = bootstrap.Modal.getOrCreateInstance(modalElement);
    button.disabled = false;
    modal.show();

}//end function