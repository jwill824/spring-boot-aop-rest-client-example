package io.thingstead.lib.samurai.annotation;

import java.lang.annotation.Annotation;

public class DynamicApi implements Api {
    private String baseUrl;
    private String[] headerValues;
    private String[] headerKeys;

    public DynamicApi(String baseUrl, String[] headerValues, String[] headerKeys) {
        this.baseUrl = baseUrl;
        this.headerValues = headerValues;
        this.headerKeys = headerKeys;
    }

    @Override
    public String baseUrl() {
        return baseUrl;
    }

    @Override
    public String[] headerValues() {
        return headerValues;
    }

    @Override
    public String[] headerKeys() {
        return headerKeys;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Api.class;
    }
}
