package com.hfkj.bbt.systemanage.impl;

import com.hfkj.bbt.entity.Grade;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.repository.GradeRepository;
import com.hfkj.bbt.systemanage.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GradeServiceImpl implements IGradeService {
    @Autowired
    private GradeRepository gradeRepository;
    /**
     *  //返回学校年级
     *
     * @param school@return
     */
    public List<Grade> findBySchool(School school){
        return gradeRepository.findBySchool(school);
    }


    public Grade findGrade(Long gradeId){
        return gradeRepository.findOne(gradeId);
    }

}
