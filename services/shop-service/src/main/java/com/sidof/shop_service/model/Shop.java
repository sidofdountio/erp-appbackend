package com.sidof.shop_service.model;

import com.sidof.shop_service.config.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Shop extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;
    private String code;

    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true,nullable = false)
    private String phoneNumber;

    private boolean shopOpen;
    private boolean locked=false;

    private String city;
    private String address;

    private String latitude;
    private String longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id",referencedColumnName = "id", nullable = false,foreignKey = @ForeignKey(name = "fk_shop_merchant"))
    private Merchant merchant;

//    @Column(name = "user_id",nullable = false,unique = true)
//    private Long userId;

}
