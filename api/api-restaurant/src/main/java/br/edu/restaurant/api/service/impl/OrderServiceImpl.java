package br.edu.restaurant.api.service.impl;

import br.edu.restaurant.api.dto.request.OrderRequestDTO;
import br.edu.restaurant.api.dto.response.OrderPerDayForCityAndStateDTO;
import br.edu.restaurant.api.dto.response.Top10RestaurantPerCustomerDTO;
import br.edu.restaurant.api.model.OrderPerDayForCityAndState;
import br.edu.restaurant.api.model.OrderPerDayForCityAndStateInterface;
import br.edu.restaurant.api.repository.OrderRepository;
import br.edu.restaurant.api.service.OrderService;
import br.edu.restaurant.api.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OrderPerDayForCityAndStateDTO> countPerDayForCityAndState(OrderRequestDTO orderRequestDTO) {
        List<OrderPerDayForCityAndStateInterface> orderPerDayForCityAndStates = null;
        Date orderDateStart = DateUtil.setCustomTime(orderRequestDTO.getOrderDate(), 0,0,0);
        Date orderDateEnd = DateUtil.setCustomTime(orderRequestDTO.getOrderDate(), 23,59,59);

        if ( orderRequestDTO.getCity() == null && orderRequestDTO.getState() == null ) {
            orderPerDayForCityAndStates = orderRepository.countAllByOrderCreatedAt(orderDateStart, orderDateEnd);
        } else if ( orderRequestDTO.getCity() == null &&  orderRequestDTO.getState() != null ) {
            orderPerDayForCityAndStates = orderRepository.countAllByOrderCreatedAt(orderDateStart, orderDateEnd , orderRequestDTO.getState());
        } else {
            orderPerDayForCityAndStates = orderRepository.countAllByOrderCreatedAt(orderDateStart, orderDateEnd , orderRequestDTO.getCity(), orderRequestDTO.getState());
        }

        return orderPerDayForCityAndStates
                .stream()
                .map(orderPerDayForCityAndState -> this.modelMapper.map(orderPerDayForCityAndState, OrderPerDayForCityAndStateDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Top10RestaurantPerCustomerDTO> top10RestaurantPerCustomer(String customerId) {
        return orderRepository
                .countAllByCustomerIdGroupByMerchantId(customerId)
                .stream()
                .map(top10RestaurantPerCustomerInterface -> this.modelMapper.map(top10RestaurantPerCustomerInterface, Top10RestaurantPerCustomerDTO.class))
                .collect(Collectors.toList());
    }
}
