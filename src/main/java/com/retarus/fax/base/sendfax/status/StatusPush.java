package com.retarus.fax.base.sendfax.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.retarus.fax.exception.ApiException;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author thiagon
 * <p>
 * In order to receive notification via HTTP, the JobRequest must contain the httpStatusPush element.<br>
 * The URL to which the HTTP POST request is sent has to be specified.
 */
public class StatusPush {
    /**
     * The HTTP url to post the fax job status to (webhook). A default URL can be configured for the account.
     * It cannot be null or empty.
     */
    @JsonProperty("targetUrl")
    private final String url;

    /**
     * The username to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client id to obtain access tokens.
     */
    @JsonProperty("principal")
    private final String username;

    /**
     * The password to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client secret to obtain access tokens.
     */
    @JsonProperty("credentials")
    private final String password;

    /**
     * The authentication method for the status push. The default value is NONE.
     */
    @JsonProperty("authMethod")
    private final AuthenticationMethod authenticationMethod;

    /**
     * Constructor for StatusPush
     *
     * @param targetUrl            The HTTP url to post the fax job status to (webhook). A default URL can be configured for the account.
     * @param authenticationMethod The authentication method for the status push. The default value is NONE.
     * @param username             The username to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client id to obtain access tokens.
     * @param password             The password to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client secret to obtain access tokens.
     */
    private StatusPush(String targetUrl, AuthenticationMethod authenticationMethod, String username, String password) {
        this.url = targetUrl;
        this.authenticationMethod = authenticationMethod;
        this.username = username;
        this.password = password;
    }

    public static StatusPushBuilder builder() {
        return new StatusPushBuilder();
    }

    /**
     * @return The HTTP url to post the fax job status to (webhook). A default URL can be configured for the account.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return The username to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client id to obtain access tokens.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The password to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client secret to obtain access tokens.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return The authentication method for the status push. The default value is NONE.
     */
    @JsonProperty("authMethod")
    public AuthenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusPush that = (StatusPush) o;
        return Objects.equals(url, that.url) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && authenticationMethod == that.authenticationMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, username, password, authenticationMethod);
    }

    public static class StatusPushBuilder {
        private String url;
        private String username;
        private String password;
        private AuthenticationMethod authenticationMethod;

        private StatusPushBuilder() {
        }

        /**
         * The HTTP url to post the fax job status to (webhook). A default URL can be configured for the account.
         * <br>Example: <a>https://retarus.com/test-path/test-target</a>
         *
         * @param url The HTTP url to post the fax job status to (webhook). A default URL can be configured for the account.
         * @return StatusPushBuilder
         * @throws ApiException if url is null or empty
         */
        public StatusPushBuilder url(String url) {
            if (isBlank(url)) {
                throw new ApiException("target url cannot be null or empty");
            }
            this.url = url;
            return this;
        }

        /**
         * The username to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client id to obtain access tokens.
         *
         * @param username The username to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client id to obtain access tokens.
         * @return StatusPushBuilder
         */
        public StatusPushBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * The password to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client secret to obtain access tokens.
         *
         * @param password The password to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client secret to obtain access tokens.
         * @return StatusPushBuilder
         */
        public StatusPushBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * The authentication method for the status push. The default value is NONE.
         *
         * @param authenticationMethod The authentication method for the status push. The default value is NONE.
         * @return StatusPushBuilder
         */
        public StatusPushBuilder authenticationMethod(AuthenticationMethod authenticationMethod) {
            this.authenticationMethod = authenticationMethod;
            return this;
        }

        /**
         * Builds the StatusPush object
         *
         * @return StatusPush
         * @throws ApiException if url is null or empty
         */
        public StatusPush build() {
            if (isBlank(url)) {
                throw new ApiException("target url cannot be null or empty");
            }

            if (authenticationMethod == null) {
                authenticationMethod = AuthenticationMethod.NONE;
            }
            return new StatusPush(url, authenticationMethod, username, password);
        }

        public String toString() {
            return "StatusPush.StatusPushBuilder(url=" + this.url + ", username=" + this.username + ", password=" + this.password + ", authenticationMethod=" + this.authenticationMethod + ")";
        }
    }
}
