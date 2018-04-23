package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.entity.ClassRoom;
import com.hfkj.bbt.vo.ClassVo;

import java.util.List;


public interface IClassRoomService {

    /**
     * 添加教室/办公室
     * @param classRoom
     */
    void doSaveClassRoom(ClassRoom classRoom);

    /**
     * 根据id回填教学楼教室班级
     * @param roomId
     * @return
     */
    ClassVo findGradeClassByBuildingId(Long roomId);

    /**
     * 根据roomId查询教室
     * @param id
     * @return
     */
    ClassRoom findClassRoomById(Long id);

    /**
     * 根据班级id回填教学楼教室班级
     * @param classId
     * @return
     */
    ClassVo findGradeClassByClassId(Long classId);

    /**
     * 添加教室
     * @param classVo
     * @return
     */
    String addClassRoom(ClassVo classVo);

    /**
     * 查询教室编号
     * @param buildingId
     * @return
     */
    List loadBuildingClassRoom(Long buildingId);

    /**
     * 解除绑定
     * @param classVo
     * @return
     */
    String removeBuilding(ClassVo classVo);

    /**
     * 查询首页使用率
     * @return
     */
    List loadEqClassRoom();

}

