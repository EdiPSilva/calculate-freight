package br.com.java.calculatefreight.application.company;

import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.calculatefreight.application.company.persistence.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity getById(final Long id) {
        final Optional<CompanyEntity> optionalCompanyEntity = companyRepository.findById(id);
        return optionalCompanyEntity.get();
    }

}
