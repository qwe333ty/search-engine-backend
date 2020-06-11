package com.aliaksandr.rahavoi.university.shared.exception.elastic;

public class DeleteIndexException extends BaseElasticsearchException {
    public DeleteIndexException(Throwable cause) {
        super("Delete index", cause);
    }
}
