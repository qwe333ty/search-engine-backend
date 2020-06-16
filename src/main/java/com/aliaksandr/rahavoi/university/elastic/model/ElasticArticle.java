package com.aliaksandr.rahavoi.university.elastic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ElasticArticle {

    //обязателен для конвертаци в Article модель
    //метаданные
    @JsonIgnore
    private String id;

    @JsonProperty("header")
    private String header;

    @JsonProperty("message")
    private String message;

    @JsonProperty("score")
    private Float score;

    @JsonProperty("votes")
    private Long votes;

    @JsonProperty("rating")
    private Float rating;

    @JsonProperty("created_when")
    private LocalDateTime createdWhen;

    @JsonProperty("updated_when")
    private LocalDateTime updatedWhen;

    @JsonProperty("original_date")
    private LocalDateTime originalDate;
}
