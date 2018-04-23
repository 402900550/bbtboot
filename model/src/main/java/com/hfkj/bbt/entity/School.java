package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tab_school")
public class School implements Serializable {

    private static final long serialVersionUID = 2774498818289536680L;
    @Column(name = "name",nullable = false)
    private String name;
    @Id
    @Column(name = "school_code")
    private String schoolCode;

    @Column(name = "address")
    private String address;

    @Column(name = "version",length = 30,nullable = false)
    private String version;

    @Column(name = "gateway_ip")
    private String gatewayIp;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Distinct distinct;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private SchoolType schoolType;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school",cascade = CascadeType.REMOVE)
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school",cascade = CascadeType.REMOVE)
    private Set<Building> buildings;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school",cascade = CascadeType.REMOVE)
    private Set<Grade> grades;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school",cascade = CascadeType.REMOVE)
    private Set<EquipmentSchool> equipmentSchools;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school",cascade = CascadeType.REMOVE)
    private Set<EquipmentSubject> equipmentSubjects;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school",cascade = CascadeType.REMOVE)
    private Set<Operations> operations;

    public Set<Operations> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operations> operations) {
        this.operations = operations;
    }

    public Set<EquipmentSchool> getEquipmentSchools() {
        return equipmentSchools;
    }

    public void setEquipmentSchools(Set<EquipmentSchool> equipmentSchools) {
        this.equipmentSchools = equipmentSchools;
    }

    public Set<EquipmentSubject> getEquipmentSubjects() {
        return equipmentSubjects;
    }

    public void setEquipmentSubjects(Set<EquipmentSubject> equipmentSubjects) {
        this.equipmentSubjects = equipmentSubjects;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public Set<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Distinct getDistinct() {
        return distinct;
    }

    public void setDistinct(Distinct distinct) {
        this.distinct = distinct;
    }

    public SchoolType getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
    }
}
