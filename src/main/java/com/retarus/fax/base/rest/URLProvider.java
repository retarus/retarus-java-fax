package com.retarus.fax.base.rest;

import com.retarus.fax.exception.ApiException;

public interface URLProvider {
    String getSendUrl();

    String getFetchUrl();
    Location[] getLocations();

    default URLProvider getLocale() {
        return this;
    }

    static URLProvider getURLProviderFromString(String value) {
        try {
            return Region.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // The value is not a Location, try Region
            try {
                return Location.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException ex) {
                // The value is not a Region or a Location
                throw new ApiException("Invalid location/region provided: " + value);
            }
        }
    }
}