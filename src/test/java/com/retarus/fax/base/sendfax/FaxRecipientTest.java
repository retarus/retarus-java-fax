package com.retarus.fax.base.sendfax;

import com.retarus.fax.base.common.Property;
import com.retarus.fax.exception.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FaxRecipientTest {

    @Test
    @DisplayName("Test builder with fax number")
    void testBuilderWithFaxNumber() {
        String faxNumber = "123456789";
        FaxRecipient faxRecipient = FaxRecipient.builder()
                .faxNumber(faxNumber)
                .build();
        assertEquals(faxNumber, faxRecipient.getFaxNumber());
    }

    @Test
    @DisplayName("Test builder with fax number and properties")
    void testBuilderWithFaxNumberAndProperties() {
        String faxNumber = "123456789";
        FaxRecipient faxRecipient = FaxRecipient.builder()
                .faxNumber(faxNumber)
                .property(new Property("key", "value"))
                .build();
        assertEquals(faxNumber, faxRecipient.getFaxNumber());
    }

    @Test
    @DisplayName("Test builder with fax number, properties and property")
    void testBuilderWithFaxNumberPropertiesAndProperty() {
        String faxNumber = "123456789";
        Property property1 = new Property("key", "value");
        List<Property> propertyList = Collections.singletonList(property1);
        Property property2 = new Property("key2", "value2");
        FaxRecipient faxRecipient = FaxRecipient.builder()
                .faxNumber(faxNumber)
                .properties(propertyList)
                .property(property2)
                .build();
        assertEquals(faxNumber, faxRecipient.getFaxNumber());
        assertEquals(2, faxRecipient.getProperties().size());
        assertTrue(faxRecipient.getProperties().contains(property1));
        assertTrue(faxRecipient.getProperties().contains(property2));
    }

    @Test
    @DisplayName("Test builder with fax number, properties and property pair")
    void testBuilderWithFaxNumberPropertiesAndPropertyPair() {
        String faxNumber = "123456789";
        Property property1 = new Property("key", "value");
        List<Property> propertyList = Collections.singletonList(property1);
        Property property2 = new Property("key2", "value2");
        FaxRecipient faxRecipient = FaxRecipient.builder()
                .faxNumber(faxNumber)
                .properties(propertyList)
                .property("key2", "value2")
                .build();
        assertEquals(faxNumber, faxRecipient.getFaxNumber());
        assertEquals(2, faxRecipient.getProperties().size());
        assertTrue(faxRecipient.getProperties().contains(property1));
        assertTrue(faxRecipient.getProperties().contains(property2));
    }

    @Test
    @DisplayName("Test builder with null fax number and validate that an exception is thrown")
    void testBuilderWithNullFaxNumber() {
        assertThrows(ApiException.class, () -> {
            FaxRecipient.builder()
                    .faxNumber(null)
                    .build();
        });
    }

    @Test
    @DisplayName("Test builder with fax number and null properties and validate that an exception is not thrown")
    void testBuilderWithFaxNumberAndNullProperties() {
        String faxNumber = "123456789";
        Assertions.assertDoesNotThrow(() -> FaxRecipient.builder()
                .faxNumber(faxNumber)
                .properties(null)
                .build());
    }

    @Test
    @DisplayName("Test empty builder and validate that an exception is thrown")
    void testEmptyBuilder() {
        assertThrows(ApiException.class, () -> {
            FaxRecipient.builder()
                    .build();
        });
    }

}