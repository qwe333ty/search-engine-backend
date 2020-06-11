package com.aliaksandr.rahavoi.university.shared.exception.elastic;

public class SearchObjectException extends BaseElasticsearchException {
    public SearchObjectException(Throwable cause) {
        super("Search object in index", cause);
    }
}
