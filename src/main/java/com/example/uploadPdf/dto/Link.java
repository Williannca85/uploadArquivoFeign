package com.example.uploadPdf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    @JsonProperty("rel")
    private String rel;

    @JsonProperty("href")
    private String href;

    @JsonProperty("media")
    private String media;

    @JsonProperty("title")
    private String title;

    @JsonProperty("type")
    private String type;
}
