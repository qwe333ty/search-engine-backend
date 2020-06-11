package com.aliaksandr.rahavoi.university.elastic.mapping;

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
        this.articleMapping = new Yaml().load(mappingStream);
    }

    public Map<String, Object> provideArticleMapping() {
        return this.articleMapping;
    }
}
