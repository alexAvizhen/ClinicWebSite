package com.bsuir.clinic.website.service;

import com.bsuir.clinic.website.entity.ClinicDepartment;
import com.bsuir.clinic.website.entity.Street;
import org.springframework.stereotype.Service;

@Service
public class ClinicBuildService {

    public Street buildStreet(String streetName, ClinicDepartment clinicDepartment) {
        Street street = new Street();
        street.setName(streetName);
        street.setDepartment(clinicDepartment);
        return street;
    }
}
