package com.bsuir.clinic.website.repository;

import com.bsuir.clinic.website.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findDoctorByLogin(String login);
}
