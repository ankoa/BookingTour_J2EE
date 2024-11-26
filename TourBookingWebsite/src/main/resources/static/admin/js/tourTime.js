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
                    <td>${tourTime.tour ? tourTime.tour.tourId : ''}</td> <!-- Hiển thị TourID -->
                    <td>${new Date(tourTime.departureTime).toLocaleString()}</td> <!-- Định dạng thời gian -->
                    <td>${tourTime.priceAdult}</td>
                    <td>${tourTime.priceChild}</td>
                    <td>${tourTime.status === 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</td>
                    <td>
					<div class="container">
					    <!-- Hàng 1 -->
					    <div class="row mb-2">
					        <div class="col">
					            <button class="btn btn-warning btn-sm w-100" data-id="${tourTime.tourTimeId}" onclick="editTourTime(this)">Chỉnh sửa</button>
					        </div>
					        <div class="col">
					            <button class="btn btn-danger btn-sm w-100" data-id="${tourTime.tourTimeId}" onclick="deleteTourTime(this)">Xóa</button>
					        </div>
					    </div>
					    <!-- Hàng 2 -->
					    <div class="row">
					        <div class="col">
					            <button class="btn btn-primary btn-sm w-100" data-id="${tourTime.tourTimeId}" onclick="applyDiscount(this)">Khuyến mãi</button>
					        </div>
					    </div>
					    <!-- Hàng 3 - Nút Phương tiện chung -->
					    <div class="row mt-2">
					        <div class="col">
					            <button class="btn btn-info btn-sm w-100" data-id="${tourTime.tourTimeId}" onclick="openTransportModal(this)">Phương tiện</button>
					        </div>
					    </div>
					</div>

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
document.getElementById('btnClearTourTime').addEventListener('click', function () {
  // Xóa giá trị của ô tìm kiếm
  document.getElementById('searchInputTourTime').value = '';

  // Reset các bộ lọc (ComboBox)
  document.getElementById('tourIdSelect').value = '';
  document.getElementById('statusSelectTourTime').value = '';
  document.getElementById('priceFilter').value = '';
  
  // Xóa giá trị của ô nhập ngày
  document.getElementById('startDate').value = '';

  // Gọi lại hàm lọc để hiển thị toàn bộ dữ liệu
  loadTourTimes();
});


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
function applyDiscount(button) {
    // Lấy tourTimeId từ data-id của nút nhấn
    const tourTimeId = button.getAttribute('data-id');
    
    // Gán tourTimeId vào trường ẩn tourId trong form
    document.getElementById('tourId').value = tourTimeId;

    // Gọi loadDiscounts để tải danh sách khuyến mãi đã được thêm vào tour
    loadDiscounts(tourTimeId);

    // Mở modal khuyến mãi
    const discountModal = new bootstrap.Modal(document.getElementById('discountModal'));
    discountModal.show();

    // Gọi API để lấy danh sách khuyến mãi và cập nhật vào ComboBox
    fetch('/admin/discounts/listDiscount')
        .then(response => response.json())
        .then(data => {
            const discountSelect = document.getElementById('discountType');
            discountSelect.innerHTML = '<option value="" disabled selected>-- Chọn loại khuyến mãi --</option>'; // Reset options

            // Duyệt qua các khuyến mãi và tạo các options trong dropdown
            data.forEach(discount => {
                const option = document.createElement('option');
                option.value = discount.discountId;
                option.textContent = `${discount.discountCode} - ${discount.discountValue}%`; // Hiển thị mã và giá trị khuyến mãi
                discountSelect.appendChild(option); // Thêm option vào select
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu khuyến mãi:', error);
        });
}


function loadDiscounts(tourTimeId) {
    // Gọi API với tourTimeId để lấy các khuyến mãi đã được thêm vào tour này
    fetch(`/admin/tour-discounts/by-tour-time/${tourTimeId}`)
        .then(response => response.json()) // Chuyển đổi kết quả thành JSON
        .then(data => {
            console.log(data); // Kiểm tra dữ liệu trả về từ API

            const tableBody = document.getElementById('selectedDiscounts');
            tableBody.innerHTML = ''; // Xóa tất cả các dòng trước khi thêm mới

            if (data.length === 0) {
                const row = document.createElement('tr'); // Tạo một dòng mới
                row.innerHTML = `
                    <td colspan="8" class="text-center">Chưa có khuyến mãi</td>
                `;
                tableBody.appendChild(row); // Thêm dòng thông báo vào bảng
            } else {
                // Lặp qua dữ liệu và thêm từng dòng vào bảng
                data.forEach(discountObj => {
                    const discount = discountObj.discount; // Truy cập vào đối tượng discount

                    console.log(discount.discountCode); // Kiểm tra giá trị discountCode

                    const row = document.createElement('tr'); // Tạo một dòng mới

                    // Kiểm tra và xử lý giá trị startDate và endDate
                    const startDate = discount.startDate ? new Date(discount.startDate).toLocaleDateString() : "Chưa có";
                    const endDate = discount.endDate ? new Date(discount.endDate).toLocaleDateString() : "Chưa có";

                    // Tạo các ô cho mỗi cột trong bảng
                    row.innerHTML = `
                        <td>${discount.discountId}</td>
                        <td>${discount.discountCode}</td>
                        <td>${discount.discountValue}</td>
                        <td>${startDate}</td>
                        <td>${endDate}</td>
                        <td>${discount.note || "Chưa có"}</td>
                        <td>${discount.status === 1 ? 'Active' : 'Inactive'}</td>
                        <td>
                          <button class="btn btn-danger" onclick="deleteDiscount(${tourTimeId},${discount.discountId})">Xóa</button>
                        </td>
                    `;

                    // Thêm dòng vào tbody
                    tableBody.appendChild(row);
                });
            }
        })
        .catch(error => {
            console.error('Error fetching discount data:', error);
        });
}

function addDiscount() {
    // Lấy giá trị từ ComboBox (select) và từ trường input ẩn
    const discountType = document.getElementById('discountType').value;
    const tourTimeId = document.getElementById('tourId').value; // Lấy giá trị từ input ẩn với id="tourId"

    // Kiểm tra nếu giá trị discountType hoặc tourTimeId không được chọn
    if (!discountType || !tourTimeId) {
        alert("Vui lòng chọn loại khuyến mãi và thời gian tour.");
        return;
    }

    // Tạo đối tượng dữ liệu để gửi lên API
    const discountData = {
        discountId: discountType, // discountType từ ComboBox
        tourTimeId: tourTimeId // tourTimeId từ input ẩn
    };

    // Gọi API để thêm khuyến mãi
    fetch('/admin/tour-discounts/add', {
        method: 'POST', // Sử dụng POST để gửi dữ liệu
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(discountData) // Chuyển dữ liệu thành JSON
    })
    .then(response => response.json()) // Chuyển đổi kết quả từ API sang JSON
    .then(data => {
        if (data.success) {
			showAlert('success', "Thêm khuyến mãi thành công!");

            // Nếu cần, bạn có thể làm mới danh sách khuyến mãi trong bảng
            loadDiscounts(tourTimeId); // Gọi lại hàm để cập nhật lại bảng khuyến mãi
        } else {
            alert('Thêm khuyến mãi thất bại. Vui lòng thử lại!');
        }
    })
    .catch(error => {
        console.error('Lỗi khi gọi API:', error);
        alert('Có lỗi xảy ra, vui lòng thử lại!');
    });
}





  // Hàm để chỉnh sửa giảm giá
  function editDiscount(discountId) {
    // Logic chỉnh sửa giảm giá
    console.log('Edit discount with ID:', discountId);
  }

  // Hàm để xóa giảm giá
  // Hàm xóa mã giảm giá với xác nhận
  function deleteDiscount(tourTimeId, discountId) {
      // Hiển thị hộp thoại xác nhận trước khi xóa
      const confirmation = confirm("Bạn có chắc chắn muốn xóa mã giảm giá này không?");
      
      if (confirmation) {
          // Gửi yêu cầu DELETE tới API nếu người dùng xác nhận
          fetch(`/admin/tour-discounts/deleteTuorDiscount/${tourTimeId}/${discountId}`, {
              method: 'DELETE',
          })
          .then(response => {
              if (response.ok) {
                  // Xử lý khi xóa thành công
                  showAlert('success', "Xóa mã giảm giá của tour time thành công");
                  loadDiscounts(tourTimeId);  // Tải lại danh sách giảm giá
              } else {
                  showAlert('danger', "Xóa thất bại");
              }
          })
          .catch(error => {
              console.error('Error:', error);
              showAlert('danger', "Đã xảy ra lỗi trong quá trình xóa mã giảm giá.");
          });
      } else {
          // Nếu người dùng chọn Cancel, không làm gì cả
          showAlert('info', "Hành động xóa đã bị hủy.");
      }
  }
  // Mở modal thêm phương tiện
  function openTransportModal(button) {
    // Lấy tourTimeId từ button và đặt vào trường ẩn trong form
    const tourTimeId = button.getAttribute('data-id');
    document.getElementById('tourTimeId').value = tourTimeId;

    // Mở modal
    const transportModal = new bootstrap.Modal(document.getElementById('transportModal'));
    transportModal.show();
	loadTransports();
  }

  

  // Xóa phương tiện
  function removeTransport(transportDetailId) {
    if (confirm('Bạn có chắc chắn muốn xóa phương tiện này?')) {
      // Gửi yêu cầu xóa qua API
      fetch(`/path/to/your/api/endpoint/${transportDetailId}`, {
        method: 'DELETE',
      })
      .then(response => response.json())
      .then(data => {
        if (data.success) {
          alert('Phương tiện đã được xóa');
          // Cập nhật lại bảng nếu cần thiết
        } else {
          alert('Lỗi khi xóa phương tiện');
        }
      })
      .catch(error => console.error('Error:', error));
    }
  }
  function loadTransports() {
      // Gọi API để lấy danh sách phương tiện
      fetch('/admin/transports/listTransport')
        .then(response => response.json()) // Chuyển đổi response thành JSON
        .then(data => {
          // Tìm dropdown theo ID
          const transportSelect = document.getElementById('transportId');
          
          // Xóa các option cũ trong dropdown
          transportSelect.innerHTML = '<option value="" disabled selected>-- Chọn phương tiện --</option>';
          
          // Duyệt qua dữ liệu và thêm các option mới vào dropdown
          data.forEach(transport => {
            const option = document.createElement('option');
            option.value = transport.transportId;  // Mã phương tiện
            option.textContent = transport.transportId + " - " +transport.transportName;  // Tên phương tiện
            transportSelect.appendChild(option);  // Thêm option vào select
          });
        })
        .catch(error => console.error('Error fetching transport data:', error));
    }
	document.getElementById("addtransport").addEventListener("click", function(event) {
	    addTransport();
	});

	function addTransport() {
	    try {
	        // Lấy giá trị từ các input field
	        const transportId = Number(document.getElementById('transportId').value);
	        const tourTimeId = Number(document.getElementById('tourTimeId').value);

	        const departureDate = document.getElementById('departureDate').value;
	        const departureHour = document.getElementById('departureHour').value;
	        const departureMinute = document.getElementById('departureMinute').value;

	        const arrivalDate = document.getElementById('arrivalDate').value;
	        const arrivalHour = document.getElementById('arrivalHour').value;
	        const arrivalMinute = document.getElementById('arrivalMinute').value;

	        const status = document.getElementById('status').value;

	        // Tạo chuỗi thời gian cho departureTime và arrivalTime theo định dạng YYYY-MM-DD HH:mm:ss
	        const departureTime = `${departureDate} ${departureHour}:${departureMinute}:00`;
	        const arrivalTime = `${arrivalDate} ${arrivalHour}:${arrivalMinute}:00`;

	        console.log("Transport ID: " + transportId);
	        console.log("Tour Time ID: " + tourTimeId);
	        console.log("Arrival Time: " + arrivalTime);
	        console.log("Departure Time: " + departureTime);
	        console.log("Status: " + status);

	        // Kiểm tra các trường bắt buộc
	        if (!transportId || !tourTimeId || !arrivalTime || !departureTime || !status) {
	            alert('Vui lòng điền đầy đủ thông tin');
	            return;
	        }

	        const data = {
	            transportId: transportId,
	            tourTimeId: tourTimeId,
	            arrivalTime: arrivalTime,
	            departureTime: departureTime,
	            status: status
	        };

	        console.log("Data to be sent:", data);

	        // Hiển thị hộp thoại xác nhận
	        const userConfirmed = confirm(
	            `Bạn có chắc chắn muốn thêm thông tin này?\n\n` +
	            `- Transport ID: ${transportId}\n` +
	            `- Tour Time ID: ${tourTimeId}\n` +
	            `- Arrival Time: ${arrivalTime}\n` +
	            `- Departure Time: ${departureTime}\n` +
	            `- Status: ${status}`
	        );

	        if (!userConfirmed) {
	            console.log('Hành động bị hủy bởi người dùng.');
	            return; // Dừng nếu người dùng chọn "Hủy"
	        }

	        // Gửi dữ liệu tới API
	        fetch('/admin/transport-details/addTransportToTourTime', {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            body: JSON.stringify(data)
	        })
	            .then(response => {
	                if (!response.ok) {
	                    throw new Error('Network response was not ok');
	                }
	                return response.text();
	            })
	            .then(text => {
	                console.log('Response Text:', text); // Log the raw response
	                try {
	                    const result = JSON.parse(text); // Try to parse it as JSON
	                    console.log('Transport added successfully:', result);
	                    alert('Thêm thông tin phương tiện thành công!');
	                    clearFields(); // Xóa sạch các trường sau khi thêm thành công
	                } catch (error) {
	                    console.error('Error parsing JSON:', error);
	                    alert('Phản hồi không hợp lệ từ máy chủ.');
	                }
	            })
	            .catch(error => {
	                console.error('Error adding transport:', error);
	                alert('Có lỗi xảy ra. Vui lòng thử lại.');
	            });
	    } catch (error) {
	        console.error("Unexpected error:", error);
	        alert("Đã xảy ra lỗi không mong muốn. Vui lòng thử lại.");
	    }
	}

	// Hàm xóa sạch các trường nhập liệu
	function clearFields() {
	    document.getElementById('transportId').value = '';
	    document.getElementById('tourTimeId').value = '';
	    document.getElementById('departureDate').value = '';
	    document.getElementById('departureHour').value = '';
	    document.getElementById('departureMinute').value = '';
	    document.getElementById('arrivalDate').value = '';
	    document.getElementById('arrivalHour').value = '';
	    document.getElementById('arrivalMinute').value = '';
	    document.getElementById('status').value = '';
	}

