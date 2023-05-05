package br.com.java.calculatefreight.application.calculationTypeRangeFreight.builders;

import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeEnum;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.calculatefreight.application.freightRoute.builders.FreightRouteEntityBuilder;
import br.com.java.calculatefreight.application.rangeFreight.builders.RangeFreightEntityBuilder;
import br.com.java.calculatefreight.application.typeDelivery.builders.TypeDeliveryEntityBuilder;
import br.com.java.calculatefreight.utils.Fuctions;

public class CalculationTypeRangeFreightEntityBuilder {

    private CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity;

    private CalculationTypeRangeFreightEntityBuilder() {

    }

    public static CalculationTypeRangeFreightEntityBuilder getInstance() {
        return getInstance(1L);
    }

    public static CalculationTypeRangeFreightEntityBuilder getInstance(final Long id) {
        final CalculationTypeRangeFreightEntityBuilder builder = new CalculationTypeRangeFreightEntityBuilder();
        builder.calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntity.builder()
                .id(id)
                .calculationType(CalculationTypeEnum.CUBAGE)
                .rangeFreightEntity(RangeFreightEntityBuilder.getBasicRangeFreightEntity().getRangeFreightEntity())
                .typeDeliveryEntity(TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity().getTypeDeliveryEntity())
                .freightRouteEntity(FreightRouteEntityBuilder.getBasicFreightRouteEntity().getFreightRouteEntity())
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public CalculationTypeRangeFreightEntity getCalculationTypeRangeFreightEntity() {
        return this.calculationTypeRangeFreightEntity;
    }
}
