package br.com.java.calculatefreight.application.freightRoute;

import br.com.java.calculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.calculatefreight.application.freightRoute.persistence.FreightRouteRepository;
import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FreightRouteService {

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private FreightRouteRepository freightRouteRepository;

    @Transactional(readOnly = true)
    public FreightRouteEntity getFreightRouteEntityByPostalCode(final String postalCode) {
        final FreightRouteEntity freightRouteEntity = freightRouteRepository.findFreightRouteEntityByPostalCode(postalCode);
        if (freightRouteEntity == null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(rota de frete)"), HttpStatus.NOT_FOUND);
        }
        return freightRouteEntity;
    }
}
