package com.retarus.fax.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class ValidationUtils {

    public static final DateTimeFormatter ISO_FORMATTER = ISO_INSTANT;

    private ValidationUtils() {
    }

    /**
     * Validate if a String is a valid ISO 8601 date
     *
     * @param input the String to validate
     *              throws an ApiException if the String is not a valid ISO 8601 date
     * @return true if the String is a valid ISO 8601 date, false otherwise
     */
    public static boolean isIso8601ValidDate(String input) {
        try {
            ISO_FORMATTER.parse(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Convert an Instant to a String in ISO 8601 format
     *
     * @param instant the Instant to convert
     * @return the String in ISO 8601 format
     */
    public static String convertInstantToIso8601String(Instant instant) {
        return ISO_FORMATTER.format(instant);
    }

    /**
     * Validate if a String is a valid duration
     *
     * @param input the String to validate
     *              throws an ApiException if the String is not a valid duration
     * @return true if the String is a valid duration, false otherwise
     */
    public static boolean isStringValidDuration(String input) {
        try {
            Duration.parse(input);
            return true;
        } catch (Exception e2) {
            return false;
        }
    }
}
