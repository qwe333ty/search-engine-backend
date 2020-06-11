package com.aliaksandr.rahavoi.university.elastic.repository;

import com.aliaksandr.rahavoi.university.elastic.model.ElasticArticle;
import com.aliaksandr.rahavoi.university.model.Article;
import com.aliaksandr.rahavoi.university.shared.exception.elastic.DeleteObjectException;
import com.aliaksandr.rahavoi.university.shared.exception.elastic.ExistsObjectException;
import com.aliaksandr.rahavoi.university.shared.exception.elastic.SaveObjectException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {

    private final RestHighLevelClient elasticClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ConversionService conversionService;

    @Value("${spring.elasticsearch.info.index-name}")
    private String indexName;

    @Override
    public Article save(Article article) {
        final String id = article.getId();
        final ElasticArticle elasticArticle = this.conversionService.convert(article, ElasticArticle.class);
        final Map<String, Object> sourceMap = this.objectMapper.convertValue(
                elasticArticle,
                new TypeReference<Map<String, Object>>() {
                }
        );
        final UpdateRequest updateRequest = new UpdateRequest(indexName, id);
        updateRequest.upsertRequest()
                .id(id)
                .source(sourceMap, XContentType.JSON);
        try {
            this.elasticClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during saving to index {} next object: id={}, source={}",
                    indexName, id, elasticArticle);
            throw new SaveObjectException(e);
        }
        return article;
    }

    @Override
    public void deleteById(String id) {
        final DeleteRequest deleteRequest = new DeleteRequest(indexName).id(id);
        try {
            this.elasticClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during deleting object with id={} from {} index", id, indexName);
            throw new DeleteObjectException(e);
        }
    }

    @Override
    public List<Article> search() {
        return null;
    }

    @Override
    public boolean existsById(String id) {
        GetRequest getRequest = new GetRequest(indexName);
        getRequest.id(id);
        try {
            return this.elasticClient.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during checking if object exists with id={}", id);
            throw new ExistsObjectException(e);
        }
    }

    @Override
    public Article findById(String id) {
        GetRequest getRequest = new GetRequest(indexName);
        getRequest.id(id);
        try {
            GetResponse response = this.elasticClient.get(getRequest, RequestOptions.DEFAULT);
            ElasticArticle elasticArticle = this.objectMapper.convertValue(response.getSource(), ElasticArticle.class);
            elasticArticle.setId(response.getId());
            return this.conversionService.convert(elasticArticle, Article.class);
        } catch (IOException e) {
            log.error("Occurred exception during checking if object exists with id={}", id);
            throw new ExistsObjectException(e);
        }
    }
}
