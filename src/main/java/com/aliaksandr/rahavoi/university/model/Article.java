package com.aliaksandr.rahavoi.university.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_header")
    private String header;

    @JsonProperty("_message")
    private String message;

    @JsonProperty("_rating")
    private Float rating;

    @JsonProperty("_createdWhen")
    private OffsetDateTime createdWhen;

    @JsonProperty("_updatedWhen")
    private OffsetDateTime updatedWhen;

    @JsonProperty("_originalDate")
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
