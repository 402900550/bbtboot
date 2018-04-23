package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tab_develop")
public class DevelopType implements Serializable{

    private static final long serialVersionUID=12556388227L;

    @Id
    @GeneratedValue
    private Long id;

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
