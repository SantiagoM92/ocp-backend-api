package com.jsmu.backendapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class UserService {

    private final RestClient restClient;

    public UserService(RestClient.Builder restClientBuilder,
            @Value("${external.users.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public List<String> findUsersByPrefix(String prefix) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/names")
                            .queryParam("prefix", prefix)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<String>>() {});
        } catch (RestClientException exception) {
            throw new IllegalStateException("Failed to fetch users from external service", exception);
        }
    }
}
