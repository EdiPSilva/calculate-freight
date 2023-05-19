package br.com.java.calculatefreight.application.calculationFreight.builders;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightRequest;

public class CalculationFreightRequestBuilder {

    private CalculationFreightRequest calculationFreightRequest;

    private CalculationFreightRequestBuilder() {

    }

    public static CalculationFreightRequestBuilder getInstance() {
        final CalculationFreightRequestBuilder builder = new CalculationFreightRequestBuilder();
        builder.calculationFreightRequest = CalculationFreightRequest.builder()
                .company(1L)
                .destinyPostalCode("14000001")
                .width(1.0)
                .height(1.0)
                .length(1.5)
                .weight(0.5)
                .build();
        return builder;
    }

    public CalculationFreightRequest getCalculationFreightRequest() {
        return this.calculationFreightRequest;
    }
}
