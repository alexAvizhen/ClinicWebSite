package com.bsuir.clinic.website.repository;

import com.bsuir.clinic.website.entity.ClinicDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicDepartmentRepository extends JpaRepository<ClinicDepartment, Integer> {
    ClinicDepartment findClinicDepartmentByName(String name);

}
