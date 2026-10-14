export function InitializerPopup(){

    document.getElementById("doc_num").addEventListener("input", checkDocNumber);

    const departList = JSON.parse(document.getElementById('popupData').dataset.depsList) ;

    $('#sender_page')
        .select2( { width:'70%', dropdownParent: $("#globalModal") })
        .on('change', function () {
            // document.getElementById("sender_name").value = "rank name surname";
            getRespPersone(this, document.getElementById("sender_name"), departList);
        });

    $('#receiver_page')
        .select2( { width:'70%', dropdownParent: $("#globalModal") })
        .on('change', function () {
            console.log('--------------> search for resp person' + this.value);
            getRespPersone(this, document.getElementById('receiver_name'), departList);
        });

    // document.getElementById("sender_page").addEventListener("change", function () {
    //     console.log('-------------->' + this.value);
    //     getRespPersone(this, sender_name, departList);
    // });

    // document.getElementById("receiver_page").addEventListener("change", function () {
    //     console.log('-------------->' +  this.value);
    //     getRespPersone(this, sender_name, departList);
    // });



    flatpickr("#data_doc", {
        enableTime: true, time_24hr: true, dateFormat: "d.m.Y H:i", defaultDate: new Date(),
        onChange: function (selectedDates, dateStr, instance) {
            document.getElementById('data_doc_oper').value = dateStr;
            let date = new Date(selectedDates[0]);
            date.setDate(date.getDate() + 3);
            let day = String(date.getDate()).padStart(2,'0');
            let month = String(date.getMonth()+1).padStart(2,'0');
            let year = String(date.getFullYear());
            let hour = String(date.getHours()).padStart(2,'0');
            let minutes = String(date.getMinutes()).padStart(2,'0');

            document.getElementById('data_doc_during').value = `${day}.${month}.${year} ${hour}:${minutes}`
        }
    });
}