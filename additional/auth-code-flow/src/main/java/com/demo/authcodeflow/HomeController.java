package com.demo.authcodeflow;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(OAuth2AuthenticationToken token) {
        return  "Hello, " + token.getPrincipal().getAttribute("name") + ", " + token.getPrincipal().getAttribute("email") + "!" + token.getAuthorities().toString();
    }
}
