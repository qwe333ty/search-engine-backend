package com.aliaksandr.rahavoi.university.elastic.mapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * Предоставляет все маппинги для Elasticsearch,
 * доступные в приложении.
 */
@Component
public class MappingProvider implements InitializingBean {

    @Value("${spring.elasticsearch.info.mapping}")
    private Resource mappingResource;

    private Map<String, Object> articleMapping;

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream mappingStream = this.mappingResource.getInputStream();
        this.articleMapping = new ObjectMapper().readValue(mappingStream, new TypeReference<Map<String, Object>>() {
        });
    }

    public Map<String, Object> provideArticleMapping() {
        return this.articleMapping;
    }
}
