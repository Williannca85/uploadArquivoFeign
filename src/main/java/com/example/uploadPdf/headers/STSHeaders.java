package com.example.uploadPdf.headers;

import com.example.uploadPdf.exception.ConsultaDocumentoException;
import org.springframework.http.HttpHeaders;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import net.logstash.logback.argument.StructuredArguments;

import java.util.UUID;

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
      LOGGER.error("Nome do arquivo não pode ser nulo ou vazio",
            StructuredArguments.kv("nomeArquivo", nomeArquivo));
      throw new ConsultaDocumentoException("Nome do arquivo não pode ser nulo ou vazio");
    }

    if (xContentLength <= 0) {
      LOGGER.error("Tamanho do arquivo não pode ser menor ou igual a zero",
            StructuredArguments.kv("xContentLength", xContentLength));
      throw new ConsultaDocumentoException("Tamanho do arquivo não pode ser menor ou igual a zero");
    }

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-Type", "application/octet-stream");
    httpHeaders.set("appid", "eaf784ab-5ae6-41d4-9007-e08957256f50");
    httpHeaders.set("x-apigw-api-id", UUID.randomUUID().toString());
    httpHeaders.set("flowID", "f436b711-49ac-4e41-aab1-b08241604c9b");
    httpHeaders.set("apikey", "123");
    httpHeaders.set("correlationId", UUID.randomUUID().toString());
    httpHeaders.set("nome_arquivo", nomeArquivo);
    httpHeaders.set("x-content-length", String.valueOf(xContentLength));
    httpHeaders.set("Transfer-Encoding", "chunked");
    LOGGER.info("HttpHeaders: {}", StructuredArguments.kv("httpHeaders", httpHeaders));
    LOGGER.debug("HttpHeaders: {}", StructuredArguments.kv("httpHeaders", httpHeaders));
    return httpHeaders;
  }
}