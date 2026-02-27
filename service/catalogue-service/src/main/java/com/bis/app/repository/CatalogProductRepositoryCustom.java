package com.bis.app.repository;

import com.bis.app.model.CatalogProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/24/26
 * </blockquote></pre>
 */


public interface CatalogProductRepositoryCustom {

    /* =========================================================
       GLOBAL SEARCH
       ========================================================= */

    Page<CatalogProduct> searchAll(
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
       BY SHOP
       ========================================================= */

    Page<CatalogProduct> findByShop(
            UUID shopId,
            boolean inStockOnly,
            boolean promotionOnly,
            Pageable pageable
    );

    /* =========================================================
       BY CATEGORY
       ========================================================= */

    Page<CatalogProduct> findByCategory(
            UUID categoryId,
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    );

    /* =========================================================
       NEARBY
       ========================================================= */

    Page<CatalogProduct> findNearby(
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    );

    /* =========================================================
       PROMOTIONS
       ========================================================= */

    Page<CatalogProduct> findPromotions(
            Double lat,
            Double lng,
            Pageable pageable
    );

    /* =========================================================
       SEARCH
       ========================================================= */

    Page<CatalogProduct> search(
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
       PRODUCT DETAILS
       ========================================================= */

    Optional<CatalogProduct> findDetails(
            UUID productId,
            Double lat,
            Double lng
    );

    Optional<CatalogProduct> findProductId(
            UUID productId
    );


}
