package com.hfkj.bbt.entity;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

import javax.persistence.*;

/**
 * 用户表
 */
@Entity
@Table(name = "tab_time_table")
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @Column(name = "name",length = 30,nullable = false)
    private String name;

    @Column(name = "start",length = 30,nullable = false)
    private String start;
    @Column(name = "end",length = 30,nullable = false)
    private String end;

    @ManyToOne(fetch = FetchType.LAZY)
    private Grade grade;

    public TimeTable(){};

    public TimeTable(String name,String start,String end , Grade grade){
        this.name=name;
        this.start=start;
        this.end=end;
        this.grade=grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
