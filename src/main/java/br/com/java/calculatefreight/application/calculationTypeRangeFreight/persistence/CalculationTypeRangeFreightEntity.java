package br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence;

import br.com.java.calculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.calculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
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
@Table(name = "calculation_type_range_freight")
public class CalculationTypeRangeFreightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Convert(converter = CalculationTypeConverter.class)
    @Column(name = "calculation_type")
    private CalculationTypeEnum calculationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "range_freight_id")
    private RangeFreightEntity rangeFreightEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freight_route_id")
    private FreightRouteEntity freightRouteEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_delivery_id")
    private TypeDeliveryEntity typeDeliveryEntity;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;
}