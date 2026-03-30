package com.bis.app.repository;

import com.bis.app.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 2/1/26
 * </blockquote></pre>
 */

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
