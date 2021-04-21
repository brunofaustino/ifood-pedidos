package br.edu.restaurant.api.model;

import java.util.Date;

public interface OrderPerDayForCityAndStateInterface {

    Date getOrderCreatedAt();

    String getDeliveryAddressState();
    String getDeliveryAddressCity();

    Long getCount();
}
