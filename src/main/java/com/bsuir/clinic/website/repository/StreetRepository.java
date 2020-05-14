package com.bsuir.clinic.website.repository;

import com.bsuir.clinic.website.entity.Clinic;
import com.bsuir.clinic.website.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepository extends JpaRepository<Street, Integer> {

}
