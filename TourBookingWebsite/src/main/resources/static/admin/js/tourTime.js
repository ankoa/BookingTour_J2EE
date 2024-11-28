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
							<button  
							  class="btn btn-danger btn-sm w-100" 
							  data-id="${tourTime.tourTimeId}" 
							  onclick="confirmDeleteTourTime(this)" 
							  ${tourTime.status === 0 ? 'disabled' : ''}
							>Xóa</button>						
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
function fetchTourNameAddEdit(tourId) {
    if (tourId) {
      fetch(`/admin/tours/${tourId}`)
        .then(response => response.json())
        .then(data => {
          document.getElementById("tourNameinTourTime").value = data.tourName || "Tour không tồn tại";
        })
        .catch(error => {
          console.error('Lỗi khi lấy tên Tour:', error);
          document.getElementById("tourNameinTourTime").value = "Lỗi khi lấy tên Tour";
        });
    } else {
      document.getElementById("tourNameinTourTime").value = "";
    }
  }

  function addTourTime() {
      const timeName = document.getElementById("timeName").value.trim();
      const tourTimeCode = document.getElementById("tourTimeCode").value.trim();
      const tourId = Number(document.getElementById('tourIdinTourTime').value.trim());
      const departureDate = document.getElementById("departureDateinTourTime").value.trim();
      const departureHour = document.getElementById("departureHourinTourTime").value.trim();
      const departureMinute = document.getElementById("departureMinuteinTourTime").value.trim();
      const arrivalDate = document.getElementById("arrivalDateinTourTime").value.trim();
      const arrivalHour = document.getElementById("arrivalHourinTourTime").value.trim();
      const arrivalMinute = document.getElementById("arrivalMinuteinTourTime").value.trim();
      const priceAdult = document.getElementById("priceAdultinTourTime").value.trim();
      const priceChild = document.getElementById("priceChildinTourTime").value.trim();
      const note = document.getElementById("noteinTourTime").value.trim();
      const quantity = document.getElementById("quantityinTourTime").value.trim();

      // Check for null or empty values
      if (!timeName || !tourTimeCode || isNaN(tourId) || !departureDate || !departureHour || !departureMinute ||
          !arrivalDate || !arrivalHour || !arrivalMinute || !priceAdult || !priceChild || !quantity) {
          alert("Vui lòng điền đầy đủ tất cả thông tin!");
          return;
      }

      // Create full timestamps for departure and arrival
      const departureTime = new Date(`${departureDate}T${departureHour}:${departureMinute}:00`);
      const returnTime = new Date(`${arrivalDate}T${arrivalHour}:${arrivalMinute}:00`);

      // Check if the departure time is in the future
      if (departureTime <= new Date()) {
          alert("Ngày đi phải là ngày trong tương lai!");
          return;
      }

      // Check if the return time is valid (same day but later time or a later day)
      if (returnTime < departureTime) {
          alert("Ngày đến phải cùng ngày với ngày đi nhưng có giờ lớn hơn, hoặc phải là ngày sau!");
          return;
      }

      // Prepare the data object to send
      const tourTimeData = {
          timeName,
          tourTimeCode,
          tourId,
          departureTime: departureTime.toISOString(),
          returnTime: returnTime.toISOString(),
          priceAdult: Number(priceAdult),
          priceChild: Number(priceChild),
          note,
          quantity: Number(quantity) // Ensure quantity is treated as a number
      };

      console.log(tourTimeData);

      // Send POST request
      fetch('/admin/tour-times/add', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify(tourTimeData)
      })
      .then(response => response.json())
      .then(data => {
          alert("Thêm Tour Time thành công!");
          loadTourTimes();
          const modal = bootstrap.Modal.getInstance(document.getElementById('addTourTimeModal'));
          modal.hide();
      })
      .catch(error => {
          console.error("Error adding tour time:", error);
          alert("Đã xảy ra lỗi khi thêm Tour Time. Vui lòng thử lại sau.");
      });
  }

  document.getElementById('addTourTimeModal').addEventListener('hide.bs.modal', function () {
    // Xóa lớp nền của modal
    const backdrop = document.querySelector('.modal-backdrop');
    if (backdrop) {
      backdrop.parentNode.removeChild(backdrop);
    }
  });
