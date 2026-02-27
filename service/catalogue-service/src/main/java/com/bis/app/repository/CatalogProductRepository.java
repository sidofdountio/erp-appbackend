package com.bis.app.repository;

import com.bis.app.model.CatalogProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

public interface CatalogProductRepository extends
        JpaRepository<CatalogProduct, UUID>,
        PagingAndSortingRepository<CatalogProduct, UUID>,
        CatalogProductRepositoryCustom {

    Optional<CatalogProduct> findByProductId(String productId);

}

