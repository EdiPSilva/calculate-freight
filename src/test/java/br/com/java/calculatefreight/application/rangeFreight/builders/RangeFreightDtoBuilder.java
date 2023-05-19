package br.com.java.calculatefreight.application.rangeFreight.builders;

import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightDto;

public class RangeFreightDtoBuilder {
    private RangeFreightDto rangeFreightDto;

    private RangeFreightDtoBuilder() {

    }

    public static RangeFreightDtoBuilder getInstance() {
        final RangeFreightDtoBuilder builder = new RangeFreightDtoBuilder();
        builder.rangeFreightDto = RangeFreightDto.getInstance(RangeFreightEntityBuilder.getInstance().getRangeFreightEntity(), 0.1);
        return builder;
    }

    public RangeFreightDto getRangeFreightDto() {
        return this.rangeFreightDto;
    }
}
