package com.hfkj.bbt.vo.restvo;

/**
 * Created by Administrator on 2017-12-26.
 */
public class RunTime {
    //信源状态
    private String source;
    //电脑状态
    private String pc;
    //投影状态
    private String display;
    //幕布状态
    private String drop;
    //控制器
    private String workStatus;

    //插座状态
    private String sockets;
    //插座功耗
    private String power1;


    //电灯状态
    private String lights;
    //电灯功耗
    private String power2;

    //风扇状态
    private String fan;
    //风扇功耗
    private String power3;

    //空调状态
    private String airConditioning;
    //空调功耗
    private String power4;


    //全部开关
    private String All_ON_OFF;

    public String getPower1() {
        return power1;
    }

    public void setPower1(String power1) {
        this.power1 = power1;
    }

    public String getPower2() {
        return power2;
    }

    public void setPower2(String power2) {
        this.power2 = power2;
    }

    public String getPower3() {
        return power3;
    }

    public void setPower3(String power3) {
        this.power3 = power3;
    }

    public String getPower4() {
        return power4;
    }

    public void setPower4(String power4) {
        this.power4 = power4;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getAll_ON_OFF() {
        return All_ON_OFF;
    }

    public void setAll_ON_OFF(String all_ON_OFF) {
        All_ON_OFF = all_ON_OFF;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public String getLights() {
        return lights;
    }

    public void setLights(String lights) {
        this.lights = lights;
    }

    public String getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(String airConditioning) {
        this.airConditioning = airConditioning;
    }

    public String getFan() {
        return fan;
    }

    public void setFan(String fan) {
        this.fan = fan;
    }

    public String getSockets() {
        return sockets;
    }

    public void setSockets(String sockets) {
        this.sockets = sockets;
    }
}
