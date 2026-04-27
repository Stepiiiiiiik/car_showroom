package ru.gorlov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(RestApiIntegrationTest.TestSecurityConfig.class)
class RestApiIntegrationTest extends PostgresIT {

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void shouldReturnUnauthorizedWhenNoToken() {
                UUID carId = UUID.fromString("00000000-0000-0000-0000-000000000201");

                ResponseEntity<String> response = restTemplate.getForEntity(
                                "http://localhost:" + port + "/cars/" + carId,
                                String.class);

                assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        void shouldReturnCarByIdForAuthenticatedUser() throws Exception {
                String token = tokenWithRoles(
                                "00000000-0000-0000-0000-000000000101",
                                "user");

                UUID carId = UUID.fromString("00000000-0000-0000-0000-000000000201");

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);

                ResponseEntity<String> response = restTemplate.exchange(
                                "http://localhost:" + port + "/cars/" + carId,
                                org.springframework.http.HttpMethod.GET,
                                new HttpEntity<>(headers),
                                String.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());

                JsonNode body = objectMapper.readTree(response.getBody());
                assertEquals("Toyota", body.get("brand").asText());
                assertEquals("CamrySeed", body.get("model").asText());
                assertTrue(body.get("defaultSpareParts").isArray());
                assertEquals(4, body.get("defaultSpareParts").size());
        }

        @Test
        void shouldForbidUserWhenCallingAdminEndpoint() {
                String token = tokenWithRoles(
                                "00000000-0000-0000-0000-000000000101",
                                "user");

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                headers.setContentType(MediaType.APPLICATION_JSON);

                String payload = """
                                {
                                  "username": "newadmin",
                                  "firstName": "New",
                                  "lastName": "Admin",
                                  "email": "newadmin@example.com",
                                  "password": "password123"
                                }
                                """;

                ResponseEntity<String> response = restTemplate.postForEntity(
                                "http://localhost:" + port + "/users/register-admin",
                                new HttpEntity<>(payload, headers),
                                String.class);

                assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        private String tokenWithRoles(String subject, String... roles) {
                String payload = subject + "." + String.join(",", roles);
                return Base64.getUrlEncoder().withoutPadding()
                                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        }

        @TestConfiguration
        static class TestSecurityConfig {

                @Bean
                @Primary
                JwtDecoder testJwtDecoder() {
                        return token -> {
                                String decodedToken;
                                try {
                                        decodedToken = new String(
                                                        Base64.getUrlDecoder().decode(token),
                                                        StandardCharsets.UTF_8);
                                } catch (IllegalArgumentException e) {
                                        throw new JwtException("Invalid test token encoding", e);
                                }

                                String[] parts = decodedToken.split("\\.", 2);
                                if (parts.length != 2 || parts[0].isBlank()) {
                                        throw new JwtException("Invalid test token format");
                                }

                                List<String> roles = parts[1].isBlank()
                                                ? List.of()
                                                : List.of(parts[1].split(","));

                                Instant now = Instant.now();
                                Map<String, Object> claims = Map.of(
                                                "sub", parts[0],
                                                "realm_access", Map.of("roles", roles));

                                return new Jwt(
                                                token,
                                                now,
                                                now.plusSeconds(3600),
                                                Map.of("alg", "none"),
                                                claims);
                        };
                }
        }
}
