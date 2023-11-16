package com.soundlab.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI & Swagger configuration (Available under <base_url:port>/swagger-ui.html)
 */
@Configuration
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
    info = @Info(
        title = "${application.title}",
        version = "${application.version}",
        license = @License(
            name = "MIT",
            url = "https://mit-license.org/"
        ),
        termsOfService = "${app.tos}",
        description = "${application.description}"
    ),
    security = {
        @SecurityRequirement(name = "Bearer Authentication")
    }
)
public class OpenApiConfig { }