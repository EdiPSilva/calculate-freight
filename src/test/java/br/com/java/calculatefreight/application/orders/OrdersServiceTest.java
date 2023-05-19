package br.com.java.calculatefreight.application.orders;
import br.com.java.calculatefreight.application.calculationFreight.CalculationFreightService;
import br.com.java.calculatefreight.application.calculationFreight.builders.CalculationFreightDtoBuilder;
import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightDto;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightRequest;
import br.com.java.calculatefreight.application.company.CompanyService;
import br.com.java.calculatefreight.application.company.builders.CompanyEntityBuilder;
import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.calculatefreight.application.orders.builders.OrdersEntityBuilder;
import br.com.java.calculatefreight.application.orders.builders.OrdersRequestBuilder;
import br.com.java.calculatefreight.application.orders.persistence.OrdersEntity;
import br.com.java.calculatefreight.application.orders.persistence.OrdersRepository;
import br.com.java.calculatefreight.application.orders.resources.OrdersRequest;
import br.com.java.calculatefreight.application.orders.resources.OrdersResponse;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private MessageConfiguration messageConfiguration;

    @Mock
    private CompanyService companyService;

    @Mock
    private CalculationFreightService calculationFreightService;

    @Test
    @DisplayName("Deve retornar erro quando o pedido estiver invalido")
    public void shouldReturnErrorWhenTheOrderNumberWasInvalid(){
        final OrdersRequest ordersRequest = OrdersRequestBuilder.getInstance().getOrdersRequest();

        final CompanyEntity companyEntity = CompanyEntityBuilder.getInstance().getCompanyEntity();
        when(companyService.getCompanyEntityById(ordersRequest.getCalculationFreight().getCompany())).thenReturn(companyEntity);

        final OrdersEntity ordersEntity = OrdersEntityBuilder.getInstance().getOrdersEntity();
        when(ordersRepository.findOrdersByNumberAndCompany(ordersRequest.getNumber(), companyEntity.getId())).thenReturn(ordersEntity);
        assertThrows(CustomException.class, () -> ordersService.create(ordersRequest));

        when(ordersRepository.findOrdersByNumberAndCompany(ordersRequest.getNumber(), companyEntity.getId())).thenReturn(null);
        assertThrows(CustomException.class, () -> ordersService.update(ordersRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o pedido for valido no cadastro")
    public void shouldNotReturnErrorWhenTheOrderWasValidInTheCreate(){
        final OrdersRequest ordersRequest = OrdersRequestBuilder.getInstance().getOrdersRequest();

        final CompanyEntity companyEntity = CompanyEntityBuilder.getInstance().getCompanyEntity();
        when(companyService.getCompanyEntityById(ordersRequest.getCalculationFreight().getCompany())).thenReturn(companyEntity);

        CalculationFreightDto calculationFreightDto = CalculationFreightDtoBuilder.getInstance().getCalculationFreightDto();
        when(calculationFreightService.calculate(ordersRequest.getCalculationFreight(), companyEntity)).thenReturn(calculationFreightDto);

        final OrdersEntity ordersEntity = OrdersEntityBuilder.getInstance().getOrdersEntity();
        when(ordersRepository.save(Mockito.any())).thenReturn(ordersEntity);

        calculationFreightDto = CalculationFreightDtoBuilder.getInstance(ordersRequest.getCalculationFreight()).getCalculationFreightDto();
        when(calculationFreightService.calculate(ordersRequest.getCalculationFreight(), companyEntity)).thenReturn(calculationFreightDto);

        final OrdersResponse ordersResponse = assertDoesNotThrow(() -> ordersService.create(ordersRequest));
        compare(ordersResponse, ordersEntity, companyEntity, calculationFreightDto, ordersRequest);
    }

    private void compare(final OrdersResponse ordersResponse, final OrdersEntity ordersEntity, final CompanyEntity companyEntity, final CalculationFreightDto calculationFreightDto, final OrdersRequest ordersRequest) {
        assertNotNull(ordersResponse);
        assertEquals(ordersResponse.getId(), ordersEntity.getId());
        assertEquals(ordersResponse.getCompany().getId(), companyEntity.getId());
        assertEquals(ordersResponse.getCalculationFreight().getId(), calculationFreightDto.getCalculationFreightEntity().getId());
        assertEquals(ordersResponse.getCalculationFreight().getDestinyPostalCode(), ordersRequest.getCalculationFreight().getDestinyPostalCode());
        assertEquals(ordersResponse.getCalculationFreight().getWidth(), calculationFreightDto.getCalculationFreightEntity().getWidth());
        assertEquals(ordersResponse.getCalculationFreight().getHeight(), calculationFreightDto.getCalculationFreightEntity().getHeight());
        assertEquals(ordersResponse.getCalculationFreight().getLength(), calculationFreightDto.getCalculationFreightEntity().getLength());
        assertEquals(ordersResponse.getCalculationFreight().getCubage(), getCubage(ordersRequest.getCalculationFreight()));
        assertEquals(ordersResponse.getCalculationFreight().getWeight(), calculationFreightDto.getCalculationFreightEntity().getWeight());
        assertEquals(ordersResponse.getCalculationFreight().getFreightValue(), calculationFreightDto.getCalculationFreightEntity().getFreightValue());
        assertEquals(ordersResponse.getCalculationFreight().getTypeDelivery(), calculationFreightDto.getTypeDelivery());
        assertEquals(ordersResponse.getCalculationFreight().getDeliveryDay(), calculationFreightDto.getDelivaryDay());
        assertEquals(ordersResponse.getNumber(), ordersRequest.getNumber());
        assertEquals(ordersResponse.getDateCreate(), ordersEntity.getDateCreate());
    }

    private Double getCubage(final CalculationFreightRequest calculationFreight) {
        return calculationFreight.getWidth() * calculationFreight.getHeight() * calculationFreight.getLength();
    }

    @Test
    @DisplayName("Não deve retornar erro quando o pedido for valido na atualizacao")
    public void shouldNotReturnErrorWhenTheOrderWasValidInTheUpdate(){
        final OrdersRequest ordersRequest = OrdersRequestBuilder.getInstance().getOrdersRequest();

        final CompanyEntity companyEntity = CompanyEntityBuilder.getInstance().getCompanyEntity();
        when(companyService.getCompanyEntityById(ordersRequest.getCalculationFreight().getCompany())).thenReturn(companyEntity);

        final OrdersEntity ordersEntity = OrdersEntityBuilder.getInstance().getOrdersEntity();
        when(ordersRepository.findOrdersByNumberAndCompany(ordersRequest.getNumber(), companyEntity.getId())).thenReturn(ordersEntity);

        final CalculationFreightDto calculationFreightDto = CalculationFreightDtoBuilder.getInstance(ordersRequest.getCalculationFreight()).getCalculationFreightDto();
        when(calculationFreightService.calculate(ordersRequest.getCalculationFreight(), companyEntity, ordersEntity.getCalculationFreightEntity().getId())).thenReturn(calculationFreightDto);

        when(ordersRepository.save(Mockito.any())).thenReturn(ordersEntity);

        final OrdersResponse ordersResponse = assertDoesNotThrow(() -> ordersService.update(ordersRequest));
        compare(ordersResponse, ordersEntity, companyEntity, calculationFreightDto, ordersRequest);
    }
}
