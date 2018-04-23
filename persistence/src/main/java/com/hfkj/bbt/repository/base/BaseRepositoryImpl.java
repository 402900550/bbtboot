package com.hfkj.bbt.repository.base;


import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    public BaseRepositoryImpl(Class<T> tClass, EntityManager entityManager) {
        super(tClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Object[]> findListBySql(String sql) {
        return entityManager.createNativeQuery(sql).getResultList();
    }

    @Override
    public List<Object[]> findListBySql(String sql, Map<String, Object> param) {
        Query query = entityManager.createNativeQuery(sql);
        if (param != null) {
            Set<Map.Entry<String, Object>> entries = param.entrySet();
            for (Map.Entry entry : entries) {
                query.setParameter(String.valueOf(entry.getKey()), entry.getValue());
            }
        }
        return query.getResultList();
    }

    @Override
    public List<Object[]> findListByJpql(String jpql) {
        Query query = entityManager.createQuery(jpql);
        return query.getResultList();
    }



    @Override
    public int executeUpdateBySql(String sql, Map<String, Object> param) {
        Query query = entityManager.createNativeQuery(sql);
        if (param != null) {
            Set<Map.Entry<String, Object>> entries = param.entrySet();
            for (Map.Entry entry : entries) {
                query.setParameter(String.valueOf(entry.getKey()), entry.getValue());
            }
        }

        return query.executeUpdate();
    }

    @Override
    public List<Object[]> findListByJpql(String jpql, Map<String, Object> param) {
        Query query = entityManager.createQuery(jpql);
        if (param != null) {
            Set<Map.Entry<String, Object>> entries = param.entrySet();
            for (Map.Entry entry : entries) {
                query.setParameter(String.valueOf(entry.getKey()), entry.getValue());
            }
        }
        return query.getResultList();
    }

}
