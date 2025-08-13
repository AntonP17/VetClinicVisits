package by.antohakon.vetclinicvisits.service;

import by.antohakon.vetclinicvisits.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClientVisitService {

    VisitInfoDto createVisit(CreateVisitDto createVisitDto);
    Page<ClientVisitDto> getAllVisits(Pageable pageable);
    Page<ClientVisitDto> getAllVisitsByAnimalId(UUID animalId, Pageable pageable);
    Page<ClientVisitDto> getAllVisitsByDoctorId(UUID doctorID, Pageable pageable);
    Page<ClientVisitDto> getAllVisitsByOwnerId(UUID ownerId, Pageable pageable);
    VisitInfoDto getVisitById(UUID id);
    VisitFullInfoDto getFullVisitById(UUID id);
    VisitInfoDto updateVisitById(UpdateVisitDto updateVisitDto, UUID visitId);
    void deleteVisit(UUID id);

}
