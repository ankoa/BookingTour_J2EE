document.addEventListener("DOMContentLoaded", function() {
    loadTourTimes(); // Gọi hàm khi trang được tải xong
});

function loadTourTimes() {
    // Gửi yêu cầu lấy dữ liệu từ API
    fetch('/admin/tour-times/list')
        .then(response => response.json())
        .then(data => {
            const tourTimeTableBody = document.getElementById('tourTimeTableBodyMain');
            tourTimeTableBody.innerHTML = ''; // Xóa dữ liệu cũ trong bảng
            // Duyệt qua từng tour time trong dữ liệu trả về và thêm vào bảng
            data.forEach(tourTime => {
                const row = document.createElement('tr');
                
                row.innerHTML = `
                    <td>${tourTime.tourTimeId}</td>
                    <td>${tourTime.timeName}</td>
                    <td>${tourTime.tour ? tourTime.tour.tourId : ''}</td>  <!-- Hiển thị TourID -->
                    <td>${new Date(tourTime.departureTime).toLocaleString()}</td>  <!-- Định dạng thời gian -->
                    <td>${tourTime.priceAdult}</td>
                    <td>${tourTime.priceChild}</td>
                    <td>${tourTime.status === 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" data-id="${tourTime.tourTimeId}" onclick="editTourTime(this)">Chỉnh sửa</button>
                        <button class="btn btn-danger btn-sm" data-id="${tourTime.tourTimeId}" onclick="deleteTourTime(this)">Xóa</button>
                    </td>
                `;

                tourTimeTableBody.appendChild(row); // Thêm dòng vào bảng
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}
function editTourTime(button) {
    const tourTimeId = button.getAttribute('data-id');
    
    // Gọi API để lấy dữ liệu tourTime
    fetch(`/admin/gettourtimesbyidadmin/${tourTimeId}`)
        .then(response => response.json())
        .then(tourTime => {
            if (tourTime) {
                // Mở modal
                const modal = new bootstrap.Modal(document.getElementById('editTourTimeModal'));
                modal.show();

                // Cập nhật các trường trong modal với dữ liệu của tourTime
                document.getElementById('editTimeName').value = tourTime.timeName;
                document.getElementById('editTourId').value = tourTime.tourId;
                document.getElementById('editTourName').value =tourTime.tour.tourName;
                document.getElementById('editDepartureTime').value = tourTime.departureTime;
                document.getElementById('editReturnTime').value = tourTime.returnTime;
                document.getElementById('editPriceAdult').value = tourTime.priceAdult;
                document.getElementById('editPriceChild').value = tourTime.priceChild;
                document.getElementById('editQuantity').value = tourTime.quantity;
                document.getElementById('editStatus').value = tourTime.status;
                document.getElementById('editNote').value = tourTime.note;
            } else {
                alert('Không tìm thấy thông tin tourTime!');
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy dữ liệu tourTime:', error);
            alert('Có lỗi xảy ra khi tải thông tin tourTime.');
        });
}

// Hàm gọi API để lấy danh sách Tour ID và điền vào dropdown
function fetchTourIds() {
    fetch('/admin/tours/list-tour') 
        .then(response => response.json()) 
        .then(data => {
            const tourIdSelect = document.getElementById("tourIdSelect");
            tourIdSelect.innerHTML = '<option value="">Chọn Tour ID</option>';
            data.forEach(tour => {
                const option = document.createElement("option");
                option.value = tour.tourId; // Gán giá trị của option là ID tour
                option.textContent = tour.tourId+"-"+tour.tourName; // Gán tên tour vào text của option
                tourIdSelect.appendChild(option); // Thêm option vào dropdown
            });
        })
        .catch(error => {
            console.error('Có lỗi khi gọi API:', error);
        });
}

// Gọi hàm fetchTourIds khi trang được tải
window.onload = function() {
    fetchTourIds(); // Tải danh sách Tour ID
}
