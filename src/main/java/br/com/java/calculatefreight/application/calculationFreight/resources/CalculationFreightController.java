package br.com.java.calculatefreight.application.calculationFreight.resources;

import br.com.java.calculatefreight.application.calculationFreight.CalculationFreightService;
import br.com.java.calculatefreight.utils.DefaultLog;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/calculation-freight", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculationFreightController {

    @Autowired
    private DefaultLog defaultLog;
    
    @Autowired
    private CalculationFreightService calculationFreightService;

    private static final String API_V1 = "/calculation-freight/v1/";

    @ApiOperation("Cálculo de frete")
    @PostMapping(value = "/v1/")
    public ResponseEntity<CalculationFreightResponse> create(@Valid @RequestBody CalculationFreightRequest calculationFreightRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.POST, API_V1);
        return new ResponseEntity<>(calculationFreightService.create(calculationFreightRequest), HttpStatus.CREATED);
    }
}