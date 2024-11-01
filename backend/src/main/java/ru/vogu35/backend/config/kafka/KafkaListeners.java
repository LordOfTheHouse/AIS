package ru.vogu35.backend.config.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.vogu35.backend.models.metrics.Metric;

@Slf4j
//@Component
public class KafkaListeners {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = "update_metric", groupId = "updateMetric")
    void metricListener(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        String value = record.value().toString();
        Metric metric = objectMapper.readValue(value, Metric.class);
        log.info("Metric: {}", metric);
    }
}
