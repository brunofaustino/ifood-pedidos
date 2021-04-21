package br.edu.restaurant.api.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Validated
@Data
public class OrderRequestDTO {

    @NotNull(message = "Order date is required.")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date orderDate;

    private String city;

    private String state;
}
