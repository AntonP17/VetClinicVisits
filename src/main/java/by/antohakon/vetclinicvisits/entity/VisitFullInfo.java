package by.antohakon.vetclinicvisits.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "full_info_visit")
@Builder
@Getter
@Setter
@ToString
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
