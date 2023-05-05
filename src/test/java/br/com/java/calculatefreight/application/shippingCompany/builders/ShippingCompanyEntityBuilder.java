package br.com.java.calculatefreight.application.shippingCompany.builders;

import br.com.java.calculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import br.com.java.calculatefreight.utils.Fuctions;

public class ShippingCompanyEntityBuilder {

    private ShippingCompanyEntity shippingCompanyEntity;

    private ShippingCompanyEntityBuilder() {

    }

    public static ShippingCompanyEntityBuilder getBasicShippingCompanyEntity() {
        return getBasicShippingCompanyEntity(1l);
    }

    public static ShippingCompanyEntityBuilder getBasicShippingCompanyEntity(final Long id) {
        final ShippingCompanyEntityBuilder builder = new ShippingCompanyEntityBuilder();
        builder.shippingCompanyEntity = ShippingCompanyEntity.builder()
                .id(id)
                .name("JP Log ME")
                .document("88449952000138")
                .active(true)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public ShippingCompanyEntity getShippingCompanyEntity() {
        return this.shippingCompanyEntity;
    }
}
