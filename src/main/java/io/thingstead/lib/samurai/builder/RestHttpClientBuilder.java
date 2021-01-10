package io.thingstead.lib.samurai.builder;

import javassist.util.proxy.ProxyFactory;
import io.thingstead.lib.samurai.annotation.Api;
import io.thingstead.lib.samurai.annotation.DynamicApi;
import org.springframework.http.HttpHeaders;

import static io.thingstead.lib.samurai.handler.AnnotationHandler.alterAnnotationValueJDK8;
import static io.thingstead.lib.samurai.handler.JavaStreamHandler.convertCollectionToStringArray;
import static io.thingstead.lib.samurai.handler.JavaStreamHandler.convertSetToStringArray;

public class RestHttpClientBuilder {
    private String baseUrl;
    private HttpHeaders httpHeaders = new HttpHeaders();

    public RestHttpClientBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RestHttpClientBuilder header(String key, String value) {
        httpHeaders.set(key, value);
        return this;
    }

    public <T> T build(Class<T> clazz) {
        ProxyFactory factory = new ProxyFactory();
        factory.setInterfaces(new Class[]{clazz});
        try {
            //noinspection unchecked
            Class<T> object = factory.createClass();
            String[] values = convertCollectionToStringArray(httpHeaders.values());
            String[] keys = convertSetToStringArray(httpHeaders.keySet());
            Api targetClass = new DynamicApi(baseUrl, values, keys);
            alterAnnotationValueJDK8(clazz, Api.class, targetClass);
            return object.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO: Put ERROR logging here
            throw new RuntimeException(e);
        }
    }
}
