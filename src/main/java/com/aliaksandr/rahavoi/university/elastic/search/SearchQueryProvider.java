package com.aliaksandr.rahavoi.university.elastic.search;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class SearchQueryProvider {

    private static final String HEADER = "header";

    private static final String MESSAGE = "message";

    private static final String FIELD_NAME_TEMPLATE = "%s.%s";

    private static final String[] DEFAULT_FIELDS = new String[]{
            HEADER,
            MESSAGE,
            String.format(FIELD_NAME_TEMPLATE, HEADER, "ru"),
            String.format(FIELD_NAME_TEMPLATE, MESSAGE, "ru")
    };

    private SearchQueryProvider() {
    }

    public static QueryBuilder ftsAndFilters(String searchText, Map<String, Collection<Object>> terms) {
        final QueryBuilder fts = ftsQuery(searchText);
        if (CollectionUtils.isEmpty(terms)) {
            return fts;
        }

        final BoolQueryBuilder filters = filtersQuery(terms);
        if (fts != null) {
            filters.must(fts);
        }
        return filters;
    }

    private static QueryBuilder ftsQuery(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return null;
        }
        return QueryBuilders.multiMatchQuery(searchText, DEFAULT_FIELDS)
                .type(MultiMatchQueryBuilder.Type.MOST_FIELDS);
    }

    private static BoolQueryBuilder filtersQuery(Map<String, Collection<Object>> terms) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        for (Map.Entry<String, Collection<Object>> term : terms.entrySet()) {
            Iterator<Object> values = term.getValue().iterator();
            RangeQueryBuilder rangeQ = QueryBuilders.rangeQuery(term.getKey())
                    .from(values.next())
                    .to(values.next());
            boolQuery.must(rangeQ);
        }
        return boolQuery;
    }
}
