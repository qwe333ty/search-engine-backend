package com.aliaksandr.rahavoi.university.elastic.model;

import com.aliaksandr.rahavoi.university.model.Article;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Elastic2RestConverter implements Converter<ElasticArticle, Article> {

    @Override
    public Article convert(ElasticArticle elastic) {
        Article rest = new Article();
        rest.setId(elastic.getId());
        rest.setHeader(elastic.getHeader());
        rest.setMessage(elastic.getMessage());
        rest.setCreatedWhen(elastic.getCreatedWhen());
        rest.setUpdatedWhen(elastic.getUpdatedWhen());
        rest.setOriginalDate(elastic.getOriginalDate());
        rest.setRating(elastic.getRating());
        rest.setScores(elastic.getScore());
        rest.setVotes(elastic.getVotes());
        return rest;
    }
}
