const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('login-container');
const registrationForm = document.getElementById('registrationForm');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

if(window.location.pathname.endsWith('register')){
    signUpButton.click();
}

const username = document.getElementById('username');
const email = document.getElementById('email');
const password = document.getElementById('password');
const confirmPassword = document.getElementById('confirmPassword');

registrationForm.addEventListener('submit', (e) => {
    if (username.value.trim().length <= 0){
        document.getElementById('username-error').style.display = 'block';
        e.preventDefault();
    }
    if (email.value.trim().length <= 0 || !email.value.includes('@')){
        document.getElementById('email-error').style.display = 'block';
        e.preventDefault();
    }
    if (password.value !== confirmPassword.value){
        document.getElementById('password-match-error').style.display = 'block';
        e.preventDefault();
    }
    if (password.value.trim().length <= 0){
        document.getElementById('password-error').style.display = 'block';
        e.preventDefault();
    }
    if (confirmPassword.value.trim().length <= 0){
        document.getElementById('confirmPassword-error').style.display = 'block';
        e.preventDefault();
    }
    if (password.value.trim().length <= 6){
        document.getElementById('password-length-error').style.display = 'block';
        e.preventDefault();
    }
});
username.addEventListener('input', () => {
    if (username.value.trim().length <= 0) {
        document.getElementById('username-error').style.display = 'block';
    } else {
        document.getElementById('username-error').style.display = 'none';
    }
});

email.addEventListener('input', () => {
    if (email.value.trim().length <= 0 || !email.value.includes('@')){
        document.getElementById('email-error').style.display = 'block';
    } else {
        document.getElementById('email-error').style.display = 'none';
    }
});

password.addEventListener('input', () => {
    if (password.value !== confirmPassword.value){
        document.getElementById('password-match-error').style.display = 'block';
    } else {
        document.getElementById('password-match-error').style.display = 'none';
    }

    if (password.value.trim().length <= 0){
        document.getElementById('password-error').style.display = 'block';
    } else {
        document.getElementById('password-error').style.display = 'none';
    }

    if (password.value.trim().length <= 6){
        document.getElementById('password-length-error').style.display = 'block';
    }
    else {
        document.getElementById('password-length-error').style.display = 'none';
    }
});

confirmPassword.addEventListener('input', () => {
    if (confirmPassword.value.trim().length <= 0){
        document.getElementById('confirmPassword-error').style.display = 'block';
    } else {
        document.getElementById('confirmPassword-error').style.display = 'none';
    }
});