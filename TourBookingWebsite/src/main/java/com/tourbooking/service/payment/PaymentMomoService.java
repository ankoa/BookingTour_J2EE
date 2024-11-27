package com.tourbooking.service.payment;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourbooking.config.ConfigCloudinary;
import com.tourbooking.model.Booking;
import com.tourbooking.config.payment.MomoConfig;
import com.tourbooking.service.BookingService;
import com.tourbooking.utils.PaymentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


@Service
@RequiredArgsConstructor
public class PaymentMomoService {

    private final MomoConfig momoConfig;

    @Autowired
    BookingService bookingService;
    
    @Autowired
    private ConfigCloudinary configCloudinary;

    private String sendPaymentRequestToMomo(Map<String, String> requestBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpURLConnection connection = (HttpURLConnection) new URL(momoConfig.getPayUrl()).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Ghi dữ liệu vào body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Đọc response từ MoMo
        int statusCode = connection.getResponseCode();
        if (statusCode == 200) { // Thành công
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Parse JSON response để lấy paymentUrl
                JsonNode jsonResponse = objectMapper.readTree(response.toString());
                return jsonResponse.get("payUrl").asText();
            }
        } else {
            throw new IOException("Failed to connect to MoMo. HTTP code: " + statusCode);
        }
    }

    public PaymentDTO.PaymentResponse createMomoPayment(Booking booking){
        if (booking == null) {
            return PaymentDTO.PaymentResponse.builder()
                    .code("error")
                    .message("Booking not found")
                    .build();
        }

        // Tính tổng số tiền
        long amount = booking.getTotalPrice() - booking.getTotalDiscount();

        // Tạo payload request body
        Map<String, String> requestBody = momoConfig.getMomoConfig(booking.getBookingId());

        // if the value is greater than 1.000.000  then decrease 
        while (amount>1000000) {
            amount=amount/10;
        }
        requestBody.put("amount", amount+"");
        requestBody.put("orderInfo", "Payment for order " + booking.getBookingId());
        requestBody.put("requestId", String.valueOf(System.currentTimeMillis()));

        //sắp xếp theo aphab
        Map<String, String> sortedTreeRequestBody = new TreeMap<>(requestBody);

        // Tạo chữ ký (signature)
        String rawSignature = PaymentUtils.buildRawSignature(sortedTreeRequestBody);
        String signature = PaymentUtils.hmacSHA256(momoConfig.getSecretkey(), rawSignature);

        Map<String, String> finalParams = new HashMap<>(sortedTreeRequestBody);
        finalParams.put("signature", signature);
        finalParams.put("lang", "vi");

        String paymentUrl;
        try {
            paymentUrl = sendPaymentRequestToMomo(finalParams);
        } catch (Exception e) {
            return PaymentDTO.PaymentResponse.builder()
                    .code("error")
                    .message("Error during payment request: " + e.getMessage())
                    .build();
        }

        // Trả về URL thanh toán
        return PaymentDTO.PaymentResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

    public boolean OrderSuccess(String orderId) {
        return bookingService.orderSuccess(orderId);
    }


}
