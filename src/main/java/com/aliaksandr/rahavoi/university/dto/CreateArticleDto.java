package com.aliaksandr.rahavoi.university.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CreateArticleDto {

    @JsonProperty("_header")
    private String header;

    @JsonProperty("_message")
    private String message;

    @JsonProperty("_originalDate")
    private OffsetDateTime originalDate;
}
