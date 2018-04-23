package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.entity.Grade;
import com.hfkj.bbt.entity.School;

import java.util.List;

public interface IGradeService {

    //返回学校年级
    List<Grade> findBySchool(School school);

    //根据ID获取年级
    Grade findGrade(Long gradeId);
}
