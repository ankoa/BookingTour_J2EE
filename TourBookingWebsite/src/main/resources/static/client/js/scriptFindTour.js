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

const transportTags = document.querySelectorAll(".tour-transports .tag-container-item");
transportTags.forEach((item) => {
    item.addEventListener("click", function () {
        transportTags.forEach((i) => i.classList.remove("active"));
        this.classList.add("active");
    });
});