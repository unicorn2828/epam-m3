package com.epam.esm.repository.impl;

import com.epam.esm.model.BaseModel;
import com.epam.esm.repository.IBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public abstract class BaseAbstractRepository<T extends BaseModel> implements IBaseRepository<T> {

    private final EntityManager entityManager;
    private final Class<T> clazz;

    public BaseAbstractRepository(EntityManager entityManager, Class<T> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    @Override
    public Optional<T> find(long id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public T save(T t) {
        entityManager.persist(t);
        entityManager.flush();
        return entityManager.find(clazz, t.getId());
    }

    @Override
    public List<T> findAll(int pageNumber, int pageSize, String sql) {
        TypedQuery<T> query = entityManager.createQuery(sql, clazz);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}
