package com.jcayllahua.backend.config.doc;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI envolverChallengeOpenApi() {
        return new OpenAPI()

                .info(new Info().title("Backend API Challenge")
                        .description("API made to provide data for the frontend application.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("José Cayllahua")
                                .email("jcayllahua.2003@gmail.com")
                        )

                )
                .externalDocs(new ExternalDocumentation()
                        .description("Web Application")
                        .url("http://localhost:3000"));
    }
}
