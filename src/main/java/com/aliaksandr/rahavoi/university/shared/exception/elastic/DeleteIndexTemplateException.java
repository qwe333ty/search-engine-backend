package com.aliaksandr.rahavoi.university.shared.exception.elastic;

public class DeleteIndexTemplateException extends BaseElasticsearchException {
    public DeleteIndexTemplateException(Throwable cause) {
        super("Delete index template", cause);
    }
}
