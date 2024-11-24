document.addEventListener("DOMContentLoaded", function () {

    fetchCardFilter(0);

    function fetchCardFilter(page) {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
        const budget = document.querySelector(".budget-item.active")?.getAttribute("data-value").split("-");
        const formData = {
            min: budget ? budget[0] : "",
            max: budget ? budget[1] : "",
            search: document.getElementById("search").value,
            departureDate: document.getElementById("departureDate").value,
            categoryId: document.querySelector(".tag-container-item.active")?.value || "",
            page: page,
            size: 6
        }

        const params = new URLSearchParams(formData);
        const apiUrl = `/api/tours?${params.toString()}`;
        fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            return response.json();
        }).then((data) => {
            console.log(data)
            renderCardFilter(data)
        })
    }

    function renderCardFilter(data) {
        document.getElementById("totalCount").innerText = data?.totalElements
        const innerHtml = data.content.map(t => {
            return `<div class="card-filter">
                        <div class="card-thumbnail">
                            <img
                                    alt="Đông Nam Bộ"
                                    loading="lazy"
                                    width="0"
                                    height="0"
                                    decoding="async"
                                    data-nimg="1"
                                    src=${t.tourImages[0] ? t.tourImages[0].imageUrl : "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGXbTGoxQL8XMvTihnWUFA93sSGkFnQfMjwg&s"}
                                    style="color: transparent"
                            />
                            <div class="card-tag">
                                <i></i>
                                <span>${t.category?.categoryName}</span>
                            </div>
                        </div>

                        <div class="card-content">
                            <div class="card-content-info">
                                <div class="content-header">
                                    <a href="" title="" class=""
                                    >
                                    ${t.tourName}
                                    </a>
                                </div>
                                <div class="content-info-tour">
                                    <div class="info-tour-row">
                                        <div class="tour-code">
                                            <div class="label-wrap">
                                                <i class="fa-solid fa-ticket"></i>
                                                <label for="">Mã tour:</label>
                                            </div>
                                            <p>${t.tourCode}</p>
                                        </div>
<!--                                        <div class="tour-departure">-->
<!--                                            <div class="label-wrap">-->
<!--                                                <i class="fa-solid fa-location-dot"></i>-->
<!--                                                <label for="">Khởi hành:</label>-->
<!--                                            </div>-->
<!--                                            <p>TP. Hồ Chí Minh</p>-->
<!--                                        </div>-->
                                            <div class="tour-time">
                                            <div class="label-wrap">
                                                <i class="fa-solid fa-clock"></i>
                                                <label for="">Thời gian:</label>
                                            </div>
                                            <p>${t.dayStay} ngày</p>
                                        </div>
                                    </div>
                                   </div>
                            </div>
                            <div class="card-content-price">
                                <div class="content-price-wrapper">
                                    <div class="content-price-old">
                                        <label for="">Giá từ: </label>
<!--                                        <span>19.990.000 ₫</span>-->
                                    </div>
                                    <div class="content-price-new">
                                        <p>${formatNumber(t.tourPrice)} ₫</p>
                                    </div>
                                </div>
                                <div class="content-price-btn">
                                    <a class="btn btn-primary btn-md" href="/tour/${t.tourId}">Xem chi tiết</a>
                                </div>
                            </div>
                        </div>
                    </div>`;
        })
        document.getElementById("content").innerHTML = innerHtml
        renderPagination(data.totalPages, data.number)
    }

    function formatNumber(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    }

    function renderPagination(totalPages, currentPage) {
        const paginationContainer = document.getElementById("pagination-container");
        paginationContainer.innerHTML = "";
        if (totalPages === 0) return
        // Previous button
        const prevButton = document.createElement("button");
        prevButton.textContent = "Previous";
        prevButton.disabled = currentPage === 0;
        prevButton.onclick = () => fetchCardFilter(currentPage - 1);
        paginationContainer.appendChild(prevButton);

        // Page buttons
        for (let i = 0; i < totalPages; i++) {
            const pageButton = document.createElement("button");
            pageButton.textContent = i + 1;
            pageButton.classList.toggle("active", i === currentPage);
            pageButton.onclick = () => fetchCardFilter(i);
            paginationContainer.appendChild(pageButton);
        }

        // Next button
        const nextButton = document.createElement("button");
        nextButton.textContent = "Next";
        nextButton.disabled = currentPage === totalPages - 1;
        nextButton.onclick = () => fetchCardFilter(currentPage + 1);
        paginationContainer.appendChild(nextButton);
    }

    const closeOverlay = () => {
        document.querySelector(".overlay").style.display = "none"; // Ẩn overlay
    };

    const openOverlay = () => {
        document.querySelector(".overlay").style.display = "flex"; // Hiển thị overlay
    };

    const openFilterMobile = () => {
        const filter = document.querySelector(".filter");
        filter.classList.add("active");
        openOverlay();
        document.body.classList.add("no-scroll");
    };

    const closeFilterMobile = () => {
        closeOverlay();
        const filter = document.querySelector(".filter");
        filter.classList.toggle("active");
        document.body.classList.remove("no-scroll");
    };

    document
        .querySelector(".filter-btn-mobile")
        .addEventListener("click", openFilterMobile);

    document
        .querySelector(".overlay")
        .addEventListener("click", closeFilterMobile);

    document
        .querySelector(".close-btn")
        .addEventListener("click", closeFilterMobile);

    const budgetItems = document.querySelectorAll(".budget-item");
    budgetItems.forEach((item) => {
        item.addEventListener("click", function () {
            budgetItems.forEach((i) => i.classList.remove("active"));

            this.classList.add("active");
        });
    });

    const categoryTags = document.querySelectorAll(".tour-categories .tag-container-item");
    categoryTags.forEach((item) => {
        item.addEventListener("click", function () {
            categoryTags.forEach((i) => i.classList.remove("active"));
            this.classList.add("active");
        });
    });

    document.getElementById("filter-form").addEventListener("submit", (event) => {
        event.preventDefault();
        fetchCardFilter(0);
    })

    document.getElementById("btnReset").addEventListener("click", (event) => {
        const categoryTags = document.querySelectorAll(".tour-categories .tag-container-item");
        categoryTags.forEach((item) => {
            item.classList.remove("active")
        })
        const budgets = document.querySelectorAll(".budget-item");
        budgetItems.forEach((item) => {
            item.classList.remove("active")
        })

        // document.getElementById("departureDate").reset()
        // document.getElementById("search").reset()
        //
        document.getElementById("filter-form").reset();
    })
})
