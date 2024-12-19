document.addEventListener("DOMContentLoaded", function () {

    async function fetchData(buttonPage) {
        let data;
        try {
            console.log(`http://localhost:8080/api/account-info?page=${buttonPage}`)
            const response = await fetch(`http://localhost:8080/api/account-info?page=${buttonPage}`);
             data = await response.json();
            console.log(data)
        } catch (error) {
            console.error('Có lỗi xảy ra:', error);
        }
        renderTable(data);
    }

    const buttons = document.querySelectorAll('.fetch-button');
    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const buttonPage = this.getAttribute('data-page');
            fetchData(buttonPage);
        });
    });


     function renderTable(bookingResponses) {
        // Lấy phần tử tbody
        const tableBody = document.getElementById("booking-table-body");

        tableBody.innerHTML = '';
        bookingResponses.forEach(bookingResponse => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${bookingResponse.bookingId}</td>
                <td>${bookingResponse.tourTimeResponse.tourTimeCode}</td>
                <td>${bookingResponse.adultCount + bookingResponse.childCount}</td>
                <td>${bookingResponse.time}</td>
                <td>${bookingResponse.paymentMethod}</td>
                <td>${bookingResponse.status == 0 ? 'Đã hủy' : (bookingResponse.status == 1 ? 'Chưa thanh toán' : 'Đã thanh toán')}</td>
                <td><a href="/booking/${bookingResponse.bookingId}">Chi Tiết</a></td>
            `;

            tableBody.appendChild(row);
        });
    }
})