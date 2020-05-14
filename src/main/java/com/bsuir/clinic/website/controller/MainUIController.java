package com.bsuir.clinic.website.controller;

import com.bsuir.clinic.website.entity.Appointment;
import com.bsuir.clinic.website.entity.ClinicDepartment;
import com.bsuir.clinic.website.entity.Doctor;
import com.bsuir.clinic.website.entity.Patient;
import com.bsuir.clinic.website.entity.User;
import com.bsuir.clinic.website.repository.ClinicDepartmentRepository;
import com.bsuir.clinic.website.service.ClinicService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@SessionAttributes({"currentUser", "appointmentIds", "appointmentDoctor", "appointmentPatient"})
public class MainUIController {

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private ClinicDepartmentRepository clinicDepartmentRepository;


    @RequestMapping("/")
    public ModelAndView welcome(ModelMap modelMap) {
        initSession(modelMap);
        modelMap.put("message", "Hello World");
        return new ModelAndView("index");
    }

    @RequestMapping("/departments")
    public String departments(ModelMap modelMap) {
        initSession(modelMap);
        return "departments";
    }

    @RequestMapping("/login")
    public String loginPage(ModelMap model) {
        initSession(model);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(ModelMap model, @RequestParam("username") String login, @RequestParam("password") String password, @ModelAttribute("appointmentIds") List<Integer> appointmentIds) {
        User logonUser = clinicService.getUserByLogin(login);
        if (logonUser != null) {
            model.addAttribute("currentUser", logonUser);
            appointmentIds.clear();
            if (logonUser.isAdmin()) {
                for (Appointment appointment : ((Doctor) logonUser).getAppointments()) {
                    appointmentIds.add(appointment.getId());
                }
            } else {
                for (Appointment appointment : ((Patient) logonUser).getAppointments()) {
                    appointmentIds.add(appointment.getId());
                }
            }
            return "index";
        } else {
            model.addAttribute("msg", "Пользователь с именем " + login + " не найден");
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logoutUser(ModelMap model) {
        model.remove("currentUser");
        initSession(model);
        return "login";
    }

    @RequestMapping(value = "/clinicDepartment/{id}", method = RequestMethod.GET)
    public ModelAndView goToClinicDepartment(@PathVariable Integer id, ModelMap model) {
        ModelAndView modelAndView = new ModelAndView("clinicDepartment");
        initSession(model);
        Optional<ClinicDepartment> clinicDepartmentOptional = clinicDepartmentRepository.findById(id);
        clinicDepartmentOptional.ifPresent(clinicDepartment -> modelAndView.addObject("clinicDepartment", clinicDepartment));
        return modelAndView;
    }

    private void initSession(ModelMap model) {
        if (!model.containsAttribute("currentUser")) {
            User guest = new Patient("guest", "guest", null);
            guest.setId(0);
            model.addAttribute("currentUser", guest);
        }
        if (!model.containsAttribute("appointmentIds")) {
            model.addAttribute("appointmentIds", new LinkedList<Integer>());
        }
    }

    @RequestMapping(value = "/doctor/{id}", method = RequestMethod.GET)
    public ModelAndView goToDoctor(@PathVariable Integer id) {
        Doctor doctorById = clinicService.getDoctorById(id);
        return goToDoctorWithEditMode(doctorById, false);
    }

    @RequestMapping(value = "/editDoctor/{id}", method = RequestMethod.GET)
    public ModelAndView goToEditDoctor(@PathVariable Integer id) {
        Doctor doctorById = clinicService.getDoctorById(id);
        return goToDoctorWithEditMode(doctorById, true);
    }

    private ModelAndView goToDoctorWithEditMode(Doctor doctorById, boolean isEditMode) {
        ModelAndView modelAndView = new ModelAndView("doctor");
        modelAndView.addObject("currentDoctor", doctorById);
        modelAndView.addObject("isEditMode", isEditMode);
        return modelAndView;
    }

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.GET)
    public ModelAndView goToPatient(@PathVariable Integer id) {
        Patient patientById = clinicService.getPatientById(id);
        return goToPatientWithEditMode(patientById, false);
    }

    @RequestMapping(value = "/editPatient/{id}", method = RequestMethod.GET)
    public ModelAndView goToEditPatient(@PathVariable Integer id, ModelMap model) {
        Patient patientById = clinicService.getPatientById(id);
        return goToPatientWithEditMode(patientById, true);
    }

    private ModelAndView goToPatientWithEditMode(Patient patientById, boolean isEditMode) {
        ModelAndView modelAndView = new ModelAndView("patient");
        modelAndView.addObject("currentPatient", patientById);
        modelAndView.addObject("isEditMode", isEditMode);
        return modelAndView;
    }

    @RequestMapping(value = "/removeDoctor/{id}", method = RequestMethod.GET)
    public ModelAndView removeDoctor(@PathVariable Integer id, @ModelAttribute("currentUser") User currentUser, ModelMap modelMap) {
        Doctor doctorById = clinicService.getDoctorById(id);
        if (doctorById == null) {
            return welcome(modelMap);
        }
        Integer doctorClinicDepartmentId = doctorById.getClinicDepartment().getId();
        if (currentUser.isAdmin()) {
            clinicService.removeDoctorById(id);
        }
        return goToClinicDepartment(doctorClinicDepartmentId, modelMap);
    }

    @RequestMapping(value = "/admin/editDoctor", method = RequestMethod.POST)
    public ModelAndView editDoctor(@ModelAttribute("currentUser") User currentUser, ModelMap model, HttpServletRequest request) throws ParseException {
        if (currentUser.isAdmin()) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Integer doctorId = Integer.valueOf(parameterMap.get("doctorId")[0]);
            Doctor doctorToEdit = clinicService.getDoctorById(doctorId);
            if (doctorToEdit == null) {
                doctorToEdit = new Doctor();
                Integer clinicDepartmentId = Integer.valueOf(parameterMap.get("clinicDepartmentId")[0]);
                doctorToEdit.setClinicDepartment(clinicDepartmentRepository.getOne(clinicDepartmentId));
            }

            doctorToEdit.setName(parameterMap.get("doctorName")[0]);
            doctorToEdit.setSurname(parameterMap.get("doctorSurname")[0]);
            doctorToEdit.setPhoneNumber(parameterMap.get("doctorPhoneNumber")[0]);
            Date birthDate = DateUtils.parseDate(parameterMap.get("doctorBirthDate")[0], "yyyy-mm-dd");
            doctorToEdit.setBirthDate(birthDate);
            Doctor updatedDoctor = clinicService.createOrUpdateDoctor(doctorToEdit);
            return goToDoctorWithEditMode(updatedDoctor, false);
        }
        return welcome(model);
    }


    @RequestMapping(value = "/removePatient/{id}", method = RequestMethod.GET)
    public ModelAndView removePatient(@PathVariable Integer id, @ModelAttribute("currentUser") User currentUser, ModelMap modelMap) {
        Patient patientById = clinicService.getPatientById(id);
        if (patientById == null) {
            return welcome(modelMap);
        }
        Integer patientClinicId = patientById.getClinicDepartment().getId();
        if (currentUser.isAdmin()) {
            clinicService.removePatientById(id);
        }
        return goToClinicDepartment(patientClinicId, modelMap);
    }

    @RequestMapping(value = "/admin/editPatient", method = RequestMethod.POST)
    public ModelAndView editPatient(@ModelAttribute("currentUser") User currentUser, ModelMap model, HttpServletRequest request) throws ParseException {
        if (currentUser.isAdmin()) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Integer patientId = Integer.valueOf(parameterMap.get("patientId")[0]);
            Patient patientById = clinicService.getPatientById(patientId);
            if (patientById == null) {
                patientById = new Patient();
                Integer clinicDepartmentId = Integer.valueOf(parameterMap.get("clinicDepartmentId")[0]);
                patientById.setClinicDepartment(clinicDepartmentRepository.getOne(clinicDepartmentId));
            }

            patientById.setName(parameterMap.get("patientName")[0]);
            patientById.setSurname(parameterMap.get("patientSurname")[0]);
            patientById.setPhoneNumber(parameterMap.get("patientPhoneNumber")[0]);
            patientById.setAddress(parameterMap.get("patientAddress")[0]);
            Date birthDate = DateUtils.parseDate(parameterMap.get("patientBirthDate")[0], "yyyy-mm-dd");
            patientById.setBirthDate(birthDate);
            Patient updatedPatient = clinicService.createOrUpdatePatient(patientById);
            return goToPatientWithEditMode(updatedPatient, false);
        }
        return welcome(model);
    }


