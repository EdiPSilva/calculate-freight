package br.com.java.calculatefreight.application.orders.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

    @Query("select o from OrdersEntity o where o.orderNumber = :orderNumber and o.companyEntity.id = :id")
    public OrdersEntity findOrdersByNumberAndCompany(@Param("orderNumber") String orderNumber, @Param("id") Long id);

}
