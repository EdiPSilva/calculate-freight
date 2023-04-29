package br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Converter
public class CalculationTypeConverter implements AttributeConverter<CalculationTypeEnum, String> {

    private static final Map<String, CalculationTypeEnum> CALCULATION_TYPE = new HashMap<>();

    static {
        Arrays.stream(CalculationTypeEnum.values()).forEach(calculationType -> CALCULATION_TYPE.put(calculationType.getValue(), calculationType));
    }

    @Override
    public String convertToDatabaseColumn(CalculationTypeEnum attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public CalculationTypeEnum convertToEntityAttribute(String value) {
        return value != null ? CALCULATION_TYPE.get(value) : null;
    }
}
