package kg.kstu.service.impl;

import jakarta.transaction.Transactional;
import kg.kstu.entity.Appointment;
import kg.kstu.entity.Department;
import kg.kstu.entity.Doctor;
import kg.kstu.entity.Hospital;
import kg.kstu.exeptions.MyException;
import kg.kstu.repository.AppointmentRepository;
import kg.kstu.repository.DepartmentRepository;
import kg.kstu.repository.DoctorRepository;
import kg.kstu.repository.HospitalRepository;
import kg.kstu.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository repository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;


    public void saveDoctor(Doctor doctor, Long hispitalId) throws MyException {
        Hospital hospital = hospitalRepository.findById(hispitalId).orElse(null);
        hospital.addDoc(doctor);
        doctor.setHospital(hospital);
        repository.save(doctor);
    }

    public Doctor getDoctorById(Long id) throws MyException {
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Department with id "+id+" not found!"));
    }

    public List<Doctor> getAllDoctors() throws MyException {
        return repository.findAll();
    }


    public void updateDoctor(Long id, Doctor updatedDoctor) throws MyException {
        Doctor doctorById = getDoctorById(id);
        doctorById.setFirstName(updatedDoctor.getFirstName());
        doctorById.setLastName(updatedDoctor.getLastName());
        doctorById.setGender(updatedDoctor.getGender());
        doctorById.setPosition(updatedDoctor.getPosition());
        doctorById.setEmail(updatedDoctor.getEmail());
        repository.save(doctorById);
    }

    public void deleteDoctor(Long id) throws MyException {
        Doctor doctor = repository.findById(id).get();
        List<Hospital> hospitals = hospitalRepository.findAll();

        for (Hospital hospital: hospitals) {
            List<Appointment> appointments = hospital.getAppointments();
            if(appointments!=null){
                List<Appointment> appointmentList = appointments.stream().filter(app -> app.getDoctor().getId().equals(id)).toList();
                appointmentList.forEach(appointments::remove);
                appointments.forEach(app -> appointmentRepository.deleteById(app.getId()));
            }
        }
        doctor.getHospital().getDoctors().removeIf(doctor1 -> doctor1.getId().equals(id));

        List<Department> departments = doctor.getDepartments();
        if(departments!=null){
            departments.forEach(dep->dep.getDoctors().removeIf(doctor1 -> doctor1.getId().equals(id)));
        }
        repository.deleteById(id);
    }

    @Override
    public List<Doctor> findAll(Long hospitalId) {
        return repository.findDoctorByHospitalId(hospitalId);
    }

    public void assign(Long doctorId, Long departmentsId) {
        Doctor doctor = repository.findById(doctorId).
                orElseThrow(() -> new NoSuchElementException("Not found!!!"));
        Department department = departmentRepository.findById(departmentsId).
                orElseThrow(() -> new NoSuchElementException("Not found!!!"));
        doctor.getDepartments().add(department);
        department.getDoctors().add(doctor);
        repository.save(doctor);
        departmentRepository.save(department);
    }


}
