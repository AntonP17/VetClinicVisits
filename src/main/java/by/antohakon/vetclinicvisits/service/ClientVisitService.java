package by.antohakon.vetclinicvisits.service;

import by.antohakon.vetclinicvisits.dto.ClientVisitDto;
import by.antohakon.vetclinicvisits.dto.CreateVisitDto;
import by.antohakon.vetclinicvisits.dto.ShortVisitDto;
import by.antohakon.vetclinicvisits.dto.UpdateVisitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClientVisitService {

    ClientVisitDto createVisit(CreateVisitDto createVisitDto);
    Page<ShortVisitDto> getAllVisits(Pageable pageable);
    Page<ShortVisitDto> getAllVisitsByAnimalId(UUID animalId);
    Page<ShortVisitDto> getAllVisitsByDoctorId(UUID doctorID);
    Page<ShortVisitDto> getAllVisitsByOwnerId(UUID ownerId);
    ClientVisitDto getVisitById(UUID id);
    ClientVisitDto updateVisitById(UpdateVisitDto updateVisitDto);
    void deleteVisit(UUID id);

}
