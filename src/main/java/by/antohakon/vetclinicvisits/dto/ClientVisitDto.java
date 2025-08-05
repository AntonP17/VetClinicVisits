package by.antohakon.vetclinicvisits.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ClientVisitDto( UUID visitId, UUID ownerId, UUID doctorId, UUID animalId, String reasonRequest,  LocalDateTime visitDate) {
}
