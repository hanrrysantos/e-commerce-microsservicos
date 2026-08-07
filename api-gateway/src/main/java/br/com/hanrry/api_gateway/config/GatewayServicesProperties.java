package br.com.hanrry.api_gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "gateway")
public class GatewayServicesProperties {

    private Map<String, String> services = new LinkedHashMap<>();

    private Docs docs = new Docs();

    @Getter
    @Setter
    public static class Docs {
        private long cacheTtlMinutes = 30;
        private int maxRetries = 4;
        private long retryBackoffSeconds = 5;
    }
}
