package com.hfkj.bbt.opertion.impl;

import com.hfkj.bbt.activity.IActService;
import com.hfkj.bbt.constant.Constants;
import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.opertion.IOpertionService;
import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.util.DateUtil;
import com.hfkj.bbt.util.UserUtil;
import com.hfkj.bbt.vo.HistoryActVo;
import com.hfkj.bbt.vo.OpertionDetailVo;
import com.hfkj.bbt.vo.SubmitAviceVo;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OpertionServiceImpl implements IOpertionService {

    @Autowired
    private IActService actService;

    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OpertionRepository opertionRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private ActValueRepository actValueRepository;
    @Autowired
    private AccessoryRepository accessoryRepository;

    /**
     * 得到当前用户的学校
     *
     * @return
     */
    private School getCurrentSchool() {
        User currentUser = UserUtil.getCurrentUser();
        return userRepository.findOne(currentUser.getId()).getSchool();
    }

    /**
     * 得到当前用户的公司
     *
     * @return
     */
    private Company getCurrentCompany() {
        User currentUser = UserUtil.getCurrentUser();
        return userRepository.findOne(currentUser.getId()).getCompany();
    }

    /**
     * 添加手动运维单
     *
     * @param classId
     * @param description
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String addOpertionAndStartProcess(Long classId, String description, Long companyId) {
        if (classId == null) {
            return "班级不能为空!";
        }
        if (!ComUtil.stringIsNotNull(description)) {
            return "请填写异常信息!";
        }
        School school = getCurrentSchool();
        if (school == null) {
            return "当前用户没有所属学校,不能添加运维单!";
        }
        if (companyId == null) {
            return "未选择上报公司!";
        }
        Operations operations = new Operations();
        operations.setClasses(classRepository.findOne(classId));
        operations.setSchool(school);
        operations.setBirthType(Operations.HAND);
        operations.setExceptionDescription(description);
        operations.setUser(UserUtil.getCurrentUser());
        operations.setStartDate(new Date());
        operations.setCompany(companyRepository.findOne(companyId));
        Map<String, Object> param = new HashMap<>();
        param.put(Constants.COMPANYID, Constants.COMPANYID + ":" + companyId);
        param.put(Constants.SCHOOLCODE, Constants.SCHOOLCODE + ":" + school.getSchoolCode());
        String processId = actService.startProcessByKey(Operations.HANDEXCEPTIONKEY, param);
        operations.setProcessId(processId);
        opertionRepository.save(operations);
        return "提交成功!请等待审批!";
    }

    /**
     * 根据用户角色查询运维单
     * status 1表示完成 2表示未完成
     *
     * @return
     */
    @Override
    public List<Object[]> loadOpertionsByRole(Integer status) {
        Role role = UserUtil.getCurrentMostHighRole();
        String roleName = role.getRoleName();
        String sql = "SELECT " +
                " DATE_FORMAT(tos.start_date,'%Y-%m-%d %H:%i:%s'), " +
                " ts.`name` sname, " +
                " tos.end_date, " +
                " tos.id " +
                "FROM " +
                " tab_operations tos " +
                " LEFT JOIN tab_company compan ON compan.id = tos.company_id " +
                " LEFT JOIN tab_school ts on ts.school_code=tos.school_school_code " +
                " LEFT JOIN tab_user u on u.id=tos.user_id ";
        Map<String, Object> map = new HashMap<>();
        if (status != null) {
            if (status == 1) {
                sql += "WHERE tos.end_date is not null ";
            } else {
                sql += "WHERE tos.end_date is  null ";
            }
        } else {
            sql += "WHERE tos.end_date is  null ";
        }


        if (Constants.COMPANY.equals(roleName)) {
            sql += " AND compan.id=:companyId ";
            Company currentCompany = getCurrentCompany();
            if (currentCompany != null) {
                map.put("companyId", currentCompany.getId());
            }
        }

        if (Constants.SCHOOLADMIN.equals(roleName)) {
            sql += " AND ts.school_code=:schoolCode ";
            School currentSchool = getCurrentSchool();
            if (currentSchool != null) {
                map.put("schoolCode", currentSchool.getSchoolCode());
            }
        }

        if (Constants.HUANFANGADMIN.equals(roleName)) {
            sql += " AND tos.birth_type='autoB'";
        }

        if (Constants.DISTNCTADMIN.equals(roleName)) {
            sql += " AND tos.birth_type='scarp'";
        }

        return opertionRepository.findListBySql(sql, map);
    }

    /**
     * 查询流程详情
     *
     * @param opertionId
     */
    @Override
    public OpertionDetailVo loadProcessDetail(Long opertionId) {
        OpertionDetailVo vo = new OpertionDetailVo();
        if (opertionId != null) {
            Operations operations = opertionRepository.findOne(opertionId);
            if (Operations.AUTOA.equals(operations.getBirthType())) {
                vo.setCanChoose(1);
            } else {
                vo.setCanChoose(0);
            }
            String processId = operations.getProcessId();
            School opSchool = operations.getSchool();
            if (opSchool != null) {
                vo.setOpUserName(opSchool.getName());
            } else {
                vo.setOpUserName("系统生成");
            }
            vo.setExceptionDescription(operations.getExceptionDescription());
            vo.setOpertionId(opertionId);
            if (operations.getEndDate() != null) {
                vo.setEndDate(DateUtil.tranDateToString(Constants.YYYYMMDDHHMMSS, operations.getEndDate()));
            }
            //加载流程历史活动
            vo.setActVos(actService.loadProcessHisByInstanceId(processId));
            vo.setStartDate(DateUtil.tranDateToString(Constants.YYYYMMDDHHMMSS, operations.getStartDate()));
            Task task = actService.findTaskByProcessId(processId);
            if (task != null) {
                String taskId = task.getId();
                vo.setTaskId(taskId);
                //得到当前角色
                Role role = UserUtil.getCurrentMostHighRole();
                String roleName = role.getRoleName();
                //得到该任务该谁处理
                String assignee = task.getAssignee();
                String[] ass = assignee.split(":");
                if (ass.length > 1) {
                    if (Constants.COMPANYID.equals(ass[0])) {
                        //该此公司处理
                        Company company = companyRepository.findOne(Long.valueOf(ass[1]));
                        if (Constants.COMPANY.equals(roleName)) {
                            Company currentCompany = getCurrentCompany();
                            if (company.getId().equals(currentCompany.getId())) {
                                vo.setCanProcess(1);
                            }
                        }
                    }
                    if (Constants.SCHOOLCODE.equals(ass[0])) {
                        //该此学校处理
                        School school = schoolRepository.findOne(ass[1]);
                        if (Constants.SCHOOLADMIN.equals(roleName)) {
                            School currentSchool = getCurrentSchool();
                            if (currentSchool != null) {
                                if (school.getSchoolCode().equals(currentSchool.getSchoolCode())) {
                                    vo.setCanProcess(1);
                                }
                            }
                        }
                    }
                    if (Constants.HUANFANGADMIN.equals(ass[0])) {
                        //该此幻方管理员处理
                        if (Constants.HUANFANGADMIN.equals(roleName)) {
                            vo.setCanProcess(1);
                        }
                    }
                    if (Constants.DISTNCTADMIN.equals(ass[0])) {
                        //该此区县管理员处理
                        if (Constants.DISTNCTADMIN.equals(roleName)) {
                            vo.setCanProcess(1);
                        }
                    }
                } else {
                    vo.setCanProcess(0);
                }
            }
        }

        return vo;
    }


    /**
     * 首页运维使用数
     *
     * @return
     */
    public List loadOpertions() {
        String sql = "SELECT  " +
                " COUNT(*),(SELECT COUNT(*) FROM tab_operations top WHERE top.end_date IS NOT NULL) " +
                " FROM tab_operations top ";
        return opertionRepository.findListBySql(sql);
    }

    @Override
    @Transactional(readOnly = false)
    public String doCompleteTask(SubmitAviceVo vo) {
        Map<String, Object> param = new HashMap<>();
        Operations operations = opertionRepository.findOne(vo.getOpertionId());
        //自动异常处理
        if (Operations.AUTOA.equals(operations.getBirthType())) {
            param.put(Constants.CHOOSE, vo.getChoose());
            if ("no".equals(vo.getChooseCompany())) {
                param.put(Constants.COMPANYID, Constants.COMPANYID + ":" + vo.getChooseCompany());
            }
            actService.doCompleteTask(vo.getTaskId(), param);
        } else if (Operations.SCARP.equals(operations.getBirthType())) {
            //报废暂时没有打回操作
            actService.doCompleteTask(vo.getTaskId());
            Accessory one = accessoryRepository.findOne(operations.getAccessoryId());
            one.setFacilities(3);
            accessoryRepository.save(one);
        } else {
            actService.doCompleteTask(vo.getTaskId());
        }
        ActivityValue activityValue = new ActivityValue();
        activityValue.setTaskId(vo.getTaskId());
        activityValue.setValue(vo.getProcessAdvice());
        activityValue.setType(ActivityValue.REMARK);
        actValueRepository.save(activityValue);
        actService.isEnded(operations.getProcessId());
        operations.setEndDate(actService.isEnded(operations.getProcessId()));
        return "审批成功!";
    }


    /**
     * 申请报废
     *
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String approScrap(Long accessoryId, Long classId) {
        Accessory accessory = accessoryRepository.findOne(accessoryId);
        ClassRoom classRoom = accessory.getClassRoom();
        Building building = classRoom.getBuilding();
        School school = building.getSchool();

        School currentSchool = getCurrentSchool();
        Role highRole = UserUtil.getCurrentMostHighRole();
        if (Constants.SCHOOLADMIN.equals(highRole.getRoleName())) {
            if (school != null && currentSchool != null) {
                if (school.getSchoolCode().equals(currentSchool.getSchoolCode())) {
                    Operations operations = new Operations();
                    operations.setClasses(classRepository.findOne(classId));
                    operations.setSchool(school);
                    operations.setBirthType(Operations.SCARP);
                    operations.setExceptionDescription("申请设备报废");
                    operations.setUser(UserUtil.getCurrentUser());
                    operations.setStartDate(new Date());
                    //operations.setCompany(companyRepository.findOne(companyId));
                    Map<String, Object> param = new HashMap<>();
                    param.put(Constants.DISTNCTADMIN, Constants.COMPANYID + ":" + Constants.DISTNCTADMIN);
                    String processId = actService.startProcessByKey(Operations.SCRAPKEY, param);
                    operations.setProcessId(processId);
                    opertionRepository.save(operations);
                    return "提交成功,等待审批!";
                }
            }

        }

        return Constants.NOAUTH;
    }


}




















