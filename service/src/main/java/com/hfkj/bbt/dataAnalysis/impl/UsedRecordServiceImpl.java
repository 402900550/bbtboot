package com.hfkj.bbt.dataAnalysis.impl;

import com.hfkj.bbt.dataAnalysis.IUsedRecordService;
import com.hfkj.bbt.repository.UsedRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class UsedRecordServiceImpl implements IUsedRecordService {

    @Autowired
    private UsedRecordRepository usedRecordRepository;

    /**
     * 根据查询学校设备当天使用情况
     * @return
     */
    public List getSchoolClassWorkSchedule(){
        String sql="SELECT " +
                " tur.school_school_code, " +
                " COUNT(*) " +
                " FROM " +
                "  tab_usedrecord tur " +
                "LEFT JOIN tab_classroom room ON room.equipment_no = tur.equipment_equipment_no " +
                "LEFT JOIN tab_classes class ON class.class_room_id = room.id  " +
                "LEFT JOIN tab_time_table tws ON tws.grade_id = class.grade_id  " +
                "WHERE DATE_FORMAT(tur.collect_time,'%Y-%m-%d') = DATE_FORMAT(NOW(), '%Y-%m-%d') " +
                " AND NOT (tws.`start` > DATE_FORMAT(tur.zt5_end,'%H:%i') OR tws.`end` < DATE_FORMAT(tur.zt5_start,'%H:%i')) " +
                " GROUP BY  " +
                " tur.school_school_code ";
        return usedRecordRepository.findListBySql(sql);
    }

    /**
     * 根据查询学校所有课时使用情况
     * @return
     */
    public List getSchoolClassWorkScheduleAll(){
        String sql="SELECT " +
                " SUM(c), " +
                " school_code  " +
                "FROM " +
                " (SELECT COUNT(*) * " +
                "(SELECT COUNT(ws.`start`) FROM tab_time_table ws WHERE ws.grade_id = ss.grade_id) c,ss.grade_id,tg.school_school_code school_code " +
                " FROM " +
                "  tab_classes ss  " +
                " LEFT JOIN tab_grade tg on tg.id = ss.grade_id  " +
                " GROUP BY  " +
                " ss.grade_id,tg.school_school_code) kk  " +
                "GROUP BY  " +
                " kk.school_code ";
        return usedRecordRepository.findListBySql(sql);
    }

    /**
     * 教师每天使用情况
     * @return
     */
    public List getTeacherWorkSchedule(){
        String sql="SELECT tu.id,COUNT(*),(SELECT COUNT(tw.`start`) FROM tab_time_table tw where tw.grade_id = tws.grade_id) " +
                " FROM tab_usedrecord tur   " +
                " LEFT JOIN tab_classroom room ON room.equipment_no=tur.equipment_equipment_no  " +
                " LEFT JOIN tab_classes class ON class.class_room_id = room.id  " +
                " LEFT JOIN tab_time_table tws on tws.grade_id = class.grade_id  " +
                " LEFT JOIN tab_user tu on tu.icard_no = tur.icard_no  " +
                " where DATE_FORMAT(tur.collect_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d') AND  " +
                " NOT(tws.`start` > DATE_FORMAT(tur.zt5_end,'%H:%i') OR tws.`end` < DATE_FORMAT(tur.zt5_start,'%H:%i')) " +
                " GROUP BY tu.id ";
        return usedRecordRepository.findListBySql(sql);
    }

    /**
     * 班级每天使用情况
     * @return
     */
    public List getClassWorkSchedule(){
        String sql=" SELECT  class.id,COUNT(*), " +
                " (SELECT COUNT(tw.`start`) FROM tab_time_table tw where tw.grade_id = tws.grade_id) " +
                " FROM tab_usedrecord tur  " +
                " LEFT JOIN tab_classroom room ON room.equipment_no=tur.equipment_equipment_no " +
                " LEFT JOIN tab_classes class ON class.class_room_id=room.id  " +
                " LEFT JOIN tab_time_table tws on tws.grade_id=class.grade_id  " +
                " where DATE_FORMAT(tur.collect_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d') AND   " +
                " NOT(tws.`start` > DATE_FORMAT(tur.zt5_end,'%H:%i') OR tws.`end` < DATE_FORMAT(tur.zt5_start,'%H:%i')) " +
                " GROUP BY class.id ";
        return usedRecordRepository.findListBySql(sql);
    }


    /**
     * 科目每天使用情况
     * @return
     */
    public List getSubjectWorkSchedule(){
        String sql=" SELECT tu.subject_id,COUNT(*),ts.school_code  " +
                " FROM tab_usedrecord tur       " +
                " LEFT JOIN tab_classroom room ON room.equipment_no=tur.equipment_equipment_no " +
                " LEFT JOIN tab_classes class ON class.class_room_id=room.id  " +
                " LEFT JOIN tab_school ts on ts.school_code = tur.school_school_code " +
                " LEFT JOIN tab_time_table tws on tws.grade_id=class.grade_id   " +
                " LEFT JOIN tab_user tu on tu.icard_no = tur.icard_no   " +
                " where DATE_FORMAT(tur.collect_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d') AND " +
                " NOT (tws.`start` > DATE_FORMAT(tur.zt5_end,'%H:%i') OR tws.`end` < DATE_FORMAT(tur.zt5_start,'%H:%i')) " +
                " GROUP BY tu.subject_id ";
        return usedRecordRepository.findListBySql(sql);
    }


}
