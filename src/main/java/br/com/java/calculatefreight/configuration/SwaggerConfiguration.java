package br.com.java.calculatefreight.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final String NAME_APPLICATION = "Calculate Freight";

    private final String DESCRIPTION = "Aplicação responsável por realizar o cálculo de frete.";

    private final String PACKAGE = "br.com.java.calculatefreight.application";

    @Bean
    public Docket apiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Version 1")
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACKAGE))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfoBuilder());
    }

    private ApiInfo getApiInfoBuilder() {
        return new ApiInfoBuilder()
                .title(NAME_APPLICATION)
                .description(DESCRIPTION)
                .build();
    }
}
