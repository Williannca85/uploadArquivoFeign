package com.example.uploadPdf.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.uploadPdf.client.UploadClient;

import feign.Feign;
import feign.codec.ErrorDecoder;
import feign.Logger;
//import org.springframework.cloud.openfeign.FeignContext;

@Configuration
public class FeignConfig {

  @Bean
  Logger.Level feignLoggerLevel(){
      return Logger.Level.FULL;
  }

  	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}


}
