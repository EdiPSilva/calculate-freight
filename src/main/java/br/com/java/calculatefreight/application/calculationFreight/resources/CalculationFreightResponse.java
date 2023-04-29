package br.com.java.calculatefreight.application.calculationFreight.resources;

import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import br.com.java.calculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import br.com.java.calculatefreight.application.shippingCompany.resources.ShippingCompanyResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculationFreightResponse {

    public CalculationFreightResponse(CalculationFreightEntity calculationFreightEntity) {
        this.id = calculationFreightEntity.getId();
        this.senderPostalCode = calculationFreightEntity.getSenderPostalCode();
        this.destinyPostalCode = calculationFreightEntity.getDestinyPostalCode();
        this.width = calculationFreightEntity.getWidth();
        this.height = calculationFreightEntity.getHeight();
        this.length = calculationFreightEntity.getLength();
        this.cubage = calculationFreightEntity.getCubage();
        this.weight = calculationFreightEntity.getWeight();
        this.freightValue = calculationFreightEntity.getFreightValue();
        this.dateCreate = calculationFreightEntity.getDateCreate();
        this.dateUpdate = calculationFreightEntity.getDateUpdate();
        setShippingCompanyResponse(calculationFreightEntity.getShippingCompanyEntity());
    }

    private void setShippingCompanyResponse(ShippingCompanyEntity shippingCompanyEntity) {
        if (shippingCompanyEntity != null) {
            this.shippingCompany = ShippingCompanyResponse.from(shippingCompanyEntity);
        }
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
    private Long width;

    @ApiModelProperty(notes = "Altura", example = "1", required = true)
    private Long height;

    @ApiModelProperty(notes = "Comprimento", example = "1", required = true)
    private Long length;

    @ApiModelProperty(notes = "Cubagem", example = "1", required = true)
    private Long cubage;

    @ApiModelProperty(notes = "Peso", example = "1", required = true)
    private Double weight;

    @ApiModelProperty(notes = "Valor do frete", example = "1", required = true)
    private Double freightValue;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Data de atualização", example = "dd/MM/yyyy hh:mm:ss", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateUpdate;

    public static CalculationFreightResponse from(final CalculationFreightEntity calculationFreightEntity) {
        return new CalculationFreightResponse(calculationFreightEntity);
    }
}
