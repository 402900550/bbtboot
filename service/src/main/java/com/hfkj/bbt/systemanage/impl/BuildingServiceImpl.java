package com.hfkj.bbt.systemanage.impl;

import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.systemanage.IBuildingService;
import com.hfkj.bbt.systemanage.ITimeTableService;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.vo.ClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class BuildingServiceImpl implements IBuildingService {

    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private ITimeTableService timeTableService;
    /**
     * 根据id查询教学楼下面的教室班级
     * @param buildingId
     * @return
     */
    public List findBuildingByGradeClass(Long buildingId,Long gradeId) {
        List list=new ArrayList();
        String jpqlClass="SELECT  b.buildingName ," +
                "cr.roomType ," +
                "cr.roomCode , " +
                "CONCAT(cg.name,clss.className) ," +
                "eqp.equipmentNo ," +
                "clss.id ," +
                "cr.id  " +
                " FROM Classes clss " +
                " LEFT JOIN clss.classRoom cr " +
                " LEFT JOIN clss.grade cg " +
                " LEFT JOIN cr.building b " +
                " LEFT JOIN cr.equipment eqp " +
                " where 1=1 ";
        String jpqlBuilding="SELECT  b.buildingName ," +
                "cr.roomType ," +
                "cr.roomCode , " +
                "CONCAT(cg.name,clss.className) ," +
                "eqp.equipmentNo ," +
                "clss.id ," +
                "cr.id  " +
                " FROM  ClassRoom cr" +
                " LEFT JOIN  cr.classes clss " +
                " LEFT JOIN clss.grade cg " +
                " LEFT JOIN cr.building b " +
                " LEFT JOIN cr.equipment eqp " +
                " where 1=1 ";
        if (buildingId!=null){
            jpqlBuilding+=" and b.id="+buildingId;
            list= buildingRepository.findListByJpql(jpqlBuilding);
        }
        if (gradeId!=null){
            jpqlClass+=" and cg.id= "+ gradeId ;
            list= buildingRepository.findListByJpql(jpqlClass);
        }

        return list;
    }

    /**
     * 添加班级教室
     * @param classVo
     * @return
     */
    @Transactional(readOnly = false)
    public String addRoomClass(ClassVo classVo){
        ClassRoom classRoom = new ClassRoom();
        Classes classes = new Classes();
        Building building = buildingRepository.findOne(classVo.getBuildingId());
        if(!ComUtil.stringIsNotNull(classVo.getEquipmentNo())){
            classRoom.setRoomType(classVo.getRoomType());
            classRoom.setRoomCode(classVo.getRoomCode());
            classRoom.setBuilding(building);
            classRoomRepository.save(classRoom);
            classes.setClassName(classVo.getClassName());
            classes.setClassRoom(classRoom);
            classes.setGrade(gradeRepository.findOne(classVo.getGradeId()));
            classes.setCreateDate(new Date());
            classRepository.save(classes);
            return "添加成功，但未绑定设备";
        }else {
            Equipment equipment = equipmentRepository.findOne(classVo.getEquipmentNo());
            if(equipment==null){
                return "设备号不正确";
            }
            classRoom.setRoomType(classVo.getRoomType());
            classRoom.setRoomCode(classVo.getRoomCode());
            classRoom.setBuilding(building);
            classRoom.setEquipment(equipment);
            classRoomRepository.save(classRoom);
            classes.setClassName(classVo.getClassName());
            classes.setGrade(gradeRepository.findOne(classVo.getGradeId()));
            classes.setCreateDate(new Date());
            classes.setClassRoom(classRoom);
            classRepository.save(classes);
            return "添加成功";
        }

    }

    /**
     * 添加教学楼
     * @param classVo
     * @return
     */
    @Transactional(readOnly = false)
    public String addBuilding(ClassVo classVo){
        Building building = new Building();
        School school = schoolRepository.findOne(classVo.getSchoolCode());
        building.setBuildingName(classVo.getBuildingName());
        building.setSchool(school);
        buildingRepository.save(building);
        return "添加成功";
    }


    /**
     * 添加年级
     * @param classVo
     * @return
     */
    @Transactional(readOnly = false)
    public String addGrade(ClassVo classVo){
        Grade grade = new Grade();
        School school = schoolRepository.findOne(classVo.getSchoolCode());
        grade.setName(classVo.getGradeName());
        grade.setSchool(school);
        Grade save = gradeRepository.save(grade);
        timeTableService.initTimeLists(save);
        return "添加成功";
    }

    /**
     * 删除教室/办公室
     * @param roomId
     * @return
     */
    @Transactional(readOnly = false)
    public String deleteClassRoom(Long roomId, Long classId){
        if(classId!=null&&roomId==null){
            classRepository.delete(classId);
            return "删除成功";
        }else {
            ClassRoom classRoom = classRoomRepository.findOne(roomId);
            if (classRoom.getClasses() != null) {
                return "该教室/办公室下还有班级，请先解除绑定或者删除！";
            } else if (classRoom.getEquipment() != null) {
                return "该教室/办公室绑定了设备，请先解除绑定或者删除！";
            } else {
                classRoomRepository.delete(roomId);
                return "删除成功";
            }
        }
    }

    /**
     * 删除教学楼
     * @param buildingId
     * @return
     */
    @Transactional(readOnly = false)
    public String deleteBuilding(Long buildingId){
        Building building = buildingRepository.findOne(buildingId);
        if(building.getClassRooms().size()>0){
            return "该教学楼下还有教室，请先删除教室！";
        }else {
            buildingRepository.delete(buildingId);
            return "删除成功";
        }
    }

    /**
     * 删除年级
     * @param gradeId
     * @return
     */
    @Transactional(readOnly = false)
    public String deleteGrade(Long gradeId){
        Grade grade = gradeRepository.findOne(gradeId);
        Set<Classes> classes = grade.getClasses();
        if(classes!=null) {
            Iterator<Classes> iterator = classes.iterator();
            if (iterator.hasNext()) {
                Classes next = iterator.next();
                if (next.getClassRoom() != null) {
                    return "该年级下的班级绑定了教室的，请先解除绑定！";
                }
            }
            gradeRepository.delete(gradeId);
            return "删除成功";
        }else {
         gradeRepository.delete(gradeId);
         return "删除成功";
        }
    }

    /**
     * 根据实体类查询教室
     * @param schoolCode
     * @return
     */
    public List findByBuilding(String schoolCode){
        String jpql="SELECT " +
                " cr.roomCode  " +
                " FROM ClassRoom cr " +
                " LEFT JOIN cr.building b " +
                " LEFT JOIN b.school s " +
                " where s.schoolCode = "+schoolCode;
        List list = buildingRepository.findListByJpql(jpql);
        return list;
    }

    /**
     * 动态加载教学楼
     * @param schoolCode
     * @return
     */
    public List loadBuilding(String schoolCode){
        String jpql="SELECT " +
                " b.buildingName," +
                " b.id " +
                " FROM Building b " +
                " LEFT JOIN b.school s " +
                " where s.schoolCode = "+schoolCode;
        List list = buildingRepository.findListByJpql(jpql);
        return list;
    }

    /**
     * 动态加载年级
     * @param schoolCode
     * @return
     */
    public List loadGrade(String schoolCode){
        String jpql="SELECT " +
                " g.name," +
                " g.id " +
                " FROM Grade g " +
                " LEFT JOIN g.school s " +
                " where s.schoolCode = "+schoolCode;
        List list = buildingRepository.findListByJpql(jpql);
        return list;
    }

    /**
     * 修改教室
     * @param classVo
     * @return
     */
    @Transactional(readOnly = false)
    public String updateRoom(ClassVo classVo){
        ClassRoom classRoom = classRoomRepository.findOne(classVo.getRoomId());
        classRoom.setRoomType(classVo.getRoomType());
        classRoom.setRoomCode(classVo.getRoomCode());
        classRoomRepository.save(classRoom);
        return "修改成功";
    }


}
