package com.retarus.fax.base;

import com.retarus.fax.base.rest.URLProvider;

public class ApiResponse<T> {

    private final int statusCode;
    private final T value;
    private final URLProvider urlProvider;

    public ApiResponse(T value, int statusCode, URLProvider urlProvider) {
        this.value = value;
        this.statusCode = statusCode;
        this.urlProvider = urlProvider;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getValue() {
        return value;
    }

    public URLProvider getURLProvider() {
        return urlProvider;
    }

}