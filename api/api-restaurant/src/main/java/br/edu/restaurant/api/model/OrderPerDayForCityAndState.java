package br.edu.restaurant.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPerDayForCityAndState {

    @DateTimeFormat(pattern="yyyy-dd-MM")
    @Column( columnDefinition = "order_created_at" )
    private Date orderCreatedAt;

    @Column( columnDefinition = "delivery_address_state" )
    private String deliveryAddressState;

    @Column( columnDefinition = "delivery_address_city" )
    private String deliveryAddressCity;

    @Column( columnDefinition = "count" )
    private Long count;
}
