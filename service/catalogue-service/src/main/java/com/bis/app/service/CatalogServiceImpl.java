package com.bis.app.service;

import com.bis.app.kafka.ProductCreatedEvent;
import com.bis.app.model.CatalogProduct;
import com.bis.app.model.CatalogProductResponse;
import com.bis.app.repository.CatalogProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CatalogServiceImpl implements CatalogService {

    private final CatalogProductRepository repository;
    private final CatalogProductMapper catalogProductMapper;


    @Override
    public void saveNewCatalogueProduct(ProductCreatedEvent request) {
        var productCatalogueToSave = catalogProductMapper.toCatalogProduct(request);
        getProduct(request.getProductId());
        log.info("Saving new catalogue product {}", productCatalogueToSave);
        repository.save(productCatalogueToSave);
    }



    /* =========================================================
       GLOBAL
       ========================================================= */

    @Override
    public Page<CatalogProductResponse> searchAll(
            boolean inStockOnly,
            boolean promotionOnly,
            Double minPrice,
            Double maxPrice,
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    ) {
        return repository.searchAll(
                        inStockOnly,
                        promotionOnly,
                        minPrice,
                        maxPrice,
                        lat,
                        lng,
                        radiusKm,
                        pageable
                )
                .map(p -> toResponse(p, lat, lng));
    }

    /* =========================================================
       SHOP
       ========================================================= */

    @Override
    public Page<CatalogProductResponse> getByShop(
            UUID shopId,
            boolean inStockOnly,
            boolean promotionOnly,
            Pageable pageable
    ) {
        return repository.findByShop(
                        shopId,
                        inStockOnly,
                        promotionOnly,
                        pageable
                )
                .map(p -> catalogProductMapper.toResponse(p, null, null));
    }

    /* =========================================================
       CATEGORY
       ========================================================= */

    @Override
    public Page<CatalogProductResponse> getByCategory(
            UUID categoryId,
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    ) {
        return repository.findByCategory(
                        categoryId,
                        lat,
                        lng,
                        radiusKm,
                        pageable
                )
                .map(p -> toResponse(p, lat, lng));
    }

    /* =========================================================
       NEARBY
       ========================================================= */

    @Override
    public Page<CatalogProductResponse> getNearby(
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    ) {
        return repository.findNearby(
                        lat,
                        lng,
                        radiusKm,
                        pageable
                )
                .map(p -> toResponse(p, lat, lng));
    }

    /* =========================================================
       PROMOTIONS
       ========================================================= */

    @Override
    public Page<CatalogProductResponse> getPromotions(
            Double lat,
            Double lng,
            Pageable pageable
    ) {
        return repository.findPromotions(lat, lng, pageable)
                .map(p -> toResponse(p, lat, lng));
    }

    /* =========================================================
       SEARCH
       ========================================================= */

    @Override
    public Page<CatalogProductResponse> search(
            String keyword,
            UUID categoryId,
            Double minPrice,
            Double maxPrice,
            boolean inStockOnly,
            Double lat,
            Double lng,
            Pageable pageable
    ) {
        return repository.search(
                        keyword,
                        categoryId,
                        minPrice,
                        maxPrice,
                        inStockOnly,
                        lat,
                        lng,
                        pageable
                )
                .map(p -> toResponse(p, lat, lng));
    }

    /* =========================================================
       DETAILS
       ========================================================= */

    @Override
    public CatalogProductResponse getProductDetails(
            UUID productId,
            Double lat,
            Double lng
    ) {
        CatalogProduct product = repository.findDetails(productId, lat, lng)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found: " + productId)
                );

        return toResponse(product, lat, lng);
    }

    @Override
    public CatalogProductResponse getProduct(UUID productId) {
        CatalogProduct product = repository.findProductId(productId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found: " + productId)
                );
        return catalogProductMapper.toResponse(product);
    }

    /* =========================================================
       MAPPER
       ========================================================= */

    private CatalogProductResponse toResponse(
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

    /* =========================================================
       DISTANCE UTILITY
       ========================================================= */

    private double haversine(
            double lat1,
            double lon1,
            double lat2,
            double lon2
    ) {
        final int R = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
