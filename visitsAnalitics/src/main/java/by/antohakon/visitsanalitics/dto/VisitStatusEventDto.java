package by.antohakon.visitsanalitics.dto;

import by.antohakon.visitsanalitics.entity.Status;
import lombok.Builder;

import java.util.UUID;

@Builder
public record VisitStatusEventDto(UUID visitId,
                                  Status doctorStatus,
                                  Status animalStatus,
                                  Status ownerStatus,
                                  String comment){
}
