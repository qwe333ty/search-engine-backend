package com.aliaksandr.rahavoi.university.service;

import com.aliaksandr.rahavoi.university.dto.RecalculateRatingDto;
import com.aliaksandr.rahavoi.university.model.Article;

public interface RatingService {

    Article recalculateRating(RecalculateRatingDto dto);
}
