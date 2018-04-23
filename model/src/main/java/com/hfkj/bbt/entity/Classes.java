package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/9 0009.
 */
/*班级*/
@Entity
@Table(name = "tab_classes",uniqueConstraints = @UniqueConstraint(columnNames = {"class_name","grade_id"}))
public class Classes implements Serializable {
    private static final long serialVersionUID = -8058370300493933429L;
    /*主键id*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*班级名称*/
    @Column(name = "class_name", length = 50,nullable = false)
    private String className;

    @Column(name = "create_date")
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Grade grade;

    //班主任
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id",unique = true)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classRoom_id",unique = true)
    private ClassRoom classRoom;

    @OneToMany(mappedBy = "classes",cascade = CascadeType.REMOVE)
    private Set<EquipmentClass> equipmentClasses;

    @OneToMany(mappedBy = "classes",cascade = CascadeType.REMOVE)
    private Set<Operations> operations;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Operations> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operations> operations) {
        this.operations = operations;
    }

    public Set<EquipmentClass> getEquipmentClasses() {
        return equipmentClasses;
    }

    public void setEquipmentClasses(Set<EquipmentClass> equipmentClasses) {
        this.equipmentClasses = equipmentClasses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }
}