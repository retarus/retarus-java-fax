package com.retarus.fax.base.sendfax.metadata;

import com.retarus.fax.base.sendfax.metadata.JobValidation;
import com.retarus.fax.base.sendfax.metadata.Metadata;
import com.retarus.fax.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetadataTest {
    private Metadata metadata;
    private String customerReference;
    private String start;
    private String end;

    @BeforeEach
    void setUp() {
        customerReference = "testCustomerReference";
        start = "2022-03-23T12:34:56Z";
        end = "2022-03-24T12:34:56Z";
        metadata = Metadata.builder()
                .customerReference(customerReference)
                .jobValidation(JobValidation.builder().startTime(start).expiryTime(end).build())
                .build();
    }

    @Test
    @DisplayName("Test that metadata with valid input values is created correctly")
    void testMetadataCreatedSuccessfully() {
        assertEquals(customerReference, metadata.getCustomerReference());
        assertNotNull(metadata.getJobValidation());
        assertEquals(start, metadata.getJobValidation().getStart());
        assertEquals(end, metadata.getJobValidation().getEnd());
    }

    @Test
    @DisplayName("Test that metadata with null customer reference throws an exception")
    void testMetadataWithNullCustomerReferenceThrowsException() {
        assertThrows(ApiException.class, () -> Metadata.builder().customerReference(null).build());
    }

    @Test
    @DisplayName("Test that metadata with invalid start date throws an exception")
    void testMetadataWithInvalidStartDateThrowsException() {
        assertThrows(ApiException.class, () -> Metadata.builder().jobValidation("2022-03-23", end).build());
    }

    @Test
    @DisplayName("Test that metadata with invalid end date throws an exception")
    void testMetadataWithInvalidEndDateThrowsException() {
        assertThrows(ApiException.class, () -> Metadata.builder().jobValidation(start, "2022-03-24").build());
    }

}