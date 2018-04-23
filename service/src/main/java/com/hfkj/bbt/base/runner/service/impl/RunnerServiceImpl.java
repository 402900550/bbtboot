package com.hfkj.bbt.base.runner.service.impl;

import com.hfkj.bbt.constant.Constants;
import com.hfkj.bbt.entity.Distinct;
import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.entity.SchoolType;
import com.hfkj.bbt.entity.Subject;
import com.hfkj.bbt.repository.DistinctRepository;
import com.hfkj.bbt.base.runner.service.RunnerService;
import com.hfkj.bbt.repository.RoleRepository;
import com.hfkj.bbt.repository.SchoolTypeRepository;
import com.hfkj.bbt.repository.SubjectRepository;
import com.hfkj.bbt.util.ComUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class RunnerServiceImpl implements RunnerService{

    @Value("${custom.distinct.code}")
    private String distinctCode;
    @Value("${custom.distinct.name}")
    private String distinctName;

    @Autowired
    private DistinctRepository distinctRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    @Override
    @Transactional(readOnly = false)
    public void updateDistinct() {
        Distinct distinct=new Distinct();
        distinct.setCode(distinctCode);
        distinct.setName(distinctName);
        distinctRepository.save(distinct);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateRoles() {
        List<Role> all = roleRepository.findAll();
        if(!ComUtil.listNotNull(all)){
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(Constants.TEACHER,1,"教师"));
            roles.add(new Role(Constants.DISTNCTUSER,1,"区县用户"));
            roles.add(new Role(Constants.CLASSMONITOR,1,"班长"));
            roles.add(new Role(Constants.CLASSTEACHER,2,"班主任"));
            roles.add(new Role(Constants.SCHOOLADMIN,3,"学校管理员"));
            roles.add(new Role(Constants.DISTNCTADMIN,4,"区县管理员"));
            roles.add(new Role(Constants.HUANFANGADMIN,9,"幻方管理员"));
            roles.add(new Role(Constants.COMPANY,6,"维修人员"));
            roleRepository.save(roles);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateSchoolType() {
        List<SchoolType> all = schoolTypeRepository.findAll();
        if(!ComUtil.listNotNull(all)){
            List<SchoolType> schoolTypes = new ArrayList<>();
            schoolTypes.add(new SchoolType("教委"));
            schoolTypes.add(new SchoolType("直属单位"));
            schoolTypes.add(new SchoolType("小学"));
            schoolTypes.add(new SchoolType("初中"));
            schoolTypes.add(new SchoolType("高中"));
            schoolTypes.add(new SchoolType("一贯制学校"));
            schoolTypes.add(new SchoolType("完全中学"));
            schoolTypes.add(new SchoolType("职高"));
            schoolTypes.add(new SchoolType("幼儿园"));
            schoolTypes.add(new SchoolType("实属中学"));
            schoolTypes.add(new SchoolType("其他"));
            schoolTypeRepository.save(schoolTypes);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateSubject() {
        List<Subject> all = subjectRepository.findAll();
        if(!ComUtil.listNotNull(all)){
            List<Subject> subjects=new ArrayList<>();
            subjects.add(new Subject("语文"));
            subjects.add(new Subject("数学"));
            subjects.add(new Subject("英语"));
            subjects.add(new Subject("物理"));
            subjects.add(new Subject("化学"));
            subjects.add(new Subject("生物"));
            subjects.add(new Subject("地理"));
            subjects.add(new Subject("政治"));
            subjects.add(new Subject("历史"));
            subjects.add(new Subject("体育"));
            subjects.add(new Subject("音乐"));
            subjects.add(new Subject("书法"));
            subjects.add(new Subject("美术"));
            subjects.add(new Subject("信息技术"));
            subjects.add(new Subject("科学"));
            subjectRepository.save(subjects);
        }
    }


}


















