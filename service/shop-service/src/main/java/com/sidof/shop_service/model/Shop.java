package com.sidof.shop_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 11/25/25
 * </blockquote></pre>
 */

@Table(name = "shop")
@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;
    private String code;

//    private String email;
//    private String managerName;
//    @Column(name = "contact_phone")
//    private String contactPhone;

//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private ShopCategory category;

//    private String latitude;
//    private String longitude;

    @Column(name = "user_id",nullable = false,unique = true)
    private Long userId;
}
