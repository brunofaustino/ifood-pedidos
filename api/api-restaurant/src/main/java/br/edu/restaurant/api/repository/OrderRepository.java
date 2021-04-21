package br.edu.restaurant.api.repository;

import br.edu.restaurant.api.model.Order;
import br.edu.restaurant.api.model.OrderPerDayForCityAndStateInterface;
import br.edu.restaurant.api.model.Top10RestaurantPerCustomerInterface;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Interface for generic CRUD and Query custom operations on a repository for a specific type.
 *
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query(
        value = " SELECT" +
                "    TO_DATE(order_created_at, 'YYYY-MM-DD') as orderCreatedAt," +
                "    delivery_address_state as deliveryAddressState," +
                "    delivery_address_city as deliveryAddressCity," +
                "    count(order_id) as count" +
                " FROM gold.gold_order" +
                " WHERE order_created_at BETWEEN :orderDateStart AND :orderDateEnd" +
                " GROUP BY TO_DATE(order_created_at, 'YYYY-MM-DD'), delivery_address_state, delivery_address_city" +
                " ORDER BY COUNT(order_id) DESC;",
        nativeQuery = true
    )
    List<OrderPerDayForCityAndStateInterface> countAllByOrderCreatedAt(@Param("orderDateStart") Date orderDateStart,
                                                                       @Param("orderDateEnd") Date orderDateEnd);

    @Query(
        value = " SELECT" +
                "    TO_DATE(order_created_at, 'YYYY-MM-DD') as orderCreatedAt," +
                "    delivery_address_state as deliveryAddressState," +
                "    delivery_address_city as deliveryAddressCity," +
                "    count(order_id) as count" +
                " FROM gold.gold_order" +
                " WHERE order_created_at BETWEEN :orderDateStart AND :orderDateEnd" +
                " AND delivery_address_state = :state" +
                " GROUP BY TO_DATE(order_created_at, 'YYYY-MM-DD'), delivery_address_state, delivery_address_city" +
                " ORDER BY COUNT(order_id) DESC;",
        nativeQuery = true
    )
    List<OrderPerDayForCityAndStateInterface> countAllByOrderCreatedAt(@Param("orderDateStart") Date orderDateStart,
                                                                       @Param("orderDateEnd") Date orderDateEnd,
                                                                       @Param("state") String state);

    @Query(
        value = " SELECT" +
                "    TO_DATE(order_created_at, 'YYYY-MM-DD') as orderCreatedAt," +
                "    delivery_address_state as deliveryAddressState," +
                "    delivery_address_city as deliveryAddressCity," +
                "    count(order_id) as count" +
                " FROM gold.gold_order" +
                " WHERE order_created_at BETWEEN :orderDateStart AND :orderDateEnd" +
                " AND delivery_address_state = :state" +
                " AND delivery_address_city = :city" +
                " GROUP BY TO_DATE(order_created_at, 'YYYY-MM-DD'), delivery_address_state, delivery_address_city" +
                " ORDER BY COUNT(order_id) DESC;",
        nativeQuery = true
    )
    List<OrderPerDayForCityAndStateInterface> countAllByOrderCreatedAt(@Param("orderDateStart") Date orderDateStart,
                                                                       @Param("orderDateEnd") Date orderDateEnd,
                                                                       @Param("city") String city,
                                                                       @Param("state") String state);

    @Query(
        value = " SELECT" +
                "    o.merchant_id as merchantId," +
                "    r.price_range as priceRange," +
                "    r.merchant_city as merchantCity," +
                "    r.merchant_state as merchantState," +
                "    r.merchant_country as merchantCountry," +
                "    count(order_id)" +
                " FROM gold.gold_order AS o" +
                " INNER JOIN gold.silver_restaurant AS r" +
                " ON o.merchant_id = r.id" +
                " WHERE o.customer_id = :customerId" +
                " GROUP BY o.merchant_id, r.price_range, r.merchant_city, r.merchant_state, r.merchant_country" +
                " ORDER BY COUNT(o.order_id) DESC" +
                " LIMIT 10",
        nativeQuery = true
    )
    List<Top10RestaurantPerCustomerInterface> countAllByCustomerIdGroupByMerchantId(@Param("customerId") String customerId);
}
