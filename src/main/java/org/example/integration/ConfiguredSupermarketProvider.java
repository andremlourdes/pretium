package org.example.integration;

import java.util.List;

import org.example.dto.SupermarketProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/** Adaptador genérico para uma API de supermercado configurada externamente. */
@Component
public class ConfiguredSupermarketProvider implements SupermarketProvider {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ConfiguredSupermarketProvider(RestTemplateBuilder builder,
                                         @Value("${pretium.supermarket-api.base-url:}") String baseUrl) {
        this.restTemplate = builder.build();
        this.baseUrl = baseUrl;
    }

    @Override
    public String name() {
        return "configured";
    }

    @Override
    public List<SupermarketProductDTO> search(String query) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Configure pretium.supermarket-api.base-url antes de usar a integração");
        }
        String url = UriComponentsBuilder.fromUriString(baseUrl)
            .path("/products")
            .queryParam("query", query)
            .toUriString();
        return restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<SupermarketProductDTO>>() {}).getBody();
    }
}

