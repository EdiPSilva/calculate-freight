package br.com.java.calculatefreight.application.calculationTypeRangeFreight;

import br.com.java.calculatefreight.application.calculationTypeRangeFreight.builders.CalculationTypeDtoBuilder;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.builders.CalculationTypeRangeFreightEntityBuilder;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeDto;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightRepository;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CalculationTypeRangeFreightServiceTeste {

    @InjectMocks
    private CalculationTypeRangeFreightService calculationTypeRangeFreightService;

    @Mock
    private MessageConfiguration messageConfiguration;

    @Mock
    private CalculationTypeRangeFreightRepository calculationTypeRangeFreightRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado os registros")
    public void shouldReturnErrorWhenNotFoundTheRegistris(){
        final CalculationTypeDto calculationTypeDto = CalculationTypeDtoBuilder.getBasicCalculationTypeDto().getCalculationTypeDto();
        final Long idFreightRoute = 1L;
        final Pageable pageable = PageRequest.of(0, 10);
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = new ArrayList<>();
        when(calculationTypeRangeFreightRepository.findAlreadyExistsCalculationTypeRangeFreight(calculationTypeDto.getCalculationTypeEnum(), idFreightRoute, pageable)).thenReturn(calculationTypeRangeFreightEntityList);
        assertThrows(CustomException.class, () -> calculationTypeRangeFreightService.getCalculationTypeRangeFreightEntity(calculationTypeDto, idFreightRoute));
    }

    @Test
    @DisplayName("Não deve retornar erro quando for localizado os registros")
    public void shouldNotReturnErrorWhenFoundTheRegistris(){
        final CalculationTypeDto calculationTypeDto = CalculationTypeDtoBuilder.getBasicCalculationTypeDto().getCalculationTypeDto();
        final Long idFreightRoute = 1L;
        final Pageable pageable = PageRequest.of(0, 10);
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        when(calculationTypeRangeFreightRepository.findAlreadyExistsCalculationTypeRangeFreight(calculationTypeDto.getCalculationTypeEnum(), idFreightRoute, pageable)).thenReturn(calculationTypeRangeFreightEntityList);
        compare(calculationTypeRangeFreightEntityList, assertDoesNotThrow(() -> calculationTypeRangeFreightService.getCalculationTypeRangeFreightEntity(calculationTypeDto, idFreightRoute)));
    }

    private void compare(final List<CalculationTypeRangeFreightEntity> listMock, final List<CalculationTypeRangeFreightEntity> listResult) {
        assertNotNull(listMock);
        assertNotNull(listResult);
        assertFalse(listMock.isEmpty());
        assertFalse(listResult.isEmpty());

        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightMock = listMock.get(0);
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightResult = listResult.get(0);

        assertEquals(calculationTypeRangeFreightMock.getId(), calculationTypeRangeFreightResult.getId());
        assertEquals(calculationTypeRangeFreightMock.getCalculationType(), calculationTypeRangeFreightResult.getCalculationType());
        assertEquals(calculationTypeRangeFreightMock.getRangeFreightEntity().getId(), calculationTypeRangeFreightResult.getRangeFreightEntity().getId());
        assertEquals(calculationTypeRangeFreightMock.getFreightRouteEntity().getId(), calculationTypeRangeFreightResult.getFreightRouteEntity().getId());
        assertEquals(calculationTypeRangeFreightMock.getTypeDeliveryEntity().getId(), calculationTypeRangeFreightResult.getTypeDeliveryEntity().getId());
        assertEquals(calculationTypeRangeFreightMock.getDateCreate(), calculationTypeRangeFreightResult.getDateCreate());
    }

    @Test
    @DisplayName("deve retornar null quando não localizado tipo de entrega")
    public void shouldReturnNullWhenNotFoundTypeDelivery(){
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        calculationTypeRangeFreightEntity.setTypeDeliveryEntity(null);
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        final String typeDelivery = calculationTypeRangeFreightService.getTypeDelivery(calculationTypeRangeFreightEntityList, calculationTypeRangeFreightEntity.getRangeFreightEntity());
        assertNull(typeDelivery);
    }

    @Test
    @DisplayName("não deve retornar null quando localizado tipo de entrega")
    public void shouldNotReturnNullWhenFoundTypeDelivery(){
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        final String typeDelivery = calculationTypeRangeFreightService.getTypeDelivery(calculationTypeRangeFreightEntityList, calculationTypeRangeFreightEntity.getRangeFreightEntity());
        assertNotNull(typeDelivery);
        assertEquals(typeDelivery, calculationTypeRangeFreightEntity.getTypeDeliveryEntity().getType());
    }
}
