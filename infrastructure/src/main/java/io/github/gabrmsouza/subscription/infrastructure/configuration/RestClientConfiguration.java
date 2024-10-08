package io.github.gabrmsouza.subscription.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gabrmsouza.subscription.infrastructure.configuration.annontations.Keycloak;
import io.github.gabrmsouza.subscription.infrastructure.configuration.annontations.KeycloakAdmin;
import io.github.gabrmsouza.subscription.infrastructure.configuration.properties.RestClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class RestClientConfiguration {
    @Bean
    @Keycloak
    @ConfigurationProperties("rest-client.keycloak")
    RestClientProperties keycloakRestClientProperties() {
        return new RestClientProperties();
    }

    @Bean
    @Keycloak
    RestClient keycloakHttpClient(@Keycloak final RestClientProperties properties, final ObjectMapper mapper) {
        return restClient(properties, mapper);
    }

    @Bean
    @KeycloakAdmin
    @ConfigurationProperties(prefix = "rest-client.keycloak-admin")
    public RestClientProperties keycloakAdminRestClientProperties() {
        return new RestClientProperties();
    }

    @Bean
    @KeycloakAdmin
    public RestClient keycloakAdminHttpClient(@KeycloakAdmin final RestClientProperties properties, final ObjectMapper objectMapper) {
        return restClient(properties, objectMapper);
    }

    private RestClient restClient(final RestClientProperties properties, final ObjectMapper mapper) {
        final var client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        final var factory = new JdkClientHttpRequestFactory(client);
        factory.setReadTimeout(properties.readTimeout());
        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(factory)
                .messageConverters(converters -> {
                    converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
                    converters.add(jsonConverter(mapper));
                    converters.add(new FormHttpMessageConverter());
                })
                .build();
    }

    private MappingJackson2HttpMessageConverter jsonConverter(final ObjectMapper mapper) {
        final var converter = new MappingJackson2HttpMessageConverter(mapper);
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        return converter;
    }
}
