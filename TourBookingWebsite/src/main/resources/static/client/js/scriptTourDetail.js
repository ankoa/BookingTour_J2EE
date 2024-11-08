document.addEventListener("DOMContentLoaded", function () {
    const swiperWrapper = document.querySelector(".swiper-wrapper");
    const tourTimeDetail = document.querySelector("#tour-time-detail");
    const tourTimeInfo = document.querySelector("#tour-info-content");


    function getDayFromDate(value) {
        const dateObj = new Date(value);
        return dateObj.getDate();
    }

    function getMonthFromDate(value) {
        const dateObj = new Date(value);
        return dateObj.getMonth();
    }

    function renderTourTimeDetail(tourTime) {
        let details = `
        <p>Mã Tour: ${tourTime.tourTimeCode}</p>
        <p>Khởi hành tại: ${tourTime.transportResponses[0] ? tourTime.transportResponses[0].departureLocation : "Đang cập nhật"}</p>
        <p>Ngày Khởi hành: ${tourTime.departureTime}</p>
        <p>Thời gian: ${tourTime.dayStay}</p>
        <p>Số chỗ còn lại: ${tourTime.remainPax<=0?"Hết ":tourTime.remainPax} chỗ</p>
        <div class="book">
            <p>Giá bán: `;
            if(tourTime.isDiscount) details+= `<del>${tourTime.priceAdult.toLocaleString('vi-VN')}</del>`;
        details+=`<span class="text-danger fw-bold">${tourTime.isDiscount ? (tourTime.priceAdult - tourTime.discountValue).toLocaleString('vi-VN') : tourTime.priceAdult.toLocaleString('vi-VN')} </span>`;
        details+=` VND</p>`;
        if(tourTime.remainPax>0)
            details+=`<a class="btn btn-danger w-100" href="/order-booking?tourTimeId=${tourTime.tourTimeId}">Đặt tour</a>`
        details+=`</div>`;
        return details;
    }

    function renderTourTimeInfo(tourTime) {
        const info = `
            <h4>Phương tiện di chuyển</h4>
            <div class="row w-100">
                <div class="col col-md-6 col col-12">
                    ${tourTime.transportResponses.map(transport =>
            transport.isOutbound ? `
                        <div class="d-flex justify-content-between gap-2">
                            <p>Ngày đi - ${dayjs(transport.departureTime).format("DD/MM/YYYY")}</p>
                            <p>${transport.transportCode}</p>
                        </div>
                        <div class="d-flex justify-content-between gap-2 border-bottom border-4 border-black">
                            <p>${dayjs(transport.departureTime).format("HH:mm")}</p>
                            <p>${dayjs(transport.arrivalTime).format("HH:mm")}</p>
                        </div>
                        <div class="d-flex justify-content-between gap-2">
                            <p>${transport.departureLocation}</p>
                            <p>${transport.transportName}</p>
                            <p>${transport.destinationLocation}</p>
                        </div>` : `
                    `).join('')}
                </div>
                <div class="col col-md-6 col col-12">
                    ${tourTime.transportResponses.map(transport =>
            !transport.isOutbound ? `
                        <div class="d-flex justify-content-between gap-2">
                            <p>Ngày Về - ${dayjs(transport.departureTime).format("DD/MM/YYYY")}</p>
                            <p>${transport.transportCode}</p>
                        </div>
                        <div class="d-flex justify-content-between gap-2 border-bottom border-4 border-black">
                            <p>${dayjs(transport.departureTime).format("HH:mm")}</p>
                            <p>${dayjs(transport.arrivalTime).format("HH:mm")}</p>
                        </div>
                        <div class="d-flex justify-content-between gap-2">
                            <p>${transport.departureLocation}</p>
                            <h5>${transport.transportName}</h5>
                            <p>${transport.destinationLocation}</p>
                        </div>` : `
                    `).join('')}
                </div>
            </div>
            <h4>Giá Tour</h4>
            <div class="row w-100">
                <div class="col col-6">
                    <div class="d-flex justify-content-between gap-2">
                        <p>Người lớn</p>
                        <p>${tourTime.priceAdult} đ</p>
                    </div>
                </div>
                <div class="col col-6">
                    <div class="d-flex justify-content-between gap-2">
                        <p>Trẻ em</p>
                        <p>${tourTime.priceChild} đ</p>
                    </div>
                </div>
            </div>
            <div class="note border-3 border border-warning p-2 rounded-2 color-white">${tourTime.note}</div>`;
        return info;
    }

    // Hàm kiểm tra sự kiện đặc biệt
    function getTourTimeEvent(month, day) {
        const tourTime = tourTimes.find((m) => (getMonthFromDate(m.departureTime) === month) && getDayFromDate(m.departureTime) === day);
        return tourTime ? tourTime : null;
    }

    // Hàm tạo các ngày trong một tháng
    function renderDaysInMonth(date) {
        const day = date.getDate();
        const month = date.getMonth();
        const year = date.getFullYear();
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        const firstDay = new Date(date.getFullYear(), date.getMonth(), 1).getDay();

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

        for (let i = 1; i <= daysInMonth; i++) {
            const specialEvent = getTourTimeEvent(month, i);
            if (specialEvent) {
                daysHTML += `<div class="day cursor-pointer special-day" data-month="${month}" data-day="${i}">`;
                if (specialEvent.isDiscount)
                    daysHTML += `${i} <img src="https://cdn-icons-png.flaticon.com/512/6664/6664427.png" alt="" width="20px"><br>${
                        specialEvent.priceAdult / 1000
                    }k</div>`;
                else daysHTML += `${i} <br>${specialEvent.priceAdult / 1000}k</div>`;
            } else {
                daysHTML += `<div class="day cursor-pointer">${i}</div>`;
            }
        }

        return `<div class="days">${daysHTML}</div>`;
    }

    // Tạo các slide cho mỗi tháng
    const TourTimesRendered = new Map();
    tourTimes.forEach((monthData) => {
        const month = new Date(monthData.departureTime);

        const monthKey = `${month.getFullYear()}-${month.getMonth() + 1}`;
        if (!TourTimesRendered.has(monthKey)) {

            const monthSlide = document.createElement("div");
            monthSlide.classList.add("swiper-slide");

            // Thêm tên tháng và các ngày trong tháng
            monthSlide.innerHTML =
                `<h2>Tháng ${getMonthFromDate(month) + 1}</h2>` + renderDaysInMonth(month);

            // Thêm slide vào Swiper
            swiperWrapper.appendChild(monthSlide);
            TourTimesRendered.set(monthKey, []);
        }
        // Kiểm tra nếu tháng có dữ liệu


    });

    // Hàm xử lý khi nhấp vào ngày đặc biệt
    function handleSpecialDayClick(event) {
        const month = event.target.getAttribute("data-month");
        const day = event.target.getAttribute("data-day");

        const specialEvent = getTourTimeEvent(Number(month), Number(day));

        if (specialEvent) {
            tourTimeInfo.innerHTML = renderTourTimeInfo(specialEvent);
            tourTimeDetail.innerHTML = renderTourTimeDetail(specialEvent);
        }
    }

    // Lắng nghe sự kiện click vào các ngày đặc biệt
    document.addEventListener("click", function (event) {
        if (event.target.classList.contains("special-day")) {
            handleSpecialDayClick(event);
        }
    });

    //js chuyển ảnh
    document.querySelectorAll('.img-tour').forEach(function (img) {
        img.addEventListener('click', function () {
            // Lấy nguồn ảnh của hình ảnh đã nhấp
            const selectedSrc = this.getAttribute('src');

            // Cập nhật ảnh trong panel
            document.getElementById('mainImage').setAttribute('src', selectedSrc);

            document.querySelectorAll('.img-tour').forEach(function (img) {
                img.classList.remove('active');
            });
            this.classList.add('active');
        });
    });

    const swiper = new Swiper(".mySwiper", {
        spaceBetween: 30,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        allowTouchMove: false,
    });
});


