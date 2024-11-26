// Function to open the modal and load the tour images
function viewTourImage(tourId) {
    $('#tourImageModal').modal('show');
    document.getElementById('currentTourId').value = tourId;

    // Clear previous image data
    $('#imageTableBody').empty();

    // Reset the file input field (clear previously selected file)
    $('#newImage').val(''); // Đặt lại giá trị của input file

    // Load the tour images via AJAX
    $.ajax({
        url: '/api/tour-images/' + tourId, // URL của API lấy danh sách ảnh
        method: 'GET',
        success: function(response) {
            var tableBody = $('#imageTableBody');
            
            // Kiểm tra nếu không có ảnh nào
            if (response.length === 0) {
                // Nếu không có ảnh, hiển thị thông báo "Chưa có ảnh"
                tableBody.append(`
                    <tr>
                        <td colspan="3" class="text-center">Chưa có ảnh cho tour này</td>
                    </tr>
                `);
            } else {
                // Loop through each image in the response
                response.forEach(function(image) {
                    // Append each image to the table
                    tableBody.append(`
                        <tr>
                            <td>
                                <img src="${image.imageUrl}" alt="Tour Image" width="100" onclick="viewLargeImage('${image.imageUrl}')" style="cursor: pointer;" />
                            </td>
                            <td>${image.status === 1 ? 'Ảnh chính' : 'Ảnh phụ'}</td>
                            <td>
                                <button class="btn btn-info btn-sm" onclick="viewLargeImage('${image.imageUrl}')">Xem</button>
                                <button class="btn btn-warning btn-sm" onclick="editImage(${image.imageId}, '${image.imageUrl}', ${image.status})">Đổi</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteImage(${image.imageId})">Xóa</button>
                            </td>
                        </tr>
                    `);
                });
            }
        },
        error: function(error) {
            // In thông báo lỗi ra console
            console.error('Lỗi khi tải danh sách ảnh:', error);
        }
    });
}



    // Function to view large image when clicked
    function viewLargeImage(imageUrl) {
      // Hide the tour image modal
      $('#tourImageModal').modal('hide');
      
      // Show the large image modal
      $('#largeImage').attr('src', imageUrl);
      $('#viewImageModal').modal('show');
    }
	function addNewImage(tourId) {
	    var formData = new FormData();
	    var file = $('#newImage')[0].files[0]; // Lấy file từ input
	    var status = document.getElementById('imageStatus').value; // Lấy trạng thái ảnh

	    if (!file) {
	        alert('Vui lòng chọn một tệp!');
	        return;
	    }

	    if (status == 1) {
	        // Kiểm tra nếu đã có ảnh status = 1
	        $.ajax({
	            url: '/active-images/' + tourId, // API kiểm tra
	            type: 'GET',
	            success: function (hasActiveImage) {
	                if (hasActiveImage==false) {
						uploadAndSaveImage(formData, file, tourId, status);

	                } else {
	                    // Nếu chưa có ảnh status = 1, tiến hành thêm ảnh
						alert('Không thể thêm ảnh! Tour này đã có ảnh chính.');

	                }
	            },
	            error: function (error) {
	                console.error('Lỗi khi kiểm tra trạng thái ảnh:', error);
	                alert('Có lỗi xảy ra, vui lòng thử lại!');
	            }
	        });
	    } else {
	        // Nếu trạng thái không phải 1, tiến hành thêm ảnh
	        uploadAndSaveImage(formData, file, tourId, status);
	    }
	}

	function uploadAndSaveImage(formData, file, tourId, status) {
	    formData.append('file', file); // Đưa file vào formData

	    // Gọi API để upload
	    $.ajax({
	        url: '/api/images/upload', // Endpoint API upload
	        type: 'POST',
	        data: formData,
	        contentType: false, // Không đặt kiểu nội dung
	        processData: false, // Không xử lý dữ liệu
	        success: function (response) {
	            // Gửi yêu cầu AJAX để lưu ảnh vào cơ sở dữ liệu
	            var saveData = {
	                tourId: tourId, // ID của tour
	                imageUrl: response, // URL của ảnh từ Cloudinary (hoặc nguồn khác)
	                status: status // Trạng thái ảnh
	            };

	            $.ajax({
	                url: '/api/tour-images/save', // API lưu ảnh vào cơ sở dữ liệu
	                method: 'POST',
	                data: JSON.stringify(saveData), // Chuyển dữ liệu thành JSON
	                contentType: 'application/json', // Thiết lập kiểu nội dung là JSON
	                success: function (response) {
	                    // Sau khi lưu ảnh thành công
	                    $('#imageTableBody').empty(); // Xóa dữ liệu cũ trong bảng
	                    viewTourImage(tourId); // Tải lại danh sách ảnh
	                    $('#addImageForm')[0].reset(); // Reset form
	                    alert('Ảnh đã được thêm thành công!');
	                },
	                error: function (error) {
	                    console.error('Lỗi khi lưu ảnh:', error);
	                    alert('Lỗi khi lưu ảnh!');
	                }
	            });
	        },
	        error: function (xhr, status, error) {
	            alert('Upload thất bại: ' + xhr.responseText); // Thông báo lỗi nếu có
	            console.error('Lỗi:', error);
	        }
	    });
	}


  

// Lắng nghe sự kiện submit form
$('#addImageForm').on('submit', function(e) {
    e.preventDefault(); // Ngừng việc gửi form mặc định
    var tourId = document.getElementById('currentTourId').value; // Lấy tourId từ form hoặc input đã có
    addNewImage(tourId); // Gọi hàm thêm ảnh mới
});


