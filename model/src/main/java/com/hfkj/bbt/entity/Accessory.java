package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**附属设备表
 * Created by Administrator on 2017-11-06.
 */
@Entity
@Table(name = "tab_accessory")
public class Accessory implements Serializable {

    private static final long serialVersionUID = -2837178437059350592L;
    /**id*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    /**采购时间*/
    @Column(name = "purchase_date",nullable = false)
    private Date purchaseDate;
    /**单价*/
    @Column(name = "price",nullable = false)
    private Integer price;
    /**维修次数*/
    @Column(name = "repair_times")
    private Integer repairTimes;

    /**使用总时间*/
    @Column(name = "used_time")
    private String usedTime;


    /**完好情况 1表示完好 2表示维修中 3表示报废*/
    @Column(name = "facilities",nullable = false)
    private Integer facilities;

    @ManyToOne(fetch = FetchType.LAZY)
    private  ClassRoom classRoom;
    //规格型号
    @ManyToOne(fetch = FetchType.LAZY)
    private SpecificationModel specificationModel;

    //品牌
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccessoryName accessoryName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRepairTimes() {
        return repairTimes;
    }

    public void setRepairTimes(Integer repairTimes) {
        this.repairTimes = repairTimes;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }


    public Integer getFacilities() {
        return facilities;
    }

    public void setFacilities(Integer facilities) {
        this.facilities = facilities;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public SpecificationModel getSpecificationModel() {
        return specificationModel;
    }

    public void setSpecificationModel(SpecificationModel specificationModel) {
        this.specificationModel = specificationModel;
    }

    public AccessoryName getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(AccessoryName accessoryName) {
        this.accessoryName = accessoryName;
    }
}
