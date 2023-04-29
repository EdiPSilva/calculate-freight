package br.com.java.calculatefreight.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@PropertySource("messages.properties")
public class MessageConfiguration {

    @Autowired
    private Environment environment;

    public String getMessageByCode(MessageCodeEnum messageCodeEnum) {
        return environment.getProperty(messageCodeEnum.getValue()).replace(" %s", "");
    }

    public String getMessageByCode(MessageCodeEnum messageCodeEnum, String value) {
        return String.format(Objects.requireNonNull(environment.getProperty(messageCodeEnum.getValue())), value);
    }
}
