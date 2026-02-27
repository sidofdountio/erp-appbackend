package com.bis.app.kafka;

import com.bis.app.service.CatalogService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 2/19/26
 * </blockquote></pre>
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventConsumer {

    private final CatalogService catalogService;

    @KafkaListener(topics = "product.created.v1", groupId = "catalogue-group")
    @Transactional
    public void consumeProductCreated(ProductCreatedEvent event) {
        log.info("Received product event: {}", event);
        catalogService.saveNewCatalogueProduct(event);

    }
}
