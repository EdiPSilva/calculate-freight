package br.com.java.calculatefreight.application.freightRoute.builders;
import br.com.java.calculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.calculatefreight.application.freightRoute.persistence.StatesEnum;
import br.com.java.calculatefreight.utils.Fuctions;

public class FreightRouteEntityBuilder {

    private FreightRouteEntity freightRouteEntity;

    private FreightRouteEntityBuilder () {

    }

    public static FreightRouteEntityBuilder getInstance() {
        return getInstance(1L);
    }

    public static FreightRouteEntityBuilder getInstance(final Long id) {
        final FreightRouteEntityBuilder builder = new FreightRouteEntityBuilder();
        builder.freightRouteEntity = FreightRouteEntity.builder()
                .id(id)
                .startPostalCode("14000000")
                .endPostalCode("14000020")
                .deliveryDays(10L)
                .devolutionDays(15L)
                .active(true)
                .dateCreate(Fuctions.getCreateDate())
                .stateCode(StatesEnum.SP)
                .build();
        return builder;
    }

    public FreightRouteEntity getFreightRouteEntity() {
        return this.freightRouteEntity;
    }
}
