package com.bsuir.clinic.website.entity;

import com.bsuir.clinic.website.constants.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ClinicDepartment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(JsonViews.Public.class)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "clinic_id")
    @JsonView(JsonViews.Public.class)
    private Clinic clinic;

    @JsonView(JsonViews.Public.class)
    private String name;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonView(JsonViews.Public.class)
    private List<Street> streets;

    @Transient
    @JsonView(JsonViews.Public.class)
    private String streetNames;

    @OneToMany(mappedBy = "clinicDepartment", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonView(JsonViews.Public.class)
    private List<Patient> patients = new ArrayList<>();;

    @OneToMany(mappedBy = "clinicDepartment", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonView(JsonViews.Public.class)
    private List<Doctor> doctors = new ArrayList<>();

    public ClinicDepartment() {
    }

    public ClinicDepartment(Clinic clinic, String name, List<Street> streets) {
        this.id = null;
        this.clinic = clinic;
        this.name = name;
        this.streets = streets;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public String getName() {
        return name;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctor(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public String getStreetNames() {
        if (streetNames == null) {
            List<String> streetNamesAsList = new ArrayList<>();
            for (Street street : getStreets()) {
                streetNamesAsList.add(street.getName());
            }
            streetNames = StringUtils.join(streetNamesAsList, ", ");
        }
        return streetNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClinicDepartment)) return false;
        ClinicDepartment that = (ClinicDepartment) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClinic(), getName(), getStreets(), getPatients(), getDoctors());
    }
}
