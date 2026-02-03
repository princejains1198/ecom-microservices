package com.demo.client;

import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Service2Client {

    private final RestTemplate restTemplate;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Value("${service2.url}")
    public String service2Url;

    public Service2Client(RestTemplate restTemplate, OAuth2AuthorizedClientManager authorizedClientManager) {
        this.restTemplate = restTemplate;
        this.authorizedClientManager = authorizedClientManager;
    }

    public String fetchData() {

        /*
            FOR GENERATING THE NEW TOKEN AND FORWARD THE REQUEST
         */
        /*var authRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak-client")
                .principal("machine")
                .build();

        var client = authorizedClientManager.authorize(authRequest);
        String token = client.getAccessToken().getTokenValue();
        System.out.println("TOKEN VALUE : " + token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var response = restTemplate.exchange(
                service2Url + "/data",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        return response.getBody();*/

        /*
            FOR USING THE SAME TOKEN AND FORWARD THE REQUEST
         */
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        String incomingToken = null;

        if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            incomingToken = jwtAuthenticationToken.getToken().getTokenValue();
        }

        System.out.println("TOKEN VALUE : " + incomingToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(incomingToken);

        var response = restTemplate.exchange(
                service2Url + "/data",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        return response.getBody();
    }
}