// Function to edit an image (open edit modal) 
function editImage(imageId, imageUrl, status) {
  // Hide the tour image modal
  $('#tourImageModal').modal('hide');

  // Populate the edit modal with the selected image data
  $('#editImageId').val(imageId);
  $('#editImageUrl').val(imageUrl);
  $('#editImageStatus').val(status);

  // Reset the file input to clear previous selections
  $('#editImageFile').val('');

  // Show the edit modal
  $('#editImageModal').modal('show');
}

// When the 'viewImageModal' is hidden, reopen the 'tourImageModal'
$('#viewImageModal').on('hidden.bs.modal', function () {
  $('#tourImageModal').modal('show');
});

// When the 'editImageModal' is hidden, reopen the 'tourImageModal'
$('#editImageModal').on('hidden.bs.modal', function () {
  $('#tourImageModal').modal('show');
});

$('#editImageForm').submit(function (event) {
  event.preventDefault(); // Ngăn không cho form reload trang

  var formData = new FormData();
  var file = $('#editImageFile')[0].files[0]; // Lấy file ảnh mới (nếu có)
  var imageId = $('#editImageId').val(); // Lấy ID ảnh
  var status = $('#editImageStatus').val(); // Lấy trạng thái ảnh
  var imageUrl = $('#editImageUrl').val(); // Lấy URL ảnh
  var tourId = $('#currentTourId').val(); // ID tour hiện tại

  if (status == 1) {
    // Kiểm tra xem có ảnh với trạng thái status = 1 và trùng imageId hay không
    $.ajax({
      url: '/active-images/' + tourId + '/' + imageId, // Endpoint kiểm tra
      type: 'GET',
      success: function (isMatching) {
        if (isMatching) {
          // Nếu có ảnh trùng với imageId và status = 1, tiếp tục xử lý
          processImageEdit(file, imageId, imageUrl, status);
        } else {
          // Nếu không có ảnh trùng, kiểm tra xem tour đã có ảnh với status = 1 chưa
          $.ajax({
            url: '/active-images/' + tourId, // API kiểm tra
            type: 'GET',
            success: function (hasActiveImage) {
              if (hasActiveImage == false) {
                processImageEdit(file, imageId, imageUrl, status);
              } else {
                // Nếu đã có ảnh chính, không thể thay đổi
                alert('Không thể đổi ảnh thành ảnh chính đã có ảnh chính rồi');
              }
            },
            error: function (error) {
              console.error('Lỗi khi kiểm tra trạng thái ảnh:', error);
              alert('Có lỗi xảy ra, vui lòng thử lại!');
            }
          });
        }
      },
      error: function (error) {
        console.error('Lỗi khi kiểm tra trạng thái ảnh:', error);
        alert('Có lỗi xảy ra, vui lòng thử lại!');
      }
    });
  } else {
    // Nếu trạng thái không phải 1, tiến hành xử lý ảnh
    processImageEdit(file, imageId, imageUrl, status);
  }
});

// Hàm xử lý upload và lưu chỉnh sửa ảnh
function processImageEdit(file, imageId, imageUrl, status) {
  if (file) {
    // Nếu có file mới, upload trước
    var formData = new FormData();
    formData.append('file', file);

    $.ajax({
      url: '/api/images/upload', // Endpoint upload ảnh
      type: 'POST',
      data: formData,
      contentType: false,
      processData: false,
      success: function (response) {
        // Cập nhật URL ảnh mới từ response
        imageUrl = response;
        saveEditedImage(imageId, imageUrl, status);
      },
      error: function (xhr, status, error) {
        alert('Upload ảnh thất bại: ' + xhr.responseText);
      }
    });
  } else {
    // Nếu không có file mới, chỉ lưu các thay đổi khác
    saveEditedImage(imageId, imageUrl, status);
  }
}

// Hàm lưu ảnh sau khi chỉnh sửa
function saveEditedImage(imageId, imageUrl, status) {
  var saveData = {
    imageId: imageId,
    imageUrl: imageUrl,
    status: status,
  };

  $.ajax({
    url: '/api/tour-images/update', // API lưu thay đổi vào cơ sở dữ liệu
    method: 'PUT',
    data: JSON.stringify(saveData),
    contentType: 'application/json',
    success: function (response) {
      // Sau khi cập nhật thành công
      $('#editImageModal').modal('hide');
      alert('Cập nhật ảnh thành công!');
      $('#imageTableBody').empty();
      viewTourImage($('#currentTourId').val()); // Tải lại danh sách ảnh
    },
    error: function (error) {
      console.error('Lỗi khi cập nhật ảnh:', error);
      alert('Lỗi khi cập nhật ảnh!');
    }
  });
}
  
 // Hàm để xóa hình ảnh
    function deleteImage(imageId) {
        if (confirm("Bạn có chắc chắn muốn xóa hình ảnh này không?")) {
            $.ajax({
                url: '/api/tour-images/delete/' + imageId,  // Đường dẫn API để xóa ảnh
                type: 'DELETE',  // Sử dụng phương thức DELETE
                success: function(response) {
                    alert('Ảnh đã được xóa thành công!');
                    $('#imageTableBody').empty(); 
                    viewTourImage( document.getElementById('currentTourId').value);  
                },
                error: function(error) {
                    // Nếu có lỗi, hiển thị thông báo lỗi
                    console.error('Lỗi khi xóa ảnh:', error);
                    alert('Có lỗi xảy ra khi xóa ảnh.');
                }
            });
        }
    }
