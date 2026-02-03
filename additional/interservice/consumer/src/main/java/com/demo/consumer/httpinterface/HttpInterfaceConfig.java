package com.demo.consumer.httpinterface;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class HttpInterfaceConfig {

//    @Bean
//    @LoadBalanced
//    public WebClient.Builder webClientBuilder(){
//        return WebClient.builder();
//    }

//    @Bean
//    public ProviderHttpInterface webClientHttpInterface(WebClient.Builder webClientBuilder) {
//        WebClient webClient = webClientBuilder
////                                .baseUrl("http://localhost:8081")
//                                .baseUrl("http://provider-service")
//                                .build();
//        WebClientAdapter adapter = WebClientAdapter.create(webClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        ProviderHttpInterface providerHttpInterface = factory.createClient(ProviderHttpInterface.class);
//        return providerHttpInterface;
//    }

//    @Bean
//    @LoadBalanced
//    public RestClient.Builder restClientBuilder() {
//        return RestClient.builder();
//    }

//    @Bean
//    public ProviderHttpInterface restClientHttpInterface(RestClient.Builder restClientBuilder) {
//        RestClient restClient = restClientBuilder
////                .baseUrl("http://localhost:8081")
//                .baseUrl("http://provider-service")
//                .build();
//        RestClientAdapter adapter = RestClientAdapter.create(restClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        ProviderHttpInterface providerHttpInterface = factory.createClient(ProviderHttpInterface.class);
//        return providerHttpInterface;
//    }

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

//    @Bean
//    public ProviderHttpInterface restTemplateHttpInterface(RestTemplate restTemplate) {
////        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081"));
//        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://provider-service"));
//        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        ProviderHttpInterface providerHttpInterface = factory.createClient(ProviderHttpInterface.class);
//        return providerHttpInterface;
//    }

    @Bean
    public ProviderHttpInterface webClientHttpInterface() {
        WebClient webClient = WebClient.builder()
    //                                .baseUrl("http://localhost:8081")
                .baseUrl("http://provider-service")
                .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        ProviderHttpInterface providerHttpInterface = factory.createClient(ProviderHttpInterface.class);
        return providerHttpInterface;
    }

}

