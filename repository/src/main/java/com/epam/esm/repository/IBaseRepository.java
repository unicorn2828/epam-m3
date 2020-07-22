package com.epam.esm.repository;

import com.epam.esm.model.BaseModel;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<T extends BaseModel> {

    List<T> findAll(int pageNumber, int pageSize, String sql);

    Optional<T> find(long id);

    T save(T t);
}