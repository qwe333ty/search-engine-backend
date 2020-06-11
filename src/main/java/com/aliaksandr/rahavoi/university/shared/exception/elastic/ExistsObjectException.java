package com.aliaksandr.rahavoi.university.shared.exception.elastic;

public class ExistsObjectException extends BaseElasticsearchException {
    public ExistsObjectException(Throwable cause) {
        super("Check if object exists in index", cause);
    }
}
