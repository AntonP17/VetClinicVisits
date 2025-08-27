package by.antohakon.vetclinicvisits.service;

import by.antohakon.vetclinicvisits.dto.ClientVisitDto;
import by.antohakon.vetclinicvisits.dto.ClientVisitFullInfoDto;
import by.antohakon.vetclinicvisits.dto.VisitFullInfoDto;
import by.antohakon.vetclinicvisits.entity.VisitFullInfo;
import by.antohakon.vetclinicvisits.exceptions.VisitNotFoundException;
import by.antohakon.vetclinicvisits.repository.VisitFullInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitFullInfoServiceImpl implements VisitFullInfoService {

    private final VisitFullInfoRepository visitFullInfoRepository;


    @Override
    public Page<ClientVisitFullInfoDto> getAllVisitFullInfo(Pageable pageable) {

        log.info("method getAllVisitFullInfo");
        return visitFullInfoRepository.findAll(pageable)
                .map(visit -> ClientVisitFullInfoDto.builder()
                        .visitId(visit.getVisitId())
                        .doctor(visit.getDoctor())
                        .animal(visit.getAnimal())
                        .visitDate(visit.getVisitDate())
                        .build());

    }


    ///   ///////////////////////////////////
    @Override
    public Page<ClientVisitFullInfoDto> getAllVisitsByAnimalName(String animalName, Pageable pageable) {
        log.info("method getAllVisitsByAnimalName");
        return visitFullInfoRepository.findAllByAnimal(animalName, pageable)
                .map(visit -> ClientVisitFullInfoDto.builder()
                        .visitId(visit.visitId())
                        .doctor(visit.doctor())
                        .animal(visit.animal())
                        .visitDate(visit.visitDate())
                        .build());
    }

    @Override
    public Page<ClientVisitFullInfoDto> getAllVisitsByOwnerFullName(String ownerFullName, Pageable pageable) {
        log.info("method getAllVisitsByOwnerName");
        return visitFullInfoRepository.findAllByOwner(ownerFullName, pageable)
                .map(visit -> ClientVisitFullInfoDto.builder()
                        .visitId(visit.visitId())
                        .doctor(visit.doctor())
                        .animal(visit.animal())
                        .visitDate(visit.visitDate())
                        .build());
    }

    @Override
    public Page<ClientVisitFullInfoDto> getAllVisitsByDoctorFullName(String doctorFullName, Pageable pageable) {
        log.info("method getAllVisitsByDoctorName");
        return visitFullInfoRepository.findAllByDoctor(doctorFullName, pageable)
                .map(visit -> ClientVisitFullInfoDto.builder()
                        .visitId(visit.visitId())
                        .doctor(visit.doctor())
                        .animal(visit.animal())
                        .visitDate(visit.visitDate())
                        .build());
    }

    @Override
    @Cacheable(value = "full_visit_client_cache", key = "#visitId")
    public VisitFullInfoDto getFullVisitById(UUID visitId) {

        log.info("method getFullVisitById");
        log.info("try get visit to DB : {}", visitId);
        VisitFullInfo findVisit = visitFullInfoRepository.findByVisitId(visitId);
        if (findVisit == null) {
            throw new VisitNotFoundException("Visit not found with id: " + visitId);
        }

        log.info("successfully visit to DB : {}", findVisit);
        VisitFullInfoDto visitFullInfoDto = VisitFullInfoDto.builder()
                .visitId(findVisit.getVisitId())
                .owner(findVisit.getOwner())
                .doctor(findVisit.getDoctor())
                .animal(findVisit.getAnimal())
                .reasonRequest(findVisit.getReasonRequest())
                .visitDate(findVisit.getVisitDate())
                .build();

        log.info("return visitFullInfoDto : {}", visitFullInfoDto);
        return visitFullInfoDto;

    }
}
