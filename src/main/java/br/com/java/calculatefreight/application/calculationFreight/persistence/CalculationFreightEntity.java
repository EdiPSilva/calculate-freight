package br.com.java.calculatefreight.application.calculationFreight.persistence;

import br.com.java.calculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
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
@Table(name = "calculation_freight")
public class CalculationFreightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_company_id")
    private ShippingCompanyEntity shippingCompanyEntity;

    @Column(name = "sender_postal_code")
    private String senderPostalCode;

    @Column(name = "destiny_postal_code")
    private String destinyPostalCode;

    @Column(name = "width")
    private Long width;

    @Column(name = "height")
    private Long height;

    @Column(name = "length")
    private Long length;

    @Column(name = "cubage")
    private Long cubage;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "freight_value")
    private Double freightValue;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;
}
