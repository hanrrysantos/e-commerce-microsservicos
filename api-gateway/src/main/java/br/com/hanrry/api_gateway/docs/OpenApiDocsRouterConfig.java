package br.com.hanrry.api_gateway.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class OpenApiDocsRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> openApiDocsRoutes(OpenApiDocsHandler handler) {
        return RouterFunctions.route()
                .GET("/docs/{service}", handler::getServiceDocs)
                .build();
    }
}
