package br.com.java.calculatefreight.utils;

import io.swagger.models.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultLog {

    public void printLogExecutedEndpoint(HttpMethod httpMethod, String endpoint) {
        log.info("Executed - [" + httpMethod.name() + "] " + endpoint);
    }
}
