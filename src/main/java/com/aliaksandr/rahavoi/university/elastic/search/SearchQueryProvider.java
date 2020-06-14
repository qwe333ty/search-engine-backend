package com.aliaksandr.rahavoi.university.elastic.search;

import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class SearchQueryProvider {

    private static final String DEFAULT_LANGUAGE = "en";

    private static final String FIELD_NAME_TEMPLATE = "%s.%s";

    private static final LanguageDetector LANGUAGE_DETECTOR = new OptimaizeLangDetector().loadModels();

    private static final String HEADER = "header";

    private static final String MESSAGE = "message";

    private static final String[] DEFAULT_FIELDS = new String[]{
            HEADER,
            MESSAGE
    };

    private SearchQueryProvider() {
    }

    public static QueryBuilder ftsAndFilters(String searchText, Map<String, Collection<Object>> terms) {
        final String[] searchFields;
        final LanguageResult languageResult = LANGUAGE_DETECTOR.detect(searchText);
        if (languageResult.isUnknown()) {
            searchFields = DEFAULT_FIELDS;
        } else {
            searchFields = appendLanguagePostfix(languageResult.getLanguage());
        }
        MultiMatchQueryBuilder fts = QueryBuilders.multiMatchQuery(searchText, searchFields)
                .type(MultiMatchQueryBuilder.Type.MOST_FIELDS);
        if (terms.isEmpty()) {
            return fts;
        }

        BoolQueryBuilder mainQuery = QueryBuilders.boolQuery();
        mainQuery.must(fts);
        for (Map.Entry<String, Collection<Object>> term : terms.entrySet()) {
            Iterator<Object> values = term.getValue().iterator();
            RangeQueryBuilder rangeQ = QueryBuilders.rangeQuery(term.getKey())
                    .from(values.next())
                    .to(values.next());
            mainQuery.must(rangeQ);
        }
        return mainQuery;
    }

    private static String[] appendLanguagePostfix(String language) {
        if (DEFAULT_LANGUAGE.equals(language)) {
            return DEFAULT_FIELDS;
        }
        return new String[]{
                String.format(FIELD_NAME_TEMPLATE, HEADER, language),
                String.format(FIELD_NAME_TEMPLATE, MESSAGE, language)
        };
    }

    private void appendSort(QueryBuilder queryBuilder) {

    }
}
