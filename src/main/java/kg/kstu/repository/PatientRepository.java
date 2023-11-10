package kg.kstu.repository;


import kg.kstu.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findPatientByHospitalId(Long id);
}
