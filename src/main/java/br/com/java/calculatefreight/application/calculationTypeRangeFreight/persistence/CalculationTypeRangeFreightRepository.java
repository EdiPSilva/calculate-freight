package br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationTypeRangeFreightRepository extends JpaRepository<CalculationTypeRangeFreightEntity, Long> {

    @Query("select c.id from CalculationTypeRangeFreightEntity c " +
            "where c.calculationType = :calculationType " +
            "and c.rangeFreightEntity.id = :rangeFreightId " +
            "and c.freightRouteEntity.id = :freightRouteId " +
            "and c.typeDeliveryEntity.id = :typeDeliveryId ")
    public Long findAlreadyExistsCalculationTypeRangeFreight(@Param("calculationType") CalculationTypeEnum calculationType,
                                                             @Param("rangeFreightId") Long rangeFreightId,
                                                             @Param("freightRouteId") Long freightRouteId,
                                                             @Param("typeDeliveryId") Long typeDeliveryId);
}
