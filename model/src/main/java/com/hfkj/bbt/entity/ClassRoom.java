package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/10 0010.
 */
/*教室*/
@Entity
@Table(name = "tab_classroom",uniqueConstraints = {@UniqueConstraint(columnNames = {"room_code","building_id"})})
public class ClassRoom implements Serializable {
    private static final long serialVersionUID = -7505850338254488531L;
    /*主键id*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*教室代码 如2栋1楼1号房2101*/
    @Column(name = "room_code", length = 50,nullable = false)
    private String roomCode;


    /*教室类型 教室 办公室*/
    @Column(name = "room_type",nullable = false)
    private String roomType;

    /**负责人*/
    private String manager;

    /**
     * 人工及耗材费
     */
    @Column(name = "person_cost")
    private Integer personCost;

    /**配备类型*/
    @ManyToOne(fetch = FetchType.LAZY)
    private DevelopType developType;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Building building;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "classRoom")
    private Classes classes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_no",unique = true)
    private Equipment equipment;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "classRoom",cascade = CascadeType.REMOVE)
    private Set<Accessory> accessories = new HashSet<Accessory>();

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Integer getPersonCost() {
        return personCost;
    }

    public void setPersonCost(Integer personCost) {
        this.personCost = personCost;
    }

    public DevelopType getDevelopType() {
        return developType;
    }

    public void setDevelopType(DevelopType developType) {
        this.developType = developType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Set<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(Set<Accessory> accessories) {
        this.accessories = accessories;
    }
}
