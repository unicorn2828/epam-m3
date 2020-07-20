package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.ITagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.repository.data.TagRepositoryData.*;

@Repository
public class TagRepository implements ITagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> findWidelyTag() {
        Query query = entityManager.createNativeQuery(FIND_WIDELY_TAGS_QUERY, Tag.class);
        return query.getResultList();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        TypedQuery<Tag> query = entityManager.createQuery(FIND_BY_NAME_QUERY, Tag.class);
        return Optional.ofNullable(query.setParameter(TAG_NAME, name).getSingleResult());
    }

    @Override
    public Optional<Tag> find(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> findAll(int pageNumber, int pageSize, String sql) {
        TypedQuery<Tag> query = entityManager.createQuery(sql, Tag.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Tag save(Tag tag) {
        entityManager.persist(tag);
        entityManager.flush();
        return entityManager.find(Tag.class, tag.getId());
    }

    @Override
    public void delete(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
        entityManager.flush();
        entityManager.clear();
    }
}
