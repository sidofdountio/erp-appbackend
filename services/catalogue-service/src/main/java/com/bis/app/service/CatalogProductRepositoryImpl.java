package com.bis.app.service;

import com.bis.app.model.CatalogProduct;
import com.bis.app.repository.CatalogProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

@Repository
@RequiredArgsConstructor
@Slf4j
public class CatalogProductRepositoryImpl implements CatalogProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


   /* =========================================================
       GLOBAL SEARCH
       ========================================================= */

    @Override
    public Page<CatalogProduct> searchAll(
            boolean inStockOnly,
            boolean promotionOnly,
            Double minPrice,
            Double maxPrice,
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CatalogProduct> cq = cb.createQuery(CatalogProduct.class);
        Root<CatalogProduct> root = cq.from(CatalogProduct.class);

        List<Predicate> predicates = buildCommonPredicates(
                cb, root, inStockOnly, promotionOnly, minPrice, maxPrice
        );

        cq.where(predicates.toArray(new Predicate[0]));
        applySorting(cb, cq, root, pageable);

        return executePagedQuery(cq, pageable);
    }

    /* =========================================================
      BY SHOP
      ========================================================= */
    @Override
    public Page<CatalogProduct> findByShop(
            UUID shopId,
            boolean inStockOnly,
            boolean promotionOnly,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CatalogProduct> cq = cb.createQuery(CatalogProduct.class);
        Root<CatalogProduct> root = cq.from(CatalogProduct.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("shopId"), shopId));

        if (inStockOnly) predicates.add(cb.isTrue(root.get("inStock")));
        if (promotionOnly) predicates.add(cb.isTrue(root.get("promotion")));

        cq.where(predicates.toArray(new Predicate[0]));
        applySorting(cb, cq, root, pageable);

        return executePagedQuery(cq, pageable);
    }


    /* =========================================================
       BY CATEGORY
       ========================================================= */

    @Override
    public Page<CatalogProduct> findByCategory(
            UUID categoryId,
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CatalogProduct> cq = cb.createQuery(CatalogProduct.class);
        Root<CatalogProduct> root = cq.from(CatalogProduct.class);

        cq.where(cb.equal(root.get("categoryId"), categoryId));
        applySorting(cb, cq, root, pageable);

        return executePagedQuery(cq, pageable);
    }


    /* =========================================================
       NEARBY
       ========================================================= */

    @Override
    public Page<CatalogProduct> findNearby(
            Double lat,
            Double lng,
            Integer radiusKm,
            Pageable pageable
    ) {
        // Geo filtering can be implemented with PostGIS or native SQL
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CatalogProduct> cq = cb.createQuery(CatalogProduct.class);
        Root<CatalogProduct> root = cq.from(CatalogProduct.class);

        cq.where(cb.isTrue(root.get("inStock")));
        applySorting(cb, cq, root, pageable);

        return executePagedQuery(cq, pageable);
    }

    /* =========================================================
       PROMOTIONS
       ========================================================= */

    @Override
    public Page<CatalogProduct> findPromotions(
            Double lat,
            Double lng,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CatalogProduct> cq = cb.createQuery(CatalogProduct.class);
        Root<CatalogProduct> root = cq.from(CatalogProduct.class);

        cq.where(cb.isTrue(root.get("promotion")));
        applySorting(cb, cq, root, pageable);

        return executePagedQuery(cq, pageable);
    }

    /* =========================================================
       SEARCH
       ========================================================= */

    @Override
    public Page<CatalogProduct> search(
            String keyword,
            UUID categoryId,
            Double minPrice,
            Double maxPrice,
            boolean inStockOnly,
            Double lat,
            Double lng,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CatalogProduct> cq = cb.createQuery(CatalogProduct.class);
        Root<CatalogProduct> root = cq.from(CatalogProduct.class);

        List<Predicate> predicates = new ArrayList<>();

        if (keyword != null) {
            predicates.add(
                    cb.or(
                            cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("description")), "%" + keyword.toLowerCase() + "%")
                    )
            );
        }

        if (categoryId != null) {
            predicates.add(cb.equal(root.get("categoryId"), categoryId));
        }

        if (inStockOnly) predicates.add(cb.isTrue(root.get("inStock")));

        cq.where(predicates.toArray(new Predicate[0]));
        applySorting(cb, cq, root, pageable);

        return executePagedQuery(cq, pageable);
    }

    /* =========================================================
       DETAILS
       ========================================================= */

    @Override
    public Optional<CatalogProduct> findDetails(
            UUID productId,
            Double lat,
            Double lng
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CatalogProduct> cq = cb.createQuery(CatalogProduct.class);
        Root<CatalogProduct> root = cq.from(CatalogProduct.class);

        cq.where(cb.equal(root.get("productId"), productId));

        return em.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public Optional<CatalogProduct> findProductId(UUID productId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CatalogProduct> cq = cb.createQuery(CatalogProduct.class);
        Root<CatalogProduct> root = cq.from(CatalogProduct.class);

        cq.where(cb.equal(root.get("productId"), productId));
        return em.createQuery(cq).getResultStream().findFirst();
    }



     /* =========================================================
       HELPERS
       ========================================================= */

    private List<Predicate> buildCommonPredicates(
            CriteriaBuilder cb,
            Root<CatalogProduct> root,
            boolean inStockOnly,
            boolean promotionOnly,
            Double minPrice,
            Double maxPrice
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (inStockOnly) predicates.add(cb.isTrue(root.get("inStock")));
        if (promotionOnly) predicates.add(cb.isTrue(root.get("promotionActive")));
        if (minPrice != null) predicates.add(cb.ge(root.get("price"), minPrice));
        if (maxPrice != null) predicates.add(cb.le(root.get("price"), maxPrice));

        return predicates;
    }


    private void applySorting(
            CriteriaBuilder cb,
            CriteriaQuery<CatalogProduct> cq,
            Root<CatalogProduct> root,
            Pageable pageable
    ) {
        if (!pageable.getSort().isEmpty()) {
            List<Order> orders = new ArrayList<>();
            pageable.getSort().forEach(sort ->
                    orders.add(
                            sort.isAscending()
                                    ? cb.asc(root.get(sort.getProperty()))
                                    : cb.desc(root.get(sort.getProperty()))
                    )
            );
            cq.orderBy(orders);
        }
    }


    private Page<CatalogProduct> executePagedQuery(
            CriteriaQuery<CatalogProduct> cq,
            Pageable pageable
    ) {

        // 1️ Fetch page content
        TypedQuery<CatalogProduct> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<CatalogProduct> results = query.getResultList();

        // 2️Build COUNT query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CatalogProduct> countRoot = countQuery.from(CatalogProduct.class);

        // IMPORTANT: reuse predicates from the original query
        countQuery.select(cb.count(countRoot));
        countQuery.where(cq.getRestriction());

        Long total = em.createQuery(countQuery).getSingleResult();

        // 3️ Return correct Page
        return new PageImpl<>(results, pageable, total);
    }

}