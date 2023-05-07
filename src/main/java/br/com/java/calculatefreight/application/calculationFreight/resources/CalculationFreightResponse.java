package br.com.java.calculatefreight.application.calculationFreight.resources;

import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
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


    private CalculationFreightResponse() {

    }

    private CalculationFreightResponse(final CalculationFreightEntity calculationFreightEntity, final ShippingCompanyEntity shippingCompanyEntity, final String typeDelivery) {
        this.id = calculationFreightEntity.getId();
        this.destinyPostalCode = calculationFreightEntity.getDestinyPostalCode();
        this.width = calculationFreightEntity.getWidth();
        this.height = calculationFreightEntity.getHeight();
        this.length = calculationFreightEntity.getLength();
        this.cubage = calculationFreightEntity.getCubage();
        this.weight = calculationFreightEntity.getWeight();
        this.freightValue = formatDouble(calculationFreightEntity.getFreightValue());
        this.dateCreate = calculationFreightEntity.getDateCreate();
        setShippingCompanyResponse(shippingCompanyEntity);
        this.typeDelivery = typeDelivery;
        this.deliveryDay = calculationFreightEntity.getDelivaryDay();
    }

    private Double formatDouble(final Double value) {
        return Double.valueOf(String.format("%.2f", value).replace(",", "."));
    }

    private void setShippingCompanyResponse(final ShippingCompanyEntity shippingCompanyEntity) {
        if (shippingCompanyEntity != null) {
            this.shippingCompany = ShippingCompanyResponse.from(shippingCompanyEntity);
        }
    }

    @ApiModelProperty(notes = "Id do cálculo de frete", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Transportadora do frete", example = "1", required = false)
    private ShippingCompanyResponse shippingCompany;

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

    public static CalculationFreightResponse from(final CalculationFreightEntity calculationFreightEntity, final ShippingCompanyEntity shippingCompanyEntity, final String typeDelivery) {
        return new CalculationFreightResponse(calculationFreightEntity, shippingCompanyEntity, typeDelivery);
    }
}
