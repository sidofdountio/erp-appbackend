package com.bis.app.repository;

import com.bis.app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/16/26
 * </blockquote></pre>
 */

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
