package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/10 0010.
 */
/*教学楼*/
@Entity
@Table(name = "tab_building")
public class Building implements Serializable {
    private static final long serialVersionUID = 7471652897614939459L;
    /*教学楼id*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*教室名称*/
    @Column(name = "building_name", length = 50,nullable = false)
    private String buildingName;

    //多对一
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private School school;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "building",cascade = CascadeType.REMOVE)
    private Set<ClassRoom> classRooms;

    public Set<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(Set<ClassRoom> classRooms) {
        this.classRooms = classRooms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
