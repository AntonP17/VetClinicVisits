package by.antohakon.vetclinicvisits.service;

import by.antohakon.vetclinicvisits.dto.ClientVisitDto;
import by.antohakon.vetclinicvisits.dto.CreateVisitDto;
import by.antohakon.vetclinicvisits.dto.VisitInfoDto;
import by.antohakon.vetclinicvisits.dto.UpdateVisitDto;
import by.antohakon.vetclinicvisits.entity.ClientVisit;
import by.antohakon.vetclinicvisits.repository.ClientVisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientVisitServiceImpl implements ClientVisitService {


    private final ClientVisitRepository clientVisitRepository;

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
        log.info("successfully visit to DB : {}", clientVisit);

        VisitInfoDto visitInfoDto = VisitInfoDto.builder()
                .visitId(clientVisit.getVisitId())
                .ownerId(clientVisit.getOwnerId())
                .doctorId(clientVisit.getDoctorId())
                .animalId(clientVisit.getAnimalId())
                .reasonRequest(clientVisit.getReasonRequest())
                .visitDate(clientVisit.getVisitDate())
                .build();

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
    public VisitInfoDto getVisitById(UUID id) {

        log.info("method getVisitById");
        log.info("try get visit to DB : {}", id);
        ClientVisit findClientVisit = clientVisitRepository.findByVisitId(id);
        if (findClientVisit == null) {
            throw new RuntimeException("Visit not found with id: " + id);
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
    public VisitInfoDto updateVisitById(UpdateVisitDto updateVisitDto, UUID visitId) {

        log.info("method updateVisitById");
        log.info("try find visit to DB : {}", visitId);
        ClientVisit findClientVisit = clientVisitRepository.findByVisitId(visitId);
        if (findClientVisit == null) {
            throw new RuntimeException("Visit not found with id: " + visitId);
        }
        log.info("successfully find visit to DB : {}", findClientVisit);

        log.info("try update visit to DB ");
        findClientVisit.setReasonRequest(updateVisitDto.reasonRequest());
        clientVisitRepository.save(findClientVisit);
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
    public void deleteVisit(UUID visitId) {

        log.info("method deleteVisit");
        log.info("try find visit to DB : {}", visitId);
        ClientVisit findClientVisit = clientVisitRepository.findByVisitId(visitId);
        if (findClientVisit == null) {
            throw new RuntimeException("Visit not found with id: " + visitId);
        }
        log.info("successfully find visit to DB : {}", findClientVisit);

        clientVisitRepository.delete(findClientVisit);
        log.info("successfully delete visit to DB : {}", findClientVisit);

    }
}
