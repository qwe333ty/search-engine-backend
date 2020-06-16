package com.aliaksandr.rahavoi.university.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;
import java.util.Map;

@Data
public class SearchArticlesDto {

    @JsonProperty("searchText")
    private String searchText;

    @JsonProperty("terms")
    private Map<String, Collection<Object>> terms;
}
