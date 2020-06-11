package com.aliaksandr.rahavoi.university.web;

import com.aliaksandr.rahavoi.university.dto.CreateArticleDto;
import com.aliaksandr.rahavoi.university.dto.SearchArticlesDto;
import com.aliaksandr.rahavoi.university.dto.UpdateArticleDto;
import com.aliaksandr.rahavoi.university.model.Article;
import com.aliaksandr.rahavoi.university.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody CreateArticleDto dto) {
        Article article = this.articleService.createArticle(dto);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Article> updateArticle(@RequestBody UpdateArticleDto dto,
                                                 @PathVariable("id") String id) {
        Article article = this.articleService.updateArticle(id, dto);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") String articleId) {
        Article article = this.articleService.getArticleById(articleId);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Article>> searchArticles(@RequestBody SearchArticlesDto dto) {
        List<Article> articles = this.articleService.searchArticles(dto);
        if (CollectionUtils.isEmpty(articles)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") String articleId) {
        this.articleService.deleteArticle(articleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
