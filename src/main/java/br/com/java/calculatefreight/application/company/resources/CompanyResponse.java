package br.com.java.calculatefreight.application.company.resources;

import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponse {

    private CompanyResponse(CompanyEntity companyEntity) {
        this.id = companyEntity.getId();
        this.name = companyEntity.getName();
        this.document = companyEntity.getDocument();
        this.postalCode = companyEntity.getPostalCode();
        this.active = companyEntity.getActive();
        this.dateCreate = companyEntity.getDateCreate();
        this.dateUpdate = companyEntity.getDateUpdate();
    }

    @ApiModelProperty(notes = "Id da empresa", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Nome da empresa", example = "JP Comercio ME", required = true)
    private String name;

    @ApiModelProperty(notes = "CNPJ da empresa", example = "33662514000161", required = true)
    private String document;

    @ApiModelProperty(notes = "CEP da empresa", example = "13052110", required = true)
    private String postalCode;

    @ApiModelProperty(notes = "Status da empresa", example = "true", required = true)
    private Boolean active;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Data de atualização", example = "dd/MM/yyyy hh:mm:ss", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateUpdate;

    public static CompanyResponse from(CompanyEntity companyEntity) {
        return new CompanyResponse(companyEntity);
    }
}
