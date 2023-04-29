package br.com.java.calculatefreight.application.freightRoute.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FreightRouteRepository extends JpaRepository<FreightRouteEntity, Long> {

    public FreightRouteEntity findByStartPostalCode(String startPostalCode);

    public FreightRouteEntity findByEndPostalCode(String endPostalCode);

    @Query("select f.id from FreightRouteEntity f where f.startPostalCode = :postalCode")
    public Long findFreightRouteEntityByStartPostalCode(@Param("postalCode") String postalCode);

    @Query("select f.id from FreightRouteEntity f where f.endPostalCode = :postalCode")
    public Long findFreightRouteEntityByEndPostalCode(@Param("postalCode") String postalCode);
}