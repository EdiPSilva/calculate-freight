package br.com.java.calculatefreight.exceptions;
import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class ResponseControllerException {

    @Autowired
    MessageConfiguration messageConfiguration;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomExceptions(CustomException customException) {
        log.error(customException.getMessage());
        return buildResponseEntity(customException.getMessage(), customException.getHttpStatus());
    }

    @ExceptionHandler({NullPointerException.class, ClassCastException.class, LinkageError.class, AssertionError.class, ThreadDeath.class, VirtualMachineError.class, JsonMappingException.class})
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception) {
        log.error(exception.getMessage());
        exception.printStackTrace();
        return buildResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(messageConfiguration.getMessageByCode(MessageCodeEnum.INVALID_REQUEST_DEFAULT_MESSAGE))
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .errors(Arrays.asList(ErrorObject.builder().message(message).build()))
                .build(),
                httpStatus);
    }



}
