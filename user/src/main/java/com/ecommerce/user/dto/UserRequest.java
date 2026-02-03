package com.ecommerce.user.dto;

import com.ecommerce.user.constants.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private String phone;

    private AddressDto address;

    private UserRole role;
}
