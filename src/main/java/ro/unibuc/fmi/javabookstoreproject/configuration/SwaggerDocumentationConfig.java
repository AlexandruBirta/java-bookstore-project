package ro.unibuc.fmi.javabookstoreproject.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info().title("Bookstore API")
                                .description("Java bookstore project")
                                .version("v0.1.5")
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Project github repository")
                                .url("https://github.com/AlexandruBirta/java-bookstore-project")
                );

    }

}
