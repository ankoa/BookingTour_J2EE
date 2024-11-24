// Hàm hiển thị thông báo alert
function showAlert(type, message) {
    const alertBox = document.createElement('div');
    alertBox.className = `alert alert-${type}`;
    alertBox.innerText = message;

    // Đảm bảo vị trí hiển thị hợp lý
    alertBox.style.position = 'fixed';
    alertBox.style.top = '20px'; // Hiển thị ở gần đầu trang
    alertBox.style.left = '50%';
    alertBox.style.transform = 'translateX(-50%)';
    alertBox.style.zIndex = '9999'; // Đảm bảo luôn trên cùng

    document.body.appendChild(alertBox);

    // Hiệu ứng mờ dần
    setTimeout(() => {
        alertBox.style.opacity = '0';
        alertBox.style.transform = 'translateX(-50%) translateY(-10px)'; // Nhẹ nhàng di chuyển lên trên
        setTimeout(() => {
            alertBox.remove();
        }, 600); // Đảm bảo alert đã biến mất hoàn toàn trước khi xóa
    }, 3000);
}

// Hàm tải danh sách discount
function loadDiscounts() {
    fetch('/admin/discounts/listDiscount')
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = ''; // Xóa nội dung cũ

            data.forEach(discount => {
				const startDate = discount.startDate ? discount.startDate : '';
				const endDate = discount.endDate ? discount.endDate : '';
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${discount.discountId}</td>
                    <td>${discount.discountCode}</td>
					<td>${discount.discountValue}</td>
					<td>${startDate}</td>
					<td>${endDate}</td>
                    <td>
                        <span>${discount.status === 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</span>
                    </td>
					<td>${discount.note}</td>
                    <td class="d-flex justify-content-evenly">
                        <button type="button" class="btn btn-warning btn-sm" data-id="${discount.discountId}" onclick="editDiscount(this)">Chỉnh sửa</button>
                        <button type="button" class="btn btn-danger btn-sm" data-id="${discount.discountId}" ${discount.status === 0 ? 'disabled' : ''} onclick="deleteDiscount(this)">Xóa</button>
                    </td>
                `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

// Hàm tìm kiếm discount
function filterDiscounts() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;
    const tableRows = document.querySelectorAll('#table-content tr');

    tableRows.forEach(row => {
        const discountID = row.cells[0].innerText.toLowerCase();
        const discountValue = row.cells[1].innerText.toLowerCase();
        const status = row.cells[5].innerText.toLowerCase().trim(); // Cột trạng thái

        const matchesSearch = 
            discountID.includes(searchInput) || 
            discountValue.includes(searchInput);

        const matchesStatus = 
            statusFilter === '' ||
            (statusFilter === '1' && status === 'hoạt động') ||
            (statusFilter === '0' && status === 'ngưng hoạt động');

        row.style.display = matchesSearch && matchesStatus ? '' : 'none';
    });
}

// Hàm mở modal thêm discount
function showAddModal() {
    document.getElementById('addForm').reset();
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

// Hàm thêm discount
function addDiscount() {
    const discountValue = document.getElementById('newDiscountValue').value;
    const discountCode = document.getElementById('newDiscountCode').value;
    const startDate = document.getElementById('newStartDate').value;
    const endDate = document.getElementById('newEndDate').value;
    const status = document.getElementById('newStatus').value;
	const note = document.getElementById('newNote').value;
    if (!discountValue || !discountCode ) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    const discountData = {
        discountValue,
        discountCode,
        startDate,
        endDate,
        status,
		note,
    };

    fetch('/admin/discounts/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(discountData)
    })
    .then(response => {
        if (response.ok) {
            showAlert('success', 'Thêm discount thành công!');
            loadDiscounts();
            document.getElementById('addForm').reset();
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide();
        } else {
            showAlert('danger', 'Thêm discount thất bại!');
        }
    })
    .catch(error => {
        console.error('Lỗi khi thêm discount:', error);
    });
}

// Hàm xóa discount
function deleteDiscount(button) {
    const discountId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch(`/admin/discounts/delete/${discountId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa discount thành công!');
                    loadDiscounts();
                    clearFormSearch();
                } else {
                    showAlert('danger', 'Xóa discount thất bại!');
                }
                confirmDeleteModal.hide();
            })
            .catch(error => {
                console.error('Error:', error);
                showAlert('danger', 'Có lỗi xảy ra!');
                confirmDeleteModal.hide();
            });
    };
    confirmDeleteModal.show();
}

// Hàm chỉnh sửa discount
function editDiscount(button) {
    const discountId = button.getAttribute('data-id');
    fetch(`/admin/discounts/${discountId}`)
        .then(response => response.json())
        .then(discount => {
            document.getElementById('discountId').value = discount.discountId;
            document.getElementById('discountValue').value = discount.discountValue;
            document.getElementById('discountCode').value = discount.discountCode;
            document.getElementById('startDate').value = discount.startDateAsString || '';
            document.getElementById('endDate').value = discount.endDateAsString || '';
            document.getElementById('status').value = discount.status;
			document.getElementById('note').value = discount.note;
			console.log("Submitting sex value:", discount.note);
            const editModal = new bootstrap.Modal(document.getElementById('editModal'));
            editModal.show();
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi lấy thông tin discount!');
        });
}

// Hàm lưu thay đổi thông tin discount
function saveChanges() {
    const discountId = document.getElementById('discountId').value;
    const discountValue = document.getElementById('discountValue').value;
    const discountCode = document.getElementById('discountCode').value;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const status = document.getElementById('status').value;
	const note = document.getElementById('note').value;
    if (!discountValue || !discountCode) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    fetch(`/admin/discounts/update/${discountId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            discountId,
            discountValue,
            discountCode,
            startDate,
            endDate,
            status,
			note,
        })
    })
    .then(response => {
        if (response.ok) {
            showAlert('success', 'Lưu thay đổi thành công!');
            loadDiscounts();
            const editModal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
            editModal.hide();
        } else {
            showAlert('danger', 'Lưu thay đổi thất bại!');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showAlert('danger', 'Có lỗi xảy ra khi lưu thay đổi!');
    });
}

// Hàm đóng modal chỉnh sửa
function closeModal() {
    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
    if (modal) modal.hide();
}

// Hàm xóa nội dung tìm kiếm
function clearFormSearch() {
    document.getElementById('searchInput').value = '';
    document.getElementById('statusFilter').selectedIndex = 0;
}
