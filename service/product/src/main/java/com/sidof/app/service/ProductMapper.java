package com.sidof.app.service;

import com.sidof.app.model.Category;
import com.sidof.app.model.Product;
import com.sidof.app.request.ProductRequest;
import com.sidof.app.response.ProductResponse;
import org.springframework.stereotype.Service;

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

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request, Category category) {
        return Product.builder()
                .name(request.name())
                .sku(request.sku())
                .category(category)
                .shopId(UUID.fromString(request.shop_id()))
                .build();
    }

    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .price(product.getPrice())
                .categoryName(product.getCategory().getName())
                .createdBy(product.getCreatedBy()) // From our Auditing system!
                .build();
    }
}
