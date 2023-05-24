package com.retarus.fax.base.sendfax.status;

import com.retarus.fax.base.sendfax.status.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StatusReportOptionsTest {

    @Test
    @DisplayName("Test builder with status push")
    void testBuilder() {
        StatusReportOptions statusReportOptions = StatusReportOptions.builder()
                .statusPush(StatusPush.builder()
                        .url("https://example.com")
                        .username("username")
                        .password("password")
                        .build())
                .build();

        assertEquals("https://example.com", statusReportOptions.getStatusPush().getUrl());
        assertEquals("username", statusReportOptions.getStatusPush().getUsername());
        assertEquals("password", statusReportOptions.getStatusPush().getPassword());
        assertEquals(AuthenticationMethod.NONE, statusReportOptions.getStatusPush().getAuthenticationMethod());
    }

    //Test builder with report mail
    @Test
    @DisplayName("Test builder with report mail")
    void testBuilderWithReportMail() {
        StatusReportOptions statusReportOptions = StatusReportOptions.builder()
                .reportMail(ReportMail.builder()
                        .successAddress("email@email.com")
                        .failureAddress("email@email.com")
                        .attachedFaxImageMode(FaxImageMode.ALWAYS)
                        .attachedFaxImageFormat(FaxImageFormat.PDF)
                        .build())
                .build();

        assertEquals("email@email.com", statusReportOptions.getReportMail().getSuccessAddress());
        assertEquals("email@email.com", statusReportOptions.getReportMail().getFailureAddress());
        assertEquals(FaxImageMode.ALWAYS, statusReportOptions.getReportMail().getAttachedFaxImageMode());
        assertEquals(FaxImageFormat.PDF, statusReportOptions.getReportMail().getAttachedFaxImageFormat());
    }

    //Test builder with report mail and status push
    @Test
    @DisplayName("Test builder with report mail and status push")
    void testBuilderWithReportMailAndStatusPush() {
        StatusReportOptions statusReportOptions = StatusReportOptions.builder()
                .statusPush(StatusPush.builder()
                        .url("https://example.com")
                        .username("username")
                        .password("password")
                        .authenticationMethod(AuthenticationMethod.HTTP_DIGEST)
                        .build())
                .reportMail(ReportMail.builder()
                        .successAddress("email@email.com")
                        .failureAddress("email@email.com")
                        .attachedFaxImageMode(FaxImageMode.ALWAYS)
                        .attachedFaxImageFormat(FaxImageFormat.PDF)
                        .build())
                .build();

        assertEquals("https://example.com", statusReportOptions.getStatusPush().getUrl());
        assertEquals("username", statusReportOptions.getStatusPush().getUsername());
        assertEquals("password", statusReportOptions.getStatusPush().getPassword());
        assertEquals(AuthenticationMethod.HTTP_DIGEST, statusReportOptions.getStatusPush().getAuthenticationMethod());

        assertEquals("email@email.com", statusReportOptions.getReportMail().getSuccessAddress());
        assertEquals("email@email.com", statusReportOptions.getReportMail().getFailureAddress());
        assertEquals(FaxImageMode.ALWAYS, statusReportOptions.getReportMail().getAttachedFaxImageMode());
        assertEquals(FaxImageFormat.PDF, statusReportOptions.getReportMail().getAttachedFaxImageFormat());
    }

    //Test builder with null values
    @Test
    @DisplayName("Test builder with null values")
    void testBuilderWithNullValues() {
        StatusReportOptions statusReportOptions = StatusReportOptions.builder()
                .statusPush(null)
                .reportMail(null)
                .build();

        assertNull(statusReportOptions.getStatusPush());
        assertNull(statusReportOptions.getReportMail());
    }
}