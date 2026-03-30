package com.sidof.app.repository;

import com.sidof.app.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/14/26
 * </blockquote></pre>
 */


public interface ProductRepository extends JpaRepository<Product, UUID> {
    // 1. Basic filter by Shop
    List<Product> findByShopId(UUID shopId);

    // 2. Filter by Shop and Category (Very common for "Shop by Category" pages)
    List<Product> findByShopIdAndCategoryId(UUID shopId, UUID categoryId);

    // 3. Search products within a shop by name (Case Insensitive)
    List<Product> findByShopIdAndNameContainingIgnoreCase(UUID shopId, String name);

    // 4. Find only active products for a shop
    List<Product> findByShopIdAndActiveTrue(UUID shopId);

    // 5. Paginated results (Essential for performance)
    Page<Product> findByShopId(UUID shopId, Pageable pageable);
}
