package com.bsuir.clinic.website.service;

import com.bsuir.clinic.website.entity.ClinicDepartment;
import com.bsuir.clinic.website.entity.Street;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClinicBuildServiceTest {

    private ClinicBuildService clinicBuildService;

    @Before
    public void setUp() {
        clinicBuildService = new ClinicBuildService();
    }

    @Test
    public void buildStreet() {
        ClinicDepartment clinicDepartment = new ClinicDepartment();
        String streetName = "Test Street";
        Street street = clinicBuildService.buildStreet(streetName, clinicDepartment);
        Assert.assertEquals(streetName, street.getName());
    }
}