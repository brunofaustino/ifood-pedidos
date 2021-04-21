package br.edu.restaurant.api.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class OrderPerDayForCityAndStateDTO {

    private Date orderCreatedAt;

    private String deliveryAddressState;
    private String deliveryAddressCity;
    private Long count;
}
