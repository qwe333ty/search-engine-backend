package com.aliaksandr.rahavoi.university.service;

import com.aliaksandr.rahavoi.university.dto.CreateArticleDto;
import com.aliaksandr.rahavoi.university.dto.SearchArticlesDto;
import com.aliaksandr.rahavoi.university.dto.UpdateArticleDto;
import com.aliaksandr.rahavoi.university.model.Article;

import java.util.List;

public interface ArticleService {

    Article createArticle(CreateArticleDto dto);

    Article updateArticle(String id, UpdateArticleDto dto);

    Article getArticleById(String articleId);

    List<Article> searchArticles(SearchArticlesDto dto);

    void deleteArticle(String articleId);
}