/*
  function addTourTime() {
  	const tourTimeData = {
  	  tourTimeCode: "TT1234",
  	  timeName: "Tour 1",
  	  departureTime: "2024-11-30 08:00:00",
  	  returnTime: "2024-12-07 18:00:00",
  	  quantity: 20,
  	  priceAdult: 150000,
  	  priceChild: 120000,
  	  note: "Thời gian tour đặc biệt, chỉ có vào cuối tuần.",
  	  status: 1,
  	  tourId: 8
  	};

  	fetch('/admin/tour-times/add', {
  	  method: 'POST',
  	  headers: {
  	    'Content-Type': 'application/json',
  	  },
  	  body: JSON.stringify(tourTimeData)
  	})
  	.then(response => response.json())
  	.then(data => {
  	  if (data.message) {
  	    console.log(data.message); // "Thêm thời gian tour thành công." hoặc thông báo lỗi
  	  }
  	})
  	.catch(error => {
  	  console.error('Error:', error);
  	});

    }*/
	function editTourTime(button) {
	    const tourTimeId = button.getAttribute('data-id');
	    // Gọi API để lấy dữ liệu tourTime
	    fetch(`/admin/gettourtimesbyidadmin/${tourTimeId}`)
	        .then(response => {
	            if (!response.ok) {  // Kiểm tra nếu có lỗi từ server
	                throw new Error('Không thể lấy dữ liệu tourTime');
	            }
	            return response.json();
	        })
	        .then(tourTime => {
	            if (tourTime) {
	                console.log(tourTime);
	                // Mở modal
	                const modal = new bootstrap.Modal(document.getElementById('editTourTimeModal'));
	                modal.show();

	                // Cập nhật các trường trong modal với dữ liệu của tourTime
	                document.getElementById('editTimeName').value = tourTime.timeName;  // Tên Tour Time
	                document.getElementById('editTourTimeCode').value = tourTime.tourTimeCode;  // Mã Tour Time
	                console.log(tourTime.tour.tourId);

	                // Cập nhật Tour ID và Tên Tour (Tour ID không cho chỉnh sửa)
	                document.getElementById('editTourIdinTT').value = tourTime.tour.tourId;  // Tour ID
	                document.getElementById('editTourNameinTT').value = tourTime.tour.tourName;  // Tên Tour

	                // Cập nhật ngày và giờ khởi hành (bao gồm cả giờ và phút)
	                const departureDate = new Date(tourTime.departureTime);
	                const formattedDepartureDate = departureDate.toISOString().slice(0, 19).replace("T", " "); // Định dạng yyyy-mm-dd hh:mm:ss
	                document.getElementById('editDepartureDate').value = formattedDepartureDate;  // Ngày khởi hành

	                // Cập nhật ngày và giờ đến (bao gồm cả giờ và phút)
	                const arrivalDate = new Date(tourTime.returnTime);
	                const formattedArrivalDate = arrivalDate.toISOString().slice(0, 19).replace("T", " "); // Định dạng yyyy-mm-dd hh:mm:ss
	                document.getElementById('editArrivalDate').value = formattedArrivalDate;  // Ngày đến

	                // Cập nhật giá người lớn và trẻ em
	                document.getElementById('editPriceAdult').value = tourTime.priceAdult;  // Giá người lớn
	                document.getElementById('editPriceChild').value = tourTime.priceChild;  // Giá trẻ em

	                // Cập nhật số lượng và ghi chú
	                document.getElementById('editQuantity').value = tourTime.quantity;  // Số lượng
	                document.getElementById('editNote').value = tourTime.note;  // Ghi chú

					// Giả sử bạn đã có đối tượng tourTime với thuộc tính status
					const statusSelect = document.getElementById('editStatus');

					// Kiểm tra giá trị của tourTime.status và chọn giá trị tương ứng trong <select>
					if (tourTime.status == 1) {
					    statusSelect.value = "1";  // Trạng thái Hoạt động
					} else if (tourTime.status == 0) {
					    statusSelect.value = "0";  // Trạng thái Ngưng hoạt động
					}


	                // Cập nhật giá trị cho input ẩn tourTimeId
	                document.getElementById('editTourTimeId').value = tourTimeId;  // Lưu tourTimeId vào input ẩn
	            } else {
	                alert('Không tìm thấy thông tin tourTime!');
	            }
	        })
	        .catch(error => {
	            console.error('Lỗi khi lấy dữ liệu tourTime:', error);
	            alert('Có lỗi xảy ra khi tải thông tin tourTime.');
	        });
	}
	function updateTourTime() {
	    const timeName = document.getElementById('editTimeName').value;
	    const tourTimeCode = document.getElementById('editTourTimeCode').value;
	    const status = document.getElementById('editStatus').value;
	    const priceAdult = Number(document.getElementById('editPriceAdult').value);
	    const priceChild = Number(document.getElementById('editPriceChild').value);
	    const quantity = Number(document.getElementById('editQuantity').value);
	    const note = document.getElementById('editNote').value;
	    const tourTimeId = Number(document.getElementById('editTourTimeId').value);

	    console.log('Tour Time ID:', tourTimeId);
	    console.log('Time Name:', timeName);
	    console.log('Tour Time Code:', tourTimeCode);
	    console.log('Status:', status);
	    console.log('Price Adult:', priceAdult);
	    console.log('Price Child:', priceChild);
	    console.log('Quantity:', quantity);
	    console.log('Note:', note);

	    // Gửi yêu cầu PUT đến server
	    fetch(`/admin/tour-times/update/${tourTimeId}`, {
	        method: 'PUT',
	        headers: {
	            'Content-Type': 'application/json',
	        },
	        body: JSON.stringify({
	            timeName: timeName,
	            tourTimeCode: tourTimeCode,
	            status: status,
	            priceAdult: priceAdult,
	            priceChild: priceChild,
	            quantity: quantity,
	            note: note,
	        }),
	    })
	    .then(response => {
	        // Chỉ cần giả định rằng cập nhật luôn thành công
	        return response.json();
	    })
	    .then(data => {
	        // Giả định là cập nhật thành công, không cần kiểm tra `data.success`
	        alert('Cập nhật tour time thành công!');
	        const modal = bootstrap.Modal.getInstance(document.getElementById('editTourTimeModal'));
	        modal.hide();
			loadTourTimes();
	    })
	    .catch(error => {
	        console.error('Lỗi khi cập nhật tour time:', error);
	        alert('Có lỗi xảy ra khi cập nhật dữ liệu.');
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
	clearFields();
    // Lấy tourTimeId từ button và đặt vào trường ẩn trong form
    const tourTimeId = button.getAttribute('data-id');
    document.getElementById('tourTimeId').value = tourTimeId;

    // Mở modal
    const transportModal = new bootstrap.Modal(document.getElementById('transportModal'));
    transportModal.show();
	loadTransports();
	fetchAndRenderTransports(tourTimeId);
  }

  

  function removeTransport(transportDetailId) {
	const tourTimeId = Number(document.getElementById('tourTimeId').value);

     if (confirm('Bạn có chắc chắn muốn xóa phương tiện này?')) {
         // Gửi yêu cầu xóa qua API
         fetch(`/admin/transport-details/delete/${transportDetailId}`, {
             method: 'DELETE',
         })
         .then(response => {
             if (!response.ok) {
                 throw new Error('Không thể xóa phương tiện');
             }
             return response.json();
         })
         .then(data => {
             alert('Phương tiện đã được xóa');
             // Cập nhật lại bảng nếu cần thiết
             fetchAndRenderTransports(tourTimeId); // Thực hiện render lại bảng với dữ liệu mới
         })
         .catch(error => {
             console.error('Error:', error);
             alert('Có lỗi xảy ra khi xóa phương tiện');
         });
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

	async function addTransport() {
	    const tourTimeId = Number(document.getElementById('tourTimeId').value);
	    
	    // Kiểm tra số lượng phương tiện trước khi thêm mới
	    const canAddTransport = await checkTransportCount(tourTimeId);
	    if (!canAddTransport) {
	        return; // Nếu đã đủ 4 phương tiện thì dừng lại
	    }

	    try {
	        const transportId = Number(document.getElementById('transportId').value);
	        const departureDate = document.getElementById('departureDate').value;
	        const departureHour = document.getElementById('departureHour').value;
	        const departureMinute = document.getElementById('departureMinute').value;
	        const arrivalDate = document.getElementById('arrivalDate').value;
	        const arrivalHour = document.getElementById('arrivalHour').value;
	        const arrivalMinute = document.getElementById('arrivalMinute').value;
	        const status = document.getElementById('status').value;

	        const departureTime = `${departureDate} ${departureHour}:${departureMinute}:00`;
	        const arrivalTime = `${arrivalDate} ${arrivalHour}:${arrivalMinute}:00`;

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

	        const userConfirmed = confirm(`Bạn có chắc chắn muốn thêm thông tin này?\n\n- Transport ID: ${transportId}\n- Tour Time ID: ${tourTimeId}\n- Arrival Time: ${arrivalTime}\n- Departure Time: ${departureTime}\n- Status: ${status}`);
	        if (!userConfirmed) return;

	        const response = await fetch('/admin/transport-details/addTransportToTourTime', {
	            method: 'POST',
	            headers: { 'Content-Type': 'application/json' },
	            body: JSON.stringify(data)
	        });

	        if (!response.ok) throw new Error('Network response was not ok');
	        const result = await response.json();
	        alert('Thêm thông tin phương tiện thành công!');
			fetchAndRenderTransports(tourTimeId);
	        clearFields();
	    } catch (error) {
	        console.error('Lỗi khi thêm phương tiện:', error);
	        alert('Có lỗi xảy ra. Vui lòng thử lại.');
	    }
	}

	async function fetchAndRenderTransports(tourTimeId) {
	    const tbody = document.getElementById('selectedTransports');
	    tbody.innerHTML = ''; // Xóa nội dung cũ trước khi render

	    try {
	      // Gọi API
	      const response = await fetch(`/admin/transport-details/by-tour-time/${tourTimeId}`);
	      
	      if (!response.ok) {
	        throw new Error('Không thể tải dữ liệu, lỗi: ' + response.statusText);
	      }

	      const transports = await response.json();

	      // Kiểm tra nếu không có dữ liệu
	      if (transports.length === 0) {
	        tbody.innerHTML = '<tr><td colspan="6" class="text-center">Không có phương tiện nào.</td></tr>';
	        return;
	      }

	      // Render dữ liệu ra bảng
	      transports.forEach(transport => {
	        const row = document.createElement('tr');
	        row.innerHTML = `
	          <td>${transport.transport.transportId}</td>
	          <td>${transport.transport.transportName || 'N/A'}</td>
	          <td>${new Date(transport.arrivalTime).toLocaleString()}</td>
	          <td>${new Date(transport.departureTime).toLocaleString()}</td>
	          <td>${transport.status === 1 ? 'Hoạt động' : 'Không hoạt động'}</td>
	          <td>
	            <button class="btn btn-primary btn-sm" onclick="editTransport(${transport.transportDetailId})">Sửa</button>
	            <button class="btn btn-danger btn-sm" onclick="removeTransport(${transport.transportDetailId})">Xóa</button>
	          </td>
	        `;
	        tbody.appendChild(row);
	      });
	    } catch (error) {
	      console.error('Lỗi khi tải dữ liệu:', error);
	      tbody.innerHTML = '<tr><td colspan="6" class="text-center text-danger">Lỗi khi tải dữ liệu.</td></tr>';
	    }
	  }
	  
	  async function checkTransportCount(tourTimeId) {
	      try {
	          const response = await fetch(`/admin/transport-details/by-tour-time/${tourTimeId}`);
	          if (!response.ok) {
	              throw new Error('Không thể tải dữ liệu, lỗi: ' + response.statusText);
	          }

	          const transports = await response.json();
	          if (transports.length >= 4) {
	              alert('Đã đủ 4 phương tiện, không thể thêm thêm.');
	              return false;
	          } else {
	              return true; // Có thể thêm phương tiện mới
	          }
	      } catch (error) {
	          console.error('Lỗi khi kiểm tra số lượng phương tiện:', error);
	          alert('Có lỗi xảy ra khi kiểm tra số lượng phương tiện.');
	          return false;
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
	let selectedTourTimeId = null;

	// Hàm mở modal xác nhận xóa TourTime
	function confirmDeleteTourTime(button) {
	  selectedTourTimeId = button.getAttribute('data-id'); // Lấy TourTimeId từ nút
	  console.log(selectedTourTimeId);
	  const confirmDeleteTourTimeModal = new bootstrap.Modal(
	    document.getElementById('confirmDeleteTourTimeModal')
	  );
	  confirmDeleteTourTimeModal.show(); // Mở modal
	}

	// Hàm xử lý xóa TourTime
	function deleteTourTime() {
	  if (selectedTourTimeId) {
	    // Gửi yêu cầu xóa đến server
	    fetch(`/admin/tour-times/delete?tourTimeId=${selectedTourTimeId}`, {
	      method: 'POST',
	    })
	      .then(response => {
	        if (response.ok) {
				showAlert('success', 'Xóa thành công!');
	          // Reload lại trang sau khi xóa thành công
	          loadTourTimes();
	        } else {
	          // Kiểm tra nếu có lỗi từ API và hiển thị chi tiết lỗi
	          return response.text().then(text => {
	            throw new Error(text);
	          });
	        }
	      })
	      .catch(error => {
	        // Hiển thị thông báo lỗi nếu có vấn đề với yêu cầu fetch
	        alert("Có lỗi xảy ra: " + error.message);
	      })
	      .finally(() => {
	        // Đảm bảo đóng modal sau khi xóa xong
	        const confirmDeleteTourTimeModal = bootstrap.Modal.getInstance(
	          document.getElementById('confirmDeleteTourTimeModal')
	        );
	        confirmDeleteTourTimeModal.hide();
	      });
	  } else {
	    alert("Không tìm thấy TourTime để xóa.");
	  }
	}
