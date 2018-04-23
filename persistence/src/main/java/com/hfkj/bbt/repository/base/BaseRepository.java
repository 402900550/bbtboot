package com.hfkj.bbt.repository.base;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends JpaRepository<T,ID> {

    List<Object[]> findListBySql(String sql);

    List<Object[]> findListBySql(String sql,Map<String,Object> param);

    List<Object[]> findListByJpql(String jpql);

    int executeUpdateBySql(String sql, Map<String,Object> param);

    List<Object[]> findListByJpql(String jpql,Map<String,Object> param);
}
