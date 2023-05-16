package com.retarus.fax.exception;

/**
 * @author thiagon
 * <p>
 * Exception class for the authentication.
 * This exception is thrown when the authentication fails or missing credentials.
 */
public class AuthException extends RetarusException {
    public AuthException(final String message) {
        super(message);
    }

    public AuthException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
