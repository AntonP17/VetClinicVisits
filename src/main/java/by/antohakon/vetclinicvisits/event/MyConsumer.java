package by.antohakon.vetclinicvisits.event;

import by.antohakon.vetclinicvisits.dto.AnimalAndOwnerEvent;
import by.antohakon.vetclinicvisits.dto.EmployeEvent;
import by.antohakon.vetclinicvisits.dto.ExceptionNotFoundDto;
import by.antohakon.vetclinicvisits.dto.VisitStatusEventDto;
import by.antohakon.vetclinicvisits.entity.ClientVisit;
import by.antohakon.vetclinicvisits.entity.Status;
import by.antohakon.vetclinicvisits.entity.VisitFullInfo;
import by.antohakon.vetclinicvisits.exceptions.VisitNotFoundException;
import by.antohakon.vetclinicvisits.repository.ClientVisitRepository;
import by.antohakon.vetclinicvisits.repository.VisitFullInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyConsumer {

    private final ObjectMapper objectMapper;
    private final VisitFullInfoRepository visitFullInfoRepository;
    private final ClientVisitRepository clientVisitRepository;

    // успешный статус клиента
    @KafkaListener(
            topics = "${kafka.topic.comand.save.client}",
            groupId = "${kafka.group.response.group}"
    )
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void listenClientChangeStatus(String message) {

        AnimalAndOwnerEvent animalAndOwnerEvent = null;
        try {
            log.info("Take message {}", message);
            animalAndOwnerEvent = objectMapper.readValue(message, AnimalAndOwnerEvent.class);
            log.info("after parsing {}", animalAndOwnerEvent.toString());
        } catch (JsonProcessingException e) {
            log.error("Failed to parse order from JSON: {}", message, e);
        }
        log.info("AnimalOwners response: {}", animalAndOwnerEvent.toString());

        VisitFullInfo visitFullInfo = visitFullInfoRepository.findByVisitId(animalAndOwnerEvent.visitId());
        if (visitFullInfo == null) {
            throw new VisitNotFoundException("Visit not found with id: " + animalAndOwnerEvent.visitId());
        }
        visitFullInfo.setOwner(animalAndOwnerEvent.fullName());
        visitFullInfo.setAnimal(animalAndOwnerEvent.animalName());
        visitFullInfoRepository.save(visitFullInfo);

        log.info("sucessfully update visitFullInfo");

    }

    // успешный статус врача
    @KafkaListener(
            topics = "${kafka.topic.comand.save.doctor}",
            groupId = "${kafka.group.response.group}"
    )
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void listenDoctorChangeStatus(String message) {

        EmployeEvent employeEvent = null;
        try {
            log.info("Take message {}", message);
            employeEvent = objectMapper.readValue(message, EmployeEvent.class);
            log.info("after parsing {}", employeEvent.toString());
        } catch (JsonProcessingException e) {
            log.error("Failed to parse order from JSON: {}", message, e);
        }

        log.info("Doctors response: {}", employeEvent.toString());

        VisitFullInfo visitFullInfo = visitFullInfoRepository.findByVisitId(employeEvent.visitId());
        if (visitFullInfo == null) {
            throw new VisitNotFoundException("Visit not found with id: " + employeEvent.visitId());
        }
        visitFullInfo.setDoctor(employeEvent.fullName());
        visitFullInfoRepository.save(visitFullInfo);

        log.info("sucessfully update visitFullInfo");

    }

    // exceptions
    @SneakyThrows
    @KafkaListener(
            topics = "${kafka.topic.comand.delete}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenClientDeleteStatus(String messageexception) {

        ExceptionNotFoundDto exceptionNotFoundDto = objectMapper.readValue(messageexception, ExceptionNotFoundDto.class);
        log.error("Received exception: {}", exceptionNotFoundDto.errorMessage());

        log.info("try find visit to DB : {}", exceptionNotFoundDto.visitId());

        ClientVisit findClientVisit = clientVisitRepository.findByVisitId(exceptionNotFoundDto.visitId());
        VisitFullInfo fullVisitInfo = visitFullInfoRepository.findByVisitId(exceptionNotFoundDto.visitId());

        if (findClientVisit == null && fullVisitInfo == null) {
            throw new VisitNotFoundException("Visit not found with id: " + exceptionNotFoundDto.visitId());
        }

        log.info("successfully find visit to DB : {}", findClientVisit);

        clientVisitRepository.delete(findClientVisit);
        visitFullInfoRepository.delete(fullVisitInfo);
        log.info("successfully delete visit to DB : {}", findClientVisit);

    }
}
