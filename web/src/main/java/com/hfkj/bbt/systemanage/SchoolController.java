package com.hfkj.bbt.systemanage;



import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.util.UserUtil;
import com.hfkj.bbt.vo.SchoolVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "school")
public class SchoolController {

    @Autowired
    private ISchoolService schoolService;


    /**
     * 新增学校
     *
     * @return
     */
    @PostMapping(value = "addSchool")
    public String addSchool(SchoolVo schoolVo) {
        if (!ComUtil.stringIsNotNull(schoolVo.getSchoolCode())) {
            return "学校代码不能为空！";
        }
        if (!ComUtil.stringIsNotNull(schoolVo.getSchoolName())) {
            return "学校名称不能为空！";
        }
        if (schoolVo.getSchoolType() == null) {
            return "学校类型不能为空！";
        }
        schoolService.doSaveSchool(schoolVo);
        return "保存成功!";
    }


    /**
     * 修改学校
     *
     * @return
     */
    @PostMapping(value = "modifySchool")
    public String modifySchool(SchoolVo schoolVo) {

        if (!ComUtil.stringIsNotNull(schoolVo.getSchoolName())) {
            return "学校名称不能为空！";
        }
        if (schoolVo.getSchoolType() == null) {
            return "学校类型不能为空！";
        }
        return schoolService.doModifySchool(schoolVo);


    }

    /**
     * 查询学校
     *
     * @return
     */
    @PostMapping(value = "findSchoolBySchoolCode")
    public SchoolVo findSchoolBySchoolCode(String schoolCode) {
        School school = schoolService.findBySchoolCode(schoolCode);
        SchoolVo schoolVo=new SchoolVo();
        schoolVo.setSchoolName(school.getName());
        schoolVo.setSchoolCode(school.getSchoolCode());
        schoolVo.setSchoolType(school.getSchoolType().getId());
        schoolVo.setSchoolPhone(school.getPhone());
        schoolVo.setSchoolAddress(school.getAddress());
        schoolVo.setGatewayIp(school.getGatewayIp());
        schoolVo.setSchoolDescription(school.getIntroduction());
        return schoolVo;
    }

    /**
     * 删除学校
     *
     * @param schoolCode
     * @return
     */
    @PostMapping(value = "deleteSchool")
    public String deleteSchool(String schoolCode) {
        Role currentRole = UserUtil.getCurrentMostHighRole();
        if("HUANFANGADMIN".equals(currentRole.getRoleName())||"DISTNCTADMIN".equals(currentRole.getRoleName())){
            schoolService.deleteBySchoolCode(schoolCode);
            return "删除成功";
        }else {
            return "没有权限!";
        }

    }

    @GetMapping(value = "loadSchoolList")
    public Map<String, List> findSchoolList() {
        Map<String, List> map = new HashMap<>();
        map.put("data", schoolService.findBySchoolAll());
        return map;
    }


    @GetMapping(value = "forwardBuilding")
    public ModelAndView forwardBuilding(String schoolCode) {
        ModelAndView modelAndView = new ModelAndView("systemManage/building");
        School school = schoolService.findBySchoolCode(schoolCode);
        modelAndView.addObject("school", schoolService.findBySchoolCode(schoolCode));
        modelAndView.addObject("buildingList", school.getBuildings());
        modelAndView.addObject("gradeAll", school.getGrades());
        modelAndView.addObject("userAll", school.getUsers());
        return modelAndView;
    }

}
