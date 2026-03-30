package com.bis.app.mail;

import com.bis.app.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

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
public class EmailService {
    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;
    String templateName;

    private static final String NEW_PAYMENT = "";
    private static final String ORDER_CONFIRMATION = "Order confirmation";
    private static final String PAYMENT_CONFIRMATION = "Payment successfully process";


    @Async
    public void sendPaymentSuccessPayment(
            String destinationEmail,
            String customerName,
            String orderReference,
            BigDecimal amount
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("sidof@sidof.xzy");

        templateName = "payment-confirmation";
        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("amount", amount);
        model.put("orderReference", orderReference);
        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(PAYMENT_CONFIRMATION);
        try {
            String html = templateEngine.process(templateName, context);
            messageHelper.setText(html, true);
            messageHelper.setTo(destinationEmail);

            javaMailSender.send(mimeMessage);
            log.info("Email successfully send to customer {}", customerName);
        } catch (MessagingException exception) {
            log.error("Cannot send email to {}", destinationEmail);
        }

    }


    @Async
    public void sendConfirmationOrder(
            String destinationEmail,
            String customerName,
            String orderReference,
            BigDecimal amount,
            List<Product>products
    ) throws MessagingException {
        MimeMessage  mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,UTF_8.name());
        messageHelper.setFrom("sidof@sidof.xzy");

        templateName = "order-confirmation";
        Map<String, Object> model = new HashMap<>();
        model.put("customerName",customerName);
        model.put("totalAmount",amount);
        model.put("orderReference",orderReference);
        model.put("products",products);
        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(ORDER_CONFIRMATION);
        try {
            String html = templateEngine.process(templateName,context);
            messageHelper.setText(html,true);
            messageHelper.setTo(destinationEmail);

            javaMailSender.send(mimeMessage);
            log.info("Email successfully send to customer {}",customerName);
        }catch (MessagingException exception){
            log.error("Cannot send email to {}",destinationEmail);
        }

    }
}
