package com.sidof.shop_service.service;

import com.sidof.shop_service.model.Merchant;
import com.sidof.shop_service.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 3/6/26
 * </blockquote></pre>
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;

    public Merchant save(Merchant merchant) {
        boolean existEmail = merchantRepository.findByEmail(merchant.getEmail()).isPresent();
        if (existEmail) {
            log.error("A merchant with email already exists! {}", merchant.getEmail());
            throw new IllegalStateException("A merchant with email already exists !" +  merchant.getEmail());
        }
        log.info("Saving merchant with id {}", merchant.getId());
        return merchantRepository.save(merchant);
    }

    public Merchant getMerchantById(UUID id) {
        log.info("Getting merchant with id {}", id);
       return merchantRepository.findById(id).orElseThrow(() -> new RuntimeException("Merchant with id " + id + " not found"));
    }

    public Merchant getMerchantEmail(String email) {
        log.info("Getting merchant with Email {}", email);
        return merchantRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Merchant with Email " + email + " not found"));
    }

    public List<Merchant> getMerchants() {
        log.info("Getting merchants");
        return merchantRepository.findAll();
    }
}
