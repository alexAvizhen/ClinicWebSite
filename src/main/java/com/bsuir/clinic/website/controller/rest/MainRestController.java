package com.bsuir.clinic.website.controller.rest;


import com.bsuir.clinic.website.constants.JsonViews;
import com.bsuir.clinic.website.entity.Appointment;
import com.bsuir.clinic.website.entity.ClinicDepartment;
import com.bsuir.clinic.website.entity.Doctor;
import com.bsuir.clinic.website.entity.Patient;
import com.bsuir.clinic.website.repository.ClinicDepartmentRepository;
import com.bsuir.clinic.website.service.ClinicService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MainRestController {

    private final ClinicDepartmentRepository clinicDepartmentRepository;
    private final ClinicService clinicService;

    @Autowired
    public MainRestController(ClinicDepartmentRepository clinicDepartmentRepository, ClinicService clinicService) {
        this.clinicDepartmentRepository = clinicDepartmentRepository;
        this.clinicService = clinicService;
    }

    @GetMapping("/departments")
    @JsonView(JsonViews.Public.class)
    public List<ClinicDepartment> getAllAdverts() {
        return clinicDepartmentRepository.findAll();
    }

    @GetMapping("/department/{id}/patients")
    @JsonView(JsonViews.Public.class)
    public List<Patient> getDepartmentPatients(@PathVariable Integer id) {
        ClinicDepartment clinicDepartment = clinicDepartmentRepository.getOne(id);
        return clinicDepartment.getPatients();
    }

    @GetMapping("/department/{id}/doctors")
    @JsonView(JsonViews.Public.class)
    public List<Doctor> getDepartmentDoctors(@PathVariable Integer id) {
        ClinicDepartment clinicDepartment = clinicDepartmentRepository.getOne(id);
        return clinicDepartment.getDoctors();
    }

    @RequestMapping(value = "/userAppointments/{id}", method = RequestMethod.GET)
    @JsonView(JsonViews.AppointmentsInfo.class)
    public List<Appointment> getUserAppointments(@PathVariable Integer id) {
        Doctor doctorById = clinicService.getDoctorById(id);
        if (doctorById != null) {
            return doctorById.getAppointments();
        }
        Patient patientById = clinicService.getPatientById(id);
        if (patientById != null) {
            return patientById.getAppointments();
        }
        return new ArrayList<>();
    }




}
