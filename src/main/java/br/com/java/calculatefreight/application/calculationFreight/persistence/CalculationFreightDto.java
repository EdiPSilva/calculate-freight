package br.com.java.calculatefreight.application.calculationFreight.persistence;

import br.com.java.calculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculationFreightDto {

    private CalculationFreightEntity calculationFreightEntity;
    private ShippingCompanyEntity shippingCompanyEntity;
    private String typeDelivery;
    private Long deliveryDay;

}
