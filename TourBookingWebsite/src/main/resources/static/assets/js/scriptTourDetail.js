document.addEventListener("DOMContentLoaded", function () {
    const months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

    const tour = {};
    const tourTimes = [
        {
            months: 1,
            data: [
                {
                    day: 2,
                    tourId: "685dc869-4c01-434c-9f9e-5a2b3350755a",
                    tourCode: "NNSGN524-048-221024VN-D",
                    tourName:
                        "Hàn Quốc: Seoul - Đảo Nami - Công viên Lotte World - Thủy Cung Lotte Aquarium - Hồ Seokchon | Trải nghiệm mặc Hanbok tại Cung điện Hoàng Gia Gyeongbok | Thu Sang",
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
                        fightCode: "VN402",
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
                        fightCode: "VN403",
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
                },
                {
                    day: 8,
                    tourId: "685dc869-4c01-434c-9f9e-5a2b3350755a",
                    tourCode: "NNSGN524-ưqeqweqwe",
                    tourName:
                        "Hàn Quốc: Seoul - Đảo Nami - Công viên Lotte World - Thủy Cung Lotte Aquarium - Hồ Seokchon | Trải nghiệm mặc Hanbok tại Cung điện Hoàng Gia Gyeongbok | Thu Sang",
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
                        fightCode: "VN402",
                        startDate: "2024-10-22T09:30:00",
                        endDate: "2024-10-22T16:25:00",
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
                        fightCode: "VN403",
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
                    note: "Hạ03 khách)\r\nChưa bao gồm tiền tip 153.000vnđ/ngày/khách (tương đương 6usd/ngày/khách)\r\nTour không tách đoàn, nếu tách đoàn đóng thêm phụ phí 4,000,000 VND/khách /ngày.\r\n",
                },
                {
                    day: 15,
                    tourId: "685dc869-4c01-434c-9f9e-5a2b3350755a",
                    tourCode: "NNSGN524-048-221024VN-D",
                    tourName:
                        "Hàn Quốc: Seoul - Đảo Nami - Công viên Lotte World - Thủy Cung Lotte Aquarium - Hồ Seokchon | Trải nghiệm mặc Hanbok tại Cung điện Hoàng Gia Gyeongbok | Thu Sang",
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
                    remainPax: 2,
                    vehicleStart: null,
                    vehicleEnd: null,
                    flightStart: {
                        fightCode: "VN402",
                        startDate: "2024-10-22T09:30:00",
                        endDate: "2024-10-22T16:25:00",
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
                        fightCode: "VN403",
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
                    note: "Hạn chót nhận hồ sơ visa rời trước 12h ngày 22/11\r\nHạn chót nhận hồ sơ visa đoàn trước 12h ngày 26/11 (tối thiểu 03 khách)\r\nChưa bao gồm tiền tip 153.000vnđ/ngày/khách (tương đương 6usd/ngày/khách)\r\nTour không tách đoàn, nếu tách đoàn đóng thêm phụ phí 4,000,000 VND/khách /ngày.\r\n",
                },
            ],
        },
        {
            months: 2,
            data: [
                {
                    day: 14,
                    tourId: "685dc869-4c01-434c-9f9e-5a2b3350755a",
                    tourCode: "NNSGN524-048-221024VN-D",
                    tourName:
                        "Hàn Quốc: Seoul - Đảo Nami - Công viên Lotte World - Thủy Cung Lotte Aquarium - Hồ Seokchon | Trải nghiệm mặc Hanbok tại Cung điện Hoàng Gia Gyeongbok | Thu Sang",
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
                    remainPax: 2,
                    vehicleStart: null,
                    vehicleEnd: null,
                    flightStart: {
                        fightCode: "VN402",
                        startDate: "2024-10-22T09:30:00",
                        endDate: "2024-10-22T16:25:00",
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
                        fightCode: "VN403",
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
                    note: "Hạàn đóng thêm phụ phí 4,000,000 VND/khách /ngày.\r\n",
                },
                {
                    day: 15,
                    tourId: "685dc869-4c01-434c-9f9e-5a2b3350755a",
                    tourCode: "NNSGN524-048-221024VN-D",
                    tourName:
                        "Hàn Quốc: Seoul - Đảo Nami - Công viên Lotte World - Thủy Cung Lotte Aquarium - Hồ Seokchon | Trải nghiệm mặc Hanbok tại Cung điện Hoàng Gia Gyeongbok | Thu Sang",
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
                    remainPax: 2,
                    vehicleStart: null,
                    vehicleEnd: null,
                    flightStart: {
                        fightCode: "VN402",
                        startDate: "2024-10-22T09:30:00",
                        endDate: "2024-10-22T16:25:00",
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
                        fightCode: "VN403",
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
                    note: "Hạn chót nhận hồ sơ visa rời trước 12h ngày 22/11\r\nHạn chót nhận hồ sơ visa đoàn trước 12h ngày 26/11 (tối thiểu 03 khách)\r\nChưa bao gồm tiền tip 153.000vnđ/ngày/khách (tương đương 6usd/ngày/khách)\r\nTour không tách đoàn, nếu tách đoàn đóng thêm phụ phí 4,000,000 VND/khách /ngày.\r\n",
                },
            ],
        },
    ];

    const swiperWrapper = document.querySelector(".swiper-wrapper");
    const tourTimeDetail = document.querySelector("#tour-time-detail");
    const tourTimeInfo = document.querySelector("#tour-info-content");

    // Hàm tính thứ của ngày đầu tháng bằng Day.js
    function getFirstDayOfMonth(monthIndex, year = 2024) {
        return dayjs(`${year}-${monthIndex + 1}-01`).day();
    }

    // Hàm kiểm tra sự kiện đặc biệt
    function getSpecialEvent(month, day) {
        const specialMonth = tourTimes.find((m) => m.months === month);
        if (!specialMonth) return null;

        const specialDay = specialMonth.data.find((d) => d.day === day);
        return specialDay ? specialDay : null;
    }

    // Hàm tạo các ngày trong một tháng
    function generateDays(monthIndex, month, year) {
        const daysInMonth = dayjs(`${year}-${monthIndex + 1}`).daysInMonth();
        const firstDay = getFirstDayOfMonth(monthIndex, year);
        let daysHTML =
            `<div class="title-day">Thứ 2</div>` +
            `<div class="title-day">Thứ 3</div>` +
            `<div class="title-day">Thứ 4</div>` +
            `<div class="title-day">Thứ 5</div>` +
            `<div class="title-day">Thứ 6</div>` +
            `<div class="title-day">Thứ 7</div>` +
            `<div class="title-day">Chủ nhật</div>`;

        // Thêm các ô trống cho những ngày trước thứ 2
        for (let i = 0; i < (firstDay === 0 ? 6 : firstDay - 1); i++) {
            daysHTML += `<div class="day empty"></div>`;
        }

        // Thêm các ngày trong tháng
        for (let i = 1; i <= daysInMonth; i++) {
            const specialEvent = getSpecialEvent(month, i);
            if (specialEvent) {
                daysHTML += `<div class="day cursor-pointer special-day" data-month="${month}" data-day="${i}">`;
                if (specialEvent.isDiscount)
                    daysHTML += `${i} ${htmlGilf}<br>${
                        specialEvent.salePrice / 1000
                    }k</div>`;
                else daysHTML += `${i} <br>${specialEvent.salePrice / 1000}k</div>`;
            } else {
                daysHTML += `<div class="day cursor-pointer">${i}</div>`;
            }
        }

        return `<div class="days">${daysHTML}</div>`;
    }

    // Tạo các slide cho mỗi tháng
    months.forEach((month, index) => {
        const monthSlide = document.createElement("div");
        monthSlide.classList.add("swiper-slide");

        // Thêm tên tháng và các ngày trong tháng

        monthSlide.innerHTML =
            `<h2>Tháng ${month}</h2>` + generateDays(index, month, 2024);

        // Thêm slide vào Swiper
        swiperWrapper.appendChild(monthSlide);
    });
    // Khởi tạo Swiper
    const swiper = new Swiper(".mySwiper", {
        spaceBetween: 30,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        allowTouchMove: false,
    });

    // Hàm xử lý khi nhấp vào ngày đặc biệt
    function handleSpecialDayClick(event) {
        const month = event.target.getAttribute("data-month");
        const day = event.target.getAttribute("data-day");

        const specialEvent = getSpecialEvent(Number(month), Number(day));

        if (specialEvent) {
            // Hiển thị chi tiết sự kiện trong một modal hoặc div
            const details = `
        <p>Mã Tour: ${specialEvent.tourCode}</p>
        <p>Giá bán: ${specialEvent.salePrice}</p>
        <p>Khởi hành tại: ${specialEvent.departureName}</p>
        <p>Ngày Khởi hành: ${specialEvent.departureDate}</p>
        <p>Thời gian: ${specialEvent.dayStayText}</p>
        <p>Số chỗ còn lại: ${specialEvent.remainPax} chỗ</p>
        <div class="book"><a class="btn btn-danger w-100" href="order-booking?tour-id=${specialEvent.tourId}">Đặt tour</a></div>
        </div>
      `;
            const info = `<h4>Phương tiện di chuyển</h4>
                        <div class="row w-100">
                            <div class="col col-md-6 col col-12">
                                <div class="d-flex justify-content-between">
                                    <p>Ngày đi - ${dayjs(
                specialEvent.flightStart.startDate
            ).format("DD/MM/YYYY")}</p>
                                    <p>${specialEvent.flightStart.fightCode}</p>
                                </div>
                                <div class="d-flex justify-content-between border-bottom border-4 border-black">
                                    <p>${dayjs(
                specialEvent.flightStart.startDate
            ).format("HH:mm")}</p>
                                    <p>${dayjs(
                specialEvent.flightStart.endDate
            ).format("HH:mm")}</p>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <p>${
                specialEvent.flightStart.airportCodeFrom
            }</p>
                                    <p><img src="${
                specialEvent.flightStart.airlinesLogo
            }"></p>
                                    <p>${
                specialEvent.flightStart.airportCodeTo
            }</p>
                                </div>
                            </div>
                            <div class="col col-md-6 col col-12">
                                <div class="d-flex justify-content-between">
                                    <p>Ngày về - ${dayjs(
                specialEvent.flightEnd.startDate
            ).format("DD/MM/YYYY")}</p>
                                    <p>Ngày về - ${
                specialEvent.flightEnd.flightCode
            }</p>
                                </div>
                                <div class="d-flex justify-content-between border-bottom border-4 border-black">
                                    <p>${dayjs(
                specialEvent.flightStart.startDate
            ).format("HH:mm")}</p>
                                    <p>${dayjs(
                specialEvent.flightStart.endDate
            ).format("HH:mm")}</p>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <p>${
                specialEvent.flightStart.airportCodeFrom
            }</p>
                                    <p><img src="${
                specialEvent.flightStart.airlinesLogo
            }"></p>
                                    <p>${
                specialEvent.flightStart.airportCodeTo
            }</p>
                                </div>
                            </div>
                            </div>
                        </div>
                        <h4>Giá Tour</h4>
                        <div class="row w-100">
                            <div class="col col-6">
                                <div class="d-flex justify-content-between">
                                    <p>Người lớn</p>
                                    <p>${specialEvent.tourPrice.ADULT} đ</p>
                                </div>
                            </div>
                            <div class="col col-6">
                                <div class="d-flex justify-content-between">
                                    <p>Trẻ em</p>
                                    <p>${specialEvent.tourPrice.CHILD} đ</p>
                                </div>
                            </div>


                        </div>
                        <div class="note border-3 border border-warning p-2 rounded-2 color-white">${
                specialEvent.note
            }</div>`;
            tourTimeDetail.innerHTML = details;
            tourTimeInfo.innerHTML = info;
            console.log("end");
        }
    }

    // Lắng nghe sự kiện click vào các ngày đặc biệt
    document.addEventListener("click", function (event) {
        if (event.target.classList.contains("special-day")) {
            console.log("start");
            handleSpecialDayClick(event);
        }
    });
});

const htmlGilf = `<img src="https://cdn-icons-png.flaticon.com/512/6664/6664427.png" width="20px">`;
