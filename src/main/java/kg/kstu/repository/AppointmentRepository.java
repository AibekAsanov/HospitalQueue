package kg.kstu.repository;

import kg.kstu.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select h from Hospital l " +
            "join l.appointments h where l.id=:id " +
            "order by h.id desc")
    List<Appointment> findAppointmentByHospitalId(Long id);
}
