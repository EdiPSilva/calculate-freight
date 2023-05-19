package br.com.java.calculatefreight.application.typeDelivery.builders;
import br.com.java.calculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import br.com.java.calculatefreight.utils.Fuctions;

public class TypeDeliveryEntityBuilder {

    private TypeDeliveryEntity typeDeliveryEntity;

    private TypeDeliveryEntityBuilder() {

    }

    public static TypeDeliveryEntityBuilder getInstance() {
        return getInstance(1l);
    }

    public static TypeDeliveryEntityBuilder getInstance(final Long id) {
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
