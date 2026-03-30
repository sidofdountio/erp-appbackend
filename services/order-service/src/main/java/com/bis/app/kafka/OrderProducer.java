package com.bis.app.kafka;

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
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String,OrderConfirmation> stringOrderConfirmationKafkaTemplate;

    public void sendOrderConfirmation(OrderConfirmation orderConfirmation){
        log.info("Sending order conformation {}",orderConfirmation);
        Message<OrderConfirmation> orderConfirmationMessage=
                MessageBuilder
                        .withPayload(orderConfirmation)
                        .setHeader(KafkaHeaders.TOPIC,"order-topic")
                        .build();
        stringOrderConfirmationKafkaTemplate.send(orderConfirmationMessage);
    }
}
