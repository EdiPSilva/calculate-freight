package br.com.java.calculatefreight.application.calculationFreight.resources;

import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class CalculationFreightRequest {

    @Min(value = 1L, message = "Valor mínimo é 1")
    @ApiModelProperty(notes = "Empresa", example = "1", required = true)
    private Long company;

    @Size(min = 8, max = 8, message = "Remetente inválido")
    @ApiModelProperty(notes = "Remetente", example = "13052110", required = true)
    private String senderPostalCode;

    @NotBlank
    @Size(min = 8, max = 8, message = "Destinatário inválido")
    @ApiModelProperty(notes = "Destinatário", example = "13052110", required = true)
    private String destinyPostalCode;

    @Min(value = 0, message = "Valor mínimo é 0")
    @ApiModelProperty(notes = "Largura", example = "1", required = true)
    private Double width;

    @Min(value = 0, message = "Valor mínimo é 0")
    @ApiModelProperty(notes = "Altura", example = "1", required = true)
    private Double height;

    @Min(value = 0, message = "Valor mínimo é 0")
    @ApiModelProperty(notes = "Comprimento", example = "1.5", required = true)
    private Double length;

    @Min(value = 0, message = "Valor mínimo é 0")
    @ApiModelProperty(notes = "Peso", example = "0.5", required = true)
    private Double weight;

    public CalculationFreightEntity to() {
        return CalculationFreightEntity.builder()
                .senderPostalCode(this.senderPostalCode)
                .destinyPostalCode(this.destinyPostalCode)
                .width(this.width)
                .height(this.height)
                .length(this.length)
                .weight(this.weight)
                .build();
    }
}
