package com.bis.app.service;

import com.bis.app.kafka.ProductCreatedEvent;
import com.bis.app.model.CatalogProduct;
import com.bis.app.model.CatalogProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/26/26
 * </blockquote></pre>
 */


@Service
@Slf4j
public class CatalogProductMapper {


    public CatalogProduct toCatalogProduct(ProductCreatedEvent request) {
        if (request == null) return null;
        log.info("Mapping product created event to Catalogue product");
        return CatalogProduct.builder()
                // Basic product info
                .productId(request.getProductId().toString())
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .promotionPrice(request.getPromotionPrice())
                .availableQuantity(request.getAvailableQuantity() != null ? request.getAvailableQuantity() : 0)
                .inStock(request.isInStock())
                .unitOfMeasure(request.getUnitOfMeasure())
                .active(request.isActive())
                .promotion(request.isPromotion())

                // Shop info
                .shopId(request.getShopId())
                .shopName(request.getShopName())

                // Category info
                .categoryId(request.getCategoryId().toString())
                .categoryName(request.getCategoryName())

                // Image & metadata
                .imageUrl(request.getImageUrl())
                .latitude(Double.valueOf(request.getLatitude()))
                .longitude(Double.valueOf(request.getLongitude()))
                .soldCount(0L)
                .popularityScore(0.0)
                .build();
    }

    public CatalogProductResponse toResponse(
            CatalogProduct p,
            Double userLat,
            Double userLng
    ) {
        CatalogProductResponse r = new CatalogProductResponse();

        /* PRODUCT INFO */
        r.setProductId(p.getProductId());
        r.setProductName(p.getName());
        r.setDescription(p.getDescription());
        r.setImageUrl(p.getImageUrl());
        r.setCategoryId(p.getCategoryId());
        r.setCategoryName(p.getCategoryName());

        /* PRICING */
        r.setPrice(p.getPrice());
        r.setPromotionActive(p.isPromotion());
        r.setPromotionPrice(p.getPromotionPrice());

        /* INVENTORY */
        r.setInStock(p.isInStock());
        r.setAvailableQuantity(p.getAvailableQuantity());

        /* SHOP INFO */
        r.setShopId(p.getShopId());
        r.setShopName(p.getShopName());
        r.setShopOpen(true); // can be synced later

        /* GEO */
        if (userLat != null && userLng != null
                && p.getLatitude() != null
                && p.getLongitude() != null) {
            r.setDistanceKm(
                    haversine(userLat, userLng, p.getLatitude(), p.getLongitude())
            );
        }

        /* ANALYTICS */
        r.setPopularityScore(
                p.getPopularityScore() != null
                        ? p.getPopularityScore().longValue()
                        : 0L
        );
        r.setTotalSales(
                p.getSoldCount() != null
                        ? p.getSoldCount()
                        : 0L
        );

        return r;
    }

    public CatalogProductResponse toResponse(
            CatalogProduct p
    ) {
        CatalogProductResponse r = new CatalogProductResponse();

        /* PRODUCT INFO */
        r.setProductId(p.getProductId());
        r.setProductName(p.getName());
        r.setDescription(p.getDescription());
        r.setImageUrl(p.getImageUrl());
        r.setCategoryId(p.getCategoryId());
        r.setCategoryName(p.getCategoryName());

        /* PRICING */
        r.setPrice(p.getPrice());
        r.setPromotionActive(p.isPromotion());
        r.setPromotionPrice(p.getPromotionPrice());

        /* INVENTORY */
        r.setInStock(p.isInStock());
        r.setAvailableQuantity(p.getAvailableQuantity());

        /* SHOP INFO */
        r.setShopId(p.getShopId());
        r.setShopName(p.getShopName());
        r.setShopOpen(true); // can be synced later


        /* ANALYTICS */
        r.setPopularityScore(
                p.getPopularityScore() != null
                        ? p.getPopularityScore().longValue()
                        : 0L
        );
        r.setTotalSales(
                p.getSoldCount() != null
                        ? p.getSoldCount()
                        : 0L
        );

        return r;
    }


    /* =========================================================
       DISTANCE UTILITY
       ========================================================= */

    private double haversine(
            double userLat,
            double UserLon,
            double shopLat,
            double shopLon
    ) {
        final int R = 6371; // km
        double dLat = Math.toRadians(shopLat - userLat);
        double dLon = Math.toRadians(shopLon - UserLon);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(userLat))
                * Math.cos(Math.toRadians(shopLat))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
