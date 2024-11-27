// Hàm hiển thị thông báo
function showAlert(type, message) {
    const alertBox = document.createElement('div');
    alertBox.className = `alert alert-${type}`;
    alertBox.innerText = message;
    document.body.appendChild(alertBox);
    
    // Sử dụng JavaScript để làm mờ thông báo
    alertBox.style.display = 'block';
    setTimeout(() => {
        alertBox.style.opacity = 0; // Làm mờ thông báo
        setTimeout(() => {
            alertBox.remove(); // Xóa thông báo sau khi đã mờ
        }, 600);
    }, 3000);
}

// Hàm tải danh sách booking
async function fetchBookings() {
    try {
        const response = await fetch('/admin/booking/list-booking');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const jsonData = await response.json(); // Parse dữ liệu JSON
        console.log(jsonData.bookings, jsonData.size); // Truy cập thuộc tính trong JSON
        renderBookings(jsonData.bookings); // Hiển thị danh sách bookings (nếu cần)
    } catch (error) {
        console.error('Có lỗi xảy ra khi thực hiện fetch:', error);
    }
}


// Hàm hiển thị danh sách bookings trong bảng
function renderBookings(bookings) {
    const tableContent = document.getElementById("table-content");
    tableContent.innerHTML = ""; // Xóa nội dung cũ

    bookings.forEach(booking => {
        const row = `
            <tr>
                <td>${booking.bookingId}</td>
                <td>${booking.customer.customerId}</td>
                <td>${booking.tourTime.tourTimeId}</td>
                <td>${booking.totalPrice.toLocaleString()} VNĐ</td>
                <td>${booking.adultCount}</td>
                <td>${booking.childCount}</td>
				<td>
				    ${booking.status == 2 ? "Đã thanh toán" : (booking.status == 1 ? "Chưa thanh toán" : "Cancel Booking")}
				</td>
                <td>${new Date(booking.time).toLocaleString()}</td>
                <td>${booking.totalDiscount ? booking.totalDiscount.toLocaleString() + " VNĐ" : "Không có"}</td>
                <td>${booking.paymentMethod || "Chưa cập nhật"}</td>
                <td>
                    <div class="text-center">
                        <button data-id="${booking.bookingId}" class="detail-btn btn btn-info btn-sm mb-2" onclick="viewDetails(this)">Chi tiết booking</button>
                        <div class="d-flex justify-content-center">
							<button data-id="${booking.bookingId}" class="edit-btn btn btn-warning btn-sm me-1" onclick="showEditModal(event)">Sửa</button>
                            <button data-id="${booking.bookingId}" class="delete-btn btn btn-danger btn-sm ms-1" onclick="deleteBooking(this)" ${booking.status === 0 ? 'disabled' :'' }>Xóa</button>
                        </div>
                    </div>
                </td>
            </tr>
        `;
        tableContent.innerHTML += row; // Thêm hàng vào bảng
    });
}


// Hàm lấy tên khách hàng
function fetchCustomerName(customerID, isEdit = false) { 
    const customerNameInput = isEdit ? document.getElementById('editCustomerName') : document.getElementById('newCustomerName');
    
    // Nếu có mã khách hàng, tiến hành gửi yêu cầu API
    if (customerID) {
        fetch(`/admin/customers/${customerID}`)
            .then(response => response.ok ? response.json() : Promise.reject('Khách hàng không tồn tại'))
            .then(data => customerNameInput.value = data.customerName || 'Khách hàng không tồn tại')
            .catch(error => customerNameInput.value = 'Lỗi trong việc lấy thông tin khách hàng');
			} else {
        customerNameInput.value = '';  // Nếu không có mã khách hàng, để trống
    }
}

