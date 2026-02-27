package com.bis.app.controller;

import com.bis.app.request.CustomerRequest;
import com.bis.app.response.CustomerResponse;
import com.bis.app.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

@RestController @RequestMapping("/api/v1/bis/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<CustomerResponse>create(@Validated @RequestBody CustomerRequest request, Authentication authentication  ){
        var response = customerService.saveNewCustomer(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse>customer(@Validated @PathVariable UUID customerId){
        return new ResponseEntity<>(customerService.getCustomer(customerId),HttpStatus.OK);
    }

}