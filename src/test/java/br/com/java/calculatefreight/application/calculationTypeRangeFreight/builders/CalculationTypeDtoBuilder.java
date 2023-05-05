package br.com.java.calculatefreight.application.calculationTypeRangeFreight.builders;

import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeDto;

public class CalculationTypeDtoBuilder {

    private CalculationTypeDto calculationTypeDto;

    private CalculationTypeDtoBuilder() {

    }

    public static CalculationTypeDtoBuilder getBasicCalculationTypeDto() {
        final CalculationTypeDtoBuilder builder = new CalculationTypeDtoBuilder();
        builder.calculationTypeDto = CalculationTypeDto.from(2D, 1D);
        return builder;
    }

    public CalculationTypeDto getCalculationTypeDto() {
        return this.calculationTypeDto;
    }
}
