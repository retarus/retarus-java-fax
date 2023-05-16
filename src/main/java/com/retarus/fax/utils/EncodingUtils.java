package com.retarus.fax.utils;

import java.util.Base64;

/**
 * Utility class for encoding.
 * Please note that this class is not part of the public API and may change without notice.
 * This can be removed or changed at any time to specific classes, based on the needs and requirements.
 *
 * @author thiagon
 */
public class EncodingUtils {

    private EncodingUtils() {
    }

    /**
     * Encode the username and password to a base64 string, to be used in the HTTP request.
     *
     * @param username the username
     * @param password the password
     * @return the encoded string
     */
    public static String encodeCredentialsToString(String username, String password) {
        String input = username + ":" + password;
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
}
