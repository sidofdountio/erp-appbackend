package com.sidof.app.controller;

import com.sidof.app.request.CategoryRequest;
import com.sidof.app.response.CategoryResponse;
import com.sidof.app.service.CategorieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategorieService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request, Authentication authentication) {
        var saved = categoryService.saveNewCategory(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
