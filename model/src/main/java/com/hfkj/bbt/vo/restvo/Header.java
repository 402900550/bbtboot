package com.hfkj.bbt.vo.restvo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017-12-26.
 */
public class Header {
    //操作时间yyyy-MM-dd HH:mm:ss
    @JSONField(ordinal = 8)
    private String time;
    //是否处理成功
    @JSONField(ordinal = 6)
    private String success;
    //数据类型
    @JSONField(ordinal = 1)
    private String dataType;
    //设备号
    @JSONField(ordinal = 2)
    private String equipmentNo;
    //设备类型
    @JSONField(ordinal = 3)
    private String equipmentType;
    //学校代码
    @JSONField(ordinal = 4)
    private String schoolCode;
    //IC卡号
    @JSONField(ordinal = 5)
    private String icardNo;
    //教师数据版本号
    @JSONField(ordinal = 9)
    private String version;
    @JSONField(ordinal = 7)
    private String ip;
    /**
     * 1：网络通信失败2:485通信失败
     */
    @JSONField(ordinal = 10)
    private String failType;

    public String getFailType() {
        return failType;
    }

    public void setFailType(String failType) {
        this.failType = failType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcardNo() {
        return icardNo;
    }

    public void setIcardNo(String icardNo) {
        this.icardNo = icardNo;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
}
