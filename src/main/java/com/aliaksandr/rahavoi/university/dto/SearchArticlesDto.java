package com.aliaksandr.rahavoi.university.dto;

import lombok.Data;

import java.util.Collection;
import java.util.Map;

@Data
public class SearchArticlesDto {

    private String searchText;

    private Map<String, Collection<Object>> terms;
}
