package com.hfkj.bbt.systemanage.impl;

import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.systemanage.IClassRoomService;
import com.hfkj.bbt.vo.ClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class ClassRoomService implements IClassRoomService{

    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;

    /**
     * 添加教室/办公室
     * @param classRoom
     */
    @Transactional(readOnly = false)
    public void doSaveClassRoom(ClassRoom classRoom){
        classRoomRepository.save(classRoom);
    }

    /**
     * 根据id回填教学楼教室班级
     * @param roomId
     * @return
     */
    public ClassVo findGradeClassByBuildingId(Long roomId){
        ClassRoom classRoom = classRoomRepository.findOne(roomId);
        ClassVo classVo = new ClassVo();
        classVo.setBuildingId(classRoom.getBuilding().getId());
        classVo.setRoomType(classRoom.getRoomType());
        classVo.setRoomCode(classRoom.getRoomCode());
        classVo.setRoomId(classRoom.getId());
        Equipment equipment = classRoom.getEquipment();
        if(equipment!=null){
            classVo.setEquipmentNo(equipment.getEquipmentNo());
        }
        Classes classes = classRoom.getClasses();
        if(classes!=null){
            classVo.setGradeId(classes.getGrade().getId());
            classVo.setClassName(classRoom.getClasses().getClassName());
            classVo.setClassId(classes.getId());
        }
        return classVo;
    }

    /**
     * 根据roomId查询教室
     * @param id
     * @return
     */
    public ClassRoom findClassRoomById(Long id){
        return classRoomRepository.findClassRoomById(id);
    }


    /**
     * 根据班级id回填教学楼教室班级
     * @param classId
     * @return
     */
    public ClassVo findGradeClassByClassId(Long classId){
        ClassVo classVo = new ClassVo();
        Classes classes = classRepository.findOne(classId);
        classVo.setClassName(classes.getClassName());
        classVo.setClassId(classes.getId());
        classVo.setGradeId(classes.getGrade().getId());
        classVo.setUserId(classes.getUser().getId());
        return classVo;
    }

    /**
     * 添加教室
     * @param classVo
     * @return
     */
    @Transactional(readOnly = false)
    public String addClassRoom(ClassVo classVo){
        ClassRoom classRoom = new ClassRoom();
        Building building = buildingRepository.findOne(classVo.getBuildingId());
        classRoom.setRoomCode(classVo.getRoomCode());
        classRoom.setRoomType(classVo.getRoomType());
        classRoom.setBuilding(building);
        classRoom.setEquipment(equipmentRepository.findOne(classVo.getEquipmentNo()));
        classRoomRepository.save(classRoom);
        return "添加成功";
    }

    /**
     * 查询教室编号
     * @param buildingId
     * @return
     */
    public List loadBuildingClassRoom(Long buildingId){
        String jpql="SELECT " +
                " cr.roomType, " +
                " cr.roomCode  " +
                " FROM ClassRoom cr " +
                " LEFT JOIN cr.building b " +
                " LEFT JOIN cr.classes cs " +
                " where cs.classRoom is null and  b.id="+buildingId;
        List list = classRoomRepository.findListByJpql(jpql);
        return list;
    }

    /**
     * 解除绑定
     * @param classVo
     * @return
     */
    @Transactional(readOnly = false)
    public String removeBuilding(ClassVo classVo){
        Long classId = classVo.getClassId();
        Long roomId = classVo.getRoomId();
        Long cancelClass = classVo.getClassRoomId();
        Long cancelEquipment = classVo.getEqClassRoom();
        if(cancelClass!=null) {
            if(classId!=null){
                Classes classes = classRepository.findOne(classVo.getClassId());
                classes.setClassRoom(null);
                classRepository.save(classes);
            }
        }
        if(cancelEquipment!=null) {
            if(roomId!=null){
                ClassRoom classRoom = classRoomRepository.findOne(classVo.getRoomId());
                classRoom.setEquipment(null);
                classRoomRepository.save(classRoom);
            }
        }
        return "解除成功";
    }


    /**
     * 查询首页使用率
     * @return
     */
    public List loadEqClassRoom(){
        String sql="SELECT (SELECT COUNT(tc.id) FROM tab_classes tc  " +
                " LEFT JOIN tab_classroom tcr on tc.class_room_id = tcr.id " +
                " LEFT JOIN tab_equipment tpt ON tcr.equipment_no = tpt.equipment_no WHERE tpt.work_status = 1 ), " +
                " ROUND((SELECT COUNT(tc.id) FROM tab_classes tc  " +
                " LEFT JOIN tab_classroom tcr on tc.class_room_id = tcr.id " +
                " LEFT JOIN tab_equipment tpt ON tcr.equipment_no = tpt.equipment_no WHERE tpt.work_status = 1 ) " +
                " /COUNT(tc.id),1) " +
                " FROM tab_classroom tcr  " +
                " LEFT JOIN tab_classes tc on tc.class_room_id = tcr.id " +
                " WHERE tcr.equipment_no is not null";
        List list = classRoomRepository.findListBySql(sql);
        return list;
    }

}
