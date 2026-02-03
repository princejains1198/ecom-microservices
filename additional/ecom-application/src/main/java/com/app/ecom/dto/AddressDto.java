package com.app.ecom.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class AddressDto {

    private String street;

    private String city;

    private String state;

    private String country;

    private String zipcode;
}
