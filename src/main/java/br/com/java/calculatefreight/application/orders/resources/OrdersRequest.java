package br.com.java.calculatefreight.application.orders.resources;

import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightRequest;
import br.com.java.calculatefreight.application.orders.persistence.OrdersEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
@Builder
public class OrdersRequest {

    @Min(value = 1L, message = "Valor mínimo é 1")
    @ApiModelProperty(notes = "Pedido", example = "1", required = true)
    private String number;

    @ApiModelProperty(notes = "Cálculo de frete", required = true)
    private CalculationFreightRequest calculationFreight;

    public OrdersEntity to() {
        return OrdersEntity.builder()
                .orderNumber(this.number)
                .build();
    }
}
