package com.retarus.fax.http;

import com.retarus.fax.base.common.HttpMethod;
import org.apache.http.HttpResponse;

import java.io.IOException;

public interface ApiClient {

    HttpResponse sendRequest(HttpMethod httpMethod, String url, String jsonPayload) throws IOException;

    HttpResponse sendRequest(HttpMethod httpMethod, String url) throws IOException;

    String getUsername();

    String getPassword();

    String getCustomerNumber();
}
