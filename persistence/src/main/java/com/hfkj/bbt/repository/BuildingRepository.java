package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.Building;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.repository.base.BaseRepository;

import java.util.List;

public interface BuildingRepository extends BaseRepository<Building, Long> {

    /**
     * 根据学校单位代码查询教学楼
     * @param school
     * @return
     */
    List<Building> findBySchool(School school);

}
