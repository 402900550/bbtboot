package com.hfkj.bbt.assetmanage;

import com.hfkj.bbt.entity.Classes;
import com.hfkj.bbt.systemanage.IClassService;
import com.hfkj.bbt.systemanage.ISchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资产统计controller
 */
@Controller
@RequestMapping(value = "statis")
public class AccessoryStatisController {

    @Autowired
    private IAssetStatisticService statisticService;

    @Autowired
    private ISchoolService schoolService;

    @Autowired
    private IClassService classService;

    /**
     * 加载区县所有学校的数据
     * @return
     */
    @GetMapping(value = "loadAssetStatis")
    @ResponseBody
    public Map<String,Object> loadAssetStatis(){
        Map<String,Object> map=new HashMap<>();
        map.put("data",statisticService.loadAssetStatis());
        return map;
    }

    /**
     * 跳转到单个学校详情
     * @param schoolCode
     * @return
     */
    @PostMapping(value = "forwardSchoolStatics")
    public ModelAndView forwardSchoolStatics(String schoolCode){
        ModelAndView mav=new ModelAndView("assetManage/schoolEqStatistics");
        mav.addObject("school",schoolService.findBySchoolCode(schoolCode));
        return mav;
    }


    /**
     * 加载学校的数据
     * @return
     */
    @GetMapping(value = "loadSchoolAssetStatis")
    @ResponseBody
    public Map<String,Object> loadSchoolAssetStatis(String schoolCode){
        Map<String,Object> map=new HashMap<>();
        map.put("data",statisticService.loadSchoolAssetStatis(schoolCode));
        return map;
    }

    /**
     * 跳转到班级详情
     * @param classId
     * @return
     */
    @PostMapping(value = "forwardClassesStatics")
    public ModelAndView forwardClassesStatics(Long classId){
        ModelAndView mav=new ModelAndView("assetManage/classEqStatistics");
        Classes classes = classService.findClassById(classId);
        mav.addObject("classes",classes);
        mav.addObject("grade",classes.getGrade());
        return mav;
    }


    /**
     * 加载班级的数据
     * @return
     */
    @GetMapping(value = "loadClassAssetStatis")
    @ResponseBody
    public Map<String,Object> loadClassAssetStatis(Long classId){
        Map<String,Object> map=new HashMap<>();
        map.put("data",statisticService.loadClassAssetStatis(classId));
        return map;
    }

    /**
     * 查询学校设备的完好率
     * @return
     */
    @PostMapping(value = "loadSchoolEqAccrssery")
    @ResponseBody
    public List loadSchoolEqAccrssery(){
        return statisticService.loadSchoolEqAccrssery();
    }


}
