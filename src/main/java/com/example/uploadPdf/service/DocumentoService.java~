package com.example.uploadPdf.service;

import com.example.uploadPdf.ConsultaDocumento;
import com.example.uploadPdf.dto.UploadResponseBody;
import com.example.uploadPdf.exception.ConsultaDocumentoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DocumentoService {

   private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoService.class);
   @Autowired
   private final ConsultaDocumento consultaDocumento;

   public DocumentoService(ConsultaDocumento consultaDocumento) {
      this.consultaDocumento = consultaDocumento;
   }


   public UploadResponseBody uploadDocumento(String idDocumento,
                                                byte[] request,
                                                String nomeArquivo) throws ConsultaDocumentoException {

      if (idDocumento == null || idDocumento.isEmpty()) {
         LOGGER.error("[ERROR] O ID do documento não pode ser nulo ou vazio");
         throw new ConsultaDocumentoException("O ID do documento não pode ser nulo ou vazio");
      }

      if (request == null || request.length == 0) {
         LOGGER.error("[ERROR] A requisição do documento nao pode ser nula ou vazia");
         throw new ConsultaDocumentoException("A requisição do documento nao pode ser nula ou vazia");
      }

      if (nomeArquivo == null || nomeArquivo.isEmpty()) {
         LOGGER.error("[ERROR] O nome do documento nao pode ser nulo ou vazio");
         throw new ConsultaDocumentoException("O nome do documento nao pode ser nulo ou vazio");
      }

      try {
         return consultaDocumento.uploadDocumento(
               idDocumento,
               request,
               nomeArquivo);
      } catch (ConsultaDocumentoException e) {
         LOGGER.error("[ERROR] Ocorreu um erro no upload do documento", e);
         throw e;
      }
   }

}
