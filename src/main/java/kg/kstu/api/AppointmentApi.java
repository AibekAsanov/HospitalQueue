package kg.kstu.api;

import kg.kstu.entity.Appointment;
import kg.kstu.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kg.kstu.exeptions.MyException;
import kg.kstu.service.*;

@Controller
@RequestMapping("/appointment")
@RequiredArgsConstructor

public class AppointmentApi {
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;

    @GetMapping()
    String getAllApp(Model model) {
        try {
            model.addAttribute("allAppointments", appointmentService.getAllAppointments());
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        return "Appointment/getAllApp";
    }

    @GetMapping("/create/{hospitalId}")
    String createAppointmentByHospitalId(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("newAppointment", new Appointment());
        model.addAttribute("allDepartments",departmentService.findAll(hospitalId));
        model.addAttribute("allPatients",patientService.findAll(hospitalId));
        model.addAttribute("allDoctors",doctorService.findAll(hospitalId));
        model.addAttribute(hospitalId);
        return "Appointment/save-page";
    }

    @RequestMapping(value = "/save/{hospitalId}", method = RequestMethod.POST)
    String saveAppointment(@PathVariable Long hospitalId, @ModelAttribute("newAppointment") Appointment appointment) {
        try {
            appointmentService.saveAppointment(appointment, hospitalId);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/appointment/" + hospitalId;
    }

    @GetMapping("/{hospitalId}")
    String findAllDepartmentByHospitalId(Model model, @PathVariable Long hospitalId) {
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("appointments", appointmentService.findAll(hospitalId));
        return "Appointment/findDepartmentByHospital";
    }

    @GetMapping("/{appointmentId}/delete")
    public String deleteById(@PathVariable Long appointmentId, Model model) {
        try {
            appointmentService.deleteAppointment(appointmentId);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/appointment";
    }

    @GetMapping("/{appointmentId}/edit/{hospitalId}")
    public String newUpdate(@PathVariable Long appointmentId,@PathVariable Long hospitalId, Model model) {
        try {
            model.addAttribute("appUpdate",appointmentService.getAppointmentById(appointmentId));
            model.addAttribute("departments",departmentService.findAll(hospitalId));
            model.addAttribute("doctors",doctorService.findAll(hospitalId));
            model.addAttribute("patients",patientService.findAll(hospitalId));
            model.addAttribute("hospitalId", hospitalId);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        return "Appointment/appUpdate";
    }

    @PutMapping("/{appointmentId}/update")
    public String update(
                         @PathVariable Long appointmentId,
                         @ModelAttribute("appUpdate") Appointment appointment) {

        try {
            appointmentService.updateAppointment(appointmentId, appointment);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/appointment";
    }
}
