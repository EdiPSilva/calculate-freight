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
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightDto;
import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import br.com.java.calculatefreight.utils.GenericValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CalculationFreightService {


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

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Transactional
    public CalculationFreightResponse create(final CalculationFreightRequest calculationFreightRequest) {
        return CalculationFreightResponse.from(calculate(calculationFreightRequest, null));
    }

    @Transactional
    public CalculationFreightDto calculate(final CalculationFreightRequest calculationFreightRequest, CompanyEntity companyEntity) {
        return calculate(calculationFreightRequest, companyEntity, null);
    }

    @Transactional
    public CalculationFreightDto calculate(final CalculationFreightRequest calculationFreightRequest, CompanyEntity companyEntity, final Long calculationFreightId) {
        companyEntity = getCompanyEntity(calculationFreightRequest.getCompany(), companyEntity);
        final CalculationFreightDto calculationFreightDto = calculate(calculationFreightRequest.getCubage(), calculationFreightRequest.getWeight(), calculationFreightRequest.getDestinyPostalCode());
        calculationFreightDto.setCalculationFreightEntity(save(calculationFreightRequest, calculationFreightDto, companyEntity, calculationFreightId));
        return calculationFreightDto;
    }

    @Transactional
    private CalculationFreightEntity save(final CalculationFreightRequest calculationFreightRequest, final CalculationFreightDto calculationFreightDto, final CompanyEntity companyEntity, final Long calculationFreightId) {
        CalculationFreightEntity calculationFreightEntity = null;
        if (calculationFreightId != null) {
            calculationFreightEntity = calculationFreightRequest.to(getCalculationFreightEntity(calculationFreightId), calculationFreightDto, companyEntity);
        } else {
            calculationFreightEntity = calculationFreightRequest.to(calculationFreightDto, companyEntity);
        }
        return calculationFreightRepository.save(calculationFreightEntity);
    }

    private CalculationFreightEntity getCalculationFreightEntity(final Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<CalculationFreightEntity> optionalCalculationFreightEntity = calculationFreightRepository.findById(id);
        if (optionalCalculationFreightEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(cálculo de frete)"), HttpStatus.NOT_FOUND);
        }
        return optionalCalculationFreightEntity.get();
    }

    private CompanyEntity getCompanyEntity(final Long companyId, CompanyEntity companyEntity) {
        if (companyEntity == null) {
            companyEntity = companyService.getCompanyEntityById(companyId);
        }
        return companyEntity;
    }

    private CalculationFreightDto calculate(final Double cubage, final Double weight, final String destinyPostalCode) {
        genericValidations.validatePostalCode(destinyPostalCode, MessageCodeEnum.INVALID_POST_CODE);
        final CalculationTypeDto calculationTypeDto = CalculationTypeDto.from(cubage, weight);
        final FreightRouteEntity freightRouteEntity = freightRouteService.getFreightRouteEntityByPostalCode(destinyPostalCode);
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = calculationTypeRangeFreightService.getCalculationTypeRangeFreightEntity(calculationTypeDto, freightRouteEntity.getId());
        final RangeFreightDto rangeFreightDto = rangeFreightService.getFreightValue(calculationTypeDto, calculationTypeRangeFreightEntityList);

        final CalculationFreightDto calculationFreightDto = new CalculationFreightDto();
        calculationFreightDto.setTypeDelivery(calculationTypeRangeFreightService.getTypeDelivery(calculationTypeRangeFreightEntityList, rangeFreightDto.getRangeFreightEntity()));
        calculationFreightDto.setDelivaryDay(LocalDate.now().plusDays(freightRouteEntity.getDeliveryDays()));
        calculationFreightDto.setRangeFreightDto(rangeFreightDto);

        return calculationFreightDto;
    }
}
