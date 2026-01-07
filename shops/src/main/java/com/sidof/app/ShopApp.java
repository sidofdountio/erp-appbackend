package com.sidof.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@PreAuthorize("hasAuthority('PRODUCT_CREATE')")
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableTransactionManagement
public class ShopApp {
    static void main(String[]args) {
        SpringApplication.run(ShopApp.class,args);
    }
}
