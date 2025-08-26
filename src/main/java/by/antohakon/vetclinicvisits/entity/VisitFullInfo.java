package by.antohakon.vetclinicvisits.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "full_info_visit", indexes = {
        @Index(columnList = "visitId", name = "client_visit_uuid_index"),
        @Index(columnList = "ownerId", name = "owner_visit_uuid_index"),
        @Index(columnList = "doctorId", name = "doctor_visit_uuid_index"),
        @Index(columnList = "animalId", name = "animal_visit_uuid_index"),
        @Index(columnList = "doctorId, ownerId", name = "ownerId_doctor_uuid_index"),
        @Index(columnList = "ownerId, animalId", name = "animal_owner_uuid_index")
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitFullInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID visitId;

    private String owner;
    private String doctor;
    private String animal;

    @Column(nullable = false)
    private String reasonRequest;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime visitDate;

    public VisitFullInfo(String owner, String doctor, String animal, String reasonRequest, LocalDateTime visitDate) {
        this.owner = owner;
        this.doctor = doctor;
        this.animal = animal;
        this.reasonRequest = reasonRequest;
        this.visitDate = visitDate;
    }
}
