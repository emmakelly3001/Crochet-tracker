// Function to handle form submission for register
function handleRegister(event) {
    event.preventDefault(); // Prevent the default form submission

    const email = document.getElementById('email').value.trim();
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const errorMessageDiv = document.getElementById("error-message");

    // Basic form validation
    if (!email || !username || !password) {
        errorMessageDiv.innerText = "Please fill in all fields.";
        errorMessageDiv.style.display = "block";
        return;
    }

    // Prepare the data to send in the body of the POST request
    const formData = {
        email: email,
        username: username,
        password: password
    };

    // Send the data to the backend API
    fetch('/api/auth/register', {  // Correct API endpoint
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (response.ok) {
            // If the response is successful, redirect to the login page
            window.location.href = 'login.html';  // Redirect to the login page after successful registration
        } else {
            // If there's an error (e.g., username already taken), show the error message
            return response.text().then(text => {
                errorMessageDiv.innerText = text || "An error occurred during registration.";
                errorMessageDiv.style.display = "block";
            });
        }
    })
    .catch(error => {
        console.error('Error during registration:', error);
        errorMessageDiv.innerText = "An error occurred during registration. Please try again.";
        errorMessageDiv.style.display = "block";
    });
}

// Function to handle error message display on load
function showErrorFromUrlParams() {
    const urlParams = new URLSearchParams(window.location.search);
    const errorMessageDiv = document.getElementById("error-message");

    if (urlParams.has('error')) {
        errorMessageDiv.innerText = "An error occurred. Please check your details and try again.";
        errorMessageDiv.style.display = "block";
    }
}

// Attach event listeners to forms when the document is ready
document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById('register-form');
    if (registerForm) {
        registerForm.addEventListener('submit', handleRegister);
    }

    // Handle error messages if present in URL params
    showErrorFromUrlParams();
});

class RegisterFormHandler {
    constructor(formId, errorMessageDivId) {
        this.form = document.getElementById(formId);
        this.errorMessageDiv = document.getElementById(errorMessageDivId);

        if (this.form) {
            this.form.addEventListener('submit', this.handleRegister.bind(this));
        }
    }

    handleRegister(event) {
        event.preventDefault(); // Prevent the default form submission
        console.log("Register function triggered");

        const email = document.getElementById('email').value.trim();
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();

        // Basic form validation
        if (!email || !username || !password) {
            this.showError("Please fill in all fields.");
            return;
        }

        // Prepare the data to send in the body of the POST request
        const formData = {
            email: email,
            username: username,
            password: password
        };

        console.log("Form data:", formData);

        // Send the data to the backend API
        fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                window.location.href = 'login.html';  // Redirect to the login page after successful registration
            } else {
                return response.text().then(text => {
                    this.showError(text || "An error occurred during registration.");
                });
            }
            }