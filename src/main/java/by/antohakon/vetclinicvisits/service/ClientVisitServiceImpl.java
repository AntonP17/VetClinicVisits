package by.antohakon.vetclinicvisits.service;

import by.antohakon.vetclinicvisits.dto.ClientVisitDto;
import by.antohakon.vetclinicvisits.dto.CreateVisitDto;
import by.antohakon.vetclinicvisits.dto.ShortVisitDto;
import by.antohakon.vetclinicvisits.dto.UpdateVisitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientVisitServiceImpl implements ClientVisitService {
    @Override
    public ClientVisitDto createVisit(CreateVisitDto createVisitDto) {
        return null;
    }

    @Override
    public Page<ShortVisitDto> getAllVisits(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ShortVisitDto> getAllVisitsByAnimalId(UUID animalId) {
        return null;
    }

    @Override
    public Page<ShortVisitDto> getAllVisitsByDoctorId(UUID doctorID) {
        return null;
    }

    @Override
    public Page<ShortVisitDto> getAllVisitsByOwnerId(UUID ownerId) {
        return null;
    }

    @Override
    public ClientVisitDto getVisitById(UUID id) {
        return null;
    }

    @Override
    public ClientVisitDto updateVisitById(UpdateVisitDto updateVisitDto) {
        return null;
    }

    @Override
    public void deleteVisit(UUID id) {

    }
}
