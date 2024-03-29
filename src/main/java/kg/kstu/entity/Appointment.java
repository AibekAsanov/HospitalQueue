package kg.kstu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_gen")
    @SequenceGenerator(name = "appointment_gen", sequenceName = "appointment_seq", allocationSize = 1)
    private Long id;
    private LocalDate date;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne( cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne( cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    private Department department;

    @Transient
    private Long doctorId;
    @Transient
    private Long departmentId;
    @Transient
    private Long patientId;
}