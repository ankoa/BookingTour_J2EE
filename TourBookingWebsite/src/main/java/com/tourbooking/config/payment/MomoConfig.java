package com.tourbooking.config.payment;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class MomoConfig {
    @Getter
    @Value("${payment.momo.payUrl}")
    private String payUrl;
    @Value("${payment.momo.partnerCode}")
    private String partnerCode;
    @Value("${payment.momo.accessKey}")
    private String accessKey;
    @Value("${payment.momo.returnUrl}")
    private String redirectUrl;
    @Value("${payment.momo.requestType}")
    private String requestType;
    @Getter
    @Value("${payment.momo.secretKey}")
    private String secretkey;
    @Value("${payment.momo.ipnUrl}")
    private String ipnUrl;
    @Value("${payment.momo.extraData}")
    private String extraData;




    public Map<String, String> getMomoConfig(int orderId) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("accessKey",this.accessKey);
        paramsMap.put("partnerCode",this.partnerCode);
        paramsMap.put("redirectUrl",this.redirectUrl);
        paramsMap.put("requestType",this.requestType);
        paramsMap.put("requestId",this.partnerCode+ (new Date()).getTime());
        paramsMap.put("orderInfo", orderId+( new Date()).getTime()+"");
        paramsMap.put("orderId", orderId+"");
        paramsMap.put("ipnUrl", this.ipnUrl);
        paramsMap.put("extraData", this.extraData);
        return paramsMap;
    }
}
