package com.hfkj.bbt.systemanage.impl;

import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.systemanage.IClassService;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.vo.ClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class ClassServiceImpl implements IClassService {

    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * 修改班级
     * @param classVo
     */
    @Override
    @Transactional(readOnly = false)
    public String updateClasses(ClassVo classVo) {

        if(ComUtil.stringIsNotNull(classVo.getEquipmentNo())) {
            Equipment equipment = equipmentRepository.findOne(classVo.getEquipmentNo());
            if (equipment == null) {
                return "请输入正确的设备号！";
            }
        }
       if(classVo.getRoomId()!=null){
           ClassRoom classRoom =classRoomRepository.findOne(classVo.getRoomId());
           Classes classes = classRepository.findByClassRoom(classRoom);
           if(classes!=null){
               classes.setClassRoom(null);
               classRepository.save(classes);
           }
       }
           Building building = buildingRepository.findOne(classVo.getBuildingId());
           ClassRoom classRoom1 = classRoomRepository.findByBuildingAndRoomCode(building, classVo.getRoomCode());
           classRoom1.setEquipment(equipmentRepository.findOne(classVo.getEquipmentNo()));
           classRoomRepository.save(classRoom1);
           Classes classes1 = classRepository.findOne(classVo.getClassId());
           classes1.setClassRoom(classRoom1);
           classes1.setGrade(gradeRepository.findOne(classVo.getGradeId()));
           classRepository.save(classes1);
           return "修改成功";

    }

    /**
     * 添加班级
     * @param classVo
     * @return
     */
    @Transactional(readOnly = false)
    public String addClass(ClassVo classVo){
        Grade grade = gradeRepository.findOne(classVo.getGradeId());
        User user = userRepository.findOne(classVo.getUserId());
        Classes classes = new Classes();
        classes.setCreateDate(new Date());
        classes.setClassName(classVo.getClassName());
        classes.setGrade(grade);
        classes.setUser(user);
        classRepository.save(classes);
        //新增班主任
        String sql = " INSERT INTO tab_user_to_role VALUES(7,"+user.getId()+") ";
        classRepository.findListBySql(sql);
        return "添加成功";
    }

    /**
     * 根据年级查询班级
     * @param gradeId
     * @return
     */
    public List loadBuildingGradeClass(Long gradeId){
        String jpql="SELECT " +
                " cs.id," +
                " cs.className " +
                " FROM Classes cs " +
                " LEFT JOIN cs.grade cg " +
                " where cs.classRoom is null  and  cg.id = " +gradeId;
        List list = classRoomRepository.findListByJpql(jpql);
        return list;
    }

    /**
     * 修改班级年级
     * @param classVo
     * @return
     */
    @Transactional(readOnly = false)
    public String updateClass(ClassVo classVo){
        Classes classes = classRepository.findOne(classVo.getClassId());
        Grade grade = gradeRepository.findOne(classVo.getGradeId());
        User user = userRepository.findOne(classVo.getUserId());
        classes.setClassName(classVo.getClassName());
        classes.setGrade(grade);
        classes.setUser(user);
        classRepository.save(classes);
        //删除以前的班主任
        String sql1 = " DELETE FROM tab_user_to_role WHERE role_id = 7 and user_id = "+ classes.getUser().getId();
        classRepository.findListBySql(sql1);
        //修改班主任
        String sql = " INSERT INTO tab_user_to_role VALUES(7,"+user.getId()+") ";
        classRepository.findListBySql(sql);
        return "修改成功";
    }

    /**
     * 查询首页班级检测数
     * @return
     */
    public List loadClassRoomEq(){
        String sql=" SELECT COUNT(tc.id),ROUND(COUNT(tc.id)/(SELECT COUNT(*) FROM tab_classes),1) " +
                " FROM tab_classroom tcr  " +
                " LEFT JOIN tab_classes tc on tc.class_room_id = tcr.id " +
                " WHERE tcr.equipment_no is not null ";
        List list = classRepository.findListBySql(sql);
        return list;
    }

    @Override
    public Classes findClassByEquipmentNo(String equipmentNo) {
        if(ComUtil.stringIsNotNull(equipmentNo)){
          ClassRoom classRoom=classRoomRepository.findByEquipment(equipmentRepository.findOne(equipmentNo));
          if(classRoom!=null){
              Classes classes = classRepository.findByClassRoom(classRoom);
              if(classes!=null){
                  return classes;
              }
          }
        }
        return null;
    }

    @Override
    public Classes findClassById(Long classId) {
        return classRepository.findOne(classId);
    }

    /**
     * 根据学校查询所有年级班级
     * @param schoolCode
     * @return
     */
    @Override
    public List findGradeClassBySchool(String schoolCode) {
        String sql="SELECT " +
                " tcs.id, " +
                " CONCAT(tg.`name`,tcs.class_name) " +
                "FROM " +
                " tab_classes tcs " +
                " LEFT JOIN tab_grade tg ON tg.id = tcs.grade_id " +
                " LEFT JOIN tab_school ts on ts.school_code=tg.school_school_code " +
                " WHERE ts.school_code=:schoolCode";

        Map<String,Object> map=new HashMap<>();
        map.put("schoolCode",schoolCode);
        return classRepository.findListBySql(sql,map);
    }


}
