// Lấy tất cả các mục trong navbar
const navItems = document.querySelectorAll('#navbarSupportedContent ul li a');

// Lắng nghe sự kiện click cho mỗi mục
navItems.forEach(item => {
    item.addEventListener('click', function() {
        // Loại bỏ class 'active' khỏi tất cả các mục
        navItems.forEach(link => {
            link.classList.remove('active');
        });

        // Thêm class 'active' vào mục vừa được click
        this.classList.add('active');
    });
});
document.addEventListener('DOMContentLoaded', function() {
    const dropdown = document.getElementById('userDropdown');
    const dropdownMenu = dropdown.querySelector('.dropdown-menu');
    const dropdownToggle = dropdown.querySelector('.dropdown-toggle');
    
    // Khi nhấn vào dropdown, hiển thị menu trong 5 giây
    dropdownToggle.addEventListener('click', function(e) {
        // Ngăn không cho trang tự động di chuyển khi nhấn
        e.preventDefault();
        
        // Hiển thị dropdown menu
        dropdownMenu.style.display = 'block';

        // Đặt thời gian tự động ẩn sau 5 giây
        setTimeout(function() {
            dropdownMenu.style.display = 'none';
        }, 5000); // 5000ms = 5 giây
    });

    // Đảm bảo menu ẩn khi hover khỏi dropdown
    dropdown.addEventListener('mouseleave', function() {
        dropdownMenu.style.display = 'none';
    });
});
