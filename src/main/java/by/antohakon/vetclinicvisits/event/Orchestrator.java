package by.antohakon.vetclinicvisits.event;

import by.antohakon.vetclinicvisits.dto.*;
import by.antohakon.vetclinicvisits.entity.ClientVisit;
import by.antohakon.vetclinicvisits.entity.Status;
import by.antohakon.vetclinicvisits.entity.VisitFullInfo;
import by.antohakon.vetclinicvisits.exceptions.KafkaSendMessageException;
import by.antohakon.vetclinicvisits.exceptions.VisitNotFoundException;
import by.antohakon.vetclinicvisits.repository.ClientVisitRepository;
import by.antohakon.vetclinicvisits.repository.VisitFullInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Orchestrator {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final VisitFullInfoRepository visitFullInfoRepository;
    private final ClientVisitRepository clientVisitRepository;

    @Transactional
    public void sendMessage(VisitInfoDto visitInfoDto) {

        try {
            String json = objectMapper.writeValueAsString(visitInfoDto);
            log.info("try send message : {}", json);
            VisitStatusEventDto visitStatusEventDto = VisitStatusEventDto.builder()
                    .visitId(visitInfoDto.visitId())
                    .status(Status.PROCESSING)
                    .comment("create new visit")
                    .build();

            String jsonAnalitics = objectMapper.writeValueAsString(visitStatusEventDto);

            kafkaTemplate.send("animals_owners", visitInfoDto.visitId().toString(), json)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send message to Kafka (animals_owners)", ex);
                            throw new KafkaSendMessageException("ошибка отправки сообщения в animals_owners");
                        } else {
                            log.info("Message sent to Kafka (animals_owners) successfully");
                        }
                    });

            kafkaTemplate.send("doctors", visitInfoDto.visitId().toString(), json)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send message to Kafka (doctors)", ex);
                            throw new KafkaSendMessageException("ошибка отправки сообщения в (doctors)");
                        } else {
                            log.info("Message sent to Kafka (doctors) successfully");
                        }
                    });


            kafkaTemplate.send("analytics", visitInfoDto.visitId().toString(), jsonAnalitics)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send message to Kafka (analytics)", ex);
                            throw new KafkaSendMessageException("ошибка отправки сообщения в (analytics)");
                        } else {
                            log.info("Message sent to Kafka (analytics) successfully");
                        }
                    });

        } catch (JsonProcessingException e) {
            log.error("Failed to serialize order: {}", e.getMessage());
        }

    }

    @SneakyThrows
    @KafkaListener(
            topics = "${kafka.topic.two}",
            groupId = "responseGroup"
    )
    public void listenException(String messageexception) {

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

        log.info("try send messege Analitic");
        VisitStatusEventDto visitStatusEventDto = VisitStatusEventDto.builder()
                .visitId(exceptionNotFoundDto.visitId())
                .status(Status.FAILED)
                .comment(exceptionNotFoundDto.errorMessage())
                .build();

        String jsonAnalitics = objectMapper.writeValueAsString(visitStatusEventDto);
        kafkaTemplate.send("analytics", exceptionNotFoundDto.visitId().toString(), jsonAnalitics);

        log.info("sucesses send message Analitic: {}", jsonAnalitics);
    }


    @KafkaListener(
            topics = "${kafka.topic.one}",
            groupId = "responseGroup"
    )
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void listenAnimalOwnersResponse(String message) throws JsonProcessingException {

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

        log.info("try send messege Analitic");
        VisitStatusEventDto visitStatusEventDto = VisitStatusEventDto.builder()
                .visitId(visitFullInfo.getVisitId())
                .status(Status.SUCCESS)
                .comment("ACEPTED")
                .build();

        String jsonAnalitics = objectMapper.writeValueAsString(visitStatusEventDto);
        kafkaTemplate.send("analytics", visitFullInfo.getVisitId().toString(), jsonAnalitics);
        log.info("sucesses send message Analitic: {}", jsonAnalitics);
    }

    @KafkaListener(
            topics = "${kafka.topic.three}",
            groupId = "responseGroup"
    )
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void listenDoctorsResponse(String message) throws JsonProcessingException {

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

        log.info("try send messege Analitic");
        VisitStatusEventDto visitStatusEventDto = VisitStatusEventDto.builder()
                .visitId(visitFullInfo.getVisitId())
                .status(Status.SUCCESS)
                .comment("ACEPTED")
                .build();

        String jsonAnalitics = objectMapper.writeValueAsString(visitStatusEventDto);
        kafkaTemplate.send("analytics", visitFullInfo.getVisitId().toString(), jsonAnalitics);
        log.info("sucesses send message Analitic: {}", jsonAnalitics);
    }
}
