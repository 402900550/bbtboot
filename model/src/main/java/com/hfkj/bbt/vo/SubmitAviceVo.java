package com.hfkj.bbt.vo;

public class SubmitAviceVo {
    //处理方式
    private String choose;
    //维修公司
    private Long chooseCompany;
    //任务ID
    private String taskId;
    //审批意见
    private String processAdvice;
    //运维单ID
    private Long opertionId;

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }

    public Long getChooseCompany() {
        return chooseCompany;
    }

    public void setChooseCompany(Long chooseCompany) {
        this.chooseCompany = chooseCompany;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessAdvice() {
        return processAdvice;
    }

    public void setProcessAdvice(String processAdvice) {
        this.processAdvice = processAdvice;
    }

    public Long getOpertionId() {
        return opertionId;
    }

    public void setOpertionId(Long opertionId) {
        this.opertionId = opertionId;
    }
}
