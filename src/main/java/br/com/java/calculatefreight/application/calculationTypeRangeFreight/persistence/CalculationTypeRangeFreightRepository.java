package br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculationTypeRangeFreightRepository extends JpaRepository<CalculationTypeRangeFreightEntity, Long> {

    @Query("select c from CalculationTypeRangeFreightEntity c " +
            "where c.calculationType = :calculationType " +
            "and c.freightRouteEntity.id = :freightRouteId ")
    public List<CalculationTypeRangeFreightEntity> findAlreadyExistsCalculationTypeRangeFreight(@Param("calculationType") CalculationTypeEnum calculationType,
                                                                                                @Param("freightRouteId") Long freightRouteId,
                                                                                                Pageable pageable);
}
