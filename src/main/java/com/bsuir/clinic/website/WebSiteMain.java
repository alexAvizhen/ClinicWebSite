package com.bsuir.clinic.website;

import com.bsuir.clinic.website.entity.Clinic;
import com.bsuir.clinic.website.entity.ClinicDepartment;
import com.bsuir.clinic.website.entity.Doctor;
import com.bsuir.clinic.website.entity.Patient;
import com.bsuir.clinic.website.service.ClinicService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class WebSiteMain extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebSiteMain.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebSiteMain.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(ClinicService clinicService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Clinic clinic = clinicService.createClinic();
                //создание отделений клиники
                ClinicDepartment clinicDepartmentOne = clinicService.createClinicDepartment(clinic, "Терапевтическое", Arrays.asList("Кунцевщина", "Притыцкого"));
                ClinicDepartment clinicDepartmentTwo = clinicService.createClinicDepartment(clinic, "Стоматологическое", Arrays.asList("Лидская", "Неманская"));

                Doctor rootDoctor = new Doctor("root", "root", clinicDepartmentOne);
                clinicService.createOrUpdateDoctor(rootDoctor);

                //пример создания доктора
                Doctor testDoctor = new Doctor("laguna", "laguna", clinicDepartmentOne);
                testDoctor.setName("Елизавета");
                testDoctor.setSurname("Лагуновская");
                testDoctor.setPhoneNumber("80299339992");
                testDoctor.setBirthDate(DateUtils.addYears(new Date(), -20));//сегодняшняя дата(сегодняшняя дата) - 20 лет = дата рождения 20летнего человека
                clinicService.createOrUpdateDoctor(testDoctor);

                //пример создания пациента
                Patient testPatient = new Patient("pupkin", "pupkin", clinicDepartmentOne);
                testPatient.setName("Вася");
                testPatient.setSurname("Пупкин");
                testPatient.setPhoneNumber("80296739273");
                testPatient.setBirthDate(DateUtils.addYears(new Date(), -25));
                testPatient.setAddress("Минск, ул. Кунцевщина, дом 43");//единственное отличие от врача
                clinicService.createOrUpdatePatient(testPatient);

                //искусственная генерация пациентов и врачей для тестирования
                generatePatAndDoc(0, clinicDepartmentOne, clinicService);
                generatePatAndDoc(20, clinicDepartmentTwo, clinicService);
            }
        };


    }

    private void generatePatAndDoc(int startIndex, ClinicDepartment clinicDepartment, ClinicService clinicService) {
        for (int i = startIndex; i < startIndex + 10; i++) {
            String tempWord = "testDoc" + i;
            Doctor currentDoctor = new Doctor(tempWord, tempWord, clinicDepartment);
            currentDoctor.setSurname(tempWord);
            currentDoctor.setName(tempWord);
            currentDoctor.setBirthDate(DateUtils.addYears(new Date(), -(20 + i)));
            clinicService.createOrUpdateDoctor(currentDoctor);
        }
        for (int i = startIndex + 10; i < startIndex + 20; i++) {
            String tempWord = "testPat" + i;
            Patient currentPatient = new Patient(tempWord, tempWord, clinicDepartment);
            currentPatient.setSurname(tempWord);
            currentPatient.setName(tempWord);
            currentPatient.setBirthDate(DateUtils.addYears(new Date(), -(20 + i)));
            clinicService.createOrUpdatePatient(currentPatient);
        }
    }
}
