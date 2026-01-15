package com.sidof.app.service;

import com.sidof.app.model.Category;
import com.sidof.app.repository.CategoryRepository;
import com.sidof.app.request.CategoryRequest;
import com.sidof.app.response.CategoryResponse;
import jakarta.persistence.EntityNotFoundException;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse saveNewCategory(CategoryRequest categoryRequest){
        var categoryToSave = Category.builder()
                .id(UUID.randomUUID()) .name(categoryRequest.name()).description(categoryRequest.description()).build();
        log.info("saving new category {}",categoryToSave);
        Category save = categoryRepository.save(categoryToSave);
        return new CategoryResponse(save.getName(), save.getDescription(),save.getCreatedAt(), save.getCreatedBy());
    }

    public Category getCategorie(UUID categoryId) {
        log.info("Fetching category by category id {}",categoryId);
        return categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }
}
