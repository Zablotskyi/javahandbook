document.addEventListener("DOMContentLoaded", function () {
    const menuButtons = document.querySelectorAll(".menu-title");

    menuButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            const submenu = button.nextElementSibling;

            if (submenu) {
                submenu.classList.toggle("open");
            }
        });
    });
});