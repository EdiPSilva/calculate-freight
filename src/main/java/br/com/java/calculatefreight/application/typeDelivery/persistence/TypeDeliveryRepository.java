package br.com.java.calculatefreight.application.typeDelivery.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDeliveryRepository extends JpaRepository<TypeDeliveryEntity, Long> {

    public TypeDeliveryEntity findByType(String type);

    @Query("select t.id from TypeDeliveryEntity t where t.type = :type")
    public Long findTypeDeliveryEntityByType(@Param("type") String type);
}
