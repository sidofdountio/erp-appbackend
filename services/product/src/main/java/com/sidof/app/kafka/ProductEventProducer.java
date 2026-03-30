package com.sidof.app.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 2/1/26
 * </blockquote></pre>
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventProducer {
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public void sendProductCreatedEvent(ProductCreatedEvent request) {
        log.info("Sending saved product to catalogue service  {}", request);
        Message<ProductCreatedEvent> message = MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC, "product.created.v1")
                .setHeader(KafkaHeaders.KEY, request.getProductId().toString())
                .build();
//        kafkaTemplate.send(message);
        kafkaTemplate.send(message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send ProductCreatedEvent", ex);
                    } else {
                        log.info("Event sent to partition {}",
                                result.getRecordMetadata().partition());
                    }
                });


    }
}
