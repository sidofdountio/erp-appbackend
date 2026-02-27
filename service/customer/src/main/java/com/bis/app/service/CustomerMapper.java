package com.bis.app.service;

import com.bis.app.model.Customer;
import com.bis.app.request.CustomerRequest;
import org.springframework.stereotype.Service;

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
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest customerRequest){
        return Customer.builder().firstName(customerRequest.firstName()) .lastName(customerRequest.lastName()).email(customerRequest.email()) .address(customerRequest.address()).build();
    }

}
