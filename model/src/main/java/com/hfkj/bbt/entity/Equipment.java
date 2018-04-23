package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/9 0009.
 */
/*设备*/
@Entity
@Table(name = "tab_equipment")
public class Equipment implements Serializable {
    private static final long serialVersionUID = 5650407915103169818L;
    /*设备编号*/
    @Id
    @Column(name = "equipment_no", length = 50)
    private String equipmentNo;

    /*控制器类型 电控和班班通控制器 1为班班通 2为电控*/
    @Column(name = "equipment_type")
    private Integer equipmentType;



    /*控制器当前工作状态 1开 0关*/
    @Column(name = "work_status")
    private Integer workStatus;

    /*电脑工作状态 1 开 0 关*/
    @Column(name = "pc_status")
    private Integer pcStatus;

    /*投影工作状态 1 开 0 关*/
    @Column(name = "display_status")
    private Integer displayStatus;

    /*信源状态 1 VGA1 2 VGA2*/
    @Column(name = "msgsource_status")
    private Integer msgsourceStatus;

    /*幕布状态 下降表示开 0 上升表示关*/
    @Column(name = "drop_status")
    private Integer dropStatus;

    /*风扇状态 1 开 0 关*/
    @Column(name = "fan_status")
    private Integer fanStatus;

    @Column(name = "fan_power")
    private Integer fanPower;

    /*电灯状态 1 开 0 关*/
    @Column(name = "lights_status")
    private Integer lightsStatus;

    @Column(name = "light_power")
    private Integer lightPower;

    /*空调开关 1 开 0 关*/
    @Column(name = "aircondition_status")
    private Integer airconditionStatus;

    @Column(name = "aircondition_power")
    private Integer airconditionPower;

    /*插座开关 1 开 0 关*/
    @Column(name = "sockets_status")
    private Integer socketsStatus;

    @Column(name = "sockets_power")
    private Integer socketsPower;

    /*心跳时间*/
    @Column(name = "heart_time")
    private Date heartTime;

    /*设备IP*/
    @Column(name = "equipment_ip", length = 50)
    private String equipmentIp;

    /**当天是否使用*/
    @Column(name = "is_used")
    private Integer isUsed;

    /**需要发送的消息*/
    @Column(name = "send_message")
    private String sendMessage;
    //已废弃
    @Column(name = "power")
    private String power;

    @Column(name = "icard_no")
    private String icardNo;

    /**温度*/
    @Column(name = "temperature")
    private String temperature;
    /**湿度*/
    @Column(name = "humidity")
    private String humidity;
    /**照度*/
    @Column(name = "illuminate")
    private String illuminate;
    /**噪音*/
    @Column(name = "noise")
    private String noise;
    /**pm2.5*/
    @Column(name = "pm2_5")
    private String pm25;

    @Column(name = "collect_time")
    private Date collectTime;

    //备注用于存储电控失败的
    private String remark;

    @OneToOne(mappedBy = "equipment")
    private ClassRoom classRoom;

    public Integer getFanPower() {
        return fanPower;
    }

    public void setFanPower(Integer fanPower) {
        this.fanPower = fanPower;
    }

    public Integer getLightPower() {
        return lightPower;
    }

    public void setLightPower(Integer lightPower) {
        this.lightPower = lightPower;
    }

    public Integer getAirconditionPower() {
        return airconditionPower;
    }

    public void setAirconditionPower(Integer airconditionPower) {
        this.airconditionPower = airconditionPower;
    }

    public Integer getSocketsPower() {
        return socketsPower;
    }

    public void setSocketsPower(Integer socketsPower) {
        this.socketsPower = socketsPower;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getIlluminate() {
        return illuminate;
    }

    public void setIlluminate(String illuminate) {
        this.illuminate = illuminate;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getIcardNo() {
        return icardNo;
    }

    public void setIcardNo(String icardNo) {
        this.icardNo = icardNo;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }


    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    public Integer getPcStatus() {
        return pcStatus;
    }

    public void setPcStatus(Integer pcStatus) {
        this.pcStatus = pcStatus;
    }

    public Integer getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
    }

    public Integer getMsgsourceStatus() {
        return msgsourceStatus;
    }

    public void setMsgsourceStatus(Integer msgsourceStatus) {
        this.msgsourceStatus = msgsourceStatus;
    }

    public Integer getDropStatus() {
        return dropStatus;
    }

    public void setDropStatus(Integer dropStatus) {
        this.dropStatus = dropStatus;
    }

    public Integer getFanStatus() {
        return fanStatus;
    }

    public void setFanStatus(Integer fanStatus) {
        this.fanStatus = fanStatus;
    }

    public Integer getLightsStatus() {
        return lightsStatus;
    }

    public void setLightsStatus(Integer lightsStatus) {
        this.lightsStatus = lightsStatus;
    }

    public Integer getAirconditionStatus() {
        return airconditionStatus;
    }

    public void setAirconditionStatus(Integer airconditionStatus) {
        this.airconditionStatus = airconditionStatus;
    }

    public Integer getSocketsStatus() {
        return socketsStatus;
    }

    public void setSocketsStatus(Integer socketsStatus) {
        this.socketsStatus = socketsStatus;
    }

    public Date getHeartTime() {
        return heartTime;
    }

    public void setHeartTime(Date heartTime) {
        this.heartTime = heartTime;
    }

    public String getEquipmentIp() {
        return equipmentIp;
    }

    public void setEquipmentIp(String equipmentIp) {
        this.equipmentIp = equipmentIp;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }
}
