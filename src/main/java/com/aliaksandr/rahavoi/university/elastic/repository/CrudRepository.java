package com.aliaksandr.rahavoi.university.elastic.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CrudRepository<T, ID> {

    T create(T object);

    T update(T object);

    void deleteById(ID id);

    List<T> search(String searchText, Map<String, Collection<Object>> terms);

    T findById(ID id);

    boolean existsById(ID id);
}
