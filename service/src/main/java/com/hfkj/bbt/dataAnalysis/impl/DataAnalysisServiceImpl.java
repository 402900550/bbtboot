package com.hfkj.bbt.dataAnalysis.impl;

import com.hfkj.bbt.dataAnalysis.IDataAnalysisService;
import com.hfkj.bbt.repository.DistinctRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class DataAnalysisServiceImpl implements IDataAnalysisService{

    @Autowired
    private DistinctRepository distinctRepository;

    /**
     * 区县学校使用率展示
     * @return
     */
    public List loadDistinctSchoolALL(String distinctCode){
        String sql = " SELECT " +
                " ts.`name`, " +
                " CONCAT(ROUND(sum(res.number)/sum(res.number_all),2)*100,'%') " +
                " FROM " +
                " tab_school ts " +
                " LEFT JOIN tab_equipment_school res ON res.school_school_code = ts.school_code " +
                " WHERE res.number_all IS NOT NULL AND ts.distinct_code = "+ distinctCode +
                " GROUP BY ts.school_code " +
                " ORDER BY SUM(res.number) / SUM(res.number_all) DESC " ;
        return distinctRepository.findListBySql(sql);
    }

    /**
     * 区县学科使用课时数展示
     * @return
     */
    public List loadDistinctSchoolSubject(String distinctCode){
        String jpql=" SELECT " +
                " s.subjectName," +
                " sum(es.number) " +
                " FROM EquipmentSubject es LEFT JOIN es.subject s LEFT JOIN es.school ts LEFT JOIN ts.distinct d " +
                " WHERE d.code =" + distinctCode +
                " GROUP BY s.id " +
                " ORDER BY sum(es.number) DESC ";
        return distinctRepository.findListByJpql(jpql);
    }

    /**
     * 区县教师使用率展示
     * @return
     */
    public List loadDistinctSchoolTeacher(String distinctCode){
        String sql = " SELECT " +
                " ts.name, " +
                " tu.real_name, " +
                " CONCAT(ROUND(sum(tet.number)/sum(tet.number_all),2)*100,'%') " +
                " FROM tab_equipment_teacher tet  " +
                " LEFT JOIN tab_user tu on tu.id=tet.user_id " +
                " LEFT JOIN tab_school ts on ts.school_code = tu.school_school_code " +
                " WHERE ts.`name` IS NOT NULL AND ts.distinct_code =" + distinctCode +
                " GROUP BY tu.id " +
                " ORDER BY SUM(tet.number) / SUM(tet.number_all) DESC ";
        return distinctRepository.findListBySql(sql);
    }

    /**
     * 区县班级使用率展示
     * @return
     */
    public List loadDistinctSchoolClass(String distinctCode){
        String sql = "SELECT " +
                " ts.`name` tsName, " +
                " CONCAT(tg.`name`,tc.class_name), " +
                " CONCAT(ROUND(SUM(tec.number) / SUM(tec.number_all),2) * 100,'%') " +
                " FROM tab_equipment_class tec " +
                " LEFT JOIN tab_classes tc ON tc.id = tec.classes_id " +
                " LEFT JOIN tab_grade tg ON tg.id = tc.grade_id " +
                " LEFT JOIN tab_school ts ON ts.school_code = tg.school_school_code  " +
                " WHERE ts.distinct_code = "+ distinctCode +
                " GROUP BY tc.id " +
                " ORDER BY SUM(tec.number) / SUM(tec.number_all) DESC ";
        return distinctRepository.findListBySql(sql);
    }

    /**
     * 区县设备完好数完好率展示
     * @return
     */
    public List loadDistinctEquipmentAccesssory(String distinctCode){
        String sql = " SELECT (SELECT COUNT(tcr2.id) FROM tab_classroom tcr2 LEFT JOIN tab_building tb on tb.id=tcr2.building_id LEFT JOIN tab_school ts on ts.school_code = tb.school_school_code  WHERE tcr2.equipment_no IS NOT NULL AND ts.distinct_code = "+distinctCode+" AND tcr2.id NOT IN (SELECT ta1.class_room_id FROM tab_accessory ta1 WHERE ta1.facilities != 1)),COUNT(tcr.id), " +
                " ROUND((SELECT COUNT(tcr2.id) FROM tab_classroom tcr2 LEFT JOIN tab_building tb on tb.id=tcr2.building_id LEFT JOIN tab_school ts on ts.school_code = tb.school_school_code  WHERE tcr2.equipment_no IS NOT NULL AND ts.distinct_code = "+distinctCode+" AND tcr2.id NOT IN (SELECT ta1.class_room_id FROM tab_accessory ta1 WHERE ta1.facilities != 1))/COUNT(tcr.id),2) " +
                " FROM  " +
                " tab_classroom tcr   " +
                " LEFT JOIN tab_building tb on tb.id=tcr.building_id  " +
                " LEFT JOIN tab_school ts on ts.school_code = tb.school_school_code  " +
                " WHERE tcr.equipment_no is not null AND ts.distinct_code = "+distinctCode;
        return distinctRepository.findListBySql(sql);
    }

    /**
     * 区县设备使用率展示
     * @return
     */
    public List loadDistinctEquipmentClassRoom(String distinctCode){
        String sql = "SELECT COUNT(tc.id), " +
                " (SELECT COUNT(tc.id) FROM tab_classes tc  " +
                " LEFT JOIN tab_classroom tcr on tc.class_room_id = tcr.id  " +
                " LEFT JOIN tab_grade tg on tg.id = tc.grade_id  " +
                " LEFT JOIN tab_school ts on ts.school_code = tg.school_school_code  " +
                " LEFT JOIN tab_equipment tpt ON tcr.equipment_no = tpt.equipment_no WHERE tpt.work_status !=1 AND ts.distinct_code ="+distinctCode+"), " +
                " ROUND((SELECT COUNT(tc.id) FROM tab_classes tc  " +
                " LEFT JOIN tab_classroom tcr on tc.class_room_id = tcr.id  " +
                " LEFT JOIN tab_grade tg on tg.id = tc.grade_id  " +
                " LEFT JOIN tab_school ts on ts.school_code = tg.school_school_code  " +
                " LEFT JOIN tab_equipment tpt ON tcr.equipment_no = tpt.equipment_no WHERE tpt.work_status = 1 AND ts.distinct_code ="+distinctCode+") " +
                " /COUNT(tc.id),1) " +
                " FROM tab_classroom tcr  " +
                " LEFT JOIN tab_classes tc on tc.class_room_id = tcr.id  " +
                " LEFT JOIN tab_grade tg on tg.id = tc.grade_id  " +
                " LEFT JOIN tab_school ts on ts.school_code = tg.school_school_code  " +
                " WHERE tcr.equipment_no is not null AND ts.distinct_code ="+distinctCode;
        return distinctRepository.findListBySql(sql);
    }

    /**
     * 区县设备运维使用率
     * @param distinctCode
     * @return
     */
    public List loadOpertionEquipment(String distinctCode){
        String sql = " SELECT COUNT(*),(SELECT COUNT(*) FROM tab_operations top LEFT JOIN tab_school ts on top.school_school_code = ts.school_code WHERE ts.distinct_code = "+distinctCode+" AND top.end_date IS NOT NULL), " +
                " ROUND((SELECT COUNT(*) FROM tab_operations top LEFT JOIN tab_school ts on top.school_school_code = ts.school_code WHERE ts.distinct_code = "+distinctCode+" AND top.end_date IS NOT NULL)/COUNT(*),2) " +
                " FROM tab_operations top  " +
                " LEFT JOIN tab_school ts on top.school_school_code = ts.school_code " +
                " WHERE ts.distinct_code = "+distinctCode;
        return distinctRepository.findListBySql(sql);
    }


}
