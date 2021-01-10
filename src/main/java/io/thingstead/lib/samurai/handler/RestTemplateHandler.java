package io.thingstead.lib.samurai.handler;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static io.thingstead.lib.samurai.handler.JavaStreamHandler.zipToMap;

public class RestTemplateHandler {
    public static String buildUriStringFromArgs(String baseUrl, String path, Object... args) {
        String uri;

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl).path(path);
        if (args.length <= 0) {
            uri = uriComponentsBuilder.toUriString();
        } else {
            List<String> params = Arrays.stream(path.split("/"))
                    .filter(val -> valueOf(val).matches("\\{([^}]*.?)}"))
                    .map(val -> val.substring(1, val.length() - 1))
                    .collect(Collectors.toList());
            List<Object> values = Arrays.asList(args);
            Map<String, Object> uriParams = zipToMap(params, values);
            uri = uriComponentsBuilder.buildAndExpand(uriParams).toUriString();
        }

        return uri;
    }

    public static HttpEntity createHttpEntityFromHeaders(String[] headerKeys, String[] headerValues) {
        Map<String, String> headers = zipToMap(Arrays.asList(headerKeys), Arrays.asList(headerValues));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return new HttpEntity<>(httpHeaders);
    }
}
