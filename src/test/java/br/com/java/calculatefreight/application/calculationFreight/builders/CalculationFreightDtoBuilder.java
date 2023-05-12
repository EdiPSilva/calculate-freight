package br.com.java.calculatefreight.application.calculationFreight.builders;

import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightDto;
import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightRequest;
import br.com.java.calculatefreight.application.rangeFreight.builders.RangeFreightDtoBuilder;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightDto;

import java.time.LocalDate;

public class CalculationFreightDtoBuilder {

    private CalculationFreightDto calculationFreightDto;

    private CalculationFreightDtoBuilder() {

    }

    public static CalculationFreightDtoBuilder getInstance() {
        return getInstance(null);
    }

    public static CalculationFreightDtoBuilder getInstance(final CalculationFreightRequest calculationFreightRequest) {
        final CalculationFreightDtoBuilder builder = new CalculationFreightDtoBuilder();
        builder.calculationFreightDto = build(calculationFreightRequest);
        return builder;
    }

    private static CalculationFreightDto build(final CalculationFreightRequest calculationFreightRequest) {
        final CalculationFreightDto calculationFreightDto = new CalculationFreightDto();
        calculationFreightDto.setTypeDelivery("EXPRESS");
        calculationFreightDto.setDelivaryDay(LocalDate.now());
        calculationFreightDto.setRangeFreightDto(RangeFreightDtoBuilder.getInstance().getRangeFreightDto());
        if (calculationFreightRequest != null) {
            calculationFreightDto.setCalculationFreightEntity(CalculationFreightEntityBuilder.getInstance(calculationFreightRequest).getCalculationFreightEntity());
        } else {
            calculationFreightDto.setCalculationFreightEntity(CalculationFreightEntityBuilder.getInstance().getCalculationFreightEntity());
        }
        return calculationFreightDto;
    }

    public CalculationFreightDto getCalculationFreightDto() {
        return this.calculationFreightDto;
    }
}