// Hàm lấy tên tour
function fetchTourTimeName(tourTimeID, isEdit = false) {
    const tourNameInput = isEdit ? document.getElementById('editTourName') : document.getElementById('newTourName');
    if (tourTimeID) {
        fetch(`/admin/tour-times/${tourTimeID}`)
            .then(response => response.ok ? response.json() : Promise.reject('Tour không tồn tại'))
            .then(data => tourNameInput.value = data.timeName || 'Tour không tồn tại')
            .catch(error => tourNameInput.value = 'Lỗi trong việc lấy thông tin tour');
    } else {
        tourNameInput.value = '';
    }
}
function clearFilters() {
    // Xóa giá trị trong ô tìm kiếm
    document.getElementById('searchInput').value = '';

    // Đặt lại giá trị mặc định cho các bộ lọc select
    document.getElementById('statusFilter').value = '';
    document.getElementById('priceFilter').value = '';

    // Đặt lại giá trị cho các trường date
    document.getElementById('startDate').value = '';
    document.getElementById('endDate').value = '';

    // Gọi lại hàm lọc để cập nhật danh sách booking (nếu cần)
    filterBookings();
}
function filterBookings() {
    // Lấy giá trị từ các phần tử trên form
    const searchInput = document.getElementById('searchInput').value.toLowerCase(); // Lấy giá trị từ input tìm kiếm
    const statusFilter = document.getElementById('statusFilter').value; // Lấy giá trị từ select trạng thái
    const priceFilter = document.getElementById('priceFilter').value; // Lấy giá trị từ select khoảng giá
    const startDate = document.getElementById('startDate').value; // Lấy giá trị từ input ngày bắt đầu
    const endDate = document.getElementById('endDate').value; // Lấy giá trị từ input ngày kết thúc
    const tableRows = document.querySelectorAll('#table-content tr'); // Các hàng trong bảng booking
    
    // In các giá trị ra console để kiểm tra
    console.log("Search Input:", searchInput);
    console.log("Status Filter:", statusFilter);
    console.log("Price Filter:", priceFilter);
    console.log("Start Date:", startDate);
    console.log("End Date:", endDate);
    
    tableRows.forEach(row => {
        // Lấy giá trị từ các cột trong mỗi hàng
        const bookingId = row.cells[0].innerText.toLowerCase(); // Cột Booking ID
        const customerId = row.cells[1].innerText.toLowerCase(); // Cột Customer ID
        const tourTimeId = row.cells[2].innerText.toLowerCase(); // Cột Tour Time ID
        const status = row.cells[6].innerText.toLowerCase().trim(); // Cột Trạng thái
        const totalPrice = parseFloat(row.cells[3].innerText.replace(',', '')); // Cột tổng giá, loại bỏ dấu phân cách nghìn
        const bookingDate = new Date(row.cells[7].innerText); // Cột ngày booking
        
        // In giá trị của các cột trong mỗi hàng ra console để kiểm tra
        console.log("Booking ID:", bookingId);
        console.log("Status:", status);
        console.log("Total Price:", totalPrice);
        console.log("Booking Date:", bookingDate);
        
        // Kiểm tra điều kiện tìm kiếm trong BookingId, CustomerId và TourTimeId
        const matchesSearch = 
            bookingId.includes(searchInput) || 
            customerId.includes(searchInput) || 
            tourTimeId.includes(searchInput);

        // Kiểm tra trạng thái
        const matchesStatus = 
            statusFilter === '' || // Nếu trạng thái là Tất cả
            (statusFilter === '1' && status === 'đã thanh toán') ||
            (statusFilter === '0' && status === 'chưa thanh toán');

        // Kiểm tra khoảng giá
        const matchesPrice = 
            priceFilter === '' || // Nếu khoảng giá là Tất cả
            (priceFilter === '100000' && totalPrice < 100000) ||
            (priceFilter === '100000-500000' && totalPrice >= 100000 && totalPrice <= 500000) ||
            (priceFilter === '500000-1000000' && totalPrice > 500000 && totalPrice <= 1000000) ||
            (priceFilter === '1000000-5000000' && totalPrice > 1000000 && totalPrice <= 5000000) ||
            (priceFilter === '5000000+' && totalPrice > 5000000);

        // Kiểm tra ngày booking trong khoảng thời gian
        const matchesDate = 
            (startDate === '' || bookingDate >= new Date(startDate)) && 
            (endDate === '' || bookingDate <= new Date(endDate));

        // Nếu các điều kiện đều khớp, hiển thị hàng, nếu không thì ẩn hàng
        if (matchesSearch && matchesStatus && matchesPrice && matchesDate) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}



function calculatedTotalPrice() {
    const adultCount = parseInt(document.getElementById('newAdultCount').value);
    const childCount = parseInt(document.getElementById('newChildCount').value);
    const totalPriceElement = document.getElementById('newTotalPrice');
    const tourTimeId = document.getElementById('newTourTimeID').value;
	console.log(adultCount,childCount);
    // Kiểm tra số lượng người lớn và trẻ em
    if (isNaN(adultCount) || isNaN(childCount) || adultCount < 0 || childCount < 0) {
        showAlert('danger', 'Số lượng khách không hợp lệ!');
        return;
    }

    // Gửi yêu cầu đến API để lấy thông tin của TourTime
    fetch(`/admin/tour-times/${tourTimeId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
		console.log(data);
        // Kiểm tra dữ liệu trả về có chứa giá cho người lớn và trẻ em không
        if (data && data.priceAdult && data.priceChild) {
            const adultPrice = data.priceAdult; // Giá cho người lớn
            const childPrice = data.priceChild; // Giá cho trẻ em

            // Tính tổng giá
            const totalPrice = (adultCount * adultPrice) + (childCount * childPrice);

            // Hiển thị tổng giá
            totalPriceElement.value = totalPrice;

            // Cập nhật thông báo thành công
            showAlert('success', 'Tính giá thành công!');
        } else {
            // Nếu không có giá, hiển thị lỗi
            showAlert('danger', 'Không thể lấy giá cho tour này!');
        }
    })
    .catch(error => {
        console.error('Lỗi khi lấy thông tin TourTime:', error);
        showAlert('danger', 'Lỗi khi tính tổng giá!');
    });
}

function showAddModal() {
    // Làm sạch các trường trong form
    document.getElementById('addBookingForm').reset(); // Giả sử form của bạn có id là 'accountForm'
    
    // Hiện modal
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

function addBooking() {
    // Lấy giá trị từ các trường trong form
    const customerId = document.getElementById('newCustomerID').value;
    const customerName = document.getElementById('newCustomerName').value;
    const tourTimeId = document.getElementById('newTourTimeID').value;
    const tourName = document.getElementById('newTourName').value;
    const totalPrice = document.getElementById('newTotalPrice').value;
    const adultCount = document.getElementById('newAdultCount').value;
    const childCount = document.getElementById('newChildCount').value;
    const status = document.getElementById('newStatus').value;
    //const bookingDate = document.getElementById('newBookingDate').value;

    // Kiểm tra các trường bắt buộc
    if (!customerId || !tourTimeId || !totalPrice || !adultCount || !childCount || !status ) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    // Xác nhận trước khi thêm
    const confirmation = confirm("Bạn có chắc chắn muốn thêm booking này không?");
    if (!confirmation) {
        return;
    }

    // Dữ liệu booking
    const bookingData = {
        customerId,
        tourTimeId,
        totalPrice,
        adultCount,
        childCount,
        status,
        /*bookingDate*/
    };

    // Gửi dữ liệu booking lên server
    fetch('/admin/bookings/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bookingData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                showAlert('danger', errorData.message || 'Thêm booking thất bại!');
            });
        }
        showAlert('success', 'Thêm booking thành công!');
        fetchBookings();
        document.getElementById('addBookingForm').reset();  // Reset form sau khi thêm
        const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
        addModal.hide();
    })
    .catch(error => {
        console.error('Lỗi khi thêm booking:', error);
    });
}
function showEditModal(event) {
    // Lấy thông tin của booking được chọn từ các button "Sửa"
    const bookingId = event.target.getAttribute('data-id');
    
    // Gửi yêu cầu để lấy thông tin của booking
    fetch(`/admin/bookings/${bookingId}`)
        .then(response => response.json())
        .then(data => {
            if (data) {
                console.log(data);
                // Điền các giá trị của booking vào trong modal
                document.getElementById('bookingId').value = data.booking.bookingId;
                document.getElementById('editCustomerID').value = data.booking.customer.customerId;
                fetchCustomerName(data.booking.customer.customerId, true);
                document.getElementById('editTourTimeID').value = data.booking.tourTime.tourTimeId;
                fetchTourTimeName(data.booking.tourTime.tourTimeId, true);
                document.getElementById('editAdultCount').value = data.booking.adultCount;
                document.getElementById('editChildCount').value = data.booking.childCount;
                document.getElementById('editTotalPrice').value = data.booking.totalPrice;
                document.getElementById('editVoucherPrice').value = data.booking.voucherPrice || 0;  // Thêm voucherPrice vào đây
                document.getElementById('editStatus').value = data.booking.status;
                document.getElementById('editBookingDate').value = new Date(data.booking.time).toISOString().slice(0, 16); // Định dạng datetime-local
                document.getElementById('editTotalDiscount').value = data.booking.totalDiscount || 0;
                document.getElementById('editPaymentMethod').value = data.booking.paymentMethod || '';

                // Mở modal chỉnh sửa booking
                const editModal = new bootstrap.Modal(document.getElementById('editModal'));
                editModal.show();
            } else {
                showAlert('danger', 'Không tìm thấy thông tin booking!');
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin booking:', error);
            showAlert('danger', 'Lỗi khi tải thông tin booking!');
        });
}

function editBooking() {
    // Lấy giá trị chỉ từ các trường có thể chỉnh sửa
    const status = document.getElementById('editStatus').value;
    const paymentMethod = document.getElementById('editPaymentMethod').value;

    // Kiểm tra các trường bắt buộc
    if (!status || !paymentMethod) {
        showAlert('danger', 'Vui lòng chọn đầy đủ thông tin!');
        return;
    }

    // Xác nhận trước khi chỉnh sửa
    const confirmation = confirm("Bạn có chắc chắn muốn chỉnh sửa booking này không?");
    if (!confirmation) {
        return;
    }

    // Dữ liệu booking cần cập nhật
    const bookingData = {
        status,
        paymentMethod
    };
    console.log(bookingData);

    // Gửi dữ liệu booking đã chỉnh sửa lên server
    fetch(`/admin/bookings/edit/${document.getElementById('bookingId').value}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bookingData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                showAlert('danger', errorData.message || 'Chỉnh sửa booking thất bại!');
            });
        }
        showAlert('success', 'Chỉnh sửa booking thành công!');
        fetchBookings(); // Cập nhật danh sách booking
        const editModal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
        editModal.hide();
    })
    .catch(error => {
        console.error('Lỗi khi chỉnh sửa booking:', error);
        showAlert('danger', 'Lỗi khi chỉnh sửa booking!');
    });
}



