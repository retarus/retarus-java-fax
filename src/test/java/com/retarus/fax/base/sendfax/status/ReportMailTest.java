package com.retarus.fax.base.sendfax.status;

import com.retarus.fax.base.sendfax.status.FaxImageFormat;
import com.retarus.fax.base.sendfax.status.FaxImageMode;
import com.retarus.fax.base.sendfax.status.ReportMail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportMailTest {

    @Test
    @DisplayName("Test builder method")
     void testBuilder() {
        ReportMail reportMail = ReportMail.builder()
                .successAddress("success@example.com")
                .failureAddress("failure@example.com")
                .attachedFaxImageMode(FaxImageMode.ALWAYS)
                .attachedFaxImageFormat(FaxImageFormat.PDF)
                .build();

        assertEquals("success@example.com", reportMail.getSuccessAddress());

        assertEquals("failure@example.com", reportMail.getFailureAddress());

        assertEquals(FaxImageMode.ALWAYS, reportMail.getAttachedFaxImageMode());

        assertEquals(FaxImageFormat.PDF, reportMail.getAttachedFaxImageFormat());
    }

    @Test
    @DisplayName("Test equals and hashcode methods")
     void testHashCode() {

        ReportMail reportMail = ReportMail.builder()
                .successAddress("success@example.com")
                .failureAddress("failure@example.com")
                .attachedFaxImageMode(FaxImageMode.ALWAYS)
                .attachedFaxImageFormat(FaxImageFormat.PDF)
                .build();

        ReportMail other = ReportMail.builder()
                .successAddress("success@example.com")
                .failureAddress("failure@example.com")
                .attachedFaxImageMode(FaxImageMode.ALWAYS)
                .attachedFaxImageFormat(FaxImageFormat.PDF)
                .build();

        assertEquals(reportMail.hashCode(), other.hashCode());
        assertEquals(reportMail, other);
    }

    @Test
    @DisplayName("Test equals method")
     void testNullScenario() {
        assertDoesNotThrow(() -> {
            ReportMail.builder()
                    .successAddress(null)
                    .failureAddress(null)
                    .attachedFaxImageMode(null)
                    .attachedFaxImageFormat(null)
                    .build();
        });
    }

}