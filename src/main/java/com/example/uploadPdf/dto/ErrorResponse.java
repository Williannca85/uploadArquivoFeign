package com.example.uploadPdf.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
   private String mensagem;
}
