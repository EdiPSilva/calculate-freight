package br.com.java.calculatefreight.application.shippingCompany.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingCompanyRepository extends JpaRepository<ShippingCompanyEntity, Long> {

    public ShippingCompanyEntity findByDocument(String document);

    @Query("select s.id from ShippingCompanyEntity s where s.document = :document")
    public Long findShippingCompanyEntityByDocument(@Param("document") String document);
}