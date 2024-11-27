document.addEventListener("DOMContentLoaded", function () {

    function generateItemAdult(index) {
        const html = `
        <li class="item d-flex justify-content-between row">
          <div class="input col-sm-4">
              Họ tên
              <input type="text" name="name-adult-${index}" class="w-100" required>
          </div>
          <div class="input col-sm-4">
              Giới tính
              <select name="sex-adult-${index}" class="w-100" required>
                  <option  value="1 ">Nam</option>
                  <option value="2">Nữ</option>
              </select>
          </div>
          <div class="input col-sm-4">
              Ngày sinh
              <input type="date" name="birthday-adult-${index}" class="w-100" required  min="1900-01-01" max="2025-01-31">
          </div>
        </li>
      `;
        // Tạo một div tạm để chuyển đổi chuỗi HTML thành Node
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = html;

        // Trả về phần tử đầu tiên trong tempDiv, nó là Node
        return tempDiv.firstElementChild;
    }

    function generateItemChild(index) {
        const html = `
        <li class="item d-flex justify-content-between row">
          <div class="input col-sm-4">
              Họ tên
              <input type="text" name="name-child-${index}" class="w-100" required>
          </div>
          <div class="input col-sm-4">
              Giới tính
              <select name="sex-child-${index}" class="w-100" required>
                  <option value="1">Nam</option>
                  <option value="2">Nữ</option>
              </select>
          </div>
          <div class="input col-sm-4">
              Ngày sinh
              <input type="date" name="birthday-child-${index}" class="w-100" required min="1900-01-01" max="2025-01-31">
          </div>
        </li>
      `;
        // Tạo một div tạm để chuyển đổi chuỗi HTML thành Node
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = html;

        // Trả về phần tử đầu tiên trong tempDiv, nó là Node
        return tempDiv.firstElementChild;
    }

    // element chỉnh so luong nguoi lon/ tre em
    const elementMinusBtnChild = document.getElementById("minus-btn-child");
    const elementPlusBtnChild = document.getElementById("plus-btn-child");
    const elementCounterValueChild = document.getElementById("value-child");
    const elementMinusBtnAdult = document.getElementById("minus-btn-adult");
    const elementPlusBtnAdult = document.getElementById("plus-btn-adult");
    const elementCounterValueAdult = document.getElementById("value-adult");

    const elementItemsAdultInfo = document.getElementById("items-adult");
    const elementItemsChildInfo = document.getElementById("items-child");

    const elementPriceCustomer = document.getElementById("price-customer");
    const elementPriceAdult = document.getElementById("price-adult");
    const elementPriceChild = document.getElementById("price-child");
    const elementTotalPrice = document.getElementById("total-price");
    const elementValueDiscount = document.getElementById("value-discount");

    const buttonSubmit = document.getElementById("btn-submit");

    let countChild = 0;
    let countAdult = 0;
    let totalPrice = 0
    let discountValue = 0;
    let voucherValue = 0
    let priceAdult = tourTimeResponse.priceAdult;
    let priceChild = tourTimeResponse.priceChild;

    if (tourTimeResponse.isDiscount) {
        priceAdult = tourTimeResponse.priceAdult - tourTimeResponse.discountValue;
        priceChild = tourTimeResponse.priceChild - tourTimeResponse.discountValue;
    }

    // Hàm giảm giá trị
    elementMinusBtnChild.addEventListener("click", () => {
            handleChangeQuantity("minus", "child");
        }
    );
    elementPlusBtnChild.addEventListener("click", () => {
            handleChangeQuantity("plus", "child");
        }
    );
    elementMinusBtnAdult.addEventListener("click", () => {
            handleChangeQuantity("minus", "adult");
        }
    );
    elementPlusBtnAdult.addEventListener("click", () => {
            handleChangeQuantity("plus", "adult");
        }
    );

    function handleChangeQuantity(action, type) {
        if (action === "minus") {
            if (type === "child" && countChild > 0)
                removeLastItem(type)
            if (type === "adult" && countAdult > 1)
                removeLastItem(type)
        }
        if (action === "plus")
            if ((countChild + countAdult) < tourTimeResponse.remainPax) {
                addItem(type)
            } else {
                showToast("Da Dat Gioi Han");
            }
        ChangedElementsPrice();
    }

    function addItem(type) {
        if (type === "child") {
            countChild++;
            elementItemsChildInfo.appendChild(generateItemChild(countChild-1));
            elementCounterValueChild.value = countChild;
        }
        if (type === "adult") {
            countAdult++;
            elementItemsAdultInfo.appendChild(generateItemAdult(countAdult-1));
            elementCounterValueAdult.value = countAdult;
        }
    }

    function removeLastItem(type) {
        if (type === "child") {
            countChild--;
            if (elementItemsChildInfo.lastElementChild) {
                elementItemsChildInfo.removeChild(elementItemsChildInfo.lastElementChild);
            }
            elementCounterValueChild.value = countChild;
        }
        if (type === "adult") {
            countAdult--;
            if (elementItemsAdultInfo.lastElementChild) {
                elementItemsAdultInfo.removeChild(elementItemsAdultInfo.lastElementChild);
            }
            elementCounterValueAdult.value = countAdult;
        }
    }

    function ChangedElementsPrice() {
        let priceTotalAdult = priceAdult * countAdult;
        let priceTotalChild = priceChild * countChild;
        totalPrice = priceTotalAdult + priceTotalChild - voucherValue - discountValue;
        elementPriceAdult.innerHTML = `${countAdult} x <b>${priceAdult.toLocaleString('vi-VN')} ₫</b>`;
        elementPriceChild.innerHTML = `${countChild} x <b>${priceChild.toLocaleString('vi-VN')} ₫</b>`;
        elementPriceCustomer.innerHTML = `<b>${(priceTotalAdult + priceTotalChild).toLocaleString('vi-VN')} ₫</b>`;
        elementTotalPrice.innerHTML = `<b>${(totalPrice > 0 ? totalPrice : 0).toLocaleString('vi-VN')} ₫</b>`;
        elementValueDiscount.innerHTML = `- <b>${voucherValue.toLocaleString('vi-VN')} ₫</b>`
    }

    async function fetchVoucher(voucherCode) {
        try {
            const response = await fetch(`/api/discount?code=${voucherCode}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                const voucher = await response.json();
                console.log('Voucher:', voucher);
                if (voucher.discountValue > 0) return voucher.discountValue;
                return 0;
            } else {
                console.log('Mã giảm giá không tìm thấy');
                return 0;
            }
        } catch (error) {
            console.error('Error fetching discount:', error);
            return 0;
        }
    }

    async function fetchOrderBooking(processedData) {
        try {
            // Gửi dữ liệu qua API
            const response = await fetch("/api/order-booking/submit-form", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(processedData),
            });
            if (!response.ok) {
                showToast("Gửi dữ liệu thất bại!");
                return
            }
            return await response.json();
        } catch (error) {
            console.error("Lỗi:", error);
        }
    }

    document.getElementById("btn-check-discount").addEventListener("click", async () => {
        voucherValue = await fetchVoucher(document.getElementById("value-code-voucher").value);
        const boxMessVoucher=document.getElementById("box-mess-voucher");
        if (voucherValue > 0) {
            boxMessVoucher.textContent = `Áp dụng mã giảm giá thành công`;
            boxMessVoucher.classList.remove("border-danger");
            boxMessVoucher.classList.add("border-success", "p-2", "border");
        } else {
            boxMessVoucher.textContent = `Áp dụng mã giảm giá không thành công`;
            boxMessVoucher.classList.remove("border-success");
            boxMessVoucher.classList.add("border-danger", "p-2", "border");
        }
        ChangedElementsPrice();
    });


    document.getElementById("submit-form").addEventListener("submit", async function (event) {
        event.preventDefault();

        const formData = new FormData(this);
        const processedData = {
            relatedCustomer:{
                customerName: formData.get('name'),
                phoneNumber: formData.get('phone'),
                address: formData.get('address'),
            },
            voucherCode: formData.get("value-code-voucher"),
            adults: [],
            children: [],
            tourTimeId: tourTimeResponse.tourTimeId,
            accountId: Number(formData.get('accountId')),
            paymentMethod: (formData.get('payment-method')),
        };
        for (let i = 0; i < formData.get('valueAdult'); i++){
            processedData.adults.push({
                customerName: formData.get(`name-adult-${i}`),
                sex: formData.get(`sex-adult-${i}`),
                birthday: formData.get(`birthday-adult-${i}`)
            });
        }
        for (let i = 0; i < formData.get('valueChild'); i++){
            processedData.children.push({
                customerName:formData.get(`name-child-${i}`),
                sex:formData.get(`sex-child-${i}`),
                birthday:formData.get(`birthday-child-${i}`)
            });
        }

        const result = await fetchOrderBooking(processedData);
        if (result.code === 'ok') {
            showToast("Đặt chỗ thành công")
            if (result.paymentUrl!=='') {
                window.location.href = result.paymentUrl;
            }
            else window.location.href = '/booking/'+result.bookingId;

        }
        else showToast(result.message)
    });

    handleChangeQuantity("plus", "adult")
});
