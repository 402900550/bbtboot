package com.hfkj.bbt.vo;

public class ClassRoomAccessoryVo {

    private Long roomId;

    private Long developTypeId;

    private Integer personCost;

    private String manager;

    private String inputDate;

    private String equipments;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getDevelopTypeId() {
        return developTypeId;
    }

    public void setDevelopTypeId(Long developTypeId) {
        this.developTypeId = developTypeId;
    }

    public Integer getPersonCost() {
        return personCost;
    }

    public void setPersonCost(Integer personCost) {
        this.personCost = personCost;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }
}
