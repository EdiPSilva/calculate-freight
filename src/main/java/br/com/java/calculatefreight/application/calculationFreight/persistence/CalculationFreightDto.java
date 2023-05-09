package br.com.java.calculatefreight.application.calculationFreight.persistence;

import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CalculationFreightDto {

    private CalculationFreightEntity calculationFreightEntity;

    private String typeDelivery;

    private LocalDate delivaryDay;

    private RangeFreightDto rangeFreightDto;


    private CalculationFreightDto(final CalculationFreightEntity calculationFreightEntity, final String typeDelivery) {
        this.calculationFreightEntity = calculationFreightEntity;
        this.typeDelivery = typeDelivery;
    }

    public static CalculationFreightDto from(final CalculationFreightEntity calculationFreightEntity, final String typeDelivery) {
        return new CalculationFreightDto(calculationFreightEntity, typeDelivery);
    }
}
