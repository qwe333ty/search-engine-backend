package com.aliaksandr.rahavoi.university.elastic.index;

import java.util.Map;

/**
 * Интерфейс предоставляет основные операции,
 * выполняемые Elasticsearch.
 */
public interface IndexOperations {

    void createTemplate(String templateName, String templatePattern, Map<String, Object> mapping);

    void deleteTemplate(String templateName);

    void createIndex(String indexName);

    void deleteIndex(String indexName);
}
