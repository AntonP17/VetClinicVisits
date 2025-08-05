package by.antohakon.vetclinicvisits.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ShortVisitDto(UUID visitId, UUID doctorId, UUID animalId, LocalDateTime visitDate) {
}
