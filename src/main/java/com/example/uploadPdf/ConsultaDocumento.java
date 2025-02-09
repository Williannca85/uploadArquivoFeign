package com.example.uploadPdf;

import com.example.uploadPdf.dto.UploadResponseBody;
import org.springframework.context.annotation.Configuration;
import com.example.uploadPdf.exception.ConsultaDocumentoException;

@Configuration
public interface ConsultaDocumento {


  UploadResponseBody uploadDocumento(String idArquivo,
                                         byte[] request,
                                         String nomeArquivo) throws ConsultaDocumentoException;
}
