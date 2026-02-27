package com.sidof.app.service;

import com.sidof.app.kafka.ProductCreatedEvent;
import com.sidof.app.kafka.ProductEventProducer;
import com.sidof.app.model.Product;
import com.sidof.app.repository.ProductRepository;
import com.sidof.app.request.ProductRequest;
import com.sidof.app.response.ProductResponse;
import com.sidof.app.shops.ShopClient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategorieService categoryService;
    private final ProductEventProducer productEventProducer;
    private final ShopClient shopClient;


    public ProductResponse createProduct(@Valid ProductRequest request, Authentication authentication) throws IllegalAccessException {

        if (!authentication.isAuthenticated()) {
            throw new IllegalAccessException("He make sure you are connected !");
        }
        var connectedUserEmail = authentication.getName();
        var shop = shopClient.findShopByEmail(connectedUserEmail).get();

        var category = categoryService.getCategorie(UUID.fromString(request.category_id()));

//        todo: Find the store associate with the connected user
        var productToSave = productMapper.toProduct(request,category,shop.getId());

        log.info("Saving new product {}", productToSave);
        var savedProduct = productRepository.save(productToSave);

        ProductCreatedEvent event = productMapper.toProductCreatedEvent(savedProduct, shop);
        productEventProducer.sendProductCreatedEvent(event);
        return productMapper.mapToResponse(savedProduct);
    }


    public ProductResponse getProductById(UUID id) {
        log.info("Fetching product by product ID {}",id);
        var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return productMapper.mapToResponse(product);
    }

    public List<Product> getAllProduct() {
        log.info("Fetching list of product");
        return productRepository.findAll();
    }

}
