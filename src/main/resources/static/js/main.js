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

    document.addEventListener("click", function (event) {
        const button = event.target.closest(".reply-toggle-button");

        if (!button) {
            return;
        }

        const formId = button.getAttribute("data-reply-form-id");
        const form = document.getElementById(formId);

        if (!form) {
            console.log("Reply form not found:", formId);
            return;
        }

        form.classList.toggle("open");
    });
});