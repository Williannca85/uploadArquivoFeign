package com.example.uploadPdf.controllers;

import com.example.uploadPdf.dto.UploadResponseBody;
import com.example.uploadPdf.dto.ErrorResponse;
import com.example.uploadPdf.service.DocumentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import net.logstash.logback.argument.StructuredArguments;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.uploadPdf.exception.ConsultaDocumentoException;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@RestController
@RequestMapping("/conteudos/x1")
public class DocumentoController {

   private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoController.class);
   private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // 10MB
   private static final byte[] PDF_SIGNATURE = new byte[] {0x25, 0x50, 0x44, 0x46};

   @Autowired
   private final DocumentoService documentoService;

   @Autowired
   public DocumentoController(DocumentoService documentoService) {
      this.documentoService = documentoService;
   }

   @PostMapping(value = "/arquivos/{arquivoId}/uploadDocumento")
   public ResponseEntity<?> uploadDocumentos(
         @PathVariable("arquivoId") String arquivoId,
         @RequestBody byte[] request,
         @RequestHeader("nome_arquivo") String nomeArquivo) {
      String correlationId = UUID.randomUUID().toString();
      MDC.put("correlationId", correlationId);

      LOGGER.info("[DEBUG] Dados de entrada para upload de documentos",
            StructuredArguments.kv("arquivoId", arquivoId),
            StructuredArguments.kv("nomeArquivo", nomeArquivo),
            StructuredArguments.kv("correlationId", correlationId));

      if (request.length > MAX_FILE_SIZE) {
         LOGGER.error("Tamanho do arquivo excede o limite",
               StructuredArguments.kv("arquivoId", arquivoId),
               StructuredArguments.kv("requestLength", request.length),
               StructuredArguments.kv("correlationId", correlationId));
         return errorResponse("Tamanho do arquivo excede o limite permitido de 10MB.");
      }

      if (!isValidFile(request)) {
         LOGGER.error("Arquivo inválido",
               StructuredArguments.kv("arquivoId", arquivoId),
               StructuredArguments.kv("correlationId", correlationId));
         return errorResponse("Arquivo inválido. Certifique-se de que é um PDF e que tem um tamanho válido.");
      }

      try {
         UploadResponseBody uploadResponseBody = documentoService.uploadDocumento(arquivoId, request, nomeArquivo);
         LOGGER.info("Upload realizado com sucesso",
               StructuredArguments.kv("arquivoId", arquivoId),
               StructuredArguments.kv("correlationId", correlationId));
         return ResponseEntity.ok(uploadResponseBody);
      } catch (ConsultaDocumentoException e) {
         LOGGER.error("Erro ao realizar upload do arquivo",
               StructuredArguments.kv("arquivoId", arquivoId),
               StructuredArguments.kv("correlationId", correlationId), e);
         return errorResponse("Erro interno ao realizar upload do arquivo.");
      }
   }

   private boolean isValidFile(byte[] request) {
      return request.length > PDF_SIGNATURE.length && startsWithPdfSignature(request) && request.length <= MAX_FILE_SIZE;
   }

   private boolean startsWithPdfSignature(byte[] request) {
      for (int i = 0; i < PDF_SIGNATURE.length; i++) {
         if (request[i] != PDF_SIGNATURE[i]) {
            return false;
         }
      }
      return true;
   }

   private ResponseEntity<ErrorResponse> errorResponse(String mensagem) {
      ErrorResponse errorResponse = ErrorResponse.builder()
            .mensagem(mensagem)
            .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
   }
}
