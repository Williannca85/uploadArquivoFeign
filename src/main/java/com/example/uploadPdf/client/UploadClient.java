package com.example.uploadPdf.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.uploadPdf.config.FeignConfig;
import com.example.uploadPdf.dto.UploadResponseBody;
import com.example.uploadPdf.dto.UploadResponseDataBody;

@FeignClient(name = "Uplad-Client", configuration = FeignConfig.class)
public interface UploadClient {

  @PostMapping(value = "conteudos/x1/arquivos/{id_arquivo}/uploadDocumento")
  ResponseEntity<UploadResponseDataBody> postUploadDocumentos(@PathVariable("id_arquivo") String arquivoId,
                                                             @RequestBody byte[] request,
                                                             @RequestHeader HttpHeaders httpHeaders);
                                                             

}
