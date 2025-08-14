package by.antohakon.vetclinicvisits.event;

import by.antohakon.vetclinicvisits.dto.AnimalAndOwnerEvent;
import by.antohakon.vetclinicvisits.dto.CreateVisitDto;
import by.antohakon.vetclinicvisits.dto.EmployeEvent;
import by.antohakon.vetclinicvisits.dto.VisitInfoDto;
import by.antohakon.vetclinicvisits.entity.VisitFullInfo;
import by.antohakon.vetclinicvisits.repository.ClientVisitRepository;
import by.antohakon.vetclinicvisits.repository.VisitFullInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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

    public void sendMessage(VisitInfoDto visitInfoDto) {

        try {
            String json = objectMapper.writeValueAsString(visitInfoDto);
            kafkaTemplate.send("animals_owners",visitInfoDto.visitId().toString() , json);
            kafkaTemplate.send("doctors",visitInfoDto.visitId().toString(), json);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize order: {}", e.getMessage());
        }

    }

    @KafkaListener(
            topics = "${kafka.topic.two}",
            groupId = "responseGroup"
    )
    public void listenException(String messageexception) {

        log.error("Received exception: {}", messageexception);

    }


    @KafkaListener(
            topics = "${kafka.topic.one}",
            groupId = "responseGroup"
    )
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void listenAnimalOwnersResponse(String message) {

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
        visitFullInfo.setOwner(animalAndOwnerEvent.fullName());
        visitFullInfo.setAnimal(animalAndOwnerEvent.animalName());
        visitFullInfoRepository.save(visitFullInfo);

        log.info("sucessfully update visitFullInfo");
    }

    @KafkaListener(
            topics = "${kafka.topic.three}",
            groupId = "responseGroup"
    )
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void listenDoctorsResponse(String message) {

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
        visitFullInfo.setDoctor(employeEvent.fullName());
        visitFullInfoRepository.save(visitFullInfo);

        log.info("sucessfully update visitFullInfo");
    }
}
