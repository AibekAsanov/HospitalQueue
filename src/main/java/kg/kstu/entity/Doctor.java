package kg.kstu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import kg.kstu.enums.DoctorType;
import kg.kstu.enums.Gender;


import java.util.List;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_gen")
    @SequenceGenerator(name = "doctor_gen", sequenceName = "doctor_seq", allocationSize = 1)
    private Long id;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private DoctorType position;

    @Email(message = "invalid email")
    @Column(unique = true)
    private String email;

    @ManyToMany(mappedBy = "doctors", cascade = {CascadeType.DETACH,
    CascadeType.MERGE,
    CascadeType.REFRESH,
    CascadeType.PERSIST})
    private List<Department> departments;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    private  Hospital hospital;


}
