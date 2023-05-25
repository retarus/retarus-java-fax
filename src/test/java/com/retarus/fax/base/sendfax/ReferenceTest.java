package com.retarus.fax.base.sendfax;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReferenceTest {

    @Test
    @DisplayName("Test reference builder with values")
    void testReferenceBuilderWithValues() {
        String customerDefinedId = "customerDefinedId";
        String billingCode = "billingCode";
        String billingInfo = "billingInfo";
        Reference reference = Reference.builder()
                .customerDefinedId(customerDefinedId)
                .billingCode(billingCode)
                .billingInfo(billingInfo)
                .build();
        assertEquals(customerDefinedId, reference.getCustomerDefinedId());
        assertEquals(billingCode, reference.getBillingCode());
        assertEquals(billingInfo, reference.getBillingInfo());


    }

    @Test
    @DisplayName("Test reference builder with null values")
    void testReferenceBuilderWithNullValues() {
        String customerDefinedId = null;
        String billingCode = null;
        String billingInfo = null;
        Reference reference = Reference.builder()
                .customerDefinedId(customerDefinedId)
                .billingCode(billingCode)
                .billingInfo(billingInfo)
                .build();
        assertNull(reference.getCustomerDefinedId());
        assertNull(reference.getBillingCode());
        assertNull(reference.getBillingInfo());
    }

    @Test
    @DisplayName("Test reference builder with empty values")
    void testReferenceBuilderWithEmptyValues() {
        String customerDefinedId = "";
        String billingCode = "";
        String billingInfo = "";
        Reference reference = Reference.builder()
                .customerDefinedId(customerDefinedId)
                .billingCode(billingCode)
                .billingInfo(billingInfo)
                .build();
        assertEquals(customerDefinedId, reference.getCustomerDefinedId());
        assertEquals(billingCode, reference.getBillingCode());
        assertEquals(billingInfo, reference.getBillingInfo());
    }

    @Test
    @DisplayName("Test reference builder with null customerDefinedId")
    void testReferenceBuilderWithNullCustomerDefinedId() {
        String customerDefinedId = null;
        String billingCode = "billingCode";
        String billingInfo = "billingInfo";
        Reference reference = Reference.builder()
                .customerDefinedId(customerDefinedId)
                .billingCode(billingCode)
                .billingInfo(billingInfo)
                .build();
        assertNull(reference.getCustomerDefinedId());
        assertEquals(billingCode, reference.getBillingCode());
        assertEquals(billingInfo, reference.getBillingInfo());
    }

    @Test
    @DisplayName("Test reference builder with null billingCode")
    void testReferenceBuilderWithNullBillingCode() {
        String customerDefinedId = "customerDefinedId";
        String billingCode = null;
        String billingInfo = "billingInfo";
        Reference reference = Reference.builder()
                .customerDefinedId(customerDefinedId)
                .billingCode(billingCode)
                .billingInfo(billingInfo)
                .build();
        assertEquals(customerDefinedId, reference.getCustomerDefinedId());
        assertNull(reference.getBillingCode());
        assertEquals(billingInfo, reference.getBillingInfo());
    }

    @Test
    @DisplayName("Test reference builder with null billingInfo")
    void testReferenceBuilderWithNullBillingInfo() {
        String customerDefinedId = "customerDefinedId";
        String billingCode = "billingCode";
        String billingInfo = null;
        Reference reference = Reference.builder()
                .customerDefinedId(customerDefinedId)
                .billingCode(billingCode)
                .billingInfo(billingInfo)
                .build();
        assertEquals(customerDefinedId, reference.getCustomerDefinedId());
        assertEquals(billingCode, reference.getBillingCode());
        assertNull(reference.getBillingInfo());
    }

    @Test
    @DisplayName("Test empty reference builder")
    void testEmptyReferenceBuilder() {
        Reference reference = Reference.builder().build();
        assertNull(reference.getCustomerDefinedId());
        assertNull(reference.getBillingCode());
        assertNull(reference.getBillingInfo());
    }


}