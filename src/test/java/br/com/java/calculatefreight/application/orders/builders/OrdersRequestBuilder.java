package br.com.java.calculatefreight.application.orders.builders;
import br.com.java.calculatefreight.application.calculationFreight.builders.CalculationFreightRequestBuilder;
import br.com.java.calculatefreight.application.orders.resources.OrdersRequest;

public class OrdersRequestBuilder {

    private OrdersRequest ordersRequest;

    private OrdersRequestBuilder() {

    }

    public static OrdersRequestBuilder getInstance() {
        final OrdersRequestBuilder builder = new OrdersRequestBuilder();
        builder.ordersRequest = OrdersRequest.builder()
                .number("123")
                .calculationFreight(CalculationFreightRequestBuilder.getInstance().getCalculationFreightRequest())
                .build();
        return builder;
    }

    public OrdersRequest getOrdersRequest() {
        return this.ordersRequest;
    }
}
