package com.hfkj.bbt.dataAnalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("dataAnalysis")
public class DataAnalysisContorller {


    @Value("${custom.distinct.code}")
    private String distinctCode;

    @Autowired
    private IDataAnalysisService dataAnalysisService;

    /**
     * 加载区县学校使用率
     * @return
     */
    @GetMapping(value = "loadSchool")
    @ResponseBody
    public Map<String,List> loadDataAnalysisSchool(){
        Map<String,List> map=new HashMap<>();
        map.put("data",dataAnalysisService.loadDistinctSchoolALL(distinctCode));
        return map;
    }

    /**
     * 加载区县学科使用课时数
     * @return
     */
    @GetMapping(value = "loadSchoolSubject")
    @ResponseBody
    public Map<String,List> loadDataAnalysisSchoolSubject(){
        Map<String,List> map=new HashMap<>();
        map.put("data",dataAnalysisService.loadDistinctSchoolSubject(distinctCode));
        return map;
    }

    /**
     * 加载区县教师使用率
     * @return
     */
    @GetMapping(value = "loadSchoolTeacher")
    @ResponseBody
    public Map<String,List> loadDataAnalysisSchoolTeacher(){
        Map<String,List> map=new HashMap<>();
        map.put("data",dataAnalysisService.loadDistinctSchoolTeacher(distinctCode));
        return map;
    }

    /**
     * 加载区县教师使用率
     * @return
     */
    @GetMapping(value = "loadSchoolClass")
    @ResponseBody
    public Map<String,List> loadDataAnalysisSchoolClass(){
        Map<String,List> map=new HashMap<>();
        map.put("data",dataAnalysisService.loadDistinctSchoolClass(distinctCode));
        return map;
    }

    /**
     * 设备完好图表
     * @return
     */
    @PostMapping(value = "loadDistinctEquipmentAccesssory")
    @ResponseBody
    public List loadDistinctEquipmentAccesssory(){
        return dataAnalysisService.loadDistinctEquipmentAccesssory(distinctCode);
    }

    /**
     * 设备完好图表
     * @return
     */
    @PostMapping(value = "loadDistinctEquipmentClassRoom")
    @ResponseBody
    public List loadDistinctEquipmentClassRoom(){
        return dataAnalysisService.loadDistinctEquipmentClassRoom(distinctCode);
    }

    /**
     * 运维完成图表
     * @return
     */
    @PostMapping(value = "loadOpertionEquipment")
    @ResponseBody
    public List loadOpertionEquipment(){
        return dataAnalysisService.loadOpertionEquipment(distinctCode);
    }

}
