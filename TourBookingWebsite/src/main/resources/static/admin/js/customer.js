/*function showAlert(type, message) {
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
// Load danh sách khách hàng
function loadCustomers() {
    fetch('/admin/customers/listCustomer')
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = '';
            data.forEach(customer => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${customer.customerId}</td>
                    <td>${customer.customerName}</td>
                    <td>${customer.sex === 1 ? 'Nam' : 'Nữ'}</td>
                    <td>${customer.phoneNumber}</td>
                    <td>${customer.address}</td>
                    <td>${customer.birthday}</td>
                    <td>${customer.status === 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</td>
                    <td>${customer.time}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editCustomer(${customer.customerId})">Chỉnh sửa</button>
                        <button class="btn btn-danger btn-sm" ${customer.status === 0 ? 'disabled' : ''} onclick="deleteCustomer(${customer.customerId})">Xóa</button>
                    </td>
                `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => console.error('Error:', error));
}

// Hiển thị modal thêm khách hàng
function showAddModal() {
    document.getElementById('addForm').reset();
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}


// Thêm khách hàng
function addCustomer() {
    const customer = {
        customerName: document.getElementById('addCustomerName').value,
        phoneNumber: document.getElementById('addPhoneNumber').value,
        address: document.getElementById('addAddress').value,
        birthday: document.getElementById('addBirthday').value,
        status: document.getElementById('addStatus').value
    };

    fetch('/admin/customers', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(customer)
    })
        .then(response => {
            if (response.ok) {
                loadCustomers();
                new bootstrap.Modal(document.getElementById('addModal')).hide();
            } else {
                alert('Lỗi khi thêm khách hàng!');
            }
        })
        .catch(error => console.error('Error:', error));
}

// Hiển thị modal chỉnh sửa
function editCustomer(button) {
    // Lấy ID từ thuộc tính data-id của nút
    const id = button.getAttribute('data-id');

    // Kiểm tra ID có tồn tại hay không
    if (!id) {
        console.error('Không tìm thấy ID khách hàng.');
        return;
    }
	console.log('ID khách hàng:', id); // Debug ID

    // Gửi yêu cầu lấy thông tin khách hàng
    fetch(`/admin/customers/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Không tìm thấy khách hàng!');
            return response.json();
        })
        .then(customer => {
            // Điền thông tin khách hàng vào modal
            document.getElementById('customerId').value = customer.customerId;
            document.getElementById('customerName').value = customer.customerName;
            document.getElementById('phoneNumber').value = customer.phoneNumber;
            document.getElementById('address').value = customer.address;
            document.getElementById('birthday').value = customer.birthday;
            document.getElementById('status').value = customer.status;

            // Hiển thị modal chỉnh sửa
            new bootstrap.Modal(document.getElementById('editModal')).show();
        })
        .catch(error => console.error('Error:', error));
}



// Lưu thay đổi
function saveChanges() {
    const id = document.getElementById('customerId').value;
    const customer = {
        customerName: document.getElementById('customerName').value,
        phoneNumber: document.getElementById('phoneNumber').value,
        address: document.getElementById('address').value,
        birthday: document.getElementById('birthday').value,
        status: document.getElementById('status').value
    };

    fetch(`/admin/customers/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(customer)
    })
        .then(response => {
            if (response.ok) {
                loadCustomers();
                new bootstrap.Modal(document.getElementById('editModal')).hide();
            } else {
                alert('Lỗi khi lưu thay đổi!');
            }
        })
        .catch(error => console.error('Error:', error));
}

// Xóa khách hàng (chuyển trạng thái thành ngưng hoạt động)
function deleteCustomer(id) {
    const deleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    document.getElementById('confirmDeleteButton').onclick = function () {
        fetch(`/admin/customers/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    loadCustomers();
                    deleteModal.hide();
                } else {
                    showAlert('danger', 'Lỗi khi xóa khách hàng!');
                }
            })
            .catch(error => console.error('Error:', error));
    };
    deleteModal.show();
}


// Lọc khách hàng theo trạng thái
function filterCustomers() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;

    fetch(`/admin/customers?search=${searchInput}&status=${statusFilter}`)
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = '';
            data.forEach(customer => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${customer.customerId}</td>
                    <td>${customer.customerName}</td>
                    <td>${customer.sex === 1 ? 'Nam' : 'Nữ'}</td>
                    <td>${customer.phoneNumber}</td>
                    <td>${customer.address}</td>
                    <td>${customer.birthday}</td>
                    <td>${customer.status === 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</td>
                    <td>${customer.time}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editCustomer(${customer.customerId})">Chỉnh sửa</button>
                        <button class="btn btn-danger btn-sm" ${customer.status === 0 ? 'disabled' : ''} onclick="deleteCustomer(${customer.customerId})">Xóa</button>
                    </td>
                `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => console.error('Error:', error));
}

// Tải danh sách khách hàng khi trang được load
document.addEventListener('DOMContentLoaded', loadCustomers);
*/
// Hàm hiển thị thông báo
function showAlert(type, message) {
    const alertBox = document.createElement('div');
    alertBox.className = `alert alert-${type}`;
    alertBox.innerText = message;

    alertBox.style.position = 'fixed';
    alertBox.style.top = '20px';
    alertBox.style.left = '50%';
    alertBox.style.transform = 'translateX(-50%)';
    alertBox.style.zIndex = '9999';

    document.body.appendChild(alertBox);

    setTimeout(() => {
        alertBox.style.opacity = '0';
        alertBox.style.transform = 'translateX(-50%) translateY(-10px)';
        setTimeout(() => {
            alertBox.remove();
        }, 600);
    }, 3000);
}

