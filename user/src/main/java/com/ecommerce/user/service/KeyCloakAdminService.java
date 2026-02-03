package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeyCloakAdminService {

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    @Value("${keycloak.admin.server-url}")
    private String keyCloakServerUrl;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-uid}")
    private String clientUid;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAdminAccessToken() {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("username", adminUsername);
        params.add("password", adminPassword);
        params.add("grant_type", "password");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);

        String url = keyCloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        ResponseEntity<Map> response = restTemplate.postForEntity(url, httpEntity, Map.class);
        return response.getBody().get("access_token").toString();
    }

    public String createUser(String token, UserRequest userRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);

        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("username", userRequest.getUsername());
        userPayload.put("email", userRequest.getEmail());
        userPayload.put("enabled", true);
        userPayload.put("firstName", userRequest.getFirstName());
        userPayload.put("lastName", userRequest.getLastName());

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", userRequest.getPassword());
        credentials.put("temporary", false);

        userPayload.put("credentials", List.of(credentials));

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(userPayload, httpHeaders);

        String url = keyCloakServerUrl + "/admin/realms/" + realm + "/users";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);

            if(!HttpStatus.CREATED.equals(response.getStatusCode())) {
                throw new RuntimeException("Failed to create user in Keycloak " + response.getBody());
            }

            // Extract keycloak user id
            URI location = response.getHeaders().getLocation();

            if(location == null) {
                throw new RuntimeException("Keycloak did not return Location Header for user creation" + response.getBody());
            }

            String path = location.getPath();
            return extractUserIdFromPath(path);

        } catch (HttpClientErrorException.Conflict e) {
            // User already exists, try to find the user and return the ID
            return findUserIdByUsername(token, userRequest.getUsername());
        }
    }

    private String findUserIdByUsername(String token, String username) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        String url = keyCloakServerUrl + "/admin/realms/" + realm + "/users?username=" + username;

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        List<Map<String, Object>> users = response.getBody();
        if (users != null && !users.isEmpty()) {
            return users.get(0).get("id").toString();
        }

        throw new RuntimeException("User already exists but could not be found: " + username);
    }

    private String extractUserIdFromPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private Map<String, Object> getRealmRoleRepresentation(String token, String roleName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        String url = keyCloakServerUrl + "/admin/realms/" + realm + "/clients/" + clientUid + "/roles/" + roleName;
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }

    public void assignRealmRoleToUser(String username, String roleName, String userId) {
        String token = getAdminAccessToken();

        Map<String, Object> realmRoleRepresentation = getRealmRoleRepresentation(token, roleName);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);

        HttpEntity<List<Map<String, Object>>> httpEntity = new HttpEntity<>(List.of(realmRoleRepresentation), httpHeaders);

        String url = keyCloakServerUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/clients/" + clientUid;

        ResponseEntity<Void> response = restTemplate.postForEntity(url,  httpEntity, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(
                    "Failed to assign role " + roleName +
                            " to user " + username +
                            ": HTTP " + response.getStatusCode()
            );
        }
    }
}
