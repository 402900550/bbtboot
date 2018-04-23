package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tab_model")
public class SpecificationModel implements Serializable {


    private static final long serialVersionUID = -6981695303352868525L;
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
