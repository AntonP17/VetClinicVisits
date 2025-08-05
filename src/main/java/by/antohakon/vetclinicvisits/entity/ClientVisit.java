package by.antohakon.vetclinicvisits.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "client_visit")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID visitId;

    @Column(unique = true, nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private UUID doctorId;

    @Column(nullable = false)
    private UUID animalId;

    @Column(nullable = false)
    private String reasonRequest;

    @Column(nullable = false)
    private LocalDateTime visitDate;

    public ClientVisit(UUID ownerId, UUID doctorId, UUID animalId, String reasonRequest, LocalDateTime visitDate) {
        this.ownerId = ownerId;
        this.doctorId = doctorId;
        this.animalId = animalId;
        this.reasonRequest = reasonRequest;
        this.visitDate = visitDate;
    }
}
