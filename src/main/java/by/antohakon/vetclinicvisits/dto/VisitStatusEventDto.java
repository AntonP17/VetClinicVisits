package by.antohakon.vetclinicvisits.dto;

import by.antohakon.vetclinicvisits.entity.Status;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record VisitStatusEventDto (UUID visitId,
                                   Status doctorStatus,
                                   Status animalStatus,
                                   Status ownerStatus,
                                   String comment){
}
