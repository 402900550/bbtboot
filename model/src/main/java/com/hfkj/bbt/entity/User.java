package com.hfkj.bbt.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * 用户表
 */
@Entity
@Table(name = "tab_user")
public class User implements Serializable {

    private static final long serialVersionUID = -8410587392130600745L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**用户名 登陆系统的名*/
    @Column(name = "user_name",length = 30,nullable = false,unique=true)
    private String userName;
    /**密码*/
    @Column(name = "password",length = 30,nullable = false)
    private String password;
    /**真实姓名*/
    @Column(name = "real_name",length = 30,nullable = false)
    private String realName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    /*性别 1男 2女*/
    @Column(name = "sex")
    private Integer sex;

    /*头像地址*/
    @Column(name = "photo_url",length = 100)
    private String photoUrl;

    /*电话号码*/
    @Column(name = "phone",length = 30)
    private String phone;

    /*教师IC卡*/
    @Column(name = "icard_no",length = 30)
    private String icardNo;

    /*邮箱*/
    @Column(name = "email",length = 30)
    private String email;

    /**微信openID*/
    @Column(name = "openid",unique=true)
    private String openid;

    /**用户状态 -1停用 0待审核 1启用 2审核未通过*/
    @Column(name = "status",nullable = false)
    private Integer status;

    /*账号创建时间*/
    @Column(name = "create_date",nullable = false)
    private Timestamp createDate;

    /*账号更新时间*/
    @Column(name = "update_date")
    private Timestamp updateDate;
    //单向多对多
    @ManyToMany
    //中间表的信息name:表名，joinColumns={@JoinColumn(当前User类在中间表列名，关联Role类在中间表列名}
    @JoinTable(name="tab_user_to_role",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<Role>();
    /*单位代码 即学校表中的schoolcode*/
    //多对一
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.REMOVE)
    private Set<EquipmentTeacher> equipmentTeachers;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.REMOVE)
    private Set<Operations> operations;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Set<Operations> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operations> operations) {
        this.operations = operations;
    }

    public Set<EquipmentTeacher> getEquipmentTeachers() {
        return equipmentTeachers;
    }

    public void setEquipmentTeachers(Set<EquipmentTeacher> equipmentTeachers) {
        this.equipmentTeachers = equipmentTeachers;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIcardNo() {
        return icardNo;
    }

    public void setIcardNo(String icardNo) {
        this.icardNo = icardNo;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
