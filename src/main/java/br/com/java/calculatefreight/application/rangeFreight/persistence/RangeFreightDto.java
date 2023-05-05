package br.com.java.calculatefreight.application.rangeFreight.persistence;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RangeFreightDto {

    private RangeFreightEntity rangeFreightEntity;

    private Double freightValue;

    private RangeFreightDto() {

    }

    public RangeFreightDto(RangeFreightEntity rangeFreightEntity, Double freightValue) {
        this.rangeFreightEntity = rangeFreightEntity;
        this.freightValue = freightValue;
    }

    public static RangeFreightDto getInstance(final RangeFreightEntity rangeFreightEntity, final Double freightValue) {
        return new RangeFreightDto(rangeFreightEntity, freightValue);
    }
}
