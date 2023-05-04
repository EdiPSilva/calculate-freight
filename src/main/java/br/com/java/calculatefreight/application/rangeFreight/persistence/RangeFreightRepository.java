package br.com.java.calculatefreight.application.rangeFreight.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RangeFreightRepository extends JpaRepository<RangeFreightEntity, Long> {

    @Query("select r from RangeFreightEntity r where r.id in :idList and r.active = 'true'")
    public List<RangeFreightEntity> findRangeFreight(@Param("idList") List<Long> idList);
}
