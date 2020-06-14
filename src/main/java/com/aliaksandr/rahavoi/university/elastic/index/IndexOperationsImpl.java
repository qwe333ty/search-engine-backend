package com.aliaksandr.rahavoi.university.elastic.index;

import com.aliaksandr.rahavoi.university.shared.exception.elastic.CreateIndexException;
import com.aliaksandr.rahavoi.university.shared.exception.elastic.CreateIndexTemplateException;
import com.aliaksandr.rahavoi.university.shared.exception.elastic.DeleteIndexException;
import com.aliaksandr.rahavoi.university.shared.exception.elastic.DeleteIndexTemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.cluster.storedscripts.PutStoredScriptRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class IndexOperationsImpl implements IndexOperations {

    private final RestHighLevelClient elasticClient;

    @Override
    public void createTemplate(String templateName, String templatePattern, Map<String, Object> mapping) {
        PutIndexTemplateRequest putIndexTemplateRequest = new PutIndexTemplateRequest(templateName);
        putIndexTemplateRequest.mapping(mapping);
        putIndexTemplateRequest.patterns(Collections.singletonList(templatePattern));
        try {
            log.info("Creating {} template with {} template pattern...", templateName, templatePattern);
            this.elasticClient.indices().putTemplate(putIndexTemplateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during index template creation");
            throw new CreateIndexTemplateException(e);
        }
        log.info("{} template was successfully created", templateName);
    }

    @Override
    public void deleteTemplate(String templateName) {
        DeleteIndexTemplateRequest deleteIndexTemplateRequest = new DeleteIndexTemplateRequest(templateName);
        try {
            log.info("Deleting {} template...", templateName);
            this.elasticClient.indices().deleteTemplate(deleteIndexTemplateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during index template deleting");
            throw new DeleteIndexTemplateException(e);
        }
        log.info("{} template was successfully deleted", templateName);
    }

    @Override
    public void createIndex(String indexName) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        try {
            log.info("Creating index with name {}...", indexName);
            this.elasticClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during index creation");
            throw new CreateIndexException(e);
        }
        log.info("{} index was successfully created", indexName);
    }

    @Override
    public void deleteIndex(String indexName) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        try {
            log.info("Deleting {} index...", indexName);
            this.elasticClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Occurred exception during index deleting");
            throw new DeleteIndexException(e);
        }
        log.info("{} index was successfully deleted", indexName);
    }
}
