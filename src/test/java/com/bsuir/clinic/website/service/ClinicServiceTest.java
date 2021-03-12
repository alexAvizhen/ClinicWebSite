package com.bsuir.clinic.website.service;

import com.bsuir.clinic.website.entity.Appointment;
import com.bsuir.clinic.website.repository.AppointmentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClinicServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private ClinicService clinicService;

    @Before
    public void setUp() {
        when(appointmentRepository.getOne(any(Integer.class))).thenReturn(new Appointment());
    }

    @Test
    public void buildHtmlViewByAppointmentId() {
        String htmlView = clinicService.buildHtmlViewByAppointmentId(1);
        Assert.assertTrue(htmlView.startsWith("<table"));
        Assert.assertTrue(htmlView.endsWith("</table>"));
    }
}