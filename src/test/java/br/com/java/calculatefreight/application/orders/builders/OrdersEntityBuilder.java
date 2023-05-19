package br.com.java.calculatefreight.application.orders.builders;
import br.com.java.calculatefreight.application.calculationFreight.builders.CalculationFreightEntityBuilder;
import br.com.java.calculatefreight.application.company.builders.CompanyEntityBuilder;
import br.com.java.calculatefreight.application.orders.persistence.OrdersEntity;
import br.com.java.calculatefreight.utils.Fuctions;

public class OrdersEntityBuilder {

    private OrdersEntity ordersEntity;

    private OrdersEntityBuilder() {

    }

    public static OrdersEntityBuilder getInstance() {
        final OrdersEntityBuilder builder = new OrdersEntityBuilder();
        builder.ordersEntity = OrdersEntity.builder()
                .id(1L)
                .companyEntity(CompanyEntityBuilder.getInstance().getCompanyEntity())
                .calculationFreightEntity(CalculationFreightEntityBuilder.getInstance().getCalculationFreightEntity())
                .orderNumber("123")
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public OrdersEntity getOrdersEntity() {
        return this.ordersEntity;
    }
}
