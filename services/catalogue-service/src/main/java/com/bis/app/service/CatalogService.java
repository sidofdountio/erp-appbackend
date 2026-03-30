package com.bis.app.service;

import com.bis.app.kafka.ProductCreatedEvent;
import com.bis.app.model.CatalogProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/24/26
 * </blockquote></pre>
 */


public interface CatalogService {

    void saveNewCatalogueProduct(ProductCreatedEvent request);

    /* =========================================================
       GLOBAL
       ========================================================= */

    Page<CatalogProductResponse> searchAll(
            boolean inStockOnly,
            boolean promotionOnly,
            Double minPrice,
            Double maxPrice,
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    );

    /* =========================================================
       SHOP
       ========================================================= */

    Page<CatalogProductResponse> getByShop(
            UUID shopId,
            boolean inStockOnly,
            boolean promotionOnly,
            Pageable pageable
    );

    /* =========================================================
       CATEGORY
       ========================================================= */

    Page<CatalogProductResponse> getByCategory(
            UUID categoryId,
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    );

    /* =========================================================
       NEARBY
       ========================================================= */

    Page<CatalogProductResponse> getNearby(
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    );

    /* =========================================================
       PROMOTIONS
       ========================================================= */

    Page<CatalogProductResponse> getPromotions(
            Double lat,
            Double lng,
            Pageable pageable
    );

    /* =========================================================
       SEARCH
       ========================================================= */

    Page<CatalogProductResponse> search(
            String keyword,
            UUID categoryId,
            Double minPrice,
            Double maxPrice,
            boolean inStockOnly,
            Double lat,
            Double lng,
            Pageable pageable
    );

    /* =========================================================
       DETAILS
       ========================================================= */

    CatalogProductResponse getProductDetails(
            UUID productId,
            Double lat,
            Double lng
    );


    CatalogProductResponse getProduct(
            UUID productId
    );
}
