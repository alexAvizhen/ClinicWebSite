package com.bsuir.clinic.website.entity;

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
public class Doctor extends User {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    private ClinicDepartment clinicDepartment;

    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Appointment> appointments;

    public Doctor() {
    }

    public Doctor(String login, String password, ClinicDepartment clinicDepartment) {
        super(null, login, password);
        this.clinicDepartment = clinicDepartment;
    }

    public ClinicDepartment getClinicDepartment() {
        return clinicDepartment;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void setClinicDepartment(ClinicDepartment clinicDepartment) {
        this.clinicDepartment = clinicDepartment;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clinicDepartment.hashCode());
    }
}
