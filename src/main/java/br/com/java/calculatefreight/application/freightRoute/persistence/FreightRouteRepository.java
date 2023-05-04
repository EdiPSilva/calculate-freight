package br.com.java.calculatefreight.application.freightRoute.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FreightRouteRepository extends JpaRepository<FreightRouteEntity, Long> {

    @Query("select f from FreightRouteEntity f where :postalCode between f.startPostalCode and f.endPostalCode and f.active = 'true'")
    public FreightRouteEntity findFreightRouteEntityByPostalCode(@Param("postalCode") String postalCode);

}