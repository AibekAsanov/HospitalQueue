package kg.kstu.service.impl;

import jakarta.transaction.Transactional;
import kg.kstu.entity.Appointment;
import kg.kstu.entity.Hospital;
import kg.kstu.entity.Patient;
import kg.kstu.exeptions.MyException;
import kg.kstu.repository.AppointmentRepository;
import kg.kstu.repository.HospitalRepository;
import kg.kstu.repository.PatientRepository;
import kg.kstu.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;


    public void savePatient(Patient patient, Long hospitalId) throws MyException {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElse(null);
        hospital.addPat(patient);
        patient.setHospital(hospital);
        patientRepository.save(patient);
    }

    public Patient getPatientById(Long id) throws MyException {
        return patientRepository.findById(id).orElseThrow(()->new RuntimeException("id not found"));
    }

    public List<Patient> getAllPatients() throws MyException {
        return patientRepository.findAll();
    }

    public void updatePatient(Long id, Patient updatedPatient) throws MyException {
        Patient patientById = getPatientById(id);
        patientById.setFirstName(updatedPatient.getFirstName());
        patientById.setLastName(updatedPatient.getLastName());
        patientById.setGender(updatedPatient.getGender());
        patientById.setPhoneNumber(updatedPatient.getPhoneNumber());
        patientById.setEmail(updatedPatient.getEmail());
        patientRepository.save(patientById);
    }

    public void deletePatient(Long id){
        Patient patient = patientRepository.findById(id).get();
        List<Hospital> hospitals = hospitalRepository.findAll();

        for (Hospital hospital: hospitals) {
            List<Appointment> appointments = hospital.getAppointments();
            List<Appointment> appointmentList = appointments.stream().filter(app -> app.getPatient().getId().equals(id)).toList();
            appointmentList.forEach(appointments::remove);
            appointments.forEach(app->appointmentRepository.deleteById(app.getId()));
        }

        patient.getHospital().getPatients().removeIf(pat->pat.getId().equals(id));

        patientRepository.deleteById(id);

    }

    @Override
    public List<Patient> findAll(Long hospitalId) {
        return patientRepository.findPatientByHospitalId(hospitalId);
    }
}
