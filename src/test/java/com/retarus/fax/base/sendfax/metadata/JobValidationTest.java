package com.retarus.fax.base.sendfax.metadata;

import com.retarus.fax.exception.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JobValidationTest {
    @Test
    void testBuilder() {
        JobValidation jobValidation = JobValidation.builder()
                .startTime("2023-03-23T08:00:00Z")
                .expiryTime("2023-03-23T10:00:00Z")
                .build();

        Assertions.assertEquals("2023-03-23T08:00:00Z", jobValidation.getStart());
        Assertions.assertEquals("2023-03-23T10:00:00Z", jobValidation.getEnd());
    }

    @Test
    void testInvalidDateFormat() {
        Assertions.assertThrows(ApiException.class, () -> JobValidation.builder()
                .startTime("2023-03-25")
                .expiryTime("2010-01-01T12:00:00+01:00")
                .build());

        Assertions.assertThrows(ApiException.class, () -> JobValidation.builder()
                .startTime("2023-03-23T08:00:00Z")
                .expiryTime("2023-03-25")
                .build());
    }

    @Test
    @DisplayName("Test invalid duration")
    void testInvalidDuration() {
        Assertions.assertThrows(ApiException.class, () -> JobValidation.builder()
                .startTime("P1Y2M10DT2H30M")
                .expiryTime("2010-01-01T12:00:00+01:00")
                .build());

        Assertions.assertThrows(ApiException.class, () -> JobValidation.builder()
                .startTime("2023-03-23T08:00:00Z")
                .expiryTime("P1Y2M10DT2H30M")
                .build());
    }

}