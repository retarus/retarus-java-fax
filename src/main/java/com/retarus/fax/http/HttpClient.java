package com.retarus.fax.http;

import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.utils.EncodingUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * @author thiagon
 * <p>
 * Class used to send http requests to the RetarusFax API.
 */
public class HttpClient {

    private HttpClient() {
    }

    /**
     * Private constructor to prevent instantiation.
     *
     * @param username   The username used for authentication.
     * @param password   The password used for authentication.
     * @param httpMethod The http method to use.
     * @param requestUrl The url to send the request to.
     * @return The response from the Retarus Fax API.
     * @throws IOException If an error occurs while sending the request.
     */
    protected static HttpResponse sendRequest(String username, String password, HttpMethod httpMethod, String requestUrl) throws IOException {
        return sendRequest(username, password, httpMethod, requestUrl, null);
    }

    /**
     * Send a request to the Retarus Fax API, using the given username and password for authentication.
     *
     * @param username    The username used for authentication.
     * @param password    The password used for authentication.
     * @param httpMethod  The http method to use.
     * @param requestUrl  The url to send the request to.
     * @param jsonPayload The json payload to send with the request.
     * @return The response from the Retarus Fax API.
     * @throws IOException If an error occurs while sending the request.
     */
    protected static HttpResponse sendRequest(String username, String password, HttpMethod httpMethod, String requestUrl, String jsonPayload) throws IOException {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            // Create the http request and set authorization header to base64 encoded authentication information
            HttpUriRequest httpRequest = generateHttpRequest(httpMethod, requestUrl);
            addCredentialsToRequestHeader(username, password, httpRequest);

            if (httpMethod == HttpMethod.POST) {
                ((HttpPost) httpRequest).setEntity(new StringEntity(jsonPayload, ContentType.APPLICATION_JSON));
            }

            // Execute the HttpGet request and extract the response body
            return httpClient.execute(httpRequest);
        }
    }

    /**
     * Add the authorization header to the request, containing the base64 encoded username and password.
     *
     * @param username The username to encode.
     * @param password The password to encode.
     */
    protected static void addCredentialsToRequestHeader(String username, String password, HttpUriRequest httpRequest) {
        String authHeader = "Basic " + EncodingUtils.encodeCredentialsToString(username, password);
        httpRequest.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
    }

    /**
     * Generate the http request based on the given http method.
     *
     * @param httpMethod The http method to use.
     * @param requestUrl The url to send the request to.
     * @return The http request.
     */
    protected static HttpUriRequest generateHttpRequest(HttpMethod httpMethod, String requestUrl) {
        switch (httpMethod) {
            case GET:
                return new HttpGet(requestUrl);
            case POST:
                return new HttpPost(requestUrl);
            case DELETE:
                return new HttpDelete(requestUrl);
            default:
                throw new IllegalArgumentException("Http method not supported.");
        }
    }
}
