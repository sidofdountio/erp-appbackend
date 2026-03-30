package com.bis.app.notification;

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
public class NotificationProducer {
    private final KafkaTemplate<String, PaymentNotificationRequest> kKafkaTemplate;

    public  void sendNotification(PaymentNotificationRequest request){
        log.info("Sending the notification to order service {} ",request);
        Message<PaymentNotificationRequest>message =
                MessageBuilder
                        .withPayload(request)
                        .setHeader(KafkaHeaders.TOPIC,"payment-topic")
                        .build();
        kKafkaTemplate.send(message);
    }
}
