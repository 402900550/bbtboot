package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 品牌表
 */
@Entity
@Table(name = "tab_brand")
public class Brand implements Serializable {
    private static final long serialVersionUID = 2405162040165769620L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name",unique = true)
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
