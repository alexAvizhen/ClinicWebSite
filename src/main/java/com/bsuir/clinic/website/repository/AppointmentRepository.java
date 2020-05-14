package com.bsuir.clinic.website.repository;

import com.bsuir.clinic.website.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
