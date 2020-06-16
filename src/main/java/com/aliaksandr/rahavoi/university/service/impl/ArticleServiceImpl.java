package com.aliaksandr.rahavoi.university.service.impl;

import com.aliaksandr.rahavoi.university.dto.CreateArticleDto;
import com.aliaksandr.rahavoi.university.dto.SearchArticlesDto;
import com.aliaksandr.rahavoi.university.dto.UpdateArticleDto;
import com.aliaksandr.rahavoi.university.elastic.repository.ArticleRepository;
import com.aliaksandr.rahavoi.university.model.Article;
import com.aliaksandr.rahavoi.university.rating.RatingEngine;
import com.aliaksandr.rahavoi.university.service.ArticleService;
import com.aliaksandr.rahavoi.university.shared.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;

    private final RatingEngine ratingEngine;

    @Override
    public Article createArticle(CreateArticleDto dto) {
        Article article = new Article();
        article.setId(UUID.randomUUID().toString());
        article.setOriginalDate(dto.getOriginalDate());
        article.setHeader(dto.getHeader());
        article.setMessage(dto.getMessage());
        article.setCreatedWhen(LocalDateTime.from(new Date().toInstant().atZone(ZoneId.of("UTC"))));
        article.setRating(this.ratingEngine.initialRating());

        Pair<Float, Long> initialValues = this.ratingEngine.initialScoresAndVotes();
        article.setScores(initialValues.getLeft());
        article.setVotes(initialValues.getRight());

        return this.repository.create(article);
    }

    @Override
    public Article updateArticle(String id, UpdateArticleDto dto) {
        Article article = new Article();
        article.setId(id);
        article.setHeader(dto.getHeader());
        article.setMessage(dto.getMessage());
        article.setOriginalDate(dto.getOriginalDate());
        article.setUpdatedWhen(LocalDateTime.from(new Date().toInstant().atZone(ZoneId.of("UTC"))));
        return this.repository.update(article);
    }

    @Override
    public Article getArticleById(String articleId) {
        return this.repository.findById(articleId);
    }

    @Override
    public List<Article> searchArticles(SearchArticlesDto dto) {
        return this.repository.search(dto.getSearchText(), dto.getTerms());
    }

    @Override
    public void deleteArticle(String articleId) {
        if (this.repository.existsById(articleId)) {
            this.repository.deleteById(articleId);
        }
    }
}
