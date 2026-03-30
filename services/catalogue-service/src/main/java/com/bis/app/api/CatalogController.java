package com.bis.app.api;

import com.bis.app.model.CatalogProductResponse;
import com.bis.app.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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

@RestController
@RequestMapping("/api/v1/bis/catalogues")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    /* =========================================================
       GLOBAL SEARCH
       ========================================================= */
    @GetMapping("/all")
    public Page<CatalogProductResponse> searchAll(
            @RequestParam(defaultValue = "false") boolean inStockOnly,
            @RequestParam(defaultValue = "false") boolean promotionOnly,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng,
            @RequestParam(required = false) Integer radiusKm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return catalogService.searchAll(
                inStockOnly, promotionOnly, minPrice, maxPrice,
                lat, lng, radiusKm, pageable
        );
    }

    /* =========================================================
       BY SHOP
       ========================================================= */
    @GetMapping("/shop/{shopId}")
    public Page<CatalogProductResponse> getByShop(
            @PathVariable UUID shopId,
            @RequestParam(defaultValue = "false") boolean inStockOnly,
            @RequestParam(defaultValue = "false") boolean promotionOnly,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return catalogService.getByShop(shopId, inStockOnly, promotionOnly, pageable);
    }

    /* =========================================================
       BY CATEGORY
       ========================================================= */
    @GetMapping("/category/{categoryId}")
    public Page<CatalogProductResponse> getByCategory(
            @PathVariable UUID categoryId,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng,
            @RequestParam(required = false) Integer radiusKm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return catalogService.getByCategory(categoryId, lat, lng, radiusKm, pageable);
    }

    /* =========================================================
       NEARBY
       ========================================================= */
    @GetMapping("/nearby")
    public Page<CatalogProductResponse> getNearby(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "10") Integer radiusKm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return catalogService.getNearby(lat, lng, radiusKm, pageable);
    }

    /* =========================================================
       PROMOTIONS
       ========================================================= */
    @GetMapping("/promotions")
    public Page<CatalogProductResponse> getPromotions(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return catalogService.getPromotions(lat, lng, pageable);
    }

    /* =========================================================
       SEARCH
       ========================================================= */
    @GetMapping("/search")
    public Page<CatalogProductResponse> search(
            @RequestParam String keyword,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "false") boolean inStockOnly,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return catalogService.search(keyword, categoryId, minPrice, maxPrice, inStockOnly, lat, lng, pageable);
    }

    /* =========================================================
       PRODUCT DETAILS
       ========================================================= */
    @GetMapping("/product/{productId}")
    public CatalogProductResponse getProductDetails(
            @PathVariable UUID productId,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng
    ) {
        return catalogService.getProductDetails(productId, lat, lng);
    }

    @GetMapping("/product-1/{productId}")
    public CatalogProductResponse getProduct(
            @PathVariable UUID productId
    ) {
        return catalogService.getProduct(productId);
    }

    /* =========================================================
       PAGEABLE BUILDER
       ========================================================= */
    private Pageable buildPageable(int page, int size, String sort) {
        Sort sortObj = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            String[] parts = sort.split(",");
            if (parts.length == 2) {
                sortObj = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
            } else {
                sortObj = Sort.by(parts[0]);
            }
        }
        return PageRequest.of(page, size, sortObj);
    }
}
