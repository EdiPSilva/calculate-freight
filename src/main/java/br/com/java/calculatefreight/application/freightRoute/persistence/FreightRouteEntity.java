package br.com.java.calculatefreight.application.freightRoute.persistence;

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
@Table(name = "freight_route")
public class FreightRouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "start_postal_code", length = 8)//Add unique
    private String startPostalCode;

    @Column(name = "end_postal_code", length = 8)//Add unique
    private String endPostalCode;

    @Column(name = "delivery_days")
    private Long deliveryDays;

    @Column(name = "devolution_days")
    private Long devolutionDays;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;

    @Convert(converter = StateCodeConverter.class)
    @Column(name = "state_code")
    private StatesEnum stateCode;
}
