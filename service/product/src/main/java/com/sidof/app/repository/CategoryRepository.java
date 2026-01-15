package com.sidof.app.repository;

import java.util.UUID;

import com.sidof.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/14/26
 * </blockquote></pre>
 */

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
