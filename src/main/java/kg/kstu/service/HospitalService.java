package kg.kstu.service;

import kg.kstu.entity.Hospital;

import java.util.List;

public interface HospitalService {
    void saveHospital(Hospital hospital);
    Hospital getHospitalById(Long id);
    List<Hospital> getAllHospitals();
    void updateHospital(Long id, Hospital updatedHospital);
    void deleteHospital(Long id) ;
}
