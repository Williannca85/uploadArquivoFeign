package com.example.uploadPdf.exception;

public class ConsultaDocumentoException extends RuntimeException {

   private String idDocumento;

   public ConsultaDocumentoException(
         String message,
         Throwable exception,
         String idDocumento) {
      super(message, exception);
      this.idDocumento = idDocumento;
   }

   public ConsultaDocumentoException(String message, String idDocumento) {
      super(message);
      this.idDocumento = idDocumento;
   }

   public ConsultaDocumentoException(String message, Throwable e) {
      super(message, e);
   }

   public ConsultaDocumentoException(String message) {
      super(message);
   }

   public String getIdDocumento() {
      return idDocumento;
   }
}