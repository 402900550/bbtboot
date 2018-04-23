package com.hfkj.bbt.activity.impl;

import com.hfkj.bbt.activity.IActService;
import com.hfkj.bbt.constant.Constants;
import com.hfkj.bbt.entity.ActivityValue;
import com.hfkj.bbt.entity.Company;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.repository.ActValueRepository;
import com.hfkj.bbt.repository.CompanyRepository;
import com.hfkj.bbt.repository.SchoolRepository;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.util.DateUtil;
import com.hfkj.bbt.vo.HistoryActVo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActServiceImpl implements IActService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private ActValueRepository valueRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SchoolRepository schoolRepository;
    /**
     * 根据key启动流程实例
     *
     * @param key
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String startProcessByKey(String key) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        return processInstance.getProcessInstanceId();
    }

    /**
     * 根据key启动流程实例带参数
     *
     * @param key
     * @param map
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String startProcessByKey(String key, Map<String, Object> map) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);
        return processInstance.getProcessInstanceId();
    }

    /**
     * 查询流程历史
     *
     * @param processId
     * @return
     */
    @Override
    public List<HistoryActVo> loadProcessHisByInstanceId(String processId) {
        List<HistoryActVo> actVos = new ArrayList<>();
        if (!ComUtil.stringIsNotNull(processId)) {
            return actVos;
        }
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(processId)
                .orderByHistoricActivityInstanceStartTime().asc().list();
        Iterator<HistoricActivityInstance> iterator = list.iterator();
        HistoryActVo vo;
        while (iterator.hasNext()) {
            HistoricActivityInstance instance = iterator.next();
            vo = new HistoryActVo();
            vo.setActName(instance.getActivityName());
            vo.setStartDate(DateUtil.tranDateToString(Constants.YYYYMMDDHHMMSS, instance.getStartTime()));
            vo.setEndDate(DateUtil.tranDateToString(Constants.YYYYMMDDHHMMSS, instance.getEndTime()));
            String taskId = instance.getTaskId();
            if (ComUtil.stringIsNotNull(taskId)) {
                ActivityValue activityValue = valueRepository.findByTaskIdAndType(taskId, ActivityValue.REMARK);
                if (activityValue != null) {
                    vo.setRemark(activityValue.getValue());
                }
            }
            String assignee = instance.getAssignee();
            if (ComUtil.stringIsNotNull(assignee)) {
                String[] ass = assignee.split(":");
                if (ass.length > 1) {
                    if (Constants.COMPANYID.equals(ass[0])) {
                        Company company = companyRepository.findOne(Long.valueOf(ass[1]));
                        vo.setProcessUnit(company.getName());
                    }
                    if(Constants.SCHOOLCODE.equals(ass[0])){
                        School school = schoolRepository.findOne(ass[1]);
                        vo.setProcessUnit(school.getName());
                    }
                    if(Constants.HUANFANGADMIN.equals(ass[0])){
                        vo.setProcessUnit("幻方管理员");
                    }
                    if(Constants.DISTNCTADMIN.equals(ass[0])){
                        vo.setProcessUnit("区县管理员");
                    }
                }
            }

            actVos.add(vo);

        }

        return actVos;
    }

    /**
     * 查询当前流程该谁处理了
     * @param processId
     */
    public Task findTaskByProcessId(String processId){
        return taskService.createTaskQuery().processInstanceId(processId).singleResult();
    }

    public Date isEnded(String processId){
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult().getEndTime();

    }


    @Override
    @Transactional(readOnly = false)
    public void doCompleteTask(String taskId){
        if(taskId!=null){
            taskService.complete(taskId);
        }
    }
    @Override
    @Transactional(readOnly = false)
    public void doCompleteTask(String taskId,Map<String,Object> param){
        if(taskId!=null){
            taskService.complete(taskId,param);
        }
    }

}




















