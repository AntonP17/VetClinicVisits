package by.antohakon.vetclinicvisits.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
public record UpdateVisitDto(

        @NotEmpty(message = "причина обращения должна быть указана")
        @Size(min = 2, max = 50, message = "причина должна быть от 2 до 50 символов")
        String reasonRequest

) {
}
