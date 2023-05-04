package br.com.java.calculatefreight.application.company;

import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.calculatefreight.application.company.persistence.CompanyRepository;
import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import br.com.java.calculatefreight.utils.GenericValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private GenericValidations genericValidations;

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public CompanyEntity getCompanyEntityById(final Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<CompanyEntity> optionalCompanyEntity = companyRepository.findById(id);
        if (optionalCompanyEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(empresa)"), HttpStatus.NOT_FOUND);
        }
        return optionalCompanyEntity.get();
    }
}
