package com.aliaksandr.rahavoi.university.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {

    @JsonProperty("id")
    private String id;

    @JsonProperty("header")
    private String header;

    @JsonProperty("message")
    private String message;

    @JsonProperty("rating")
    private Float rating;

    @JsonProperty("createdWhen")
    private OffsetDateTime createdWhen;

    @JsonProperty("updatedWhen")
    private OffsetDateTime updatedWhen;

    @JsonProperty("originalDate")
    private OffsetDateTime originalDate;

    //обязательное поля для конвертации из ElasticArticle модели
    //метаданные
    @JsonIgnore
    private Float scores;

    //обязательное поля для конвертации из ElasticArticle модели
    //метаданные
    @JsonIgnore
    private Long votes;
}
