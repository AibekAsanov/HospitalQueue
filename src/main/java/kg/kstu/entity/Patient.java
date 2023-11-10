package kg.kstu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import kg.kstu.enums.Gender;

import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_gen")
    @SequenceGenerator(name = "patient_gen", sequenceName = "patient_seq", allocationSize = 1)
    private Long id;

    private String firstName;
    private String lastName;

//    @Pattern(regexp = "^(?!\\+996)\\d+$", message = "Phone number must start with +996")
    private String phoneNumber;

//    @Email
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "patient", cascade ={ CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST,
            CascadeType.REMOVE
    })
    private List<Appointment> appointments;
}
