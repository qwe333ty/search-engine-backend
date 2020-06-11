package com.aliaksandr.rahavoi.university.shared.exception.elastic;

public class SaveObjectException extends BaseElasticsearchException {
    public SaveObjectException(Throwable cause) {
        super("Save object to index", cause);
    }
}
