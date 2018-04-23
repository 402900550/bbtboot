package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.Distinct;

import com.hfkj.bbt.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DistinctRepository extends BaseRepository<Distinct, String> {


    Distinct findByCode(String code);
}
