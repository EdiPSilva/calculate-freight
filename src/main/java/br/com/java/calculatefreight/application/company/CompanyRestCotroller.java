package br.com.java.calculatefreight.application.company;

import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyRestCotroller {

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<CompanyEntity> getById(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.getById(id));
    }
}
