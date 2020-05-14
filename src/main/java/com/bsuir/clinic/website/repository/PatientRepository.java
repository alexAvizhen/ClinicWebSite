package com.bsuir.clinic.website.repository;

import com.bsuir.clinic.website.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findPatientByLogin(String login);
}
