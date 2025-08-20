package by.antohakon.visitsanalitics.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "visit_history")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID visitId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime historyDate;

    private Status doctorStatus;
    private Status animalStatus;
    private Status ownerStatus;

    private String comment;

    public VisitStatus(UUID visitId, LocalDateTime historyDate, Status doctorStatus, Status animalStatus, Status ownerStatus, String comment) {
        this.visitId = visitId;
        this.historyDate = historyDate;
        this.doctorStatus = doctorStatus;
        this.animalStatus = animalStatus;
        this.ownerStatus = ownerStatus;
        this.comment = comment;
    }
}
