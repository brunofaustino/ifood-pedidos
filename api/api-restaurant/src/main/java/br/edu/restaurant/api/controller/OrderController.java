package br.edu.restaurant.api.controller;

import br.edu.restaurant.api.dto.request.OrderRequestDTO;
import br.edu.restaurant.api.dto.response.OrderPerDayForCityAndStateDTO;
import br.edu.restaurant.api.dto.response.Top10RestaurantPerCustomerDTO;
import br.edu.restaurant.api.exception.NoResultCountOrderException;
import br.edu.restaurant.api.exception.NoResultTop10ConsumedRestaurantsPerCostumerException;
import br.edu.restaurant.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("count-per-day-for-city-state")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<OrderPerDayForCityAndStateDTO> countPerDayForCityAndState(@Valid OrderRequestDTO orderRequestDTO)
            throws NoResultCountOrderException {

        List<OrderPerDayForCityAndStateDTO> orderPerDayForCityAndStateDTOList = orderService.countPerDayForCityAndState(orderRequestDTO);

        if ( orderPerDayForCityAndStateDTOList.isEmpty() ) {
            throw new NoResultCountOrderException();
        }
        return orderPerDayForCityAndStateDTOList;
    }

    @GetMapping("top-10-consumed-restaurants-per-customer")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Top10RestaurantPerCustomerDTO> top10ConsumedRestaurantsPerCustomer(@RequestParam(name = "customerId") String customerId)
            throws NoResultTop10ConsumedRestaurantsPerCostumerException {

        List<Top10RestaurantPerCustomerDTO> top10RestaurantPerCustomerDTOList = orderService.top10RestaurantPerCustomer(customerId);

        if ( top10RestaurantPerCustomerDTOList.isEmpty() ) {
            throw new NoResultTop10ConsumedRestaurantsPerCostumerException();
        }
        return top10RestaurantPerCustomerDTOList;
    }
}
