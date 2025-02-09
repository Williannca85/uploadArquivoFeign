package com.example.uploadPdf.headers;

import com.example.uploadPdf.exception.ConsultaDocumentoException;
import org.springframework.http.HttpHeaders;
import java.util.UUID;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class STSHeaders {
  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(STSHeaders.class);

  protected static final String HEADER_ACCEPT = "Accept";
  protected static final String HEADER_CONTENT_TYPE = "Content-Type";
  protected static final String HEADER_AUTHORIZATION = "Authorization";
  protected static final String HEADER_API_KEY = "apikey";
  protected static final String HEADER_CORRELATION_ID = "correlationID";
  protected static final String HEADER_FLOW_ID = "flowID";
  protected static final String HEADER_API_ID = "x-apigw-api-id";
  protected static final String HEADER_APP_ID = "appid";
  protected static final String NOME_ARQUIVO = "nome_arquivo";
  protected static final String HEADER_X_CONTENT_LENGTH = "x-content-length";
  protected static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";


    public HttpHeaders getHttpHeaders(String nomeArquivo, int xContentLength) throws ConsultaDocumentoException {

      if (nomeArquivo == null || nomeArquivo.isEmpty()) {
        LOGGER.error("Nome do arquivo não pode ser nulo ou vazio");
        throw new ConsultaDocumentoException("Nome do arquivo não pode ser nulo ou vazio");
      }

      if (xContentLength <= 0) {
        LOGGER.error("Tamanho do arquivo não pode ser menor ou igual a zero");
        throw new ConsultaDocumentoException("Tamanho do arquivo não pode ser menor ou igual a zero");
      }

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.set(HEADER_CONTENT_TYPE, "application/octet-stream");
      httpHeaders.set(HEADER_APP_ID, "eaf784ab-5ae6-41d4-9007-e08957256f50");
      httpHeaders.set(HEADER_API_ID, UUID.randomUUID().toString());
      httpHeaders.set(HEADER_FLOW_ID, "f436b711-49ac-4e41-aab1-b08241604c9b");
      httpHeaders.set(HEADER_API_KEY, "123");
      httpHeaders.set(HEADER_CORRELATION_ID, UUID.randomUUID().toString());
      httpHeaders.set(NOME_ARQUIVO, nomeArquivo);
      httpHeaders.set(HEADER_X_CONTENT_LENGTH, String.valueOf(xContentLength));
      httpHeaders.set(HEADER_TRANSFER_ENCODING, "chunked");
      LOGGER.info("HttpHeaders: " + httpHeaders);
      LOGGER.debug("HttpHeaders: " + httpHeaders);
      return httpHeaders;
  }

}
