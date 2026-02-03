package com.ecommerce.user.dto;

import com.ecommerce.user.constants.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private String id;

    private String keykeycloakId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private UserRole role;

    private AddressDto address;
}
