package br.com.java.calculatefreight.application.orders.resources;

import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightDto;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightResponse;
import br.com.java.calculatefreight.application.company.resources.CompanyResponse;
import br.com.java.calculatefreight.application.orders.persistence.OrdersEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdersResponse {

    @ApiModelProperty(notes = "Id do cálculo de frete", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Empresa", required = true)
    private CompanyResponse company;

    @ApiModelProperty(notes = "Cálculo de frete", required = true)
    private CalculationFreightResponse calculationFreight;

    @ApiModelProperty(notes = "Pedido", example = "15", required = true)
    private String number;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Data de atualização", example = "dd/MM/yyyy hh:mm:ss", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateUpdate;

    private OrdersResponse(final OrdersEntity ordersEntity, final CalculationFreightDto calculationFreightDto) {
        this.id = ordersEntity.getId();
        this.number = ordersEntity.getOrderNumber();
        this.dateCreate = ordersEntity.getDateCreate();
        this.dateUpdate = ordersEntity.getDateUpdate();
        this.company = CompanyResponse.from(ordersEntity.getCompanyEntity());
        this.calculationFreight = CalculationFreightResponse.from(calculationFreightDto);
    }
    public static OrdersResponse from(final OrdersEntity ordersEntity, final CalculationFreightDto calculationFreightDto) {
        return new OrdersResponse(ordersEntity, calculationFreightDto);
    }
}
