package com.aliaksandr.rahavoi.university.shared.exception.elastic;

public abstract class BaseElasticsearchException extends RuntimeException {

    protected static final String ELASTIC_EXCEPTION_PATTERN = "Elasticsearch exception. Operation: %s";

    public BaseElasticsearchException(String operation, Throwable cause) {
        super(String.format(ELASTIC_EXCEPTION_PATTERN, operation), cause);
    }
}
