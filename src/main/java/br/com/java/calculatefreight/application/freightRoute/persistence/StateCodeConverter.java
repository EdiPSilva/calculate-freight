package br.com.java.calculatefreight.application.freightRoute.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Converter
public class StateCodeConverter implements AttributeConverter<StatesEnum, String> {

    private static final Map<String, StatesEnum> STATES = new HashMap<>();

    static {
        Arrays.stream(StatesEnum.values()).forEach(state -> STATES.put(state.getStateCode(), state));
    }

    @Override
    public String convertToDatabaseColumn(StatesEnum attribute) {
        return attribute != null ? attribute.getStateCode() : null;
    }

    @Override
    public StatesEnum convertToEntityAttribute(String value) {
        return value != null ? STATES.get(value) : null;
    }
}
