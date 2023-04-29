package br.com.java.calculatefreight.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorObject {

    private final String message;
    private final String field;
    private final Object parameter;
}
