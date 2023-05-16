package com.retarus.fax.base.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


/**
 * @author thiagon
 * <p>
 * Class for the properties of a fax cover page.
 * A cover page can be personalized for each individual recipient and then attached to the front of each fax document. *
 * This object allows specifying a value for each of the keys in the template.
 */

public class Property {
    /**
     * The key of the property.
     */
    @JsonProperty("key")
    private String key;

    /**
     * The value of the property.
     */
    @JsonProperty("value")
    private String value;

    /**
     * Constructor for the Property class.
     *
     * @param key   The key of the property.
     * @param value The value of the property.
     */
    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Property() {
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(key, property.key) && Objects.equals(value, property.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}