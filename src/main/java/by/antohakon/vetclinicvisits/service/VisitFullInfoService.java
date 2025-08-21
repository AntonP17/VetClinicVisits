package by.antohakon.vetclinicvisits.service;

import by.antohakon.vetclinicvisits.dto.ClientVisitDto;
import by.antohakon.vetclinicvisits.dto.ClientVisitFullInfoDto;
import by.antohakon.vetclinicvisits.dto.VisitFullInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VisitFullInfoService {

    Page<ClientVisitFullInfoDto> getAllVisitFullInfo(Pageable pageable);
    Page<ClientVisitFullInfoDto> getAllVisitsByAnimalName(String animalName, Pageable pageable);
    Page<ClientVisitFullInfoDto> getAllVisitsByOwnerFullName(String ownerFullName, Pageable pageable);
    Page<ClientVisitFullInfoDto> getAllVisitsByDoctorFullName(String doctorFullName, Pageable pageable);
    VisitFullInfoDto getFullVisitById(UUID id);


}
