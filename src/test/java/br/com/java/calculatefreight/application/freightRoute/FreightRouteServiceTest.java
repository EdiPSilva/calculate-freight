package br.com.java.calculatefreight.application.freightRoute;

import br.com.java.calculatefreight.application.freightRoute.builders.FreightRouteEntityBuilder;
import br.com.java.calculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.calculatefreight.application.freightRoute.persistence.FreightRouteRepository;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FreightRouteServiceTest {

    @InjectMocks
    private FreightRouteService freightRouteService;

    @Mock
    private MessageConfiguration messageConfiguration;

    @Mock
    private FreightRouteRepository freightRouteRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado a rota de frete por CEP")
    public void shouldReturnErrorWhenNotFoundFreightRouteByPostalCode(){
        final String postalCode = "14000000";
        final FreightRouteEntity freightRouteEntity = null;
        when(freightRouteRepository.findFreightRouteEntityByPostalCode(postalCode)).thenReturn(freightRouteEntity);
        assertThrows(CustomException.class, () -> freightRouteService.getFreightRouteEntityByPostalCode(postalCode));
    }

    @Test
    @DisplayName("Não deve retornar erro quando for localizado a rota de frete por CEP")
    public void shouldNotReturnErrorWhenFoundFreightRouteByPostalCode(){
        final String postalCode = "14000000";
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity().getFreightRouteEntity();
        when(freightRouteRepository.findFreightRouteEntityByPostalCode(postalCode)).thenReturn(freightRouteEntity);
        compare(freightRouteEntity, assertDoesNotThrow(() -> freightRouteService.getFreightRouteEntityByPostalCode(postalCode)));
    }

    private void compare(final FreightRouteEntity freightRouteEntityMock, final FreightRouteEntity freightRouteEntityResult) {
        assertNotNull(freightRouteEntityMock);
        assertNotNull(freightRouteEntityResult);
        assertEquals(freightRouteEntityMock.getId(), freightRouteEntityResult.getId());
        assertEquals(freightRouteEntityMock.getStartPostalCode(), freightRouteEntityResult.getStartPostalCode());
        assertEquals(freightRouteEntityMock.getEndPostalCode(), freightRouteEntityResult.getEndPostalCode());
        assertEquals(freightRouteEntityMock.getDeliveryDays(), freightRouteEntityResult.getDeliveryDays());
        assertEquals(freightRouteEntityMock.getDevolutionDays(), freightRouteEntityResult.getDevolutionDays());
        assertEquals(freightRouteEntityMock.getActive(), freightRouteEntityResult.getActive());
        assertEquals(freightRouteEntityMock.getDateCreate(), freightRouteEntityResult.getDateCreate());
    }
}
