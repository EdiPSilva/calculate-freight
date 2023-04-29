package br.com.java.calculatefreight.application.rangeFreight.persistence;

import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "range_freight")
public class RangeFreightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_company_id")
    private ShippingCompanyEntity shippingCompanyEntity;

    @Column(name = "start_value")
    private Double startValue;

    @Column(name = "end_value")
    private Double endValue;

    @Column(name = "freight_value")
    private Double freightValue;

    @Column(name = "surplus_value")
    private Double surplusValue;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;
}
