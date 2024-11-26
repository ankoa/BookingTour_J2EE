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

// Hàm tải danh sách transport
function loadTransports() {
    fetch('/admin/transports/listTransport')
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = ''; // Xóa nội dung cũ

            data.forEach(transport => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${transport.transportId}</td>
                    <td>${transport.transportName}</td>
                    <td>${transport.transportCode}</td>
                    <td>${transport.departureLocation}</td>
                    <td>${transport.destinationLocation}</td>
                    <td>
                        <span>${transport.status === 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</span>
                    </td>
                    <td class="d-flex justify-content-evenly">
                        <button type="button" class="btn btn-warning btn-sm" data-id="${transport.transportId}" onclick="editTransport(this)">Chỉnh sửa</button>
                        <button type="button" class="btn btn-danger btn-sm" data-id="${transport.transportId}" ${transport.status === 0 ? 'disabled' : ''} onclick="deleteTransport(this)">Xóa</button>
                    </td>
                `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

// Hàm tìm kiếm transport
function filterTransports() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;
    const tableRows = document.querySelectorAll('#table-content tr');

    tableRows.forEach(row => {
        const transportID = row.cells[0].innerText.toLowerCase();
        const transportName = row.cells[1].innerText.toLowerCase();
        const status = row.cells[5].innerText.toLowerCase().trim(); // Cột trạng thái

        const matchesSearch = 
            transportID.includes(searchInput) || 
            transportName.includes(searchInput);

        const matchesStatus = 
            statusFilter === '' ||
            (statusFilter === '1' && status === 'hoạt động') ||
            (statusFilter === '0' && status === 'ngưng hoạt động');

        row.style.display = matchesSearch && matchesStatus ? '' : 'none';
    });
}

// Hàm mở modal thêm transport
function showAddModal() {
    document.getElementById('addForm').reset();
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

// Hàm thêm transport
/*function addTransport() {
    const transportName = document.getElementById('newTransportName').value;
    const transportCode = document.getElementById('newTransportCode').value;
    const departureLocation = document.getElementById('newDepartureLocation').value;
    const destinationLocation = document.getElementById('newDestinationLocation').value;
    const status = document.getElementById('newStatus').value;

    if (!transportName || !transportCode || !departureLocation || !destinationLocation) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    const transportData = {
        transportName,
        transportCode,
        departureLocation,
        destinationLocation,
        status
    };

    fetch('/admin/transports/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(transportData)
    })
    .then(response => {
        if (response.ok) {
            showAlert('success', 'Thêm phương tiện thành công!');
            loadTransports();
            document.getElementById('addForm').reset();
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide();
        } else {
            showAlert('danger', 'Thêm phương tiện thất bại!');
        }
    })
    .catch(error => {
        console.error('Lỗi khi thêm phương tiện:', error);
    });
}*/
// Hàm thêm phương tiện
function addTransport() {
    const transportName = document.getElementById('newTransportName').value;
    const transportCode = document.getElementById('newTransportCode').value;
    const departureLocation = document.getElementById('newDepartureLocation').value;
    const destinationLocation = document.getElementById('newDestinationLocation').value;
    const status = document.getElementById('newStatus').value;

    if (!transportName || !transportCode || !departureLocation || !destinationLocation) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    const transportData = {
        transportName,
        transportCode,
        departureLocation,
        destinationLocation,
        status
    };

    fetch('/admin/transports/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(transportData)
    })
    .then(response => {
        if (response.ok) {
            showAlert('success', 'Thêm phương tiện thành công!');
            loadTransports();
            document.getElementById('addForm').reset();
            const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
            addModal.hide();
        } else {
            showAlert('danger', 'Thêm phương tiện thất bại!');
        }
    })
    .catch(error => {
        console.error('Lỗi khi thêm phương tiện:', error);
    });
}


// Hàm xóa transport
function deleteTransport(button) {
    const transportId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch(`/admin/transports/delete/${transportId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa phương tiện thành công!');
                    loadTransports();
					clearFormSearch();
                } else {
                    showAlert('danger', 'Xóa phương tiện thất bại!');
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

// Hàm chỉnh sửa transport
function editTransport(button) {
    const transportId = button.getAttribute('data-id');
    fetch(`/admin/transports/${transportId}`)
        .then(response => response.json())
        .then(transport => {
            document.getElementById('transportId').value = transport.transportId;
            document.getElementById('transportName').value = transport.transportName;
            document.getElementById('transportCode').value = transport.transportCode;
            document.getElementById('departureLocation').value = transport.departureLocation;
            document.getElementById('destinationLocation').value = transport.destinationLocation;
            document.getElementById('status').value = transport.status;

            const editModal = new bootstrap.Modal(document.getElementById('editModal'));
			console.log(transport);
            editModal.show();
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi lấy thông tin phương tiện!');
        });
}

// Hàm lưu thay đổi thông tin transport
function saveChanges() {
    const transportId = document.getElementById('transportId').value;
    const transportName = document.getElementById('transportName').value;
    const transportCode = document.getElementById('transportCode').value;
    const departureLocation = document.getElementById('departureLocation').value;
    const destinationLocation = document.getElementById('destinationLocation').value;
    const status = document.getElementById('status').value;

    if (!transportName || !transportCode || !departureLocation || !destinationLocation) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }
	const confirmSaveButton = document.getElementById('confirmSaveButton');
	confirmSaveButton.onclick = function() {
    fetch(`/admin/transports/update/${transportId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            transportId,
            transportName,
            transportCode,
            departureLocation,
            destinationLocation,
            status,
        })
    })
    .then(response => {
        if (response.ok) {
            showAlert('success', 'Lưu thay đổi thành công!');
            loadTransports();
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
	$('#confirmSaveModal').modal('hide');
	};
	$('#confirmSaveModal').modal('show');
}

// Hàm đóng modal chỉnh sửa
function closeModal() {
    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
    if (modal) modal.hide();
}
function clearFormSearch() {
	    // Xóa nội dung ô input tìm kiếm
	    document.getElementById('searchInput').value = '';

	    // Đặt lại các ô select về giá trị mặc định
	    document.getElementById('statusFilter').selectedIndex = 0;
	}
