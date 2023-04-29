package br.com.java.calculatefreight.application.shippingCompany.resources;

import br.com.java.calculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingCompanyResponse {

    private ShippingCompanyResponse(ShippingCompanyEntity shippingCompanyEntity) {
        this.id = shippingCompanyEntity.getId();
        this.name = shippingCompanyEntity.getName();
        this.document = shippingCompanyEntity.getDocument();
        this.active = shippingCompanyEntity.getActive();
        this.dateCreate = shippingCompanyEntity.getDateCreate();
        this.dateUpdate = shippingCompanyEntity.getDateUpdate();
    }

    @ApiModelProperty(notes = "Id da transportadora", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Nome da transportadora", example = "JP Log ME", required = true)
    private String name;

    @ApiModelProperty(notes = "CNPJ da transportadora", example = "88449952000138", required = true)
    private String document;

    @ApiModelProperty(notes = "Status da transportadora", example = "true", required = true)
    private Boolean active;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Data de atualização", example = "dd/MM/yyyy hh:mm:ss", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateUpdate;

    public static ShippingCompanyResponse from(ShippingCompanyEntity shippingCompanyEntity) {
        return new ShippingCompanyResponse(shippingCompanyEntity);
    }
}
