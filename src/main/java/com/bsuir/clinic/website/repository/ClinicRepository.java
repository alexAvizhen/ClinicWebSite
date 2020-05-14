package com.bsuir.clinic.website.repository;

import com.bsuir.clinic.website.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

}
