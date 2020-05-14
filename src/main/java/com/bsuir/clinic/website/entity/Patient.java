package com.bsuir.clinic.website.entity;

import com.bsuir.clinic.website.constants.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Patient extends User {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    private ClinicDepartment clinicDepartment;

    @JsonView(JsonViews.Public.class)
    private String address;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonView(JsonViews.AppointmentsInfo.class)
    private List<Appointment> appointments;

    public Patient() {
    }

    public Patient(String login, String password, ClinicDepartment clinicDepartment) {
        super(null, login, password);
        this.clinicDepartment = clinicDepartment;
    }

    public ClinicDepartment getClinicDepartment() {
        return clinicDepartment;
    }

    public void setClinicDepartment(ClinicDepartment clinicDepartment) {
        this.clinicDepartment = clinicDepartment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        return super.equals(o);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clinicDepartment.hashCode(), getAddress());
    }
}
