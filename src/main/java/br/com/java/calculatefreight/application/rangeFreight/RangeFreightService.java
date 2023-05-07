package br.com.java.calculatefreight.application.rangeFreight;

import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeDto;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightDto;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightRepository;
import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RangeFreightService {

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private RangeFreightRepository rangeFreightRepository;

    @Transactional(readOnly = true)
    public RangeFreightDto getFreightValue(final CalculationTypeDto calculationTypeDto, final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList) {
        final List<RangeFreightEntity> rangeFreightEntityList = rangeFreightEntityList(calculationTypeRangeFreightEntityList);
        final RangeFreightEntity rangeFreightEntity = getRangeFreightEntityByList(rangeFreightEntityList, calculationTypeDto);
        validateShippingCompany(rangeFreightEntity);
        return RangeFreightDto.getInstance(rangeFreightEntity, calculateFreight(rangeFreightEntity.getFreightValue(), rangeFreightEntity.getSurplusValue(), rangeFreightEntity.getEndValue(), calculationTypeDto));
    }

    private List<RangeFreightEntity> rangeFreightEntityList(final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList) {
        final List<Long> idList = calculationTypeRangeFreightEntityList.stream().map(CalculationTypeRangeFreightEntity::getId).collect(Collectors.toList());
        final List<RangeFreightEntity> rangeFreightEntityList = rangeFreightRepository.findRangeFreight(idList)
                .stream()
                .sorted(Comparator.comparingDouble(RangeFreightEntity::getEndValue))
                .collect(Collectors.toList());
        if (rangeFreightEntityList.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(range de frete)"), HttpStatus.NOT_FOUND);
        }
        return rangeFreightEntityList;
    }

    private RangeFreightEntity getRangeFreightEntityByList(final List<RangeFreightEntity> rangeFreightEntityList, final CalculationTypeDto calculationTypeDto) {
        RangeFreightEntity rangeFreightEntity = null;
        for (final RangeFreightEntity rangeFreight: rangeFreightEntityList) {
            if (rangeFreight.getStartValue() <= calculationTypeDto.getValue() && rangeFreight.getEndValue() >= calculationTypeDto.getValue()) {
                rangeFreightEntity = rangeFreight;
                break;
            }
        }
        if (rangeFreightEntity == null) {
            rangeFreightEntity = rangeFreightEntityList.get(rangeFreightEntityList.size() - 1);
        }
        return rangeFreightEntity;
    }

    private void validateShippingCompany(final RangeFreightEntity rangeFreightEntity) {
        if (Boolean.FALSE.equals(rangeFreightEntity.getShippingCompanyEntity().getActive())) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.SHIPPING_COMPANY_INACTIVE));
        }
    }

    private Double calculateFreight(Double freightValue, final Double surplusValue, final Double endValue, final CalculationTypeDto calculationTypeDto) {
        if (calculationTypeDto.getValue() > endValue) {
            freightValue += (freightValue / 100) * surplusValue;
        }
        final BigDecimal bigDecimal = new BigDecimal(freightValue).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
