package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tab_grade",uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Grade implements Serializable {

    private static final long serialVersionUID = -5271611554372781839L;
    /*主键id*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*年级名称*/
    @Column(name = "name", length = 50,nullable = false)
    private String name;

    //多对一
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private School school;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "grade",cascade = CascadeType.REMOVE)
    private Set<Classes> classes;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "grade",cascade = CascadeType.REMOVE)
    private Set<TimeTable> timeTables ;

    public Set<Classes> getClasses() {
        return classes;
    }

    public void setClasses(Set<Classes> classes) {
        this.classes = classes;
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<TimeTable> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(Set<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }
}
