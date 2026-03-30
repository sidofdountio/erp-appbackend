package com.sidof.shop_service.repository;

import com.sidof.shop_service.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/27/26
 * </blockquote></pre>
 */

public interface ShopRepository extends JpaRepository<Shop, UUID>, PagingAndSortingRepository<Shop, UUID> {
    Optional<Shop> findByEmail(String email);
}
