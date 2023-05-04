package br.com.java.calculatefreight.application.shippingCompany.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingCompanyRepository extends JpaRepository<ShippingCompanyEntity, Long> {

}