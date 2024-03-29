package kg.kstu.service;

import kg.kstu.entity.Appointment;
import kg.kstu.exeptions.MyException;

import java.util.List;

public interface AppointmentService {
    void saveAppointment(Appointment appointment,Long hospitalId)throws MyException;
    Appointment getAppointmentById(Long id)throws MyException;
    List<Appointment> getAllAppointments()throws MyException;
    void updateAppointment(Long id, Appointment updatedAppointment)throws MyException;
    void deleteAppointment(Long id)throws MyException;

    List<Appointment> findAll(Long hospitalId);
}
