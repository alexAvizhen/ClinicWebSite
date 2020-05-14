package com.bsuir.clinic.website.entity;

import com.bsuir.clinic.website.constants.JsonViews;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(JsonViews.AppointmentsInfo.class)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "doctor_id")
    @JsonView(JsonViews.AppointmentsInfo.class)
    @JsonIgnoreProperties("appointments")
    private Doctor doctor;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "patient_id")
    @JsonView(JsonViews.AppointmentsInfo.class)
    @JsonIgnoreProperties("appointments")
    private Patient patient;

    @JsonView(JsonViews.AppointmentsInfo.class)
    private Date appointmentDate;

    @JsonView(JsonViews.AppointmentsInfo.class)
    private String commentToAppointment;

    public Appointment() {
    }

    public Appointment(Doctor doctor, Patient patient, Date appointmentDate, String commentToAppointment) {
        this.id = null;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.commentToAppointment = commentToAppointment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getCommentToAppointment() {
        return commentToAppointment;
    }

    public void setCommentToAppointment(String commentToAppointment) {
        this.commentToAppointment = commentToAppointment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
