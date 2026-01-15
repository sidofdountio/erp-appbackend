package com.sidof.app.service;

import com.sidof.app.repository.ProductRepository;
import com.sidof.app.request.ProductRequest;
import com.sidof.app.response.ProductResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductResponse createProduct(@Valid ProductRequest request) {
        var category = categoryService.getCategorie(UUID.fromString(request.category_id()));
        var productToSave = productMapper.toProduct(request,category);
        var productSaved = productRepository.save(productToSave);
        log.info("Saving new product {}", productToSave);
        return productMapper.mapToResponse(productSaved);
    }

    public ProductResponse getProductById(UUID id) {
        log.info("Fetching product by product ID {}",id);
        var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return productMapper.mapToResponse(product);
    }
}
