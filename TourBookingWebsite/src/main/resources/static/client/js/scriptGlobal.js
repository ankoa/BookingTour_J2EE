function showToast(message) {
    const toastContainer = document.createElement('div');
    toastContainer.className = 'position-fixed end-0 top-0 p-3';
    toastContainer.style.zIndex = '11';

    const toastHTML = `
        <div class="toast" role="alert" aria-live="assertive" aria-atomic="true" 
             data-bs-config='{"delay": 3000, "animation": true}' 
             data-bs-title="Custom Title" 
             data-bs-autohide="true">
            <div class="toast-header">
                <strong class="me-auto">Thông báo</strong>
                <small class="text-muted">Just now</small>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                ${message}
            </div>
        </div>`;
        // Gắn nội dung vào container
    toastContainer.innerHTML = toastHTML;

    // Thêm Toast vào body
    document.body.appendChild(toastContainer);

    // Kích hoạt Toast
    const toastElement = toastContainer.querySelector('.toast');
    const toast = new bootstrap.Toast(toastElement);
    toast.show();

    // Loại bỏ Toast sau khi ẩn
    toastElement.addEventListener('hidden.bs.toast', () => {
        toastContainer.remove();
    });
}
