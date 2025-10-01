package by.antohakon.vetclinicvisits.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Filter(UUID visitId,
                     UUID ownerId,
                     UUID doctorId,
                     UUID animalId) {
}
