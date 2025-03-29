package com.example.demospringboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Art Gallery Auction")
                        .version("1.2.0")
                        .description("A seamless wedding planning portal for effortless vendor management and guest coordination."))
                .addServersItem(new Server().url("http://localhost:8080"));
    }

    // @Bean
    // public PageableCustomiz pageableCustomizer() {
    //     return pageableParameterBuilder -> pageableParameterBuilder
    //             .name("page")
    //             .description("Page number (0..N)")
    //             .in(ParameterIn.QUERY.toString())
    //             .required(false);
    // }
}