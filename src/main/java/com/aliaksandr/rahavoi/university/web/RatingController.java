package com.aliaksandr.rahavoi.university.web;

import com.aliaksandr.rahavoi.university.dto.RecalculateRatingDto;
import com.aliaksandr.rahavoi.university.model.Article;
import com.aliaksandr.rahavoi.university.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rating")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/recalculate")
    public ResponseEntity<Article> recalculateRating(@RequestBody RecalculateRatingDto dto) {
        Article updatedArticle = this.ratingService.recalculateRating(dto);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

}


