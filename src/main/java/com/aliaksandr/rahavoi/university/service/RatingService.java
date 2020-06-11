package com.aliaksandr.rahavoi.university.service;

import com.aliaksandr.rahavoi.university.dto.RecalculateRatingDto;

public interface RatingService {

    Float recalculateRating(RecalculateRatingDto dto);
}
