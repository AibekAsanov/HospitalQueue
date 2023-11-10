package kg.kstu.service.impl;

import jakarta.transaction.Transactional;
import kg.kstu.entity.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import kg.kstu.repository.HospitalRepository;
import kg.kstu.service.HospitalService;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository repository;


    public void saveHospital(Hospital hospital)  {
        repository.save(hospital);
    }

    public Hospital getHospitalById(Long id)  {
        return repository.findById(id).orElseThrow(()->new NullPointerException("Hospital with id "+id+" not found!"));
    }

    public List<Hospital> getAllHospitals(){
        return repository.findAll();
    }

    public void updateHospital(Long id, Hospital updatedHospital) {
        Hospital hospitalById = getHospitalById(id);
        hospitalById.setName(updatedHospital.getName());
        hospitalById.setAddress(updatedHospital.getAddress());
        hospitalById.setImage(updatedHospital.getImage());
        repository.save(hospitalById);
    }

    public void deleteHospital(Long id)  {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else throw new NullPointerException(
                "Hospital with id " + id + " doesn't exists!"
        );
    }

}
