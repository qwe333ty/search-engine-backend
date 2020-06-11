package com.aliaksandr.rahavoi.university.elastic.model;

import com.aliaksandr.rahavoi.university.model.Article;
import com.aliaksandr.rahavoi.university.rating.RatingEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Elastic2RestConverter implements Converter<ElasticArticle, Article> {

    private final RatingEngine ratingEngine;

    @Override
    public Article convert(ElasticArticle elastic) {
        Article rest = new Article();
        rest.setId(elastic.getId());
        rest.setHeader(elastic.getHeader());
        rest.setMessage(elastic.getMessage());
        rest.setCreatedWhen(elastic.getCreatedWhen());
        rest.setUpdatedWhen(elastic.getUpdatedWhen());
        rest.setOriginalDate(elastic.getOriginalDate());

        Float rating = this.ratingEngine.calculateRating(elastic.getScore(), elastic.getVotes());
        rest.setRating(rating);

        //дополнительные поля, требуемые для пересчета
        rest.setScores(elastic.getScore());
        rest.setVotes(elastic.getVotes());

        return rest;
    }
}
