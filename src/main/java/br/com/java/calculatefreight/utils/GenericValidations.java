package br.com.java.calculatefreight.utils;

import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericValidations {

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

    public void validatePostalCode(final String postCode, final MessageCodeEnum messageCodeEnum) {
        if (postCode == null) {
            throw new NullPointerException();
        }
        if (postCode.isEmpty() || postCode.length() != 8) {
            throw new CustomException(messageConfiguration.getMessageByCode(messageCodeEnum));
        }
    }
}