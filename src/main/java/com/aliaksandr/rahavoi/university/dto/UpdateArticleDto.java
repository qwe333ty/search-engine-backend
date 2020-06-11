package com.aliaksandr.rahavoi.university.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UpdateArticleDto {

    @JsonProperty("header")
    private String header;

    @JsonProperty("message")
    private String message;

    @JsonProperty("originalDate")
    private OffsetDateTime originalDate;
}
