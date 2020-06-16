package com.aliaksandr.rahavoi.university.elastic.model;

import com.aliaksandr.rahavoi.university.model.Article;
import com.aliaksandr.rahavoi.university.rating.RatingEngine;
import com.aliaksandr.rahavoi.university.shared.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Данный конвертер позволяет сделать преобразование Rest модели
 * в Elasticsearch модель.
 *
 * Ожидается, что конвертер будет использоваться
 * только во время операции сохранения сущности в Elasticsearch.
 */
@Component
@RequiredArgsConstructor
public class Rest2ElasticConverter implements Converter<Article, ElasticArticle> {

    private final RatingEngine ratingEngine;

    @Override
    public ElasticArticle convert(Article rest) {
        ElasticArticle elastic = new ElasticArticle();
        elastic.setId(rest.getId());
        elastic.setHeader(rest.getHeader());
        elastic.setMessage(rest.getMessage());
        elastic.setCreatedWhen(rest.getCreatedWhen());
        elastic.setUpdatedWhen(rest.getUpdatedWhen());
        elastic.setOriginalDate(rest.getOriginalDate());
        elastic.setScore(rest.getScores());
        elastic.setVotes(rest.getVotes());
        elastic.setRating(rest.getRating());
        return elastic;
    }
}
