package com.example.uploadPdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UploadPdfApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadPdfApplication.class, args);
	}

}
