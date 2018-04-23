package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.entity.Classes;
import com.hfkj.bbt.vo.ClassVo;

import java.util.List;

public interface IClassService {

    /**
     * 修改班级
     * @param classVo
     */
    String updateClasses(ClassVo classVo);

    /**
     * 添加班级
     * @param classVo
     * @return
     */
    String addClass(ClassVo classVo);

    /**
     * 根据年级查询班级
     * @param gradeId
     * @return
     */
    List loadBuildingGradeClass(Long gradeId);

    /**
     * 修改班级年级
     * @param classVo
     * @return
     */
    String updateClass(ClassVo classVo);

    /**
     * 查询首页班级检测数
     * @return
     */
    List loadClassRoomEq();

    Classes findClassByEquipmentNo(String equipmentNo);

    Classes findClassById(Long classId);


    List findGradeClassBySchool(String schoolCode);
}
