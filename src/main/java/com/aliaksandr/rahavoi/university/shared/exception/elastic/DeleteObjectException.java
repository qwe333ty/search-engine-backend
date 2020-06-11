package com.aliaksandr.rahavoi.university.shared.exception.elastic;

public class DeleteObjectException extends BaseElasticsearchException {
    public DeleteObjectException(Throwable cause) {
        super("Delete object from index", cause);
    }
}
