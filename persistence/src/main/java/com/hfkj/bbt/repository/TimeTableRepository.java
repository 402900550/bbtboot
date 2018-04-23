package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.Grade;
import com.hfkj.bbt.entity.TimeTable;
import com.hfkj.bbt.repository.base.BaseRepository;

import java.util.List;

public interface TimeTableRepository extends BaseRepository<TimeTable,Long> {

    List<TimeTable> findByGradeOrderByStart(Grade grade);
}