// Hàm tải danh sách khách hàng
function loadCustomers() {
    fetch('/admin/customers/listCustomer')
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = '';

            data.forEach(customer => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${customer.customerId}</td>
                    <td>${customer.customerName}</td>
					<td>${customer.sex === 1 ? 'Nam' : 'Nữ'}</td>
					<td>${customer.phoneNumber}</td>
                    <td>${customer.address}</td>
                    <td>${customer.birthday}</td>
					
                    <td>
                        <span>${customer.status === 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</span>
                    </td>
                    <td class="d-flex justify-content-evenly">
                        <button type="button" class="btn btn-warning btn-sm" data-id="${customer.customerId}" onclick="editCustomer(this)">Chỉnh sửa</button>
                        <button type="button" class="btn btn-danger btn-sm" data-id="${customer.customerId}" ${customer.status === 0 ? 'disabled' : ''} onclick="deleteCustomer(this)">Xóa</button>
                    </td>
                `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

// Hàm tìm kiếm khách hàng
function filterCustomers() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;
    const tableRows = document.querySelectorAll('#table-content tr');

    tableRows.forEach(row => {
        const customerName = row.cells[1].innerText.toLowerCase();
        const sdt = row.cells[2].innerText.toLowerCase();
        const status = row.cells[6].innerText.toLowerCase().trim();

        const matchesSearch =
            customerName.includes(searchInput) || sdt.includes(searchInput);

        const matchesStatus =
            statusFilter === '' ||
            (statusFilter === '1' && status === 'hoạt động') ||
            (statusFilter === '0' && status === 'ngưng hoạt động');

        row.style.display = matchesSearch && matchesStatus ? '' : 'none';
    });
}

// Hàm mở modal thêm khách hàng
function showAddModal() {
    document.getElementById('addForm').reset();
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

// Hàm thêm khách hàng
function addCustomer() {
    const customerName = document.getElementById('addCustomerName').value;
    const sex = document.getElementById('addSex').value;
    const phoneNumber = document.getElementById('addPhoneNumber').value;
    const birthday = document.getElementById('addBirthday').value;
	const address = document.getElementById('addAddress').value;
    const status = document.getElementById('addStatus').value;

    if (!customerName ||  !phoneNumber || !birthday ||!phoneNumber ||!address) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    const customerData = {
        customerName,
        sex,
        phoneNumber,
        birthday,
		address,
        status
    };

    fetch('/admin/customers/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(customerData)
    })
        .then(response => {
            if (response.ok) {
                showAlert('success', 'Thêm khách hàng thành công!');
                loadCustomers();
                const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
                addModal.hide();
            } else {
                showAlert('danger', 'Thêm khách hàng thất bại!');
            }
        })
        .catch(error => {
            console.error('Lỗi khi thêm khách hàng:', error);
        });
}

// Hàm xóa khách hàng
function deleteCustomer(button) {
    const customerId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function () {
        fetch(`/admin/customers/delete/${customerId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa khách hàng thành công!');
                    loadCustomers();
                    clearFormSearch();
                } else {
                    showAlert('danger', 'Xóa khách hàng thất bại!');
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


// Hàm chỉnh sửa khách hàng
function editCustomer(button) {
    const customerId = button.getAttribute('data-id');
    fetch(`/admin/customers/${customerId}`)
        .then(response => response.json())
        .then(customer => {
			console.log("Loaded sex value:", customer.sex)
			console.log("Loaded birthday:", customer.birthday)
            document.getElementById('customerId').value = customer.customerId;
            document.getElementById('customerName').value = customer.customerName;
            document.getElementById('phoneNumber').value = customer.phoneNumber;
            document.getElementById('address').value = customer.address;
            document.getElementById('birthday').value = customer.birthday;
			document.getElementById('sex').value = customer.sex;
            document.getElementById('status').value = customer.status;

            const editModal = new bootstrap.Modal(document.getElementById('editModal'));
            editModal.show();
			
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi lấy thông tin khách hàng!');
        });
}

// Hàm lưu thay đổi thông tin khách hàng
function saveChanges() {
    const customerId = document.getElementById('customerId').value;
    const customerName = document.getElementById('customerName').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const sex = document.getElementById('sex').value;
	const address = document.getElementById('address').value;
    const birthday = document.getElementById('birthday').value;
    const status = document.getElementById('status').value;
	console.log("Submitting sex value:", birthday);
    if (!customerName ||  !phoneNumber || !birthday  ||!address) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }
	console.log("Data to be sent:", {
	    customerId,
	    customerName,
	    sex,  // Kiểm tra giá trị sex ở đây
	    phoneNumber,
	    address,
	    birthday,
	    status
	});
    fetch(`/admin/customers/update/${customerId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            customerId,
            customerName,
            sex,
            phoneNumber,
			address,
            birthday,
            status,
        })
    })
        .then(response => {
            if (response.ok) {
                showAlert('success', 'Lưu thay đổi thành công!');
                loadCustomers();
                const editModal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
                editModal.hide();
				clearFormSearch();
            } else {
                showAlert('danger', 'Lưu thay đổi thất bại!');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi lưu thay đổi!');
        });
}

// Hàm làm mới tìm kiếm
function clearFormSearch() {
    document.getElementById('searchInput').value = '';
    document.getElementById('statusFilter').selectedIndex = 0;
}
