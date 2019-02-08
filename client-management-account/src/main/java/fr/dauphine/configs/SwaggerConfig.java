/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author jihane and zouhair
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SPRING_WEB)
                .select()
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Users Management API")
                .description("Users management")
                .termsOfServiceUrl("https://github.com/jihane and zouhair/doc")
                .contact("tp_mansour@outlook.fr").license("License")
                .licenseUrl("tp_mansour@outlook.fr").version("1.0").build();
    }

}