// Hàm xem chi tiết booking
function viewDetails(button) {
    const bookingId = button.getAttribute('data-id');
    // Logic để hiển thị chi tiết booking
    alert("Xem chi tiết booking ID: " + bookingId);
}

// Hàm xóa booking
function deleteBooking(button) {
    const bookingId = button.getAttribute('data-id'); // Lấy booking ID từ thuộc tính dữ liệu
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    // Thiết lập hành động cho nút xác nhận xóa
    document.getElementById('confirmDeleteButton').onclick = function() {
		fetch(`/admin/bookings/delete-booking/${bookingId}`, { method: 'DELETE' })
		    .then(response => {
		        if (response.ok) {
		            showAlert('success', 'Xóa booking thành công!');
		            fetchBookings();
		            confirmDeleteModal.hide();
		        } else {
		            return response.text().then(text => {
		                showAlert('danger', text || 'Xóa booking thất bại!');
		            });
		        }
		    })
		    .catch(error => {
		        console.error('Error:', error);
		        showAlert('danger', 'Có lỗi xảy ra!');
		    });

    };

    confirmDeleteModal.show(); // Hiện modal xác nhận
}

// Khi DOM đã sẵn sàng, tải danh sách bookings
document.addEventListener("DOMContentLoaded", fetchBookings);

