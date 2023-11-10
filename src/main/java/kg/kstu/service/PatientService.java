package kg.kstu.service;

import kg.kstu.entity.Patient;
import kg.kstu.exeptions.MyException;

import java.util.List;

public interface PatientService {
    void savePatient(Patient patient, Long hospitalId)throws MyException;
    Patient getPatientById(Long id)throws MyException;
    List<Patient> getAllPatients()throws MyException;
    void updatePatient(Long id, Patient updatedPatient)throws MyException;
    void deletePatient(Long id);

    List<Patient> findAll(Long hospitalId);

}
