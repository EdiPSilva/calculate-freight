package br.com.java.calculatefreight.application.calculationFreight;

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
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightDto;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.utils.GenericValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
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

        final CalculationTypeDto calculationTypeDto = CalculationTypeDto.from(calculationFreightRequest.getCubage(), calculationFreightRequest.getWeight());
        final FreightRouteEntity freightRouteEntity = freightRouteService.getFreightRouteEntityByPostalCode(calculationFreightRequest.getDestinyPostalCode());
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = calculationTypeRangeFreightService.getCalculationTypeRangeFreightEntity(calculationTypeDto, freightRouteEntity.getId());
        final RangeFreightDto rangeFreightDto = rangeFreightService.getFreightValue(calculationTypeDto, calculationTypeRangeFreightEntityList);

        final CalculationFreightEntity calculationFreightEntity = calculationFreightRequest.to(rangeFreightDto,
                companyEntity,
                LocalDate.now().plusDays(freightRouteEntity.getDeliveryDays()));

        return CalculationFreightResponse.from(calculationFreightRepository.save(calculationFreightEntity),
                rangeFreightDto.getRangeFreightEntity().getShippingCompanyEntity(),
                calculationTypeRangeFreightService.getTypeDelivery(calculationTypeRangeFreightEntityList, rangeFreightDto.getRangeFreightEntity()));
    }
}