//----------------------------------------------------------------Detail----------------------------------------------------
function viewDetails(button) {
    // Lấy bookingId từ button
    const bookingId = button.getAttribute('data-id');

    // Gửi yêu cầu đến API
    fetch(`/admin/booking-details/by-booking/${bookingId}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                // Xóa nội dung cũ trong bảng
                const tableBody = document.getElementById('bookingDetailTableBody');
                tableBody.innerHTML = '';

                // Thêm các hàng mới vào bảng
                data.forEach((detail, index) => {
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${detail.bookingDetailId}</td>
                        <td>${detail.detail}</td>
                        <td>${detail.price.toLocaleString()} VNĐ</td>
                        <td>${detail.status === 1 ? 'Đã xác nhận' : 'Chưa xác nhận'}</td>
                        <td>${detail.booking.bookingId}</td>
                        <td>${detail.customer.customerId}</td>
                    `;

                    tableBody.appendChild(row);
                });

                // Hiển thị modal
                const bookingDetailModal = new bootstrap.Modal(document.getElementById('bookingDetailModal'));
                bookingDetailModal.show();
            } else {
                alert('Không tìm thấy chi tiết booking!');
            }
        })
        .catch(error => {
            console.error('Lỗi khi tải chi tiết booking:', error);
            alert('Đã xảy ra lỗi khi tải dữ liệu.');
        });
}





