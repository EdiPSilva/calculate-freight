package br.com.java.calculatefreight.application.rangeFreight.builders;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.calculatefreight.application.shippingCompany.builders.ShippingCompanyEntityBuilder;
import br.com.java.calculatefreight.utils.Fuctions;

public class RangeFreightEntityBuilder {

    private RangeFreightEntity rangeFreightEntity;

    private RangeFreightEntityBuilder() {

    }

    public static RangeFreightEntityBuilder getInstance() {
        return getInstance(1L);
    }

    public static RangeFreightEntityBuilder getInstance(final Long id) {
        final RangeFreightEntityBuilder builder = new RangeFreightEntityBuilder();
        builder.rangeFreightEntity = RangeFreightEntity.builder()
                .id(id)
                .shippingCompanyEntity(ShippingCompanyEntityBuilder.getInstance().getShippingCompanyEntity())
                .startValue(1.0)
                .endValue(1.0)
                .freightValue(1.0)
                .surplusValue(1.0)
                .active(true)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public RangeFreightEntity getRangeFreightEntity() {
        return this.rangeFreightEntity;
    }
}
