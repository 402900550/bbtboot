package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tab_usedrecord")
public class UsedRecord implements Serializable {

    private static final long serialVersionUID = 448836754275783518L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Equipment equipment;


    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    /*icka号码*/
    @Column(name = "icard_no",length = 50)
    private String icardNo;

    /*信源状态 1 VGA1 2VGA2*/
    @Column(name = "zt1")
    private Integer zt1;

    @Column(name = "zt1_start")
    private Timestamp zt1Start;

    @Column(name = "zt1_end")
    private Timestamp zt1End;

    /*电脑状态 1开 0关*/
    @Column(name = "zt2")
    private Integer zt2;

    /*电脑开启 开始时间*/
    @Column(name = "zt2_start")
    private Timestamp zt2Start;

    /*电脑关闭时间*/
    @Column(name = "zt2_end")
    private Timestamp zt2End;

    /*投影状态 1开 0关*/
    @Column(name = "zt3")
    private Integer zt3;

    @Column(name = "zt3_start")
    private Timestamp zt3Start;

    @Column(name = "zt3_end")
    private Timestamp zt3End;

    /*幕布状态 1开 0关*/
    @Column(name = "zt4")
    private Integer zt4;

    @Column(name = "zt4_start")
    private Timestamp zt4Start;

    @Column(name = "zt4_end")
    private Timestamp zt4End;

    /*控制器 状态 1开 0关*/
    @Column(name = "zt5")
    private Integer zt5;

    @Column(name = "zt5_start")
    private Timestamp zt5Start;

    @Column(name = "zt5_end")
    private Timestamp zt5End;

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

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getIcardNo() {
        return icardNo;
    }

    public void setIcardNo(String icardNo) {
        this.icardNo = icardNo;
    }

    public Integer getZt1() {
        return zt1;
    }

    public void setZt1(Integer zt1) {
        this.zt1 = zt1;
    }

    public Timestamp getZt1Start() {
        return zt1Start;
    }

    public void setZt1Start(Timestamp zt1Start) {
        this.zt1Start = zt1Start;
    }

    public Timestamp getZt1End() {
        return zt1End;
    }

    public void setZt1End(Timestamp zt1End) {
        this.zt1End = zt1End;
    }

    public Integer getZt2() {
        return zt2;
    }

    public void setZt2(Integer zt2) {
        this.zt2 = zt2;
    }

    public Timestamp getZt2Start() {
        return zt2Start;
    }

    public void setZt2Start(Timestamp zt2Start) {
        this.zt2Start = zt2Start;
    }

    public Timestamp getZt2End() {
        return zt2End;
    }

    public void setZt2End(Timestamp zt2End) {
        this.zt2End = zt2End;
    }

    public Integer getZt3() {
        return zt3;
    }

    public void setZt3(Integer zt3) {
        this.zt3 = zt3;
    }

    public Timestamp getZt3Start() {
        return zt3Start;
    }

    public void setZt3Start(Timestamp zt3Start) {
        this.zt3Start = zt3Start;
    }

    public Timestamp getZt3End() {
        return zt3End;
    }

    public void setZt3End(Timestamp zt3End) {
        this.zt3End = zt3End;
    }

    public Integer getZt4() {
        return zt4;
    }

    public void setZt4(Integer zt4) {
        this.zt4 = zt4;
    }

    public Timestamp getZt4Start() {
        return zt4Start;
    }

    public void setZt4Start(Timestamp zt4Start) {
        this.zt4Start = zt4Start;
    }

    public Timestamp getZt4End() {
        return zt4End;
    }

    public void setZt4End(Timestamp zt4End) {
        this.zt4End = zt4End;
    }

    public Integer getZt5() {
        return zt5;
    }

    public void setZt5(Integer zt5) {
        this.zt5 = zt5;
    }

    public Timestamp getZt5Start() {
        return zt5Start;
    }

    public void setZt5Start(Timestamp zt5Start) {
        this.zt5Start = zt5Start;
    }

    public Timestamp getZt5End() {
        return zt5End;
    }

    public void setZt5End(Timestamp zt5End) {
        this.zt5End = zt5End;
    }
}
