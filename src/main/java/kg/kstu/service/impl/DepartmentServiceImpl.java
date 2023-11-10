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
import kg.kstu.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public void saveDepartment(Department department, Long hospitalId) throws MyException {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElse(null);
        hospital.addDep(department);
        department.setHospital(hospital);
        repository.save(department);
    }

    public Department getDepartmentById(Long id) throws MyException {
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Department with id "+id+" not found!"));
    }

    public List<Department> getAllDepartments() throws MyException {
        return repository.findAll();
    }


    public void updateDepartment(Long id, Department updatedDepartment) throws MyException {
        Department department = getDepartmentById(id);
        department.setName(updatedDepartment.getName());
          }

    public void deleteDepartment(Long id) throws MyException {
        Department department = repository.findById(id).get();
        List<Hospital> hospitals = hospitalRepository.findAll();
        for (Hospital hospital: hospitals) {
            List<Appointment> appointments = hospital.getAppointments();
            if(appointments!=null){
                List<Appointment> list = appointments.stream().filter(app -> app.getDepartment().getId().equals(id)).toList();
                list.forEach(appointments::remove);
                appointments.forEach(app -> appointmentRepository.deleteById(app.getId()));
            }
        }

        department.getHospital().getDepartments().removeIf(department1 -> department1.getId().equals(id));

        List<Doctor> doctors = department.getDoctors();
        if(doctors!=null){
            doctors.forEach(d->d.getDepartments().removeIf(department2->department2.getId().equals(id)));
        }

        repository.deleteById(id);
    }

    @Override
    public List<Department> findAll(Long hospitalId) {
        return repository.findDepartmentByHospitalId(hospitalId);
    }

}
