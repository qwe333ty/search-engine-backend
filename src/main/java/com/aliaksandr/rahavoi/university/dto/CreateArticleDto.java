package com.aliaksandr.rahavoi.university.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateArticleDto {

    @JsonProperty("header")
    private String header;

    @JsonProperty("message")
    private String message;

    @JsonProperty("originalDate")
    private LocalDateTime originalDate;
}
