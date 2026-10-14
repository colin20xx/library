import {InitializerPopup} from "./initializer-popup.js";

document.addEventListener("DOMContentLoaded", function () {

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
});