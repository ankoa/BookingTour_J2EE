document.addEventListener("DOMContentLoaded", function () {

    function generateItemAdult(index) {
        const html = `
    <div class="item p-3 rounded-2 mt-2 border-secondary border">
      <div class="item-title">
          <h4>Người lớn</h4>
      </div>
      <ul class="item-list p-0">
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
              <input type="date" name="birthday-adult-${index}" class="w-100" required>
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

    function generateItemChild(index) {
        const html = `
    <div class="item p-3 rounded-2 mt-2 border-secondary border">
      <div class="item-title">
          <h4>Trẻ em</h4>
      </div>
      <ul class="item-list p-0">
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
              <input type="date" name="birthday-child-${index}" class="w-100" required>
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
    let discountValue = 0;
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
                if (type === "child")
                    addItem(type)
                if (type === "adult")
                    addItem(type)

            } else {
                alert("Da Dat Gioi Han");
            }
        ChangedElementsPrice();
    }

    function addItem(type) {
        if (type === "child") {
            countChild++;
            elementItemsChildInfo.appendChild(generateItemChild(countChild));
            elementCounterValueChild.value = countChild;
        }
        if (type === "adult") {
            countAdult++;
            elementItemsAdultInfo.appendChild(generateItemAdult(countAdult));
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
        const totalPrice = priceTotalAdult + priceTotalChild - discountValue;
        elementPriceAdult.innerHTML = `${countAdult} x <b>${priceAdult.toLocaleString('vi-VN')} ₫</b>`;
        elementPriceChild.innerHTML = `${countChild} x <b>${priceChild.toLocaleString('vi-VN')} ₫</b>`;
        elementPriceCustomer.innerHTML = `<b>${(priceTotalAdult + priceTotalChild).toLocaleString('vi-VN')} ₫</b>`;
        elementTotalPrice.innerHTML = `<b>${(totalPrice > 0 ? totalPrice : 0).toLocaleString('vi-VN')} ₫</b>`;
        elementValueDiscount.innerHTML = `- <b>${discountValue.toLocaleString('vi-VN')} ₫</b>`
    }

    async function fetchDiscountByCode(discountCode) {
        try {
            const response = await fetch(`/api/discount?code=${discountCode}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                const discount = await response.json();
                console.log('Discount:', discount);
                discountValue = discount.discountValue;
                return true; // Mã giảm giá hợp lệ
            } else {
                console.log('Mã giảm giá không tìm thấy');
                return false; // Mã giảm giá không hợp lệ
            }
        } catch (error) {
            console.error('Error fetching discount:', error);
            discountCode = 0;
            return false;
        }
    }

    document.getElementById("btn-check-discount").addEventListener("click", async () => {
        const isValid = await fetchDiscountByCode(document.getElementById("value-code-voucher").value);

        if (isValid.discountValue>0) {
            document.getElementById("box-mess-voucher").textContent = `Áp dụng mã giảm giá thành công`;
            document.getElementById("box-mess-voucher").classList.remove("border-danger");
            document.getElementById("box-mess-voucher").classList.add("border-success", "p-2", "border");
            ChangedElementsPrice();
        } else {
            document.getElementById("box-mess-voucher").textContent = `Áp dụng mã giảm giá không thành công`;
            document.getElementById("box-mess-voucher").classList.remove("border-success");
            document.getElementById("box-mess-voucher").classList.add("border-danger", "p-2", "border");
            ChangedElementsPrice();
        }
    });


    document.getElementById("submit-form").addEventListener("submit", async function (event) {
        event.preventDefault();

        const formData = new FormData(this);

        const processedData = {
            name: formData.get('name'),
            phone: formData.get('phone'),
            address: formData.get('address'),
            voucherCode: formData.get("value-code-voucher"),
            adults: [],
            children: [],
            note: formData.get('note'),
            tourTimeId: tourTimeResponse.tourTimeId,
            accountId: Number(formData.get('accountId'))
        };
        for (let i = 0; i < formData.get('valueAdult'); i++)
            processedData.adults.push({
                customerName: formData.get(`name-adult-${i}`),
                sex: formData.get(`sex-adult-${i}`),
                birthday: formData.get(`birthday-adult-${i}`)
            });
        for (let i = 0; i < formData.get('valueChild'); i++)
            processedData.children.push({
                customerName: formData.get(`name-child-${i}`),
                sex: formData.get(`sex-child-${i}`),
                birthday: formData.get(`birthday-child-${i}`)
            });


        try {
            // Gửi dữ liệu qua API
            const response = await fetch("/api/order-booking/submit-form", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(processedData),
            });
            console.log(processedData)

            if (!response.ok) {
                alert("Gửi dữ liệu thất bại!");
                return
            }

            const result = await response.json();
            if (result.status === 200)
                alert("Đặt tour thành công")
        } catch (error) {
            console.error("Lỗi:", error);
        }
    });

    handleChangeQuantity("plus", "adult")
});
