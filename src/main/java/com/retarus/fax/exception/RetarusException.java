package com.retarus.fax.exception;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * @author thiagon
 * <p>
 * Exception class for the RetarusFax API.
 * This is the base exception class for all RetarusFax API exceptions.
 * Contains the status code, request url, request payload and response body in case of an error, and they are available.
 */
public abstract class RetarusException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * The HTTP status code that was received from the API
     */
    private int statusCode;

    /**
     * The request url that was sent to the API
     */
    private String requestUrl;

    /**
     * The request payload (JSON body) that was sent to the API
     */
    private String requestPayload;

    /**
     * The response body (JSON body) that was received from the API
     */
    private String responseBody;

    protected RetarusException(final String message, final Throwable cause) {
        super(message, cause);
    }

    protected RetarusException(final String message) {
        this(message, null);
    }

    /**
     * Constructor for the RetarusFax exception.
     *
     * @param message        The message of the exception
     * @param cause          The cause of the exception
     * @param requestUrl     The request url that was sent to the API
     * @param requestPayload The request payload (JSON body) that was sent to the API
     * @param httpResponse   The HTTP response that was received from the API
     */
    protected RetarusException(final String message, final Throwable cause, final String requestUrl, final String requestPayload, HttpResponse httpResponse) {
        this(message, cause);
        this.requestUrl = requestUrl;
        this.requestPayload = requestPayload;
        if (httpResponse != null) {
            this.statusCode = httpResponse.getStatusLine().getStatusCode();
            try {
                this.responseBody = EntityUtils.toString(httpResponse.getEntity());
            } catch (Exception e) {
                this.responseBody = "";
            }
        } else {
            this.statusCode = 0;
            this.responseBody = "";
        }
    }

    @Override
    public String getMessage() {
        return String.format("Message: %s%nHTTP status code: %s%nHTTP request URL: %s%nHTTP request payload: %s%nHTTP response body: %s",
                super.getMessage(), this.getStatusCode(), this.getRequestUrl(), this.getRequestPayload(), this.getResponseBody());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
