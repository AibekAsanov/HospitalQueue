package kg.kstu.service.impl;

import jakarta.transaction.Transactional;
import kg.kstu.entity.Appointment;
import kg.kstu.entity.Hospital;
import kg.kstu.exeptions.MyException;
import kg.kstu.repository.*;
import kg.kstu.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository repository;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;


    public void saveAppointment(Appointment appointment, Long hospitalId) throws MyException {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElse(null);
        Appointment appointment1 = new Appointment();
        appointment1.setId(appointment.getId());
        appointment1.setPatient(patientRepository.findById(appointment.getPatientId()).orElse(null));
        appointment1.setDoctor(doctorRepository.findById(appointment.getDoctorId()).orElse(null));
        appointment1.setDepartment(departmentRepository.findById(appointment.getDepartmentId()).orElse(null));
        appointment1.setDate(appointment.getDate());

        hospital.addApp(appointment1);

        repository.save(appointment1);
    }

    public Appointment getAppointmentById(Long id) throws MyException {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Id not found!!"));
    }

    public List<Appointment> getAllAppointments() throws MyException {
        return repository.findAll();
    }

    public void updateAppointment(Long id, Appointment updatedAppointment) throws MyException {
        Appointment appointment = getAppointmentById(id);
        appointment.setDate(updatedAppointment.getDate());
        appointment.setDoctor(updatedAppointment.getDoctor());
        appointment.setDepartment(updatedAppointment.getDepartment());
        appointment.setPatient(updatedAppointment.getPatient());
        repository.save(appointment);

    }

    public void deleteAppointment(Long id) throws MyException {

        List<Hospital> hospitals = hospitalRepository.findAll();

        hospitals.forEach(hospital -> hospital.getAppointments().removeIf(hos->hos.getId().equals(id)));

        repository.deleteById(id);
    }

    @Override
    public List<Appointment> findAll(Long hospitalId) {
        return repository.findAppointmentByHospitalId(hospitalId);
    }

}
