package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.opertion.IOpertionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "equipment")
public class EquipmentController {

    @Autowired
    private IEquipment equipment;
    @Autowired
    private IOpertionService opertionService;

    /**
     * 加载首页学校使用率
     * @return
     */
    @GetMapping(value = "loadEquipmentSchool")
    @ResponseBody
    public Map<String,List> loadEquipmentSchool(){
        Map<String,List> map=new HashMap<>();
        map.put("data",equipment.loadEquipmentSchoolAll());
        return map;
    }

    /**
     * 加载首页科目课时数
     * @return
     */
    @GetMapping(value = "loadEquipmentSubject")
    @ResponseBody
    public Map<String,List> loadEquipmentSubject(){
        Map<String,List> map=new HashMap<>();
        map.put("data",equipment.loadEquipmentSubjectAll());
        return map;
    }

    /**
     * 加载首页教师使用率
     * @return
     */
    @GetMapping(value = "loadEquipmentTeacher")
    @ResponseBody
    public Map<String,List> loadEquipmentTeacher(){
        Map<String,List> map=new HashMap<>();
        map.put("data",equipment.loadEquipmentTeacherAll());
        return map;
    }

    /**
     * 首页资产图表
     * @return
     */
    @PostMapping(value = "loadEquipmentSchoolT")
    @ResponseBody
    public List loadEquipmentSchoolT(){
        return equipment.loadEquipmentSchoolT();
    }

    /**
     * 首页运维使用数
     * @return
     */
    @PostMapping(value = "loadOpertions")
    @ResponseBody
    public List loadOpertions(){
        return opertionService.loadOpertions();
    }

}
