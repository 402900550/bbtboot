package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017-10-09.
 */
@Entity
@Table(name="tab_runtotaltime")
public class RunTotalTime implements Serializable {
    private static final long serialVersionUID = 7742361842267874227L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*数据类型*/
    @Column(name = "data_type",length = 50)
    private String dataType;

    /*设备类型 1终端 2PC*/
    @Column(name = "equipment_type")
    private Integer equipmentType;

    /*设备号*/
    @Column(name = "equipment_no",length = 50)
    private String equipmentNo;


    /*学校代码*/
    @Column(name = "school_code",length = 50)
    private String schoolCode;

    /*控制器时间*/
    @Column(name = "time1",length = 50)
    private String time1;

    /*电脑时间*/
    @Column(name = "time2",length = 50)
    private String time2;

    /*投影时间*/
    @Column(name = "time3",length = 50)
    private String time3;

    /*幕布时间*/
    @Column(name = "time4",length = 50)
    private String time4;

    /*展台时间*/
    @Column(name = "time5",length = 50)
    private String time5;

    /*预留字段*/
    @Column(name = "time6",length = 50)
    private String time6;

    /*预留字段*/
    @Column(name = "time7",length = 50)
    private String time7;

    /**统计时间*/
    @Column(name = "collect_time")
    private Timestamp collectTime;

    public Timestamp getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Timestamp collectTime) {
        this.collectTime = collectTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }


    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    public String getTime5() {
        return time5;
    }

    public void setTime5(String time5) {
        this.time5 = time5;
    }

    public String getTime6() {
        return time6;
    }

    public void setTime6(String time6) {
        this.time6 = time6;
    }

    public String getTime7() {
        return time7;
    }

    public void setTime7(String time7) {
        this.time7 = time7;
    }
}
