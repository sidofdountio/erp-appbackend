package com.bis.app.service;

import com.bis.app.repository.CustomerRepository;
import com.bis.app.request.CustomerRequest;
import com.bis.app.response.CustomerResponse;
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
 * Since    : 1/16/26
 * </blockquote></pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponse saveNewCustomer(@Valid CustomerRequest request) {
        var customerToSave = customerMapper.toCustomer(request);
        var saved = customerRepository.save(customerToSave);
        return CustomerResponse.builder().id(saved.getId()).address(saved.getAddress()).createdBy(saved.getCreatedBy()).build();
    }

    public CustomerResponse getCustomer(UUID customerId) {
        var customer = customerRepository.findById(customerId).orElseThrow(()->{
            log.error("Customer with ID {} does not exist", customerId);
            return new EntityNotFoundException(String.format("Customer with that ID %s does exist",customerId));
        });
        log.info("Fetching product by ID: {}",customerId);
        return CustomerResponse.builder().id(customer.getId()).address(customer.getAddress()).createdBy(customer.getEmail()).build();
    }
}
