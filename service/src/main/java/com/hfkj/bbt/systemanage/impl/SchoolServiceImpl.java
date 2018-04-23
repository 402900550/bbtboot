package com.hfkj.bbt.systemanage.impl;

import com.hfkj.bbt.constant.Constants;
import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.systemanage.ISchoolService;
import com.hfkj.bbt.util.UserUtil;
import com.hfkj.bbt.vo.SchoolVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SchoolServiceImpl implements ISchoolService {

    @Value("${custom.distinct.code}")
    private String distinctCode;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private SchoolTypeRepository schoolTypeRepository;
    @Autowired
    private DistinctRepository distinctRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Override
    public List<Company> findCompanys() {
        return companyRepository.findAll();
    }

    /**
     * 根据schoolcode查询
     * @param schoolCode
     * @return
     */
    @Override
    public School findBySchoolCode(String schoolCode) {
        return schoolRepository.findOne(schoolCode);
    }



    /**
     * 根据当前用户的角色查询前端添加用户时候出现的学校
     * @return
     */
    @Override
    public List<School> findSchoolByRole() {
        User currentUser = UserUtil.getCurrentUser();
        Role role = UserUtil.getCurrentMostHighRole();
        User one = userRepository.findOne(currentUser.getId());
        String roleName = role.getRoleName();
        ArrayList<School> schools=new ArrayList<>();
        if (Constants.DISTNCTADMIN.equals(roleName)||Constants.HUANFANGADMIN.equals(roleName)){
            schools.addAll(schoolRepository.findAll());
        }else{
            School school = one.getSchool();
            schools.add(school);
        }
        return schools;
    }


    /**
     * 查询所有学校
     * @return
     */
    public List findBySchoolAll(){
        String jpql=" SELECT s.name," +//学校名称
                "s.schoolCode," +//学校代码
                "t.name," +//学校类型
                "s.phone," +//学校电话
                "s.address," +//学校地址
                "s.introduction" +//学校简介
                " FROM School s LEFT JOIN s.distinct d LEFT JOIN s.schoolType t ";
        List list = schoolRepository.findListByJpql(jpql);
        return list;
    }

    @Override
    @Transactional(readOnly = false)
    public String doSaveSchool(SchoolVo schoolVo) {

        String schoolCode = schoolVo.getSchoolCode();
        School school = schoolRepository.findOne(schoolCode);
        if (school!=null){
            return "学校代码重复!";
        }
        school=new School();
        school.setSchoolCode(schoolCode);
        school.setAddress(schoolVo.getSchoolAddress());
        school.setDistinct(distinctRepository.findOne(distinctCode));
        school.setGatewayIp(schoolVo.getGatewayIp());
        school.setIntroduction(schoolVo.getSchoolDescription());
        school.setName(schoolVo.getSchoolName());
        school.setSchoolType(schoolTypeRepository.findOne(schoolVo.getSchoolType()));
        school.setPhone(schoolVo.getSchoolPhone());
        school.setVersion("100");
        schoolRepository.save(school);
        return "保存成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String doModifySchool(SchoolVo schoolVo) {
        Role currentRole = UserUtil.getCurrentMostHighRole();
        if("HUANFANGADMIN".equals(currentRole.getRoleName())||"DISTNCTADMIN".equals(currentRole.getRoleName())){
            String schoolCode = schoolVo.getSchoolCode();
            School school = schoolRepository.findOne(schoolCode);
            if (school != null) {
                school.setAddress(schoolVo.getSchoolAddress());
                school.setGatewayIp(schoolVo.getGatewayIp());
                school.setIntroduction(schoolVo.getSchoolDescription());
                school.setName(schoolVo.getSchoolName());
                school.setSchoolType(schoolTypeRepository.findOne(schoolVo.getSchoolType()));
                school.setPhone(schoolVo.getSchoolPhone());
                schoolRepository.save(school);
                return "保存成功!";
            } else {
                return "保存失败!";
            }
        }else {
            return "没有权限!";
        }

    }

    /**
     * 查询所有学校
     * @return
     */
    public List<SchoolType> findBySchoolTypeAll(){
        List<SchoolType> list = schoolTypeRepository.findAll();
        return list;
    }

    /**
     * 根据学校单位代码删除学校
     * @param schoolCode
     * @return
     */
    @Transactional(readOnly = false)
    public void deleteBySchoolCode(String schoolCode){
        schoolRepository.delete(schoolCode);
    }

    /**
     * 查询所有费用
     * @return
     */
    public List findBySchoolAccessoryPrice(){
        String sql=" SELECT " +
                " SUM(ta.price) + (SELECT SUM(tc.person_cost) FROM tab_classroom tc ), " +
                " (SELECT COUNT(*) FROM tab_classroom WHERE equipment_no IS NOT NULL) " +
                " FROM " +
                " tab_accessory ta" ;
        List list = schoolRepository.findListBySql(sql);
        return list;
    }



    @Override
    public List findTop3SchoolEnvironment() {
        String sql=" SELECT " +
                " s.`name`, " +
                " teee.temperature, " +
                " teee.humidity, " +
                " teee.illuminate, " +
                " teee.noise, " +
                " teee.pm2_5," +
                " s.school_code " +
                "FROM " +
                " tab_school s  " +
                "LEFT JOIN tab_building tb ON tb.school_school_code = s.school_code " +
                "LEFT JOIN tab_classroom tc ON tc.building_id=tb.id  " +
                "LEFT JOIN tab_equipment teee ON teee.equipment_no=tc.equipment_no " +
                "WHERE tc.equipment_no is not null GROUP BY s.school_code " +
                " LIMIT 0, " +
                " 2 ";
        List list = schoolRepository.findListBySql(sql);
        return list;
    }

}
