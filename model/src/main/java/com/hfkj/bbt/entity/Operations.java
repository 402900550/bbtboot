package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/2 0002.
 */
@Entity
@Table(name = "tab_operations")
public class Operations implements Serializable {
    /**手动异常流程定义KEY手动提交运维单*/
    public static final String HANDEXCEPTIONKEY="HandException";
    /**自动异常流程定义KEY自动异常A*/
    public static final String AUTOEXCEPTIONAKEY="autoExceptionA";
    /**自动异常流程定义KEY报废流程*/
    public static final String SCRAPKEY="scarpException";

    public static final String HAND="hand";

    public static final String SCARP="scarp";

    public static final String AUTOA="autoA";

    public static final String AUTOB="autoB";

    private static final long serialVersionUID = -5918275388801104190L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**异常产生时间*/
    @Column(name = "start_date")
    private Date startDate;
    /**异常描述*/
    @Column(name = "exception_description")
    private String exceptionDescription;

    /**异常学校*/
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;
    /**出现问题的班级*/
    @ManyToOne(fetch = FetchType.LAZY)
    private Classes classes;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * 运维单所属公司
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    /**流程实例ID*/
    @Column(name = "process_id")
    private String processId;

    @Column(name = "end_date")
    private Date endDate;

    /**手动产生：hand 自动产生：auto*/
    @Column(name = "birth_type")
    private String birthType;

    /**需要报废的设备ID*/
    @Column(name = "accessory_id")
    private Long accessoryId;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Long getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Long accessoryId) {
        this.accessoryId = accessoryId;
    }

    public String getBirthType() {
        return birthType;
    }

    public void setBirthType(String birthType) {
        this.birthType = birthType;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public String getExceptionDescription() {
        return exceptionDescription;
    }

    public void setExceptionDescription(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
