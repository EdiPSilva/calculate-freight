package br.com.java.calculatefreight.application.calculationFreight.persistence;

import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.calculatefreight.application.orders.persistence.OrdersEntity;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calculation_freight")
public class CalculationFreightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity companyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "range_freight_id")
    private RangeFreightEntity rangeFreightEntity;

    @Column(name = "delivery_day")
    private LocalDate delivaryDay;

    @Column(name = "destiny_postal_code")
    private String destinyPostalCode;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "length")
    private Double length;

    @Column(name = "cubage")
    private Double cubage;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "freight_value")
    private Double freightValue;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;
}
