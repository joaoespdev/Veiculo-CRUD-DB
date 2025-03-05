package com.dbserver.veiculo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiDocSwagger() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentação da API")
                        .description("Descrição da API")
                        .version("1.0.0"));
    }
}