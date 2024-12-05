package ru.otus.hw;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тест ApiGateway ")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
class ApiGatewayApplicationTest {

    final static String API_GATEWAY_CONTROLLER = "/session";

    static String accessToken;

    @Autowired
    private WebTestClient webTestClient;

    @Container
    static KeycloakContainer keycloak = new KeycloakContainer()
            .withRealmImportFile("realm-export.json")
            .withExposedPorts(8080, 9000);

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.client.provider.keycloak.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/project");
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/project/protocol/openid-connect/certs");
        registry.add("spring.cloud.gateway.routes[0].uri",
                () -> "http://localhost:8080");
        registry.add("spring.cloud.gateway.routes[0].id", () -> "api-gateway");
        registry.add("spring.cloud.gateway.routes[0].predicates[0]", () -> "Path=/token");
    }

    @DisplayName("должен проверить поднятие контекста и старт приложения")
    @Test
    @Order(1)
    void shouldStart() {
    }

    @DisplayName("должен проверить аутентификацию и редирект на страницу логина Keycloak")
    @Test
    @Order(2)
    void shouldBeRedirectedToLoginPage() {
        webTestClient.get().uri(API_GATEWAY_CONTROLLER)
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @DisplayName("должен получить токен от Keycloak")
    @Test
    @Order(3)
    void shouldObtainAccessToken() throws URISyntaxException {
        URI authorizationURI = new URIBuilder(keycloak.getAuthServerUrl() + "/realms/project/protocol/openid-connect/token").build();

        WebClient webclient = WebClient.builder().build();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("grant_type", Collections.singletonList("password"));
        formData.put("client_id", Collections.singletonList("project-client"));
        formData.put("username", Collections.singletonList("admin"));
        formData.put("password", Collections.singletonList("admin"));

        String result = webclient.post()
                .uri(authorizationURI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        accessToken = jsonParser.parseMap(result)
                .get("access_token")
                .toString();

        assertNotNull(accessToken);
    }

    @DisplayName("должен проверить доступность ресурса")
    @Test
    @Order(4)
    void shouldReturnToken() {
        webTestClient.get().uri(API_GATEWAY_CONTROLLER)
                .header("Authorization", "Bearer " + accessToken)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}