package br.com.hanrry.api_gateway.docs;

import br.com.hanrry.api_gateway.config.GatewayServicesProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenApiDocsHandler {

    private static final String API_DOCS_PATH = "/v3/api-docs";

    private final WebClient.Builder webClientBuilder;
    private final GatewayServicesProperties properties;
    private final ObjectMapper objectMapper;

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    public Mono<ServerResponse> getServiceDocs(ServerRequest request) {
        String service = request.pathVariable("service");
        String baseUrl = properties.getServices().get(service);

        if (baseUrl == null || baseUrl.isBlank()) {
            return ServerResponse.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("{\"error\":\"Servico desconhecido para documentacao: " + service + "\"}");
        }

        CacheEntry cached = cache.get(service);
        if (cached != null && cached.expiresAt().isAfter(Instant.now())) {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(cached.body());
        }

        Duration backoff = Duration.ofSeconds(properties.getDocs().getRetryBackoffSeconds());
        int maxRetries = properties.getDocs().getMaxRetries();

        return webClientBuilder.build()
                .get()
                .uri(trimTrailingSlash(baseUrl) + API_DOCS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::rewriteServersToGateway)
                .doOnNext(body -> cache.put(service, new CacheEntry(
                        body,
                        Instant.now().plus(Duration.ofMinutes(properties.getDocs().getCacheTtlMinutes()))
                )))
                .flatMap(body -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body))
                .retryWhen(Retry.backoff(maxRetries, backoff)
                        .maxBackoff(Duration.ofSeconds(30))
                        .filter(this::isRetryable)
                        .doBeforeRetry(signal -> log.warn(
                                "Retry ao buscar OpenAPI de {} (tentativa {}): {}",
                                service,
                                signal.totalRetries() + 1,
                                signal.failure().toString()
                        )))
                .onErrorResume(ex -> {
                    if (cached != null) {
                        log.warn("Falha ao atualizar OpenAPI de {}; servindo cache expirado. Causa: {}",
                                service, ex.toString());
                        return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(cached.body());
                    }
                    log.error("Falha ao buscar OpenAPI de {}: {}", service, ex.toString());
                    return ServerResponse.status(HttpStatus.BAD_GATEWAY)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("{\"error\":\"Nao foi possivel carregar a documentacao de "
                                    + service
                                    + ". No Render free tier, aguarde o cold start e tente novamente.\"}");
                });
    }

    private boolean isRetryable(Throwable ex) {
        if (ex instanceof WebClientResponseException responseException) {
            int status = responseException.getStatusCode().value();
            return status == 429 || status == 502 || status == 503 || status == 504;
        }
        return ex instanceof WebClientRequestException;
    }

    private String rewriteServersToGateway(String openApiJson) {
        try {
            JsonNode root = objectMapper.readTree(openApiJson);
            if (root instanceof ObjectNode objectNode) {
                ArrayNode servers = objectMapper.createArrayNode();
                ObjectNode server = objectMapper.createObjectNode();
                server.put("url", "/");
                server.put("description", "API Gateway");
                servers.add(server);
                objectNode.set("servers", servers);
            }
            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            log.warn("Nao foi possivel reescrever servers do OpenAPI: {}", e.toString());
            return openApiJson;
        }
    }

    private static String trimTrailingSlash(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    private record CacheEntry(String body, Instant expiresAt) {
    }
}
