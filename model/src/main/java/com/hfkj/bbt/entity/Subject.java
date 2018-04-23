package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017-06-30.
 */
@Entity
@Table(name = "tab_subject")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1026253642806892273L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    /**科目名称*/
    @Column(name = "subject_name")
    private String subjectName;

    public Subject(){}

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
