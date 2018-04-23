package com.hfkj.bbt.systemanage.impl;

import com.hfkj.bbt.repository.EquipmentRepository;
import com.hfkj.bbt.systemanage.IEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class IEquipmentImpl implements IEquipment {

    @Autowired
    private EquipmentRepository equipmentRepository;

    /**
     * 首页学校使用率展示
     * @return
     */
    public List loadEquipmentSchoolAll(){
        String sql = " SELECT " +
                " ts.`name`, " +
                " CONCAT(ROUND(sum(res.number)/sum(res.number_all),2)*100,'%') " +
                " FROM " +
                " tab_school ts " +
                " LEFT JOIN tab_equipment_school res ON res.school_school_code = ts.school_code " +
                " WHERE res.number_all IS NOT NULL " +
                " GROUP BY ts.school_code " +
                " ORDER BY SUM(res.number) / SUM(res.number_all) DESC " ;
        return equipmentRepository.findListBySql(sql);
    }

    /**
     * 首页学科使用课时数展示
     * @return
     */
    public List loadEquipmentSubjectAll(){
        String jpql=" SELECT " +
                " s.subjectName," +
                " sum(es.number) " +
                " FROM EquipmentSubject es LEFT JOIN es.subject s " +
                " GROUP BY s.id " +
                " ORDER BY sum(es.number) DESC ";
        List list = equipmentRepository.findListByJpql(jpql);
        return list;
    }

    /**
     * 首页教师使用率展示
     * @return
     */
    public List loadEquipmentTeacherAll(){
        String sql = " SELECT " +
                " ts.name, " +
                " tu.real_name, " +
                " CONCAT(ROUND(sum(tet.number)/sum(tet.number_all),2)*100,'%') " +
                " FROM tab_equipment_teacher tet  " +
                " LEFT JOIN tab_user tu on tu.id=tet.user_id " +
                " LEFT JOIN tab_school ts on ts.school_code = tu.school_school_code " +
                " GROUP BY tu.id " +
                " ORDER BY SUM(tet.number) / SUM(tet.number_all) DESC ";
        List list = equipmentRepository.findListBySql(sql);
        return list;
    }

    /**
     * 首页设备完好数完好率展示
     * @return
     */
    public List loadEquipmentAccesssory(){
        String sql = "SELECT " +
                " (SELECT COUNT(tcr2.id) FROM tab_classroom tcr2 WHERE tcr2.equipment_no IS NOT NULL AND tcr2.id NOT IN (SELECT ta1.class_room_id FROM tab_accessory ta1 WHERE ta1.facilities != 1)), " +
                " ROUND((SELECT COUNT(tcr2.id) FROM tab_classroom tcr2  " +
                "  WHERE tcr2.equipment_no IS NOT NULL AND tcr2.id NOT IN (SELECT ta1.class_room_id FROM tab_accessory ta1 WHERE ta1.facilities != 1))/COUNT(tcr.id),2) " +
                " FROM " +
                " tab_classroom tcr  " +
                " WHERE tcr.equipment_no is not null ";
        List list = equipmentRepository.findListBySql(sql);
        return list;
    }

    /**
     * 首页资产图表
     * @return
     */
    public List loadEquipmentSchoolT(){
        String sql = " SELECT DATE_FORMAT(tay.purchase_date,'%Y年'),SUM(tay.price), " +
                " CONCAT(ROUND(SUM(tay.price)/(SELECT SUM(acc.price) FROM tab_accessory acc),2)*100,'%'), " +
                " (SELECT sum(tc.person_cost) FROM tab_classroom tc ) " +
                " FROM tab_accessory tay  " +
                " LEFT JOIN tab_classroom tc on tc.id = tay.class_room_id  " +
                " GROUP BY DATE_FORMAT(tay.purchase_date,'%Y') ";
        List list = equipmentRepository.findListBySql(sql);
        return list;
    }

    /**
     * 七天不使用故障(第三个值是表示设备在最近7是否使用了的，如果是0就表示未使用)
     * @return
     */
    public List getOperationsEquipment(){
        String sql="SELECT ts.school_code," +//学校id
                "tc.id tId," +//班级id
                "(SELECT COUNT(tu.id) FROM tab_usedrecord tu where tu.collect_time BETWEEN DATE_ADD(NOW(),INTERVAL -7 DAY) AND NOW() and tu.equipment_equipment_no = te.equipment_no ) " +
                "FROM tab_equipment te   " +
                "LEFT JOIN tab_classroom tcr on tcr.equipment_no=te.equipment_no   " +
                "LEFT JOIN tab_classes tc on tc.class_room_id = tcr.id  " +
                "LEFT JOIN tab_grade tg on tg.id = tc.grade_id  " +
                "LEFT JOIN tab_school ts on ts.school_code = tg.school_school_code " +
                "GROUP BY te.equipment_no ";
        return equipmentRepository.findListBySql(sql);
    }

    /**
     * 频繁使用设备(第三个值表示今天使用的次数，如果20次以上就表示肯定有人乱搞)
     * @return
     */
    public List getFrequentlyEquipment(){
        String sql="SELECT ts.school_code," +//学校id
                "tc.id tId," +//班级id
                "(SELECT COUNT(tu.id) FROM tab_usedrecord tu where DATE_FORMAT(tu.collect_time,'%y%m%d') = DATE_FORMAT(NOW(),'%y%m%d') and tu.equipment_equipment_no = te.equipment_no )  " +
                "FROM tab_equipment te   " +
                "LEFT JOIN tab_classroom tcr on tcr.equipment_no = te.equipment_no   " +
                "LEFT JOIN tab_classes tc on tc.class_room_id = tcr.id  " +
                "LEFT JOIN tab_grade tg  on tg.id = tc.grade_id " +
                "LEFT JOIN tab_school ts on ts.school_code = tg.school_school_code   " +
                "GROUP BY te.equipment_no ";
        return equipmentRepository.findListBySql(sql);
    }




}
