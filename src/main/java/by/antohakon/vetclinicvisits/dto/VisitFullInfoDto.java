package by.antohakon.vetclinicvisits.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
public record VisitFullInfoDto(UUID visitId,
                               String owner,
                               String doctor,
                               String animal,
                               String reasonRequest,
                               LocalDateTime visitDate) {
}
