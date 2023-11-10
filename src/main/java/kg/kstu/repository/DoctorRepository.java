package kg.kstu.repository;


import kg.kstu.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findDoctorByHospitalId(Long id);
}
