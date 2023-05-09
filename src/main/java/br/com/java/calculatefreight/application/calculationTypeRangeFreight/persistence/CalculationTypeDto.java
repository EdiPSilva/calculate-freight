package br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CalculationTypeDto {

    private CalculationTypeDto() {

    }

    private CalculationTypeDto(final CalculationTypeEnum calculationTypeEnum, final Double value) {
        this.calculationTypeEnum = calculationTypeEnum;
        this.value = value;
    }

    private CalculationTypeEnum calculationTypeEnum;

    private Double value;

    private List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList;

    private String typeDelivery;

    public static CalculationTypeDto from(final Double cubage, final Double weight) {
        Double bigger = cubage;
        CalculationTypeEnum calculationTypeEnum = CalculationTypeEnum.CUBAGE;
        if (weight > cubage) {
            bigger = weight;
            calculationTypeEnum = CalculationTypeEnum.WEIGHT;
        }
        return new CalculationTypeDto(calculationTypeEnum, bigger);
    }
}
