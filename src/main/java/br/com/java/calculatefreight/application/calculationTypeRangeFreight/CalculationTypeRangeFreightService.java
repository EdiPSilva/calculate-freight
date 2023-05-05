package br.com.java.calculatefreight.application.calculationTypeRangeFreight;

import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeDto;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightRepository;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CalculationTypeRangeFreightService {

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private CalculationTypeRangeFreightRepository calculationTypeRangeFreightRepository;

    @Transactional(readOnly = true)
    public List<CalculationTypeRangeFreightEntity> getCalculationTypeRangeFreightEntity(final CalculationTypeDto calculationTypeDto, final Long idFreightRoute) {
        final Pageable pageable = PageRequest.of(0, 10);
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = calculationTypeRangeFreightRepository.findAlreadyExistsCalculationTypeRangeFreight(calculationTypeDto.getCalculationTypeEnum(), idFreightRoute, pageable);
        if (calculationTypeRangeFreightEntityList.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(tipo de calculo)"), HttpStatus.NOT_FOUND);
        }
        return calculationTypeRangeFreightEntityList;
    }

    public String getTypeDelivery(final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList, final RangeFreightEntity rangeFreightEntity) {
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = calculationTypeRangeFreightEntityList
                .stream()
                .filter(c-> rangeFreightEntity.getId().equals(c.getRangeFreightEntity().getId()))
                .findFirst()
                .orElse(null);
        if (calculationTypeRangeFreightEntity != null && calculationTypeRangeFreightEntity.getTypeDeliveryEntity() != null) {
            return calculationTypeRangeFreightEntity.getTypeDeliveryEntity().getType();
        }
        return null;
    }
}
