package io.thingstead.lib.samurai.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.thingstead.lib.samurai.handler.RestTemplateHandler;
import io.thingstead.lib.samurai.annotation.Api;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Component
public class RestHttpClientController {
    public <G> G get(JavaType javaType, String path, Api annotation, Object... args) {
        G result;
        ResponseEntity<String> response;
        RestTemplate restTemplate = new RestTemplate();

        String uri = RestTemplateHandler.buildUriStringFromArgs(annotation.baseUrl(), path, args);
        HttpEntity entity = RestTemplateHandler
            .createHttpEntityFromHeaders(annotation.headerKeys(), annotation.headerValues());

        try {
            if (args.length <= 0) {
                response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            } else {
                response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class, args);
            }
            result = new ObjectMapper().readValue(response.getBody(), javaType);
            // TODO: Put INFO logging here
        } catch (Exception e) {
            // TODO: Put ERROR logging here
            throw new RestClientException(format("%s -> %s", uri, e.getLocalizedMessage()));
        }

        return result;
    }
}
