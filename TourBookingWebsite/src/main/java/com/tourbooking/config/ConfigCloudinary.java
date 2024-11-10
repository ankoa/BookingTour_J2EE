package com.tourbooking.config;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigCloudinary {

    // Định nghĩa Cloudinary Bean
    @Bean
    public Cloudinary configKey() {
    	Map config = new HashMap();
    	config.put("cloud_name", "drybe720w");
    	config.put("api_key", "328935211357267");
    	config.put("api_secret", "eaDdEvcb1IkctNIznI34poBW8_k");
    	Cloudinary cloudinary = new Cloudinary(config);
		return new Cloudinary(config);
    }
}
