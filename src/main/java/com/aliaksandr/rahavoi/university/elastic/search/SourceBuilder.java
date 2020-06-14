package com.aliaksandr.rahavoi.university.elastic.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

public final class SourceBuilder {

    private static final String RATING = "rating";

    private SourceBuilder() {
    }

    public static SearchSourceBuilder build(final QueryBuilder query) {
        final SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource().query(query);
        appendSort(sourceBuilder);
        return sourceBuilder;
    }

    private static void appendSort(final SearchSourceBuilder sourceBuilder) {
        sourceBuilder.sort(RATING, SortOrder.DESC);
    }
}
