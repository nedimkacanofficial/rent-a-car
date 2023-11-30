package com.rentacar.domain;

import com.rentacar.domain.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description of UpdatePasswordRequestDTO.
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.domain
 * @since 28/11/2023
 */
@Entity
@Table(name = "tbl_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType name;
}