    @RequestMapping(value = "/department/{id}/createDoctor", method = RequestMethod.GET)
    public ModelAndView createDoctorForDepartment(@PathVariable Integer id, ModelMap model) {
        Doctor doctorToCreate = new Doctor();
        doctorToCreate.setId(0);
        doctorToCreate.setClinicDepartment(clinicDepartmentRepository.getOne(id));
        return goToDoctorWithEditMode(doctorToCreate, true);
    }


    @RequestMapping(value = "/department/{id}/createPatient", method = RequestMethod.GET)
    public ModelAndView createPatientForDepartment(@PathVariable Integer id, ModelMap model) {
        Patient patientToCreate = new Patient();
        patientToCreate.setId(0);
        patientToCreate.setClinicDepartment(clinicDepartmentRepository.getOne(id));
        return goToPatientWithEditMode(patientToCreate, true);
    }

    @RequestMapping(value = "/addDoctorToAppointmentForm/{id}", method = RequestMethod.GET)
    public ModelAndView addDoctorToAppointmentForm(@PathVariable Integer id, ModelMap model) {
        Doctor appointmentDoctor = clinicService.getDoctorById(id);
        model.addAttribute("appointmentDoctor", appointmentDoctor);
        return goToClinicDepartment(appointmentDoctor.getClinicDepartment().getId(), model);
    }

