$(document).ready(function () {
    $('#loginForm').on('submit', function (event) {
        event.preventDefault(); // Ngừng hành động mặc định của form

        const username = $('#username').val();
        const password = $('#password').val();
        const errorMessageEle = $('#errorMessage');
        const classListErrorMessage = errorMessageEle[0].classList;
        if (!username || !password) {
            console.log("hih")
            classListErrorMessage.remove("d-none");
            errorMessageEle.text('Vui lòng nhập đầy đủ thông tin').show();
            return false;
        }
        // Gửi dữ liệu form qua AJAX
        $.ajax({
            url: '/login', // Đường dẫn đến controller xử lý đăng nhập
            type: 'POST',
            data: {
                username: username,
                password: password
            },
            success: function (response, status, xhr) {
                window.location.href = "/login-success"
            },
            error: function (xhr, status, error) {
                classListErrorMessage.remove("d-none");
                errorMessageEle.text("Tài khoản hoặc mật khẩu không chính xác").show();
            }
        });
    });


    const forms = $('.needs-validation');

    // Duyệt qua các biểu mẫu và ngăn chặn gửi nếu không hợp lệ
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }else{

            }
                console.log($(".form-control:invalid"))


            form.classList.add('was-validated');
        }, false);
    });

});
