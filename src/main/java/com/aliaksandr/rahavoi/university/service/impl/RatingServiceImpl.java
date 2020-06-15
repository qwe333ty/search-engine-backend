package com.aliaksandr.rahavoi.university.service.impl;

import com.aliaksandr.rahavoi.university.dto.RecalculateRatingDto;
import com.aliaksandr.rahavoi.university.elastic.repository.ArticleRepository;
import com.aliaksandr.rahavoi.university.model.Article;
import com.aliaksandr.rahavoi.university.rating.RatingEngine;
import com.aliaksandr.rahavoi.university.service.RatingService;
import com.aliaksandr.rahavoi.university.shared.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final ArticleRepository repository;

    private final RatingEngine ratingEngine;

    @Override
    public Float recalculateRating(RecalculateRatingDto dto) {
        Article article = this.repository.findById(dto.getArticleId());

        Pair<Float, Long> pair = this.ratingEngine.recalculateRating(
                article.getScores(), article.getVotes(), dto.getUserEstimation());
        article.setScores(pair.getLeft());
        article.setVotes(pair.getRight());

        Float newRating = this.ratingEngine.calculateRating(pair.getLeft(), pair.getRight());
        article.setRating(newRating);
        return this.repository.update(article).getRating();
    }
}