    @RequestMapping(value = "/addPatientToAppointmentForm/{id}", method = RequestMethod.GET)
    public ModelAndView addPatientToAppointmentForm(@PathVariable Integer id, ModelMap model) {
        Patient appointmentPatient = clinicService.getPatientById(id);
        model.addAttribute("appointmentPatient", appointmentPatient);
        return goToClinicDepartment(appointmentPatient.getClinicDepartment().getId(), model);
    }

    @RequestMapping(value = "/makeAnAppointment", method = RequestMethod.POST)
    public ModelAndView makeAnAppointment(ModelMap model, HttpServletRequest request,
                                          @ModelAttribute("appointmentIds") List<Integer> appointmentIds,
                                          @ModelAttribute("currentUser") User currentUser) throws ParseException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        String appointmentDoctorIdAsStr = parameterMap.get("appointmentDoctorId")[0];
        String appointmentPatientIdAsStr = parameterMap.get("appointmentPatientId")[0];
        if (appointmentDoctorIdAsStr == null || appointmentPatientIdAsStr == null) {
            return welcome(model);
        }
        Integer appointmentDoctorId = Integer.valueOf(appointmentDoctorIdAsStr);
        Integer appointmentPatientId = Integer.valueOf(appointmentPatientIdAsStr);
        Date appointmentDate = DateUtils.parseDate(parameterMap.get("appointmentDate")[0], "yyyy-mm-dd");
        String appointmentComment = parameterMap.get("appointmentComment")[0];
        Appointment appointment = clinicService.createAppointment(appointmentDoctorId, appointmentPatientId, appointmentDate, appointmentComment);
        if (appointmentDoctorId.equals(currentUser.getId()) || appointmentPatientId.equals(currentUser.getId())) {
            appointmentIds.add(appointment.getId());
        }
        model.addAttribute("appointmentDoctor", new Doctor());
        model.addAttribute("appointmentPatient", new Patient());

        Integer doctorFromAppointmentClinicId = clinicService.getDoctorById(appointmentDoctorId).getClinicDepartment().getId();
        return goToClinicDepartment(doctorFromAppointmentClinicId, model);
    }

    @RequestMapping(value = "/myAppointments", method = RequestMethod.GET)
    public ModelAndView myAppointments() {
        return new ModelAndView("myAppointments");
    }
}
