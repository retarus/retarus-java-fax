package com.retarus.fax.base.sendfax.status;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author thiagon
 *
 * Enum for the authentication method for the status push.
 * In order to use OAUTH2, the authorization server URL must be configured in advance for the account.
 *<p>
 * Possible values are:
 * <p>
 * NONE: no authentication<p>
 * HTTP_BASIC: HTTP basic authentication<p>
 * HTTP_DIGEST: HTTP digest authentication<p>
 * OAUTH: OAuth authentication
 */
public enum AuthenticationMethod {
    NONE, HTTP_BASIC, HTTP_DIGEST, OAUTH;

    @JsonValue
    public String getAuthMethod() {
        return name();
    }
}
