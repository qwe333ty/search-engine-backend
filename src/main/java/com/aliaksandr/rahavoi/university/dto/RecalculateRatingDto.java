package com.aliaksandr.rahavoi.university.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecalculateRatingDto {

    @JsonProperty("_articleId")
    private String articleId;

    @JsonProperty("_userEstimation")
    private Float userEstimation;
}
