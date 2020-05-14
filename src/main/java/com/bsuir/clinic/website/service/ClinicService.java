package com.bsuir.clinic.website.service;

import com.bsuir.clinic.website.entity.Appointment;
import com.bsuir.clinic.website.entity.Clinic;
import com.bsuir.clinic.website.entity.ClinicDepartment;
import com.bsuir.clinic.website.entity.Doctor;
import com.bsuir.clinic.website.entity.Patient;
import com.bsuir.clinic.website.entity.Street;
import com.bsuir.clinic.website.entity.User;
import com.bsuir.clinic.website.repository.AppointmentRepository;
import com.bsuir.clinic.website.repository.ClinicRepository;
import com.bsuir.clinic.website.repository.ClinicDepartmentRepository;
import com.bsuir.clinic.website.repository.DoctorRepository;
import com.bsuir.clinic.website.repository.PatientRepository;
import com.bsuir.clinic.website.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClinicService {

    private boolean isClinicCreated = false;
    private ClinicRepository clinicRepository;
    private ClinicDepartmentRepository clinicDepartmentRepository;
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;
    private StreetRepository streetRepository;

    @Autowired
    public ClinicService(ClinicRepository clinicRepository, ClinicDepartmentRepository clinicDepartmentRepository,
                         DoctorRepository doctorRepository, PatientRepository patientRepository,
                         AppointmentRepository appointmentRepository, StreetRepository streetRepository) {
        this.clinicRepository = clinicRepository;
        this.clinicDepartmentRepository = clinicDepartmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.streetRepository = streetRepository;
    }

    public Clinic createClinic() {
        if (!isClinicCreated) {
            Clinic clinic = new Clinic("Лидская, 14", "Полкиклинника на каменной горке");
            return clinicRepository.save(clinic);
        }
        return clinicRepository.getOne(1);
    }

    public ClinicDepartment createClinicDepartment(Clinic clinic, String departmentName, List<String> streets) {
                ClinicDepartment clinicDepartment = new ClinicDepartment(clinic, departmentName, Collections.emptyList());
        ClinicDepartment createdDepartment = clinicDepartmentRepository.save(clinicDepartment);

        for (String streetName : streets) {
            Street street = new Street();
            street.setName(streetName);
            street.setDepartment(createdDepartment);
            streetRepository.save(street);
        }
        return clinicDepartmentRepository.getOne(createdDepartment.getId());
    }

    public void createOrUpdateDepartment(ClinicDepartment clinicDepartment) {
        clinicDepartmentRepository.save(clinicDepartment);
    }

    public Doctor createOrUpdateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Patient createOrUpdatePatient(Patient patient) {
        return patientRepository.save(patient);
    }


    public void removeDoctorById(Integer doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    public void removePatientById(Integer patientId) {
        patientRepository.deleteById(patientId);
    }

    public Appointment createAppointment(Integer doctorId, Integer patientId, Date appointmentDate, String commentToAppointment) {
        Doctor doctorByLogin = doctorRepository.getOne(doctorId);
        Patient patientByLogin = patientRepository.getOne(patientId);
        Appointment appointment = new Appointment(doctorByLogin, patientByLogin, appointmentDate, commentToAppointment);
        return appointmentRepository.save(appointment);
    }

    public User getUserByLogin(String userLogin) {
        Doctor doctorByLogin = doctorRepository.findDoctorByLogin(userLogin);
        if (doctorByLogin != null) {
            return doctorByLogin;
        } else {
            return patientRepository.findPatientByLogin(userLogin);
        }
    }

    public Collection<Appointment> getAppointmentsByUserLogin(String userLogin) {
        User userByLogin = getUserByLogin(userLogin);
        if (userByLogin == null) {
            return new ArrayList<>();
        }
        if (userByLogin.isAdmin()) {
            return ((Doctor) userByLogin).getAppointments();
        } else {
            return ((Patient) userByLogin).getAppointments();
        }
    }

    public Map<String, Integer> getDateAsStrToAmountOfAppointmentsStats(Date startDate, Date endDate) {
        Map<String, Integer> dateAsStrToAmountMap = new HashMap<>();
        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Appointment appointment : appointmentRepository.findAll()) {
            if (startDate.before(appointment.getAppointmentDate()) && endDate.after(appointment.getAppointmentDate())) {
                String dateAsStr = outputFormatter.format(appointment.getAppointmentDate());
                Integer currentAmount = dateAsStrToAmountMap.get(dateAsStr);
                if (currentAmount == null) {
                    currentAmount = 0;
                }
                currentAmount++;
                dateAsStrToAmountMap.put(dateAsStr, currentAmount);
            }
        }
        return dateAsStrToAmountMap;
    }

    public Doctor getDoctorById(Integer doctorId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        return doctorOptional.orElse(null);
    }

    public Patient getPatientById(Integer patientId) {
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        return patientOptional.orElse(null);
    }
}
