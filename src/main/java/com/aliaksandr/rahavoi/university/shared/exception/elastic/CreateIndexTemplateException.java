package com.aliaksandr.rahavoi.university.shared.exception.elastic;

public class CreateIndexTemplateException extends BaseElasticsearchException {
    public CreateIndexTemplateException(Throwable cause) {
        super("Create index template", cause);
    }
}
