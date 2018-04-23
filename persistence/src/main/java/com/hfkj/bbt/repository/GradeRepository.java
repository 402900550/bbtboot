package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.Grade;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.repository.base.BaseRepository;

import java.util.List;

public interface GradeRepository extends BaseRepository<Grade,Long> {


    List<Grade> findBySchool(School school);
}
