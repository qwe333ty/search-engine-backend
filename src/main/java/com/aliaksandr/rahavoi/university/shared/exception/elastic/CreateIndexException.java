package com.aliaksandr.rahavoi.university.shared.exception.elastic;

public class CreateIndexException extends BaseElasticsearchException {
    public CreateIndexException(Throwable cause) {
        super("Create index", cause);
    }
}
