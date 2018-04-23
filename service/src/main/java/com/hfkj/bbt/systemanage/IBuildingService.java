package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.vo.ClassVo;

import java.util.List;

public interface IBuildingService {


    /**
     * 根据id查询教学楼下面的教室班级
     * @param buildingId
     * @return
     */
    List findBuildingByGradeClass(Long buildingId,Long gradeId);

    /**
     * 添加班级教室
     * @param classVo
     * @return
     */
    String addRoomClass(ClassVo classVo);

    /**
     * 添加教学楼
     * @param classVo
     * @return
     */
    String addBuilding(ClassVo classVo);

    /**
     * 添加年级
     * @param classVo
     * @return
     */
    String addGrade(ClassVo classVo);

    /**
     * 删除教室/办公室
     * @param roomId
     * @return
     */
    java.lang.String deleteClassRoom(Long roomId, Long classId);

    /**
     * 删除教学楼
     * @param buildingId
     * @return
     */
    String deleteBuilding(Long buildingId);

    /**
     * 删除年级
     * @param gradeId
     * @return
     */
    String deleteGrade(Long gradeId);

    /**
     * 根据实体类查询教室
     * @param schoolCode
     * @return
     */
    List findByBuilding(String schoolCode);

    /**
     * 动态加载教学楼
     * @param schoolCode
     * @return
     */
    List loadBuilding(String schoolCode);

    /**
     * 动态加载年级
     * @param schoolCode
     * @return
     */
    List loadGrade(String schoolCode);

    /**
     * 修改教室
     * @param classVo
     * @return
     */
    String updateRoom(ClassVo classVo);

}
