package com.hfkj.bbt.entity;

import javax.persistence.*;

/**
 * activity流程变量
 */
@Entity
@Table(name = "tab_actvity_value",uniqueConstraints = {@UniqueConstraint(columnNames = {"task_id","type"})})
public class ActivityValue {

    public static final String REMARK="remark";

    @Id
    @GeneratedValue
    private Long id;
    //任务ID act里面的
    @Column(name = "task_id")
    private String taskId;
    //变量类型 如remark表示审批意见
    private String type;
    //变量值
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
