package br.edu.restaurant.api.service;

import br.edu.restaurant.api.dto.request.OrderRequestDTO;
import br.edu.restaurant.api.dto.response.OrderPerDayForCityAndStateDTO;
import br.edu.restaurant.api.dto.response.Top10RestaurantPerCustomerDTO;

import java.util.Date;
import java.util.List;

/**
 * Interface order service layer.
 *
 */
public interface OrderService {

    /**
     * Get count orders group by Date, City and State.
     *
     * @param orderRequestDTO Order request with params
     *
     * @return List<OrderPerDayForCityAndStateDTO>
     */
    List<OrderPerDayForCityAndStateDTO> countPerDayForCityAndState(OrderRequestDTO orderRequestDTO);

    /**
     * Get top 10 restaurants per customer.
     *
     * @param customerId Customer ID
     *
     * @return List<Top10RestaurantPerCustomerDTO>
     */
    List<Top10RestaurantPerCustomerDTO> top10RestaurantPerCustomer(String customerId);
}
