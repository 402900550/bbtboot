package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017-07-13.
 */
@Table(name = "tab_school_type")
@Entity
public class SchoolType implements Serializable {

    private static final long serialVersionUID = 5381966421642195836L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 类型名称
     */
    @Column(name = "name")
    private String name;

    public SchoolType() {
    }

    public SchoolType(String name) {
        this.name = name;
    }

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
