package com.aliaksandr.rahavoi.university.elastic.repository;

import java.util.List;

public interface CrudRepository<T, ID> {

    <S extends T> S save(S object);

    void deleteById(ID id);

    List<T> search();

    T findById(ID id);

    boolean existsById(ID id);
}
