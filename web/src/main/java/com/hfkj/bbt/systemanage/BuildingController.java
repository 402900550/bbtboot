package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.vo.ClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "building")
public class BuildingController {

    @Autowired
    private IBuildingService buildingService;

    @Autowired
    private IClassRoomService classRoomService;

    @Autowired
    private IClassService classService;

    @GetMapping(value = "loadBuildingList")
    public Map<String,List> findSchoolList(Long buildingId,Long gradeId){
        Map<String,List> map=new HashMap<>();
        map.put("data",buildingService.findBuildingByGradeClass(buildingId,gradeId));
        return map;
    }

    /**
     * 添加班级教室
     * @param classVo
     * @return
     */
    @PostMapping(value = "addRoomClass")
    public String addRoomClass(ClassVo classVo) {
        if(classVo.getBuildingId()==null){
            return "请选择教学楼！";
        }else if(classVo.getClassName().equals("")){
            return "请输入班级名称！";
        }else if(classVo.getGradeId()==null){
            return "请选择年级！";
        }else if (classVo.getRoomCode().equals("")){
            return "请输入教室编号!";
        }
        return buildingService.addRoomClass(classVo);
    }

    /**
     * 回填教学楼教室班级
     * @return
     */
    @PostMapping(value = "findBuildingByRoomId")
    public ClassVo findGradeClassByBuildingId(Long roomId,Long classId){
        if(classId!=null&&roomId==null){
          return classRoomService.findGradeClassByClassId(Long.valueOf(classId));
        }
        return classRoomService.findGradeClassByBuildingId(Long.valueOf(roomId));
    }

    /**
     * 修改班级教室
     * @param classVo
     * @return
     */
    @PostMapping(value = "updateBuilding")
    public String updateBuilding(ClassVo classVo){
        if(classVo.getBuildingId()==null){
            return "教学楼不能为空！";
        }
        if(classVo.getRoomCode()==null&&classVo.getRoomType()==null){
            return "教室或编号不能为空！";
        }
        if (null==classVo.getClassId()){
            return "班级不能为空！";
        }
        return classService.updateClasses(classVo);
    }

    /**
     * 添加班级教室
     * @param classVo
     * @return
     */
    @PostMapping(value = "addBuilding")
    public String addBuilding(ClassVo classVo) {
        if(classVo.getBuildingName().equals("")){
            return "请输入教学楼名称";
        }
        return  buildingService.addBuilding(classVo);
    }

    /**
     * 添加年级
     * @param classVo
     * @return
     */
    @PostMapping(value = "addGrade")
    public String addGrade(ClassVo classVo) {
        if(classVo.getGradeName().equals("")){
            return "请输入年级名称";
        }
        return buildingService.addGrade(classVo);
    }

    /**
     * 删除教室/办公室
     * @param roomId
     * @return
     */
    @PostMapping(value = "deleteClassRoom")
    public String deleteClassRoom(Long roomId,Long classId) {
        return  buildingService.deleteClassRoom(roomId,classId);
    }

    /**
     * 删除教学楼
     * @param buildingId
     * @return
     */
    @PostMapping(value = "deleteBuilding")
    public String deleteBuilding(Long buildingId) {
        return buildingService.deleteBuilding(buildingId);
    }

    /**
     * 删除年级
     * @param gradeId
     * @return
     */
    @PostMapping(value = "deleteGrade")
    public String deleteGrade(Long gradeId) {
        return buildingService.deleteGrade(gradeId);
    }

    /**
     * 添加班级
     * @param classVo
     * @return
     */
    @PostMapping(value = "addClass")
    public String addClass(ClassVo classVo) {
        if(classVo.getClassName().equals("")){
            return "请输入班级名称";
        }
        return  classService.addClass(classVo);
    }

    /**
     * 添加教室
     * @param classVo
     * @return
     */
    @PostMapping(value = "addClassRoom")
    public String addClassRoom(ClassVo classVo) {
        if(classVo.getBuildingId()==null){
            return "教学楼不能为空！";
        }
        if("".equals(classVo.getRoomCode())){
            return "请输入教室编号！";
        }
        return  classRoomService.addClassRoom(classVo);
    }

    /**
     * 查询教室编号
     * @param buildingId
     * @return
     */
    @PostMapping(value = "loadBuildingClassRoom")
    public List loadBuildingClassRoom(Long buildingId){
        return classRoomService.loadBuildingClassRoom(buildingId);
    }

    /**
     * 查询班级
     * @param gradeId
     * @return
     */
    @PostMapping(value = "loadBuildingGradeClass")
    public List loadBuildingGradeClass(Long gradeId){
        return classService.loadBuildingGradeClass(gradeId);
    }

    /**
     * 动态加载教学楼
     * @param schoolCode
     * @return
     */
    @PostMapping(value = "loadBuilding")
    public List loadBuilding(String schoolCode){
        return buildingService.loadBuilding(schoolCode);
    }

    /**
     * 动态加载年级
     * @param schoolCode
     * @return
     */
    @PostMapping(value = "loadGrade")
    public List loadGrade(String schoolCode){
        return buildingService.loadGrade(schoolCode);
    }

    /**
     * 修改教室
     * @param classVo
     * @return
     */
    @PostMapping(value = "updateRoom")
    public String updateRoom(ClassVo classVo) {
        return  buildingService.updateRoom(classVo);
    }

    /**
     * 修改班级年级
     * @param classVo
     * @return
     */
    @PostMapping(value = "updateClass")
    public String updateClass(ClassVo classVo) {
        return classService.updateClass(classVo);
    }

    /**
     * 解除绑定
     * @param classVo
     * @return
     */
    @PostMapping(value = "removeBuilding")
    public String removeBuilding(ClassVo classVo) {
        if (null==classVo.getClassRoomId()&&null==classVo.getEqClassRoom()){
            return "请至少选择一个提交！";
        }
        return classRoomService.removeBuilding(classVo);
    }

}
