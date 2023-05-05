package br.com.java.calculatefreight.application.typeDelivery.builders;


import br.com.java.calculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import br.com.java.calculatefreight.utils.Fuctions;

import java.time.LocalDateTime;

public class TypeDeliveryEntityBuilder {

    private TypeDeliveryEntity typeDeliveryEntity;

    private TypeDeliveryEntityBuilder() {

    }

    public static TypeDeliveryEntityBuilder getBasicTypeDeliveryEntity() {
        return getBasicTypeDeliveryEntity(1l);
    }

    public static TypeDeliveryEntityBuilder getBasicTypeDeliveryEntity(final Long id) {
        final TypeDeliveryEntityBuilder builder = new TypeDeliveryEntityBuilder();
        builder.typeDeliveryEntity = TypeDeliveryEntity.builder()
                .id(id)
                .type("EXPRESS")
                .active(true)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public TypeDeliveryEntity getTypeDeliveryEntity() {
        return this.typeDeliveryEntity;
    }

}
