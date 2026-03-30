package com.bis.app.kafkak;

import com.bis.app.mail.EmailService;
import com.bis.app.notifications.NotificationRepository;
import com.bis.app.notifications.Notifications;
import com.bis.app.order.OrderConfirmation;
import com.bis.app.payment.PaymentConfirmation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.bis.app.notifications.NotificationType.ORDER_CONFIRMATION;
import static com.bis.app.notifications.NotificationType.PAYMENT_CONFIRMATION;
import static java.time.LocalDateTime.now;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 2/4/26
 * </blockquote></pre>
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumerPaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consume the message from payment Topic {}", paymentConfirmation);
        var notification = Notifications.builder().notificationType(PAYMENT_CONFIRMATION).notificationDate(now()).paymentConfirmation(paymentConfirmation).build();
        notificationRepository.save(notification);
        var customerName = paymentConfirmation.CustomerLastName() + " " + paymentConfirmation.CustomerFirstName();
        emailService.sendPaymentSuccessPayment(
                paymentConfirmation.CustomerEmail(),
                customerName,
                paymentConfirmation.orderReference(),
                paymentConfirmation.amount()

        );

    }

    @KafkaListener(topics = "order-topic")
    public void consumerOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consume the message from order confirmation Topic {}", orderConfirmation);
        var notification = Notifications.builder().notificationType(ORDER_CONFIRMATION).notificationDate(now()).orderConfirmation(orderConfirmation).build();
        notificationRepository.save(notification);
        var customerName = orderConfirmation.customer().lastName() + " " + orderConfirmation.customer().firstName();
        emailService.sendConfirmationOrder(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.orderReference(),
                orderConfirmation.totalAmount(),
                orderConfirmation.products()

        );

    }


}
