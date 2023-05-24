package com.retarus.fax.base.sendfax.status;

import com.retarus.fax.base.sendfax.status.AuthenticationMethod;
import com.retarus.fax.base.sendfax.status.StatusPush;
import com.retarus.fax.exception.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StatusPushTest {

    @Test
    @DisplayName("Test builder")
    void testBuilder() {
        StatusPush statusPush = StatusPush.builder()
                .url("https://example.com")
                .username("username")
                .password("password")
                .build();

        assertEquals("https://example.com", statusPush.getUrl());
        assertEquals("username", statusPush.getUsername());
        assertEquals("password", statusPush.getPassword());
        assertEquals(AuthenticationMethod.NONE, statusPush.getAuthenticationMethod());
    }

    @Test
    @DisplayName("Test builder with authentication method")
    void testBuilderWithAuthenticationMethod() {
        StatusPush statusPush = StatusPush.builder()
                .url("https://example.com")
                .username("username")
                .password("password")
                .authenticationMethod(AuthenticationMethod.HTTP_BASIC)
                .build();

        assertEquals("https://example.com", statusPush.getUrl());
        assertEquals("username", statusPush.getUsername());
        assertEquals("password", statusPush.getPassword());
        assertEquals(AuthenticationMethod.HTTP_BASIC, statusPush.getAuthenticationMethod());
    }

    @Test
    @DisplayName("Test builder with null values")
    void testBuilderWithNullValues() {
        Assertions.assertThrows(ApiException.class, () -> StatusPush.builder()
                .url(null)
                .username(null)
                .password(null)
                .authenticationMethod(null)
                .build());
    }

    @Test
    @DisplayName("Test builder with empty values")
    void testBuilderWithEmptyValues() {
        Assertions.assertThrows(ApiException.class, () -> StatusPush.builder()
                .url("")
                .username("")
                .password("")
                .authenticationMethod(null)
                .build());
    }

    //Test builder with only URL
    @Test
    @DisplayName("Test builder with URL only")
    void testBuilderWithURLOnly() {
        StatusPush statusPush = StatusPush.builder()
                .url("https://example.com")
                .build();

        assertEquals("https://example.com", statusPush.getUrl());
        assertNull( statusPush.getUsername());
        assertNull( statusPush.getPassword());
        assertEquals(AuthenticationMethod.NONE, statusPush.getAuthenticationMethod());
    }

}