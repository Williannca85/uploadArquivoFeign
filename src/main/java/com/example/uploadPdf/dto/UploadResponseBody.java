package com.example.uploadPdf.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UploadResponseBody {

    @JsonProperty("links")
    private List<Link> links;

    @JsonProperty("id_arquivo")
    private String idArquivo;

    @JsonProperty("versao_arquivo")
    private Integer versaoArquivo;

    @JsonProperty("tamanho_arquivo")
    private Integer tamanhoArquivo;

    @JsonProperty("tipo_arquivo")
    private String tipoArquivo;

    @JsonProperty("hash_arquivo")
    private String hashArquivo;

    @JsonProperty("nome_arquivo")
    private String nomeArquivo;
}


