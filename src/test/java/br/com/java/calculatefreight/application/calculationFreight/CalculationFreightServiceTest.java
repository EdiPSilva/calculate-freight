package br.com.java.calculatefreight.application.calculationFreight;

import br.com.java.calculatefreight.application.calculationFreight.builders.CalculationFreightRequestBuilder;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void shouldCadastreTheCalculationFreightWithoutErrors(){
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
        final CalculationFreightEntity calculationFreightEntity = calculationFreightRequest.to(rangeFreightDto,
                companyEntity,
                LocalDate.now().plusDays(freightRouteEntity.getDeliveryDays()));
        when(calculationFreightRepository.save(Mockito.any())).thenReturn(calculationFreightEntity);
        when(calculationTypeRangeFreightService.getTypeDelivery(calculationTypeRangeFreightEntityList, rangeFreightDto.getRangeFreightEntity())).thenReturn(calculationTypeRangeFreightEntity.getTypeDeliveryEntity().getType());
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
}
