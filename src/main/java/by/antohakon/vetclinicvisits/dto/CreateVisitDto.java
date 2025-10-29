package by.antohakon.vetclinicvisits.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CreateVisitDto(

        @NotNull(message = "UUID владельца должен быть не пустой")
        UUID ownerId,

        @NotNull(message = "UUID врача должен быть не пустой")
        UUID doctorId,

        @NotNull(message = "UUID животного должен быть не пустой")
        UUID animalId,

        @NotEmpty(message = "причина обращения должна быть указана")
        @Size(min = 2, max = 50, message = "причина должна быть от 2 до 50 символов")
        String reasonRequest) {
}
