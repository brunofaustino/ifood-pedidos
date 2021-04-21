package br.edu.restaurant.api.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class Top10RestaurantPerCustomerDTO {

    private UUID merchantId;

    private String priceRange;
    private String merchantCity;
    private String merchantState;
    private String merchantCountry;

    private Long count;
}
