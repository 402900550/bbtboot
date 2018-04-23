package com.hfkj.bbt.vo;

import java.util.List;

public class OpertionDetailVo {

    //上报人
    private String opUserName;
    //异常描述
    private String exceptionDescription;

    private List<HistoryActVo> actVos;

    //流程开始时间
    private String startDate;

    private String endDate;

    //是否能审批 1表示可以 0表示不可以
    private Integer canProcess;

    //任务ID
    private String taskId;
    //是否可以选择转接
    private Integer canChoose;

    private Long opertionId;

    //报废的设备ID
    private Long accessoryId;

    public Long getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Long accessoryId) {
        this.accessoryId = accessoryId;
    }

    public Long getOpertionId() {
        return opertionId;
    }

    public void setOpertionId(Long opertionId) {
        this.opertionId = opertionId;
    }

    public Integer getCanChoose() {
        return canChoose;
    }

    public void setCanChoose(Integer canChoose) {
        this.canChoose = canChoose;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getCanProcess() {
        return canProcess;
    }

    public void setCanProcess(Integer canProcess) {
        this.canProcess = canProcess;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<HistoryActVo> getActVos() {
        return actVos;
    }

    public void setActVos(List<HistoryActVo> actVos) {
        this.actVos = actVos;
    }

    public String getExceptionDescription() {
        return exceptionDescription;
    }

    public void setExceptionDescription(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }
}
