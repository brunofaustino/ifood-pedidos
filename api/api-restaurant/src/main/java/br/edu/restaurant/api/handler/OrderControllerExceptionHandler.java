package br.edu.restaurant.api.handler;

import br.edu.restaurant.api.exception.ErrorMessage;
import br.edu.restaurant.api.exception.NoResultCountOrderException;
import br.edu.restaurant.api.exception.NoResultTop10ConsumedRestaurantsPerCostumerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@ResponseBody
public class OrderControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderControllerExceptionHandler.class);

    @ExceptionHandler({
            NoResultCountOrderException.class,
            NoResultTop10ConsumedRestaurantsPerCostumerException.class
    })
    public ErrorMessage noResultException(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        return new ErrorMessage(
            HttpStatus.NO_CONTENT.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );
    }
}