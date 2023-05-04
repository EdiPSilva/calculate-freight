package br.com.java.calculatefreight.application.calculationFreight;

import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightDto;
import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightRepository;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightRequest;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightResponse;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.CalculationTypeRangeFreightService;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeDto;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.calculatefreight.application.company.CompanyService;
import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.calculatefreight.application.freightRoute.FreightRouteService;
import br.com.java.calculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.calculatefreight.application.rangeFreight.RangeFreightService;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.utils.GenericValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CalculationFreightService {

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private GenericValidations genericValidations;

    @Autowired
    private CalculationFreightRepository calculationFreightRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private FreightRouteService freightRouteService;

    @Autowired
    private CalculationTypeRangeFreightService calculationTypeRangeFreightService;

    @Autowired
    private RangeFreightService rangeFreightService;

    @Transactional
    public CalculationFreightResponse create(final CalculationFreightRequest calculationFreightRequest) {
        final CompanyEntity companyEntity = companyService.getCompanyEntityById(calculationFreightRequest.getCompany());
        genericValidations.validatePostalCode(calculationFreightRequest.getDestinyPostalCode(), MessageCodeEnum.INVALID_POST_CODE);
        final Double cubage = calculateCubage(calculationFreightRequest);

        final CalculationTypeDto calculationTypeDto = CalculationTypeDto.from(cubage, calculationFreightRequest.getWeight());
        final FreightRouteEntity freightRouteEntity = freightRouteService.getFreightRouteEntityByPostalCode(calculationFreightRequest.getDestinyPostalCode());
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = calculationTypeRangeFreightService.getCalculationTypeRangeFreightEntity(calculationTypeDto, freightRouteEntity.getId());
        final RangeFreightEntity rangeFreightEntity = rangeFreightService.getFreightValue(calculationTypeDto, calculationTypeRangeFreightEntityList);

        final CalculationFreightEntity calculationFreightEntity = calculationFreightRequest.to();
        calculationFreightEntity.setFreightValue(calculateFreight(rangeFreightEntity, calculationTypeDto));

        final CalculationFreightDto calculationFreightDto = new CalculationFreightDto();
        calculationFreightDto.setCalculationFreightEntity(calculationFreightEntity);
        calculationFreightDto.setShippingCompanyEntity(rangeFreightEntity.getShippingCompanyEntity());
        calculationFreightDto.setTypeDelivery(calculationTypeRangeFreightService.getTypeDelivery(calculationTypeRangeFreightEntityList, rangeFreightEntity));
        calculationFreightDto.setDeliveryDay(freightRouteEntity.getDeliveryDays());

        return CalculationFreightResponse.from(calculationFreightDto);
    }

    private Double calculateFreight(final RangeFreightEntity rangeFreightEntity, final CalculationTypeDto calculationTypeDto) {
        Double freightValue = rangeFreightEntity.getFreightValue();
        if (calculationTypeDto.getValue() > freightValue) {
            freightValue += (freightValue / 100) * rangeFreightEntity.getSurplusValue();
        }
        return freightValue;
    }

    private Double calculateCubage(final CalculationFreightRequest calculationFreightRequest) {
        return calculationFreightRequest.getWidth() * calculationFreightRequest.getHeight() * calculationFreightRequest.getLength();
    }
}
