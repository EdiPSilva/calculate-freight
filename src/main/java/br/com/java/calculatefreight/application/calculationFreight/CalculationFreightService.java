package br.com.java.calculatefreight.application.calculationFreight;

import br.com.java.calculatefreight.application.calculationFreight.persistence.CalculationFreightRepository;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightRequest;
import br.com.java.calculatefreight.application.calculationFreight.resources.CalculationFreightResponse;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.utils.GenericValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculationFreightService {

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private GenericValidations genericValidations;

    @Autowired
    private CalculationFreightRepository calculationFreightRepository;

    public CalculationFreightResponse calculateFreight(CalculationFreightRequest calculationFreightRequest) {
        return null;
    }
}
