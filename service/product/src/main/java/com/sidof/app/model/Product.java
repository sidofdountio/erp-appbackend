package com.sidof.app.model;

import com.sidof.app.config.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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


@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_product_shop_id", columnList = "shopId", unique = true),
        @Index(name = "idx_product_sku", columnList = "sku", unique = true),
        @Index(name = "idx_product_category_id", columnList = "category_id")
})
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private BigDecimal price;
    private double availableQuantity;

    @Column(unique = true, nullable = false)
    private String sku; // Stock Keeping Unit (Unique Identifier)
    private boolean active = true;
//    private boolean promotion = false;

//    private String unitOfMeasure;
//    private String brand;
//    private String barcode;
//    private BigDecimal discountPrice;


    @Column(nullable = false, unique = true)
    private UUID shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;

}