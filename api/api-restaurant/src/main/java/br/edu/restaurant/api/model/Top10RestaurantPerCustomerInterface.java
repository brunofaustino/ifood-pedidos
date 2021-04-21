package br.edu.restaurant.api.model;

import java.util.UUID;

public interface Top10RestaurantPerCustomerInterface {

    UUID getMerchantId();

    String getPriceRange();
    String getMerchantCity();
    String getMerchantState();
    String getMerchantCountry();

    Long getCount();
}
