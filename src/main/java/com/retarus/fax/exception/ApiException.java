package com.retarus.fax.exception;

import org.apache.http.HttpResponse;

/**
 * @author thiagon
 * <p>
 * Exception class for the API.
 * This exception is thrown when the API returns an error, either because of a bad request or a server error.
 * Additionally, it is also thrown when a field, or more, are not filled correctly.
 */
public class ApiException extends RetarusException {
    public ApiException(final String message) {
        super(message);
    }

    public ApiException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ApiException(final String message,final Throwable cause, final String requestUrl, final String requestPayload, HttpResponse httpResponse) {
        super(message, cause,  requestUrl, requestPayload, httpResponse);
    }
}
