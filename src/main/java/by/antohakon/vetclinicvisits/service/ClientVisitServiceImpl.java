package by.antohakon.vetclinicvisits.service;

import by.antohakon.vetclinicvisits.dto.*;
import by.antohakon.vetclinicvisits.entity.ClientVisit;
import by.antohakon.vetclinicvisits.entity.VisitFullInfo;
import by.antohakon.vetclinicvisits.event.Orchestrator;
import by.antohakon.vetclinicvisits.exceptions.VisitNotFoundException;
import by.antohakon.vetclinicvisits.repository.ClientVisitRepository;
import by.antohakon.vetclinicvisits.repository.VisitFullInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientVisitServiceImpl implements ClientVisitService {

    private final ClientVisitRepository clientVisitRepository;
    private final VisitFullInfoRepository visitFullInfoRepository;
    private final Orchestrator orchestrator;

    @Override
    public VisitInfoDto createVisit(CreateVisitDto createVisitDto) {

        log.info("method createVisit");
        log.info("try save visit to DB : {}", createVisitDto);
        ClientVisit clientVisit = ClientVisit.builder()
                .visitId(UUID.randomUUID())
                .ownerId(createVisitDto.ownerId())
                .doctorId(createVisitDto.doctorId())
                .animalId(createVisitDto.animalId())
                .reasonRequest(createVisitDto.reasonRequest())
                .visitDate(LocalDateTime.now())
                .build();

        clientVisitRepository.save(clientVisit);
        visitFullInfoRepository.save(VisitFullInfo.builder()
                .visitId(clientVisit.getVisitId())
                .owner(null)
                .doctor(null)
                .animal(null)
                .reasonRequest(clientVisit.getReasonRequest())
                .visitDate(clientVisit.getVisitDate())
                .build());

        log.info("successfully visit to DB : {}", clientVisit);

        VisitInfoDto visitInfoDto = VisitInfoDto.builder()
                .visitId(clientVisit.getVisitId())
                .ownerId(clientVisit.getOwnerId())
                .doctorId(clientVisit.getDoctorId())
                .animalId(clientVisit.getAnimalId())
                .reasonRequest(clientVisit.getReasonRequest())
                .visitDate(clientVisit.getVisitDate())
                .build();

        orchestrator.sendMessage(visitInfoDto);

        log.info("return visitDto : {}", visitInfoDto);
        return visitInfoDto;
    }

    @Override
    public Page<ClientVisitDto> getAllVisits(Pageable pageable) {

        log.info("method getAllVisits");
        return clientVisitRepository.findAll(pageable)
                .map(visit -> ClientVisitDto.builder()
                        .visitId(visit.getVisitId())
                        .doctorId(visit.getDoctorId())
                        .animalId(visit.getAnimalId())
                        .visitDate(visit.getVisitDate())
                        .build());

    }


    @Override
    public Page<ClientVisitDto> getAllVisitsByAnimalId(UUID animalId, Pageable pageable) {

        log.info("method getAllVisitsByAnimalId");
        return clientVisitRepository.findAllByAnimalId(animalId, pageable)
                .map(visit -> ClientVisitDto.builder()
                        .visitId(visit.getVisitId())
                        .doctorId(visit.getDoctorId())
                        .animalId(visit.getAnimalId())
                        .visitDate(visit.getVisitDate())
                        .build());

    }

    @Override
    public Page<ClientVisitDto> getAllVisitsByDoctorId(UUID doctorID, Pageable pageable) {

        log.info("method getAllVisitsByDoctorId");
        return clientVisitRepository.findAllByDoctorId(doctorID, pageable)
                .map(visit -> ClientVisitDto.builder()
                        .visitId(visit.getVisitId())
                        .doctorId(visit.getDoctorId())
                        .animalId(visit.getAnimalId())
                        .visitDate(visit.getVisitDate())
                        .build());
    }

    @Override
    public Page<ClientVisitDto> getAllVisitsByOwnerId(UUID ownerId, Pageable pageable) {

        log.info("method getAllVisitsByOwnerId");
        return clientVisitRepository.findAllByOwnerId(ownerId, pageable)
                .map(visit -> ClientVisitDto.builder()
                        .visitId(visit.getVisitId())
                        .doctorId(visit.getDoctorId())
                        .animalId(visit.getAnimalId())
                        .visitDate(visit.getVisitDate())
                        .build());
    }

    @Override
    @Cacheable(value = "visit_client_cache", key = "#visitId")
    public VisitInfoDto getVisitById(UUID visitId) {

        log.info("method getVisitById");
        log.info("try get visit to DB : {}", visitId);
        ClientVisit findClientVisit = clientVisitRepository.findByVisitId(visitId);
        if (findClientVisit == null) {
            throw new VisitNotFoundException("Visit not found with id: " + visitId);
        }

        log.info("successfully visit to DB : {}", findClientVisit);
        VisitInfoDto visitInfoDto = VisitInfoDto.builder()
                .visitId(findClientVisit.getVisitId())
                .ownerId(findClientVisit.getOwnerId())
                .doctorId(findClientVisit.getDoctorId())
                .animalId(findClientVisit.getAnimalId())
                .reasonRequest(findClientVisit.getReasonRequest())
                .visitDate(findClientVisit.getVisitDate())
                .build();

        log.info("return visitDto : {}", visitInfoDto);

        return visitInfoDto;

    }


    @Override
    @CachePut(value = "visit_client_cache", key = "#visitId")
    public VisitInfoDto updateVisitById(UpdateVisitDto updateVisitDto, UUID visitId) {

        log.info("method updateVisitById");
        log.info("try find visit to DB : {}", visitId);
        ClientVisit findClientVisit = clientVisitRepository.findByVisitId(visitId);
        VisitFullInfo findVisitFullInfo = visitFullInfoRepository.findByVisitId(visitId);
        if (findClientVisit == null && findVisitFullInfo == null) {
            throw new VisitNotFoundException("Visit not found with id: " + visitId);
        }
        log.info("successfully find visit to DB : {}", findClientVisit);

        log.info("try update visit to DB ");
        findClientVisit.setReasonRequest(updateVisitDto.reasonRequest());
        findVisitFullInfo.setReasonRequest(updateVisitDto.reasonRequest());
        clientVisitRepository.save(findClientVisit);
        visitFullInfoRepository.save(findVisitFullInfo);
        log.info("successfully update visit to DB : {}", findClientVisit);

        VisitInfoDto visitInfoDto = VisitInfoDto.builder()
                .visitId(findClientVisit.getVisitId())
                .ownerId(findClientVisit.getOwnerId())
                .doctorId(findClientVisit.getDoctorId())
                .animalId(findClientVisit.getAnimalId())
                .reasonRequest(updateVisitDto.reasonRequest())
                .visitDate(findClientVisit.getVisitDate())
                .build();
        log.info("return visitDto : {}", visitInfoDto);
        return visitInfoDto;

    }

    @Override
    @CacheEvict(value = "visit_client_cache", key = "#visitId")
    public void deleteVisit(UUID visitId) {

        log.info("method deleteVisit");
        log.info("try find visit to DB : {}", visitId);
        ClientVisit findClientVisit = clientVisitRepository.findByVisitId(visitId);
        VisitFullInfo fullVisitInfo = visitFullInfoRepository.findByVisitId(visitId);
        if (findClientVisit == null && fullVisitInfo == null) {
            throw new VisitNotFoundException("Visit not found with id: " + visitId);
        }

        log.info("successfully find visit to DB : {}", findClientVisit);

        clientVisitRepository.delete(findClientVisit);
        visitFullInfoRepository.delete(fullVisitInfo);
        log.info("successfully delete visit to DB : {}", findClientVisit);

    }
}
