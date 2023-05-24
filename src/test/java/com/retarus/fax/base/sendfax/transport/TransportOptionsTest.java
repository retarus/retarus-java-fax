package com.retarus.fax.base.sendfax.transport;

import com.retarus.fax.base.sendfax.transport.TransportOptions;
import com.retarus.fax.exception.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransportOptionsTest {

    @Test
    @DisplayName("Test builder")
    void testBuilder() {
        TransportOptions transportOptions = TransportOptions.builder()
                .csid("csid")
                .isExpress(true)
                .isDenyListed(true)
                .build();

        assertEquals("csid", transportOptions.getCsid());
        assertTrue(transportOptions.isExpress());
        assertTrue(transportOptions.isDenyListed());
    }

    @Test
    @DisplayName("Test builder with csid with length > 20")
    void testBuilderWithCsidWithLengthGreaterThan20() {
        Assertions.assertThrows(ApiException.class, () -> TransportOptions.builder()
                .csid("csidcsidcsidcsidcsidcsidcsidcsidcsidcsid")
                .build());
    }

    @Test
    @DisplayName("Test builder with null csid")
    void testBuilderWithNullCsid() {
        TransportOptions transportOptions = TransportOptions.builder()
                .csid(null)
                .build();

        assertNull(transportOptions.getCsid());
    }

}