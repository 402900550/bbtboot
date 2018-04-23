package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tab_accessoryname")
public class AccessoryName implements Serializable {

    private static final long serialVersionUID = 5868509608311566475L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

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
}
