package com.example.uploadPdf.impl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.uploadPdf.ConsultaDocumento;
import com.example.uploadPdf.headers.STSHeaders;
import com.example.uploadPdf.exception.ConsultaDocumentoException;
import com.example.uploadPdf.client.UploadClient;
import com.example.uploadPdf.dto.UploadResponseBody;
import com.example.uploadPdf.dto.UploadResponseDataBody;

import feign.FeignException;
import java.util.UUID;

@Component
public class DocumentoRestImpl implements ConsultaDocumento {

   private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DocumentoRestImpl.class);

   @Autowired
   private final UploadClient client;
   private final STSHeaders stsHeaders;

   public DocumentoRestImpl(UploadClient client, STSHeaders stsHeaders) {
      this.client = client;
      this.stsHeaders = stsHeaders;
   }

   @Override
   public UploadResponseBody uploadDocumento(String idArquivo,
                                             byte[] arquivo,
                                             String nomeArquivo) throws ConsultaDocumentoException {
      String correlationId = UUID.randomUUID().toString();
      LOGGER.debug("Iniciando upload de documento {}", idArquivo);

    try {
        // Separação de responsabilidades: criar um método separado para fazer o upload
        ResponseEntity<UploadResponseDataBody> response = fazerUpload(idArquivo,
                                                                          arquivo, 
                                                                          nomeArquivo);

        // Verificar a resposta
        verificarResposta(response, idArquivo, correlationId);

        // Retornar o corpo da resposta
        return response.getBody().getData();
    } catch (FeignException e) {
        // Tratamento de erros: capturar erros e registrar mensagens de erro
        String mensagemErro = String.format("Erro ao fazer upload de documento %s: %s  ;; correlationId: %s",
                                            idArquivo, e.getMessage());
        LOGGER.error(mensagemErro, e);
        throw new ConsultaDocumentoException(mensagemErro, e, idArquivo);
    }
}

   private ResponseEntity<UploadResponseDataBody> fazerUpload(String idArquivo,
                                                              byte[] arquivo,
                                                              String nomeArquivo) throws ConsultaDocumentoException {
      // Validar os parâmetros de entrada
      if (idArquivo == null || idArquivo.isEmpty()) {
         LOGGER.error("ID do arquivo não pode ser nulo ou vazio");
         throw new ConsultaDocumentoException("ID do arquivo não pode ser nulo ou vazio", idArquivo);
      }

      if (arquivo == null || arquivo.length == 0) {
         LOGGER.error("Arquivo não pode ser nulo ou vazio");
         throw new ConsultaDocumentoException("Arquivo não pode ser nulo ou vazio", idArquivo);
      }

      if (nomeArquivo == null || nomeArquivo.isEmpty()) {
         LOGGER.error("Nome do arquivo não pode ser nulo ou vazio");
         throw new ConsultaDocumentoException("Nome do arquivo não pode ser nulo ou vazio", idArquivo);
      }

      // Criar um cabeçalho de solicitação com o nome do arquivo e o tamanho do arquivo
      HttpHeaders headers = stsHeaders.getHttpHeaders(nomeArquivo, arquivo.length);

      // Fazer o upload do arquivo
      return client.postUploadDocumentos(idArquivo, arquivo, headers);
   }

  private void verificarResposta(ResponseEntity<UploadResponseDataBody> response, String idArquivo, String correlationId) {
        // Verificar se a resposta foi bem-sucedida
        if (response.getStatusCode().is2xxSuccessful()) {
            LOGGER.debug("Upload de documento {} bem-sucedido", idArquivo);
        } else {
            // Registrar mensagem de erro se a resposta não foi bem-sucedida
           String mensagemErro = String.format("Erro ao fazer upload de documento %s: %s ;; correlationId: %s",
                 idArquivo, response.getStatusCode(), correlationId);
          LOGGER.error(mensagemErro);
          throw new ConsultaDocumentoException(mensagemErro, idArquivo);
      }
  }

}