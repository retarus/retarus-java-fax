package com.retarus.fax.utils;

import com.retarus.fax.utils.ValidationUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.retarus.fax.utils.ValidationUtils.convertInstantToIso8601String;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void testIso8601ValidDate() {
        String validDate = "2023-03-27T15:25:00Z";
        assertTrue(ValidationUtils.isIso8601ValidDate(validDate));

        String invalidDate = "2023-03-27T25:25:00Z";
        assertFalse(ValidationUtils.isIso8601ValidDate(invalidDate));
    }

    @Test
    void testStringValidDuration() {
        String validDuration = "P3DT12H30M5S";
        assertTrue(ValidationUtils.isStringValidDuration(validDuration));

        String invalidDuration = "P3DT12H30X5S";
        assertFalse(ValidationUtils.isStringValidDuration(invalidDuration));
    }

    @Test
    void testIso8601ValidDateWithNullInput() {
        assertFalse(ValidationUtils.isIso8601ValidDate(null));
    }

    @Test
    void testStringValidDurationWithNullInput() {
        assertFalse(ValidationUtils.isStringValidDuration(null));
    }

    @Test
    void testIso8601ValidDateWithEmptyInput() {
        assertFalse(ValidationUtils.isIso8601ValidDate(""));
    }

    @Test
    void testStringValidDurationWithEmptyInput() {
        assertFalse(ValidationUtils.isStringValidDuration(""));
    }

    @Test
    public void testInstantToString() {
        Instant instant = Instant.now();
        String expectedString = instant.toString();
        String actualString = convertInstantToIso8601String(instant);
        assertEquals(expectedString, actualString);
        System.out.println(actualString);
    }
}