package br.com.java.calculatefreight.application.calculationFreight.builders;
import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightRequest;
import br.com.java.calculatefreight.application.company.builders.CompanyEntityBuilder;
import br.com.java.calculatefreight.application.rangeFreight.builders.RangeFreightEntityBuilder;
import br.com.java.calculatefreight.utils.Fuctions;
import java.time.LocalDate;

public class CalculationFreightEntityBuilder {

    private CalculationFreightEntity calculationFreightEntity;

    private CalculationFreightEntityBuilder() {

    }

    public static CalculationFreightEntityBuilder getInstance() {
        final CalculationFreightEntityBuilder builder = new CalculationFreightEntityBuilder();
        builder.calculationFreightEntity = CalculationFreightEntity.builder()
                .id(1L)
                .companyEntity(CompanyEntityBuilder.getInstance().getCompanyEntity())
                .rangeFreightEntity(RangeFreightEntityBuilder.getInstance().getRangeFreightEntity())
                .delivaryDay(LocalDate.now())
                .destinyPostalCode("14000001")
                .width(3.0)
                .height(4.0)
                .length(3.0)
                .cubage(36.0)
                .weight(3.0)
                .freightValue(2.20)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public static CalculationFreightEntityBuilder getInstance(final CalculationFreightRequest calculationFreightRequest) {
        final CalculationFreightEntityBuilder builder = new CalculationFreightEntityBuilder();
        builder.calculationFreightEntity = CalculationFreightEntity.builder()
                .id(1L)
                .companyEntity(CompanyEntityBuilder.getInstance().getCompanyEntity())
                .rangeFreightEntity(RangeFreightEntityBuilder.getInstance().getRangeFreightEntity())
                .delivaryDay(LocalDate.now())
                .destinyPostalCode(calculationFreightRequest.getDestinyPostalCode())
                .width(calculationFreightRequest.getWidth())
                .height(calculationFreightRequest.getHeight())
                .length(calculationFreightRequest.getLength())
                .cubage(calculationFreightRequest.getCubage())
                .weight(calculationFreightRequest.getWeight())
                .freightValue(2.20)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public CalculationFreightEntity getCalculationFreightEntity() {
        return this.calculationFreightEntity;
    }
}
