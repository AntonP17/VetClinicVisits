package by.antohakon.vetclinicvisits.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public NewTopic clientsTopic() {
        return TopicBuilder.name("animals_owners")
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic doctorsTopic() {
        return TopicBuilder.name("doctors")
                .partitions(5)
                .replicas(1)
                .build();
    }



}
