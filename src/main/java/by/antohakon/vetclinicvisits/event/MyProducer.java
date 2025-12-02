package by.antohakon.vetclinicvisits.event;

import by.antohakon.vetclinicvisits.dto.VisitInfoDto;
import by.antohakon.vetclinicvisits.dto.VisitStatusEventDto;
import by.antohakon.vetclinicvisits.entity.Status;
import by.antohakon.vetclinicvisits.exceptions.KafkaSendMessageException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.new.visit}")
    private String createVisitTopic;

    @Transactional
    public void sendMessage(VisitInfoDto visitInfoDto) {

        try {
            String json = objectMapper.writeValueAsString(visitInfoDto);
            log.info("try send message : {}", json);

            kafkaTemplate.send(createVisitTopic, visitInfoDto.visitId().toString(), json)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send message to Kafka (visit-topic)", ex);
                            throw new KafkaSendMessageException("ошибка отправки сообщения в visit-topic");
                        } else {
                            log.info("Message sent to Kafka (visit-topic) successfully");
                        }
                    });

        } catch (JsonProcessingException e) {
            log.error("Failed to serialize order: {}", e.getMessage());
        }

    }

}
