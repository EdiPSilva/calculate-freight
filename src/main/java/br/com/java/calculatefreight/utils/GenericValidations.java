package br.com.java.calculatefreight.utils;

import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeEnum;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.StatesEnum;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

@Component
public class GenericValidations {

    private final List<String> listInvalidCnpj = Arrays.asList("00000000000000","11111111111111","22222222222222","33333333333333","44444444444444","55555555555555","66666666666666","77777777777777","88888888888888","99999999999999");

    @Autowired
    MessageConfiguration messageConfiguration;

    public void validatevalidateNumberGreaterThanZero(Long value, MessageCodeEnum messageCodeEnum) {
        if (messageCodeEnum == null) {
            throw new NullPointerException();
        }
        if (value == null || value <= 0) {
            throw new CustomException(messageConfiguration.getMessageByCode(messageCodeEnum));
        }
    }

    public boolean cnpjIsValid(String cnpj) {
        if (listInvalidCnpj.contains(cnpj) || cnpj.length() != 14) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.INVALID_DOCUMENT));
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig13 = '0';
            } else {
                dig13 = (char)((11-r) + 48);
            }

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int)(cnpj.charAt(i)- 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10){
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig14 = '0';
            } else {
                dig14 = (char)((11-r) + 48);
            }

            if (!((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))) {
                throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.INVALID_DOCUMENT));
            }
            return true;
        } catch (InputMismatchException inputMismatchException) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.INVALID_DOCUMENT));
        }
    }

    public void validatePostCode(final String postCode, final MessageCodeEnum messageCodeEnum) {
        if (postCode == null) {
            throw new NullPointerException();
        }
        if (postCode.isEmpty() || postCode.length() != 8) {
            throw new CustomException(messageConfiguration.getMessageByCode(messageCodeEnum));
        }
    }

    public StatesEnum getStatesByCode(final String stateCode) {
        if (stateCode == null || stateCode.trim().length() != 2) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.INVALID_STATE));
        }
        for (StatesEnum state: StatesEnum.values()) {
            if (stateCode.toUpperCase().equals(state.getStateCode())) {
                return state;
            }
        }
        throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.STATE_NOT_FOUND));
    }

    public CalculationTypeEnum getCalculationTypeEnum(final String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.INVALID_CALCULATION_TYPE));
        }
        for (CalculationTypeEnum calculationTypeEnum: CalculationTypeEnum.values()) {
            if (value.toUpperCase().equals(calculationTypeEnum.getValue())) {
                return calculationTypeEnum;
            }
        }
        throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.CALCULATION_TYPE_NOT_FOUND));
    }
}