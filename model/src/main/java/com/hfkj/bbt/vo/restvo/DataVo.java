package com.hfkj.bbt.vo.restvo;

/**
 * Created by Administrator on 2017-12-27.
 */
public class DataVo {

    //共同消息
    private Header header;
    //实时运行状态
    private RunTime runtime;
    //环境监测
    private Environment environment;
    //设备使用总时间
    private Total total;
    //功耗
    private String power;
    //教师数据IC卡号集合
    private String teacherData;
    //异常类型
    private String errorType;
    //异常信息
    private String errorMsg;
    //能否使用
    private String canUse;

    public String getCanUse() {
        return canUse;
    }

    public void setCanUse(String canUse) {
        this.canUse = canUse;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public RunTime getRuntime() {
        return runtime;
    }

    public void setRuntime(RunTime runtime) {
        this.runtime = runtime;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getTeacherData() {
        return teacherData;
    }

    public void setTeacherData(String teacherData) {
        this.teacherData = teacherData;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
