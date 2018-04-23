package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 区县表
 */
@Entity
@Table(name = "tab_distinct")
public class Distinct implements Serializable {


    private static final long serialVersionUID = 7815550147158258450L;
    @Column(name = "name",nullable = false)
    private String name;

    @Id
    @Column(name = "code")
    private String code;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
