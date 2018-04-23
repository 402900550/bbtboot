package com.hfkj.bbt.application;

import com.hfkj.bbt.entity.Classes;
import com.hfkj.bbt.systemanage.IClassService;
import com.hfkj.bbt.systemanage.ISchoolService;
import com.hfkj.bbt.vo.restvo.ControlVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/30 0030.
 */
@Controller
@RequestMapping("data")
public class ApplicationController {
    @Autowired
    private IApplicationService applicationService;

    @Autowired
    private ISchoolService schoolService;

    @Autowired
    private IClassService classService;

    @GetMapping(value = "findTimeData")
    @ResponseBody
    public Map<String,Object> loadClassRoomByBuliding(String schoolName) {
        Map<String,Object> map=new HashMap<>();
        map.put("data", applicationService.findTimeData(schoolName));
        return map;
    }

    @PostMapping(value = "toDetail")
    public ModelAndView toDetail(String schoolCode){
        ModelAndView modelAndView=new  ModelAndView("applicationMonitoring/schooldetail");
        modelAndView.addObject("school",schoolService.findBySchoolCode(schoolCode));
        return  modelAndView;
    }

    @PostMapping(value = "toEnvironment")
    public ModelAndView toEnvironment(String schoolCode){
        ModelAndView modelAndView = new ModelAndView("applicationMonitoring/environment");
        modelAndView.addObject("school",schoolService.findBySchoolCode(schoolCode));
        return modelAndView;
    }

    @GetMapping(value = "showDetail")
    @ResponseBody
    public Map<String,Object> showDetail(String schoolCode){
        Map<String,Object> map=new HashMap<>();
        map.put("data", applicationService.showDetail(schoolCode));
        return map;
    }

    @PostMapping(value = "turnOnOff")
    @ResponseBody
    public String turnOnOff(ControlVo controlVo){
        return applicationService.turnOnOff(controlVo);
    }


    @GetMapping(value = "loadEnvironment" )
    @ResponseBody
    public Map<String,Object> loadEnvironment(String schoolCode){
        Map<String,Object> map=new HashMap<>();
        map.put("data", applicationService.loadEnvironment(schoolCode));
        return map;
    }


    @PostMapping(value = "loadUsedPercentChart")
    @ResponseBody
    public List<Object[]> loadUsedPercentChart(){
        return applicationService.loadUsedPercentChart();
    }


    @PostMapping(value = "toClassDetail")
    public ModelAndView toClassDetail(String equipmentNo){
        ModelAndView modelAndView = new ModelAndView("applicationMonitoring/classDetailTable");
        Classes classes = classService.findClassByEquipmentNo(equipmentNo);
        modelAndView.addObject("classes",classes);
        if(classes!=null){
            modelAndView.addObject("grade",classes.getGrade());
        }
        return modelAndView;
    }


    @GetMapping(value = "loadClassesUsedDetail" )
    @ResponseBody
    public Map<String,Object> loadClassesUsedDetail(Long classId){
        Map<String,Object> map=new HashMap<>();
        map.put("data", applicationService.loadClassesUsedDetail(classId));
        return map;
    }
    /**
     * 查询使用记录
     * @return
     */
    @GetMapping(value = "showUsedRecord")
    @ResponseBody
    public Map<String,Object> showUsedRecord(){
        Map<String,Object> map=new HashMap<>();
        map.put("data",applicationService.showUsedRecord());
        return map;
    }


}
