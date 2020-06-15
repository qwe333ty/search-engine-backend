package com.aliaksandr.rahavoi.university.elastic.repository;

import com.aliaksandr.rahavoi.university.elastic.model.ElasticArticle;
import com.aliaksandr.rahavoi.university.elastic.search.SearchQueryProvider;
import com.aliaksandr.rahavoi.university.elastic.search.SourceBuilder;
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
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    public Article create(Article object) {
        final String id = object.getId();
        final ElasticArticle elasticArticle = this.conversionService.convert(object, ElasticArticle.class);
        final Map<String, Object> sourceMap = this.objectMapper.convertValue(
                elasticArticle,
                new TypeReference<Map<String, Object>>() {
                }
        );

        final IndexRequest indexRequest = new IndexRequest(this.indexName);
        indexRequest.id(object.getId());
        indexRequest.source(sourceMap);
        try {
            this.elasticClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during creating object: id={}, source={} in index {}",
                    id, elasticArticle, this.indexName);
            throw new SaveObjectException(e);
        }
        return object;
    }

    @Override
    public Article update(Article object) {
        final String id = object.getId();
        final ElasticArticle elasticArticle = this.conversionService.convert(object, ElasticArticle.class);
        final Map<String, Object> sourceMap = this.objectMapper.convertValue(
                elasticArticle,
                new TypeReference<Map<String, Object>>() {
                }
        );
        final UpdateRequest updateRequest = new UpdateRequest(this.indexName, id);
        updateRequest.doc(sourceMap, XContentType.JSON);
        try {
            this.elasticClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during saving to index {} next object: id={}, source={}",
                    this.indexName, id, elasticArticle);
            throw new SaveObjectException(e);
        }
        return object;
    }


    @Override
    public void deleteById(String id) {
        final DeleteRequest deleteRequest = new DeleteRequest(indexName).id(id);
        try {
            this.elasticClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during deleting object with id={} from {} index", id, this.indexName);
            throw new DeleteObjectException(e);
        }
    }

    @Override
    public List<Article> search(String searchText, Map<String, Collection<Object>> terms) {
        final QueryBuilder query = SearchQueryProvider.ftsAndFilters(searchText, terms);
        final SearchSourceBuilder sourceBuilder = SourceBuilder.build(query);

        final SearchRequest searchRequest = new SearchRequest(this.indexName);
        searchRequest.source(sourceBuilder);
        List<Article> articles = new ArrayList<>();
        try {
            final SearchResponse searchResponse = this.elasticClient.search(searchRequest, RequestOptions.DEFAULT);
            final SearchHit[] hits = searchResponse.getHits().getHits();
            for (SearchHit hit : hits) {
                final ElasticArticle elasticArticle =
                        this.objectMapper.convertValue(hit.getSourceAsMap(), ElasticArticle.class);
                elasticArticle.setId(hit.getId());
                final Article article = this.conversionService.convert(elasticArticle, Article.class);
                articles.add(article);
            }
        } catch (IOException e) {
            log.error("Occurred exception during searching with text={} and terms={}", searchText, terms);
            throw new ExistsObjectException(e);
        }
        return articles;
    }

    @Override
    public boolean existsById(String id) {
        GetRequest getRequest = new GetRequest(this.indexName);
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
        GetRequest getRequest = new GetRequest(this.indexName);
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
