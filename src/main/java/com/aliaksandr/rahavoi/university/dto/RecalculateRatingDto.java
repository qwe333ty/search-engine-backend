package com.aliaksandr.rahavoi.university.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecalculateRatingDto {

    @JsonProperty("articleId")
    private String articleId;

    @JsonProperty("userEstimation")
    private Float userEstimation;
}
