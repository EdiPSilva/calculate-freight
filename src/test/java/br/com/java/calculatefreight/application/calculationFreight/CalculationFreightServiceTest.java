package br.com.java.calculatefreight.application.calculationFreight;

import br.com.java.calculatefreight.application.calculationFreight.builders.CalculationFreightEntityBuilder;
import br.com.java.calculatefreight.application.calculationFreight.builders.CalculationFreightRequestBuilder;
import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightDto;
import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightRepository;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightRequest;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightResponse;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.CalculationTypeRangeFreightService;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.builders.CalculationTypeRangeFreightEntityBuilder;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeDto;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.calculatefreight.application.company.CompanyService;
import br.com.java.calculatefreight.application.company.builders.CompanyEntityBuilder;
import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.calculatefreight.application.freightRoute.FreightRouteService;
import br.com.java.calculatefreight.application.freightRoute.builders.FreightRouteEntityBuilder;
import br.com.java.calculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.calculatefreight.application.rangeFreight.RangeFreightService;
import br.com.java.calculatefreight.application.rangeFreight.builders.RangeFreightDtoBuilder;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightDto;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import br.com.java.calculatefreight.utils.GenericValidations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculationFreightServiceTest {

    @InjectMocks
    private CalculationFreightService calculationFreightService;

    @Mock
    private MessageConfiguration messageConfiguration;

    @Mock
    private GenericValidations genericValidations;

    @Mock
    private CalculationFreightRepository calculationFreightRepository;

    @Mock
    private CompanyService companyService;

    @Mock
    private FreightRouteService freightRouteService;

    @Mock
    private CalculationTypeRangeFreightService calculationTypeRangeFreightService;

    @Mock
    private RangeFreightService rangeFreightService;

    @Test
    @DisplayName("Deve cadastrar o calculo de frete sem erros")
    public void shouldCadastreTheCalculationFreightWithoutErrors() {
        final CalculationFreightRequest calculationFreightRequest = CalculationFreightRequestBuilder.getInstance().getCalculationFreightRequest();
        final CompanyEntity companyEntity = CompanyEntityBuilder.getBasicCompanyEntity().getCompanyEntity();
        when(companyService.getCompanyEntityById(calculationFreightRequest.getCompany())).thenReturn(companyEntity);

        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity().getFreightRouteEntity();
        when(freightRouteService.getFreightRouteEntityByPostalCode(calculationFreightRequest.getDestinyPostalCode())).thenReturn(freightRouteEntity);

        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        when(calculationTypeRangeFreightService.getCalculationTypeRangeFreightEntity(Mockito.any(), Mockito.anyLong())).thenReturn(calculationTypeRangeFreightEntityList);

        final RangeFreightDto rangeFreightDto = RangeFreightDtoBuilder.getInstance().getRangeFreightDto();
        when(rangeFreightService.getFreightValue(Mockito.any(), Mockito.any())).thenReturn(rangeFreightDto);

        final String typeDelivery = calculationTypeRangeFreightEntity.getTypeDeliveryEntity().getType();
        when(calculationTypeRangeFreightService.getTypeDelivery(calculationTypeRangeFreightEntityList, rangeFreightDto.getRangeFreightEntity())).thenReturn(typeDelivery);

        final CalculationFreightDto calculationFreightDto = setCalculationFreightDto(typeDelivery, rangeFreightDto, freightRouteEntity.getDeliveryDays());
        final CalculationFreightEntity calculationFreightEntity = calculationFreightRequest.to(calculationFreightDto, companyEntity);
        calculationFreightEntity.setId(1L);
        when(calculationFreightRepository.save(Mockito.any())).thenReturn(calculationFreightEntity);

        final CalculationFreightResponse calculationFreightResponse = calculationFreightService.create(calculationFreightRequest);

        assertNotNull(calculationFreightResponse);
        assertEquals(calculationFreightResponse.getShippingCompany().getId(), rangeFreightDto.getRangeFreightEntity().getShippingCompanyEntity().getId());
        assertEquals(calculationFreightResponse.getDestinyPostalCode(), calculationFreightRequest.getDestinyPostalCode());
        assertEquals(calculationFreightResponse.getWidth(), calculationFreightRequest.getWidth());
        assertEquals(calculationFreightResponse.getHeight(), calculationFreightRequest.getHeight());
        assertEquals(calculationFreightResponse.getLength(), calculationFreightRequest.getLength());
        assertEquals(calculationFreightResponse.getWeight(), calculationFreightRequest.getWeight());
        assertEquals(calculationFreightResponse.getCubage(), calculationFreightRequest.getCubage());
        assertEquals(calculationFreightResponse.getFreightValue(), rangeFreightDto.getFreightValue());
        assertEquals(calculationFreightResponse.getDeliveryDay(), calculationFreightEntity.getDelivaryDay());
        assertEquals(calculationFreightResponse.getTypeDelivery(), calculationTypeRangeFreightEntity.getTypeDeliveryEntity().getType());
    }

    private CalculationFreightDto setCalculationFreightDto(final String typeDelivery, final RangeFreightDto rangeFreightDto, final Long deliveryDays) {
        final CalculationFreightDto calculationFreightDto = new CalculationFreightDto();
        calculationFreightDto.setTypeDelivery(typeDelivery);
        calculationFreightDto.setDelivaryDay(LocalDate.now().plusDays(deliveryDays));
        calculationFreightDto.setRangeFreightDto(rangeFreightDto);
        return calculationFreightDto;
    }

    @Test
    @DisplayName("Deve atualizar o calculo de frete sem erros")
    public void shouldUpdateTheCalculationFreightWithoutErrors() {
        final CalculationFreightRequest calculationFreightRequest = CalculationFreightRequestBuilder.getInstance().getCalculationFreightRequest();
        final CompanyEntity companyEntity = CompanyEntityBuilder.getBasicCompanyEntity().getCompanyEntity();
        final Long calculationFreightEntityId = 1L;

        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity().getFreightRouteEntity();
        when(freightRouteService.getFreightRouteEntityByPostalCode(calculationFreightRequest.getDestinyPostalCode())).thenReturn(freightRouteEntity);

        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        when(calculationTypeRangeFreightService.getCalculationTypeRangeFreightEntity(Mockito.any(), Mockito.anyLong())).thenReturn(calculationTypeRangeFreightEntityList);

        final RangeFreightDto rangeFreightDto = RangeFreightDtoBuilder.getInstance().getRangeFreightDto();
        when(rangeFreightService.getFreightValue(Mockito.any(), Mockito.any())).thenReturn(rangeFreightDto);

        final String typeDelivery = calculationTypeRangeFreightEntity.getTypeDeliveryEntity().getType();
        when(calculationTypeRangeFreightService.getTypeDelivery(calculationTypeRangeFreightEntityList, rangeFreightDto.getRangeFreightEntity())).thenReturn(typeDelivery);

        final CalculationFreightEntity calculationFreightEntity = CalculationFreightEntityBuilder.getInstance().getCalculationFreightEntity();
        final Optional<CalculationFreightEntity> optionalCalculationFreightEntity = Optional.of(calculationFreightEntity);
        when(calculationFreightRepository.findById(calculationFreightEntityId)).thenReturn(optionalCalculationFreightEntity);

        when(calculationFreightRepository.save(Mockito.any())).thenReturn(calculationFreightEntity);

        final CalculationFreightDto calculationFreightDto = calculationFreightService.calculate(calculationFreightRequest, companyEntity, calculationFreightEntityId);
        assertNotNull(calculationFreightDto);
        assertEquals(calculationFreightDto.getCalculationFreightEntity().getRangeFreightEntity().getShippingCompanyEntity().getId(), rangeFreightDto.getRangeFreightEntity().getShippingCompanyEntity().getId());
        assertEquals(calculationFreightDto.getCalculationFreightEntity().getDestinyPostalCode(), calculationFreightRequest.getDestinyPostalCode());
        assertEquals(calculationFreightDto.getCalculationFreightEntity().getWidth(), calculationFreightRequest.getWidth());
        assertEquals(calculationFreightDto.getCalculationFreightEntity().getHeight(), calculationFreightRequest.getHeight());
        assertEquals(calculationFreightDto.getCalculationFreightEntity().getLength(), calculationFreightRequest.getLength());
        assertEquals(calculationFreightDto.getCalculationFreightEntity().getWeight(), calculationFreightRequest.getWeight());
        assertEquals(calculationFreightDto.getCalculationFreightEntity().getCubage(), calculationFreightRequest.getCubage());
        assertEquals(calculationFreightDto.getCalculationFreightEntity().getFreightValue(), rangeFreightDto.getFreightValue());
        assertEquals(calculationFreightDto.getCalculationFreightEntity().getDelivaryDay(), calculationFreightEntity.getDelivaryDay());
        assertEquals(calculationFreightDto.getTypeDelivery(), calculationTypeRangeFreightEntity.getTypeDeliveryEntity().getType());
    }

    @Test
    @DisplayName("Deve retornar erro ao atualizar quando o calculo de frete nao for encontrado")
    public void shouldReturnErroTheUpdateWhenTheCalculationFreightNotFound() {
        final CalculationFreightRequest calculationFreightRequest = CalculationFreightRequestBuilder.getInstance().getCalculationFreightRequest();
        final CompanyEntity companyEntity = CompanyEntityBuilder.getBasicCompanyEntity().getCompanyEntity();
        final Long calculationFreightEntityId = 1L;

        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity().getFreightRouteEntity();
        when(freightRouteService.getFreightRouteEntityByPostalCode(calculationFreightRequest.getDestinyPostalCode())).thenReturn(freightRouteEntity);

        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        when(calculationTypeRangeFreightService.getCalculationTypeRangeFreightEntity(Mockito.any(), Mockito.anyLong())).thenReturn(calculationTypeRangeFreightEntityList);

        final RangeFreightDto rangeFreightDto = RangeFreightDtoBuilder.getInstance().getRangeFreightDto();
        when(rangeFreightService.getFreightValue(Mockito.any(), Mockito.any())).thenReturn(rangeFreightDto);

        final String typeDelivery = calculationTypeRangeFreightEntity.getTypeDeliveryEntity().getType();
        when(calculationTypeRangeFreightService.getTypeDelivery(calculationTypeRangeFreightEntityList, rangeFreightDto.getRangeFreightEntity())).thenReturn(typeDelivery);

        final Optional<CalculationFreightEntity> optionalCalculationFreightEntity = Optional.empty();
        when(calculationFreightRepository.findById(calculationFreightEntityId)).thenReturn(optionalCalculationFreightEntity);

        assertThrows(CustomException.class, () -> calculationFreightService.calculate(calculationFreightRequest, companyEntity, calculationFreightEntityId));
    }
}
