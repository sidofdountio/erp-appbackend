package com.sidof.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 12/7/25
 * </blockquote></pre>
 */
@Getter
@Setter
@Entity
@Table(name = "permission")
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    @SequenceGenerator(name = "permission_id_sequence", allocationSize = 1, sequenceName = "permission_id_sequence")
    @GeneratedValue(generator = "permission_id_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;
}