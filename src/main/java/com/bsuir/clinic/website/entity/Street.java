package com.bsuir.clinic.website.entity;

import com.bsuir.clinic.website.constants.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Street implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(JsonViews.Public.class)
    private Integer id;

    @JsonView(JsonViews.Public.class)
    private String name;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    private ClinicDepartment department;

    public Street() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClinicDepartment getDepartment() {
        return department;
    }

    public void setDepartment(ClinicDepartment department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return name;
    }
}
