package com.hfkj.bbt.index;

import com.hfkj.bbt.assetmanage.IAccessoryInputService;
import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.opertion.IOpertionService;
import com.hfkj.bbt.systemanage.*;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;


/**
 * Created by Administrator on 2018-01-10.
 */
@Controller
public class IndexController {

    @Autowired
    private ISchoolService schoolService;

    @Autowired
    private IGradeService gradeService;

    @Autowired
    private ITimeTableService timeTableService;

    @Autowired
    private IUserService userService;
    @Autowired
    private IClassService classService;
    @Autowired
    private IClassRoomService classRoomService;

    @Autowired
    private IAccessoryInputService inputService;
    @Autowired
    private IEquipment equipment;
    @Autowired
    private IOpertionService opertionService;

    @Value("${custom.distinct.name}")
    private String distinctName;

    @GetMapping(value = "/")
    public ModelAndView defaultView(){
        return new ModelAndView("login");
    }


    @GetMapping(value = "index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        User currentUser = userService.findCurrentUser();
        School school = currentUser.getSchool();
        view.addObject("currentSchool",school);
        view.addObject("currentUser",userService.findCurrentUser());
        view.addObject("currentRole",UserUtil.getCurrentMostHighRole());


        view.addObject("currentSubject",currentUser.getSubject());
        //查询待处理运维单
        List<Object[]> opertions = opertionService.loadOpertionsByRole(0);
        view.addObject("opertions",opertions);
        view.addObject("opertionCount",opertions.size());
        return view;
    }

    /**
     * 跳转到登陆页面
     *
     * @return
     */
    @GetMapping(value = "login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * 跳转到所有页面
     *
     * @return
     */
    @GetMapping(value = "toSpecialAllPages")
    public ModelAndView toSpecialAllPages(String url, String type) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(url);
        mav.addObject("currentId", type);
        //首页
        if("index".equals(type)){
            List list=schoolService.findBySchoolAccessoryPrice();
            if (list!=null){
                mav.addObject("accessoryPrice",list);
            }
            mav.addObject("classRoomEq",classService.loadClassRoomEq());
            mav.addObject("eqClassRoom",classRoomService.loadEqClassRoom());

            mav.addObject("top3School",schoolService.findTop3SchoolEnvironment());

            mav.addObject("equimentAccrssery",equipment.loadEquipmentAccesssory());

        }
        if ("school".equals(type)){
            Role role = UserUtil.getCurrentMostHighRole();
            User currentUser = userService.findCurrentUser();
            School school = currentUser.getSchool();
            if(school!=null){
                mav.addObject("currentSchoolCode",school.getSchoolCode());
            }
            mav.addObject("currentRole",role);
            mav.addObject("schoolTypeList", schoolService.findBySchoolTypeAll());
            mav.addObject("distinctName", distinctName);
        }
        if("useres".equals(type)){
            mav.addObject("companys",schoolService.findCompanys());
            mav.addObject("schools",schoolService.findSchoolByRole());
            mav.addObject("subjects",userService.findAllSubject());
            mav.addObject("roles",userService.finRolesByCurrentUser());
        }
        if ("timesTable".equals(type)){
            School school= UserUtil.getCurrentUser().getSchool();
            List<Grade> grades =gradeService.findBySchool(school);
            mav.addObject("grades", grades);
            if(grades.size()!=0){
                List timeList =timeTableService.findByGrade(grades.get(0));
                mav.addObject("timeTables", timeList);
            }else {
                mav.addObject("error", "没有年级");
                return mav;
            }
        }
        if("equipmentInput".equals(type)){

            User currentUser = userService.findCurrentUser();
            School school = currentUser.getSchool();
            mav.addObject("allAcNames",inputService.findAllAccessName());
            mav.addObject("allBrands",inputService.findAllBrand());
            mav.addObject("allModels",inputService.findAllModel());
            mav.addObject("allDevelops",inputService.findAllDevelop());
            if(school!=null){
                mav.addObject("currentSchool",school);
                Set<Grade> grades = school.getGrades();
                if(ComUtil.setNotNull(grades)){
                    mav.addObject("grades",grades);
                }
                Set<Building> buildings = school.getBuildings();
                if(ComUtil.setNotNull(buildings)){
                    mav.addObject("buildings",buildings);
                }
            }
        }

        if("operations".equals(type)){
            User currentUser = userService.findCurrentUser();
            School school = currentUser.getSchool();
            if(school!=null){
                List classList = classService.findGradeClassBySchool(school.getSchoolCode());
                mav.addObject("classList",classList);
            }
            mav.addObject("companys",inputService.findAllCompany());
        }

        return mav;
    }

}
