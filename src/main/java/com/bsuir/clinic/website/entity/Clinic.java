package com.bsuir.clinic.website.entity;

import com.bsuir.clinic.website.constants.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Clinic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonView(JsonViews.Public.class)
    private String address;

    @JsonView(JsonViews.Public.class)
    private String description;

    @OneToMany(mappedBy = "clinic", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private List<ClinicDepartment> clinicDepartments;

    public Clinic() {
    }

    public Clinic(String address, String description) {
        this.id = null;
        this.address = address;
        this.description = description;
        this.clinicDepartments = new ArrayList<>();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ClinicDepartment> getClinicDepartments() {
        return clinicDepartments;
    }

    public void setClinicDepartments(List<ClinicDepartment> clinicDepartments) {
        this.clinicDepartments = clinicDepartments;
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clinic)) return false;
        Clinic clinic = (Clinic) o;
        return Objects.equals(getId(), clinic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getDescription(), getClinicDepartments());
    }
}
