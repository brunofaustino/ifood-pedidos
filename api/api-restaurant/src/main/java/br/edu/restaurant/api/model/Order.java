package br.edu.restaurant.api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table( name = "gold_order", schema = "gold" )
@Data
public class Order implements Serializable {

    @Id
    @Column( columnDefinition = "order_id" )
    private String orderId;

    @Column(columnDefinition = "pseudonymous_id")
    private String pseudonymousId;

    @Column( columnDefinition = "customer_id" )
    private String customerId;

    @Column( columnDefinition = "order_items_id" )
    private String orderItemsId;

    @Column( columnDefinition = "merchant_id" )
    private String merchantId;

    @Column( columnDefinition = "delivery_address_city" )
    private String deliveryAddressCity;

    @Column( columnDefinition = "delivery_address_country" )
    private String deliveryAddressCountry;

    @Column( columnDefinition = "delivery_address_district" )
    private String deliveryAddressDistrict;

    @Column( columnDefinition = "delivery_address_external_id" )
    private String deliveryAddressExternalId;

    @Column( columnDefinition = "delivery_address_latitude" )
    private String deliveryAddressLatitude;

    @Column( columnDefinition = "delivery_address_longitude" )
    private String deliveryAddressLongitude;

    @Column( columnDefinition = "delivery_address_state" )
    private String deliveryAddressState;

    @Column( columnDefinition = "delivery_address_zip_code" )
    private String deliveryAddressZipCode;

    @Column( columnDefinition = "merchant_latitude" )
    private String merchantLatitude;

    @Column( columnDefinition = "merchant_longitude" )
    private String merchantLongitude;

    @Column( columnDefinition = "merchant_timezone" )
    private String merchantTimezone;

    @Column( columnDefinition = "order_created_at" )
    private Date orderCreatedAt;

    @Column( columnDefinition = "order_scheduled" )
    private Boolean orderScheduled;

    @Column( columnDefinition = "order_scheduled_date" )
    private Timestamp orderScheduledDate;

    @Column( columnDefinition = "order_total_amount" )
    private Double orderTotalAmount;

    @Column( columnDefinition = "origin_platform" )
    private String originPlatform;

    @Column( columnDefinition = "order_created_at_time" )
    private String orderCreatedAtTime;

    @Column( columnDefinition = "order_created_at_date" )
    private Timestamp orderCreatedAtDate;

    @Column( columnDefinition = "merchant_price_range" )
    private String merchantPriceRange;

    @Column( columnDefinition = "merchant_takeout_time" )
    private String merchantTakeoutTime;

    @Column( columnDefinition = "merchant_delivery_time" )
    private String merchantDeliveryTime;

    @Column( columnDefinition = "merchant_minimum_order_value" )
    private String merchantMinimumOrderValue;

    @Column( columnDefinition = "merchant_city" )
    private String merchantCity;

    @Column( columnDefinition = "merchant_zip_code" )
    private String merchantZipCode;

    @Column( columnDefinition = "merchant_state" )
    private String merchantState;

    @Column( columnDefinition = "merchant_country" )
    private String merchantCountry;
}