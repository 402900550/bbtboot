package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.entity.Grade;
import com.hfkj.bbt.entity.TimeTable;

import java.util.List;

public interface ITimeTableService {

    //返回学校年级
    List<TimeTable> findByGrade(Grade grade);
    //批量保存作息表
    int saveTables(List<TimeTable> array);
    //删除单个
    int deleteTable(Long id);

    void initTimeLists(Grade grade);

}
