package by.antohakon.vetclinicvisits.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
public record UpdateVisitDto(String reasonRequest) {
}
