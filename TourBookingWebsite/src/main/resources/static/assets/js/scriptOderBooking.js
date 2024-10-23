document.addEventListener("DOMContentLoaded", function () {
    const tourTime = {
        day: 2,
        tourCode: "NNSGN524-048-221024VN-D",
        departureName: "TP. Hồ Chí Minh",
        departureDate: "2024-10-22T09:30:00",
        endDate: "2024-10-25T17:55:00",
        dayStayText: "4N3Đ",
        transportTypeName: "Máy bay",
        salePrice: 15990000.0,
        discountPrice: 1000000.0,
        priceFinal: 14990000.0,
        isDiscount: true,
        startTime: "09:30",
        remainPax: 3,
        vehicleStart: null,
        vehicleEnd: null,
        flightStart: {
            flightCode: "VN402",
            startDate: "2024-10-22T08:20:00",
            endDate: "2024-10-22T6:25:00",
            airportCodeFrom: "SGN",
            airportNameFrom: "SÂN BAY TÂN SƠN NHẤT, TP HỒ CHÍ MINH, VIỆT NAM",
            airportCodeTo: "ICN",
            airportNameTo: "SÂN BAY INCHEON, HÀN QUỐC",
            airlinesName: "Vietnam Airlines",
            airlinesAddress: "Số 200 Nguyễn Sơn, P.Bồ Đề, Q.Long Biên, Hà Nội",
            airlinesDescription: "Tổng công ty Hàng không Việt Nam",
            airlinesShotDescription: "Tổng công ty Hàng không Việt Nam",
            airlinesLogo:
                "https://media.travel.com.vn/ImageAirlines/logo_VietNamAir.jpg",
        },
        flightEnd: {
            flightCode: "VN403",
            startDate: "2024-10-25T17:55:00",
            endDate: "2024-10-25T21:15:00",
            airportCodeFrom: "ICN",
            airportNameFrom: "SÂN BAY INCHEON, HÀN QUỐC",
            airportCodeTo: "SGN",
            airportNameTo: "SÂN BAY TÂN SƠN NHẤT, TP HỒ CHÍ MINH, VIỆT NAM",
            airlinesName: "Vietnam Airlines",
            airlinesAddress: "Số 200 Nguyễn Sơn, P.Bồ Đề, Q.Long Biên, Hà Nội",
            airlinesDescription: "Tổng công ty Hàng không Việt Nam",
            airlinesShotDescription: "Tổng công ty Hàng không Việt Nam",
            airlinesLogo:
                "https://media.travel.com.vn/ImageAirlines/logo_VietNamAir.jpg",
        },
        tourPrice: {
            ADULT: 15990000.0,
            CHILD: 11992500.0,
        },
        note: "Hạn chót nhận hồ sơ visa rời trước 12h ngày 22/11\r\nHạn chót nhận hồ sơ visa đoàn trước 12h ngày 26/11 (tối thiểu 03 khách)\r\nChưa bao gồm tiền tip 153.000vnđ/ngày/khách (tương đương 6usd/ngày/khách)\r\nTour không tách đoàn, nếu tácn",
    };

    const swiperWrapper = document.querySelector(".swiper-wrapper");

    function generateItemAdult() {
        const html = `
    <div class="item p-3 rounded-2 mt-2 border-secondary border">
      <div class="item-title">
          <h4>Người lớn</h4>
      </div>
      <ul class="item-list p-0">
        <li class="item d-flex justify-content-between row">
          <div class="input col-sm-4">
              Họ tên
              <input type="text" name="name" class="w-100">
          </div>
          <div class="input col-sm-4">
              Giới tính
              <select name="sex" class="w-100">
                  <option value="1">Nam</option>
                  <option value="2">Nữ</option>
              </select>
          </div>
          <div class="input col-sm-4">
              Ngày sinh
              <input type="date" name="birthday" class="w-100">
          </div>
        </li>
      </ul>
    </div>`;
        // Tạo một div tạm để chuyển đổi chuỗi HTML thành Node
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = html;

        // Trả về phần tử đầu tiên trong tempDiv, nó là Node
        return tempDiv.firstElementChild;
    }

    function generateItemChild() {
        const html = `
    <div class="item p-3 rounded-2 mt-2 border-secondary border">
      <div class="item-title">
          <h4>Trẻ em</h4>
      </div>
      <ul class="item-list p-0">
        <li class="item d-flex justify-content-between row">
          <div class="input col-sm-4">
              Họ tên
              <input type="text" name="name" class="w-100">
          </div>
          <div class="input col-sm-4">
              Giới tính
              <select name="sex" class="w-100">
                  <option value="1">Nam</option>
                  <option value="2">Nữ</option>
              </select>
          </div>
          <div class="input col-sm-4">
              Ngày sinh
              <input type="date" name="birthday" class="w-100">
          </div>
        </li>
      </ul>
    </div>`;
        // Tạo một div tạm để chuyển đổi chuỗi HTML thành Node
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = html;

        // Trả về phần tử đầu tiên trong tempDiv, nó là Node
        return tempDiv.firstElementChild;
    }

    // Lấy các phần tử từ DOM
    const elementMinusBtnChild = document.getElementById("minus-btn-child");
    const elementPlusBtnChild = document.getElementById("plus-btn-child");
    const elementCounterValueChild = document.getElementById("value-child");
    const elementMinusBtnAdult = document.getElementById("minus-btn-adult");
    const elementPlusBtnAdult = document.getElementById("plus-btn-adult");
    const elementCounterValueAdult = document.getElementById("value-adult");
    const elementItemsAdultInfo = document.getElementById("items-adult");
    const elementItemsChildInfo = document.getElementById("items-child");

    // Hàm giảm giá trị
    elementMinusBtnChild.addEventListener("click", () =>
        handleQuantity("minus", "child")
    );
    elementPlusBtnChild.addEventListener("click", () =>
        handleQuantity("plus", "child")
    );
    elementMinusBtnAdult.addEventListener("click", () =>
        handleQuantity("minus", "adult")
    );
    elementPlusBtnAdult.addEventListener("click", () =>
        handleQuantity("plus", "adult")
    );

    function handleQuantity(action, type) {
        let currentValue = parseInt(
            type === "child"
                ? elementCounterValueChild.textContent
                : elementCounterValueAdult.textContent,
            10
        );
        if (action == "minus" && currentValue > 0) {
            currentValue--;
        }
        if (action == "plus") {
            currentValue++;
        }

        const elementArray = document.createElement('div');
        if (type === "child") {
            for (let i = 0; i < currentValue; i++)
                elementArray.appendChild(generateItemChild());
            elementItemsChildInfo.innerHTML = elementArray.innerHTML;
            elementCounterValueChild.textContent = currentValue;
        } else {
            for (let i = 0; i < currentValue; i++)
                elementArray.appendChild(generateItemAdult());
            elementItemsAdultInfo.innerHTML = elementArray.innerHTML;
            elementCounterValueAdult.textContent = currentValue;
        }
    }
});
