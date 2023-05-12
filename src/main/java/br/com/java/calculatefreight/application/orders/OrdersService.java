package br.com.java.calculatefreight.application.orders;

import br.com.java.calculatefreight.application.calculationFreight.CalculationFreightService;
import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightDto;
import br.com.java.calculatefreight.application.company.CompanyService;
import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.calculatefreight.application.orders.persistence.OrdersEntity;
import br.com.java.calculatefreight.application.orders.persistence.OrdersRepository;
import br.com.java.calculatefreight.application.orders.resources.OrdersRequest;
import br.com.java.calculatefreight.application.orders.resources.OrdersResponse;
import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CalculationFreightService calculationFreightService;

    @Transactional
    public OrdersResponse create(final OrdersRequest ordersRequest) {
        final CompanyEntity companyEntity = companyService.getCompanyEntityById(ordersRequest.getCalculationFreight().getCompany());
        validateOrderNumberCreate(ordersRequest.getNumber(), companyEntity.getId());
        final CalculationFreightDto calculationFreightDto = calculationFreightService.calculate(ordersRequest.getCalculationFreight(), companyEntity);
        final OrdersEntity ordersEntity = ordersRequest.to();
        ordersEntity.setDateCreate(LocalDateTime.now());
        ordersEntity.setCompanyEntity(companyEntity);
        ordersEntity.setCalculationFreightEntity(calculationFreightDto.getCalculationFreightEntity());
        return OrdersResponse.from(ordersRepository.save(ordersEntity), calculationFreightDto);
    }

    private void validateOrderNumberCreate(final String number, final Long companyId) {
        validateOrderNumberAlreadyExists(number, companyId, true);
    }

    private OrdersEntity validateOrderNumberUpdate(final String number, final Long companyId) {
        return validateOrderNumberAlreadyExists(number, companyId, false);
    }

    private OrdersEntity validateOrderNumberAlreadyExists(final String number, final Long companyId, final boolean create) {
        final OrdersEntity ordersEntity = ordersRepository.findOrdersByNumberAndCompany(number, companyId);
        if (Boolean.TRUE.equals(create) && ordersEntity != null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.ORDER_NUMBER_ALREADY_EXISTS));
        }
        if (Boolean.FALSE.equals(create) && ordersEntity == null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(pedido)"), HttpStatus.NOT_FOUND);
        }
        return ordersEntity;
    }

    @Transactional
    public OrdersResponse update(OrdersRequest ordersRequest) {
        final CompanyEntity companyEntity = companyService.getCompanyEntityById(ordersRequest.getCalculationFreight().getCompany());
        final OrdersEntity ordersEntity = validateOrderNumberUpdate(ordersRequest.getNumber(), companyEntity.getId());
        final CalculationFreightDto calculationFreightDto = calculationFreightService.calculate(ordersRequest.getCalculationFreight(), companyEntity, ordersEntity.getCalculationFreightEntity().getId());
        ordersEntity.setDateUpdate(LocalDateTime.now());
        ordersEntity.setCalculationFreightEntity(calculationFreightDto.getCalculationFreightEntity());
        return OrdersResponse.from(ordersRepository.save(ordersEntity), calculationFreightDto);
    }
}
