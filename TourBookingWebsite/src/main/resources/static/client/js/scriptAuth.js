document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("loginForm")?.addEventListener('submit', function (event) {
        event.preventDefault();
        const username = document.getElementById("username").value
        const password = document.getElementById("password").value
        const errorMessageEle = document.getElementById("errorMessage");
        if (!username || !password) {
            errorMessageEle.classList.remove("d-none");
            errorMessageEle.innerText = "Vui lòng nhập đầy đủ thông tin";
            return false;
        }

        fetch("/login", {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                username,
                password
            }),
            // credentials: 'include',
        })
            .then(async (response) => {
                // console.log(response)
                if (response.ok) {
                    window.location.href = "/login-success"
                }
                    return response.json();
            })
            .then((data) => {
                if(data.status === "error") {
                    errorMessageEle.classList.remove("d-none");
                    errorMessageEle.innerText = data.message;
                }
            })
    });

    document.getElementById("birthday")?.setAttribute("max", new Date().toISOString().split("T")[0])
    const registerForm = document.getElementById("registerForm")
    const updateForm = document.getElementById("updateForm")
    registerForm?.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent actual form submission

        const formData = {
            username: document.getElementById("username").value,
            email: document.getElementById("email").value,
            password: document.getElementById("password").value,
            fullName: document.getElementById("full-name").value,
            phoneNumber: document.getElementById("phone").value,
            address: document.getElementById("address").value,
            birthday: document.getElementById("birthday").value,
            sex: document.getElementById("sex").value
        };

        let isFormValid = Array.from(registerForm.elements).every(validateField);
        isFormValid = validatePasswordMatch() && isFormValid;
        if (isFormValid) {
            // Submit the form or perform your desired action
            fetch("/api/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            }).then((respone) => {
                return respone.json();
            }).then((data) => {
                if (data.status === "error") {
                    data.errors.forEach(error => {
                        const input = document.getElementById(error?.field)
                        input.classList.add("is-invalid");
                        input.classList.remove("is-valid");
                        document.querySelector(`#${error?.field} ~ .invalid-feedback`).innerText = error?.message
                    })
                    return;
                } else {
                    alert("Đăng ký thành công!")
                    window.location.href = "account-login"
                }
            }).catch((error) => {
            })
        }

        registerForm.classList.add("was-validated")
    });

    updateForm?.addEventListener('change', function (event) {
        Array.from(updateForm.elements).every(validateField);
    });
    updateForm?.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent actual form submission
            // updateForm.classList.add("was-validated")

        const formData = {
            password: document.getElementById("password").value,
            fullName: document.getElementById("full-name").value,
            phoneNumber: document.getElementById("phone").value,
            address: document.getElementById("address").value,
            birthday: document.getElementById("birthday").value,
            sex: document.getElementById("sex").value
        };

        let isFormValid = Array.from(updateForm.elements).every(validateField);
        isFormValid = validatePasswordMatch() && isFormValid;
        if (isFormValid) {
            // Submit the form or perform your desired action
            fetch("/api/auth/update", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            }).then((respone) => {
                return respone.json();
            }).then((data) => {
                if (data.status === "error") {
                    data.errors.forEach(error => {
                        const input = document.getElementById(error?.field)
                        input.classList.add("is-invalid");
                        input.classList.remove("is-valid");
                        document.querySelector(`#${error?.field} ~ .invalid-feedback`).innerText = error?.message
                    })
                    return;
                } else {
                    alert("Cập nhật thành công!")
                }
            }).catch((error) => {
            })
        }


    });

    const validateField = (input) => {
        if (input.type === "password" && input.id === "confirmPassword") {
            return validatePasswordMatch();
        }
        if (input.checkValidity()) {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true;
        } else {
            input.classList.add("is-invalid");
            input.classList.remove("is-valid");
            return false;
        }
    }

    const validatePasswordMatch = () => {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput?.value;
        if (password === "" || confirmPassword === "") return
        if (password && confirmPassword && password === confirmPassword) {
            confirmPasswordInput.classList.add("is-valid");
            confirmPasswordInput.classList.remove("is-invalid");
            return true;
        } else {
            confirmPasswordInput.classList.add("is-invalid");
            confirmPasswordInput.classList.remove("is-valid");
            return false;
        }
    }

    const usernameInput = document.getElementById("username");
    const emailInput = document.getElementById("email");
    const fullNameInput = document.getElementById("full-name");
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("cf-password");
    confirmPasswordInput?.addEventListener("input", validatePasswordMatch);
    passwordInput?.addEventListener("input", validatePasswordMatch);

    registerForm?.addEventListener("input", (event) => {
        validateField(event.target)
        validatePasswordMatch();
        if (usernameInput?.value.length > 255) {
            document.querySelector("#username ~ .invalid-feedback").innerText = "Tên đăng nhập không được vượt quá 255 ký tự"
        } else {
            document.querySelector("#username ~ .invalid-feedback").innerText = "Vui lòng nhập tên đăng nhập"
        }
        if (emailInput?.value.length > 255) {
            document.querySelector("#email ~ .invalid-feedback").innerText = "Email không được vượt quá 255 ký tự"
        } else {
            document.querySelector("#email ~ .invalid-feedback").innerText = "Vui lòng nhập email hợp lệ."
        }

        if (fullNameInput?.value.length > 255) {
            document.querySelector("#full-name ~ .invalid-feedback").innerText = "Họ tên không được vượt quá 255 ký tự"
        } else {
            document.querySelector("#full-name ~ .invalid-feedback").innerText = "Vui lòng nhập họ tên."
        }
    });

    updateForm?.addEventListener("input", (event) => {
        validateField(event.target)
        validatePasswordMatch();
        if (usernameInput?.value.length > 255) {
            document.querySelector("#username ~ .invalid-feedback").innerText = "Tên đăng nhập không được vượt quá 255 ký tự"
        } else {
            document.querySelector("#username ~ .invalid-feedback").innerText = "Vui lòng nhập tên đăng nhập"
        }
        if (emailInput?.value.length > 255) {
            document.querySelector("#email ~ .invalid-feedback").innerText = "Email không được vượt quá 255 ký tự"
        } else {
            document.querySelector("#email ~ .invalid-feedback").innerText = "Vui lòng nhập email hợp lệ."
        }

        if (fullNameInput?.value.length > 255) {
            document.querySelector("#full-name ~ .invalid-feedback").innerText = "Họ tên không được vượt quá 255 ký tự"
        } else {
            document.querySelector("#full-name ~ .invalid-feedback").innerText = "Vui lòng nhập họ tên."
        }
    });


});
