package com.tourbooking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tourbooking.config.CloudinaryTest;

@SpringBootApplication
public class TourBookingWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourBookingWebsiteApplication.class, args);
	}
	/*@Bean
    CommandLineRunner runTest(CloudinaryTest cloudinaryTest) {
        return args -> {
            cloudinaryTest.testConnection();
            cloudinaryTest.testConnectionUpload();
        };
    }*/
}
