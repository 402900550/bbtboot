package com.hfkj.bbt.systemanage;

import com.alibaba.fastjson.JSONObject;
import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

@RestController
@RequestMapping(value = "work")
public class WorkController {

    @Autowired
    private IGradeService gradeService;

    @Autowired
    private ITimeTableService timeTableService;

    /**
     * 户管理页面
     * @return
     */
    @PostMapping(value = "loadGrades")
    public ModelAndView loadGrades(){

        ModelAndView mav=new ModelAndView("systemManage/currentUserInfo");
        Role role = UserUtil.getCurrentMostHighRole();
        if(!"TEACHER".equals(role.getRoleName())){
            mav.addObject("error", "权限不够");
            return mav;
        }
        School school= UserUtil.getCurrentUser().getSchool();
        List grades =gradeService.findBySchool(school);
        mav.addObject("grades", grades);
        return mav;
    }

    /**
     * 保存修改
     * @return
     */
    @PostMapping(value = "save")
    public String test(String obj,Long gradeId){
        Role role = UserUtil.getCurrentMostHighRole();
        List<TimeTable> array= JSONObject.parseArray(obj,TimeTable.class);
        Grade grade =gradeService.findGrade(gradeId);
        for (TimeTable tab :array){
            tab.setGrade(grade);
            if(tab.getId()==0L){
                tab.setId(null);
            }
        }
        int r =timeTableService.saveTables(array);
        return r+"";

    }

    /**
     * 删除
     * @return
     */
    @PostMapping(value = "remove")
    public String delete(Long workId){
        int r =timeTableService.deleteTable(workId);
        return r+"";
    }


    /**
     * 选择年级课表
     * @return
     */
    @PostMapping(value = "choose")
    public List choose(Long gradeId){
        Grade grade =gradeService.findGrade(gradeId);
        List<TimeTable> timeList =timeTableService.findByGrade(grade);
//        if(timeList.size()==0){
//            timeTableService.initTimeLists(grade);
//            timeList =timeTableService.findByGrade(grade);
//        }
        for (TimeTable table : timeList){
            table.setGrade(null);
        }
        return timeList;
    }

}
