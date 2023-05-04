package br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence;

import lombok.Getter;

@Getter
public class CalculationTypeDto {

    private CalculationTypeDto() {

    }

    private CalculationTypeDto(final CalculationTypeEnum calculationTypeEnum, final Double value) {
        this.calculationTypeEnum = calculationTypeEnum;
        this.value = value;
    }

    private CalculationTypeEnum calculationTypeEnum;

    private Double value;

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
