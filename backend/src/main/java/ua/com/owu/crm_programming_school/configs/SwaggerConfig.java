package ua.com.owu.crm_programming_school.configs;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        info = @Info(
                title = "CRM Programming School",
                version = "v1",
                description = "About API documentation CRM Programming School",
                license =@License(name = "BSD License"),
                contact = @Contact(
                        email = "v637904@gmail.com"
                ),
                termsOfService = "https://policies.google.com/terms"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080/api/v1",
                        description = "Base URL"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }

)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "JWT token",
        in = SecuritySchemeIn.HEADER
)

public class SwaggerConfig {

}

