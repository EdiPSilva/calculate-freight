package br.com.java.calculatefreight.application.calculationFreight.resources;

import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightDto;
import br.com.java.calculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import br.com.java.calculatefreight.application.shippingCompany.resources.ShippingCompanyResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculationFreightResponse {

    public CalculationFreightResponse(final CalculationFreightDto calculationFreightDto) {
        this.id = calculationFreightDto.getCalculationFreightEntity().getId();
        this.senderPostalCode = calculationFreightDto.getCalculationFreightEntity().getSenderPostalCode();
        this.destinyPostalCode = calculationFreightDto.getCalculationFreightEntity().getDestinyPostalCode();
        this.width = calculationFreightDto.getCalculationFreightEntity().getWidth();
        this.height = calculationFreightDto.getCalculationFreightEntity().getHeight();
        this.length = calculationFreightDto.getCalculationFreightEntity().getLength();
        this.cubage = calculationFreightDto.getCalculationFreightEntity().getCubage();
        this.weight = calculationFreightDto.getCalculationFreightEntity().getWeight();
        this.freightValue = formatDouble(calculationFreightDto.getCalculationFreightEntity().getFreightValue());
        this.dateCreate = calculationFreightDto.getCalculationFreightEntity().getDateCreate();
        setShippingCompanyResponse(calculationFreightDto.getShippingCompanyEntity());
        this.typeDelivery = calculationFreightDto.getTypeDelivery();
        this.setDeliveryDay(calculationFreightDto.getDeliveryDay());
    }

    private Double formatDouble(final Double value) {
        return Double.valueOf(String.format("%.2f", value).replace(",", "."));
    }

    private void setShippingCompanyResponse(final ShippingCompanyEntity shippingCompanyEntity) {
        if (shippingCompanyEntity != null) {
            this.shippingCompany = ShippingCompanyResponse.from(shippingCompanyEntity);
        }
    }

    private void setDeliveryDay(final Long days) {
        this.deliveryDay = LocalDate.now().plusDays(days);
    }

    @ApiModelProperty(notes = "Id do cálculo de frete", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Transportadora do frete", example = "1", required = false)
    private ShippingCompanyResponse shippingCompany;

    @ApiModelProperty(notes = "Remetente cep", example = "1", required = true)
    private String senderPostalCode;

    @ApiModelProperty(notes = "Destino cep", example = "1", required = true)
    private String destinyPostalCode;

    @ApiModelProperty(notes = "Largura", example = "1", required = true)
    private Double width;

    @ApiModelProperty(notes = "Altura", example = "1", required = true)
    private Double height;

    @ApiModelProperty(notes = "Comprimento", example = "1", required = true)
    private Double length;

    @ApiModelProperty(notes = "Cubagem", example = "1", required = true)
    private Double cubage;

    @ApiModelProperty(notes = "Peso", example = "1", required = true)
    private Double weight;

    @ApiModelProperty(notes = "Valor do frete", example = "1", required = true)
    private Double freightValue;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Tipo de entrega", example = "EXPRESS", required = true)
    private String typeDelivery;

    @ApiModelProperty(notes = "Data de entrega", example = "dd/MM/yyyy", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate deliveryDay;

    public static CalculationFreightResponse from(final CalculationFreightDto calculationFreightDto) {
        return new CalculationFreightResponse(calculationFreightDto);
    }
}
