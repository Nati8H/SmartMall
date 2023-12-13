const toggleButton = document.getElementById('header-toggle');
const toggleButtonDiv = document.getElementById('header-toggle-div');
const nav = document.getElementById('nav-bar');
const bodypd = document.getElementById('body-pd');
const smart = document.getElementById('smart');
const mall = document.getElementById('mall');

toggleButton.addEventListener("click", showNavbar);

function showNavbar() {

// Validate that all variables exist
    if (toggleButton && nav && toggleButtonDiv) {
// show navbar
        nav.classList.toggle('show');
// change icon
        toggleButton.classList.toggle('bx-x');

        toggleButtonDiv.classList.toggle('ml-auto');
    }

    if (bodypd){
// add padding to body
        bodypd.classList.toggle('body-pd');
    }

    if (smart && mall){
        smart.classList.toggle('color-white');
        mall.classList.toggle('color-white');
    }
}

/*===== LINK ACTIVE =====*/
const linkColor = document.querySelectorAll('.nav_link')

function colorLink() {
    if (linkColor) {
        linkColor.forEach(l => l.classList.remove('sidebar-active'))
        this.classList.add('sidebar-active')
    }
}

linkColor.forEach(l => l.addEventListener('click', colorLink))

// Your code to run since DOM is loaded and ready
