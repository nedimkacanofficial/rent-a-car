package com.rentacar.dto.response;

import com.rentacar.domain.Role;
import com.rentacar.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.dto.response
 * @since 28/11/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String zipCode;

    private Boolean builtIn;

    private Set<String> roles;

    public void setRoles(Set<Role> roles) {
        Set<String> rolesStr=new HashSet<>();
        roles.forEach(r->{
            if(r.getName().equals(RoleType.ROLE_ADMIN))
                rolesStr.add("Administrator");
            else
                rolesStr.add("Customer");
        });
        this.roles = rolesStr;
    }
}
