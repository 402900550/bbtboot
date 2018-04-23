package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 合作企业表
 */
@Entity
@Table(name = "tab_company")
public class Company implements Serializable {

    private static final long serialVersionUID = 8991765541746160370L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
