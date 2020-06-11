package com.aliaksandr.rahavoi.university.elastic.init;

import com.aliaksandr.rahavoi.university.elastic.index.IndexOperations;
import com.aliaksandr.rahavoi.university.elastic.mapping.MappingProvider;
import com.aliaksandr.rahavoi.university.shared.MethodWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class IndexesInitializer implements InitializingBean {

    private final IndexOperations indexOperations;

    private final MappingProvider mappingProvider;

    @Value("${spring.elasticsearch.info.template-name}")
    private String templateName;

    @Value("${spring.elasticsearch.info.template-pattern}")
    private String templatePattern;

    @Value("${spring.elasticsearch.info.index-name}")
    private String indexName;

    @Value("${application.clean-install}")
    private Boolean cleanInstallEnabled;

    @Override
    public void afterPropertiesSet() {
        if (Boolean.TRUE.equals(cleanInstallEnabled)) {
            log.info("Clean install mode enabled");
            cleanElastic();
        }
        updateTemplatesAndIndexes();
    }

    /**
     * Удаляем все содержимое Elasticsearch.
     *
     * Игнорируем ошибки, поскольку не во всех случаях
     * могут существовать индексы или шаблоны.
     */
    private void cleanElastic() {
        MethodWrapper.ignoreException(() -> this.indexOperations.deleteIndex(indexName));
        MethodWrapper.ignoreException(() -> this.indexOperations.deleteTemplate(templateName));
    }

    /**
     * Создаем/обновляем шаблоны или индексы.
     *
     * Ошибки не игнорируются, поскольку Elasticsearch является
     * основным источником данных и без него работа невозможна.
     */
    private void updateTemplatesAndIndexes() {
        this.indexOperations.createTemplate(templateName, templatePattern,
                this.mappingProvider.provideArticleMapping());
        this.indexOperations.createIndex(indexName);
    }
}
