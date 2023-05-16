package com.retarus.fax.base.sendfax;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.base.common.Property;
import lombok.Builder.ObtainVia;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author thiagon
 * <p>
 * Receipient fax status information.
 * <p>
 * If a fax can be successfully sent to one of the two fax numbers, the transmission to the respective recipient is completed and considered successful.
 * The destination number which received the fax will be indicated in the report data for each recipient under RecipientStatus - sentToNumber.
 */
@JsonIgnoreProperties({"alternativeNumbers"})
public class FaxRecipient {

    /**
     * The fax recipients primary number (international format, e.g., +49891234678).
     */
    @JsonProperty("number")
    private final String faxNumber;


    /**
     * Personalized data used for the cover page.
     */
    @ObtainVia(method = "addProperty")
    @JsonProperty("properties")
    private final ArrayList<Property> properties;


    /**
     * Constructor for FaxRecipient
     *
     * @param faxNumber  The fax recipients primary number (international format, e.g., +49891234678).
     * @param properties Personalized data used for the cover page.
     */
    private FaxRecipient(String faxNumber, ArrayList<Property> properties) {
        this.faxNumber = faxNumber;
        this.properties = properties;
    }

    /**
     * Constructor for FaxRecipient
     * @return FaxRecipient builder
     */
    public static FaxRecipientBuilder builder() {
        return new FaxRecipientBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaxRecipient that = (FaxRecipient) o;
        return Objects.equals(faxNumber, that.faxNumber) && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faxNumber, properties);
    }

    /**
     * @return The fax recipients primary number (international format, e.g., +49891234678).
     */
    public String getFaxNumber() {
        return faxNumber;
    }

    /**
     * @return Personalized data used for the cover page.
     */
    public List<Property> getProperties() {
        return properties;
    }

    public static class FaxRecipientBuilder {
        private String faxNumber;
        private ArrayList<Property> properties;

        /**
         * Constructor for FaxRecipientBuilder
         */
        private FaxRecipientBuilder() {

        }

        /**
         * @param faxNumber The fax recipients primary number (international format, e.g., +49891234678).
         * @throws ApiException if faxNumber is null or empty
         * @return the fax recipient object builder
         */
        public FaxRecipientBuilder faxNumber(String faxNumber) {
            if (isBlank(faxNumber)) {
                throw new ApiException("faxNumber must not be null or empty");
            }
            this.faxNumber = faxNumber;
            return this;
        }

        /**
         * Add a property list to the fax recipient.
         *
         * @param properties The list of properties.
         * @return the fax recipient object builder
         */
        public FaxRecipientBuilder properties(List<Property> properties) {
            if (properties == null) {
                return this;
            }

            if (this.properties == null) {
                this.properties = new ArrayList<>();
            }

            this.properties.addAll(properties);
            return this;
        }

        /**
         * Add a property to the fax recipient properties list.
         *
         * @param property A property to add to the list.
         * @return the fax recipient object builder
         */
        public FaxRecipientBuilder property(Property property) {
            if (property == null) {
                return this;
            }

            if (this.properties == null) {
                this.properties = new ArrayList<>();
            }

            this.properties.add(property);
            return this;
        }

        /**
         * Add a property to the fax recipient properties list.
         * <br> Key and value must not be null.
         *
         * @param key   The key of the property.
         * @param value The value of the property.
         * @return the fax recipient object builder
         */
        public FaxRecipientBuilder property(String key, String value) {
            if (isBlank(key) || isBlank(value)) {
                return this;
            }

            if (this.properties == null) {
                this.properties = new ArrayList<>();
            }

            this.properties.add(new Property(key, value));
            return this;
        }

        /**
         * Default builder constructor
         *
         * @return the fax recipient object
         * @throws ApiException if faxNumber is null or empty
         */
        public FaxRecipient build() {
            if (isBlank(faxNumber)) {
                throw new ApiException("faxNumber must not be null or empty");
            }
            return new FaxRecipient(faxNumber, properties);
        }

        public String toString() {
            return "FaxRecipient.FaxRecipientBuilder(faxNumber=" + this.faxNumber + ", properties=" + this.properties + ")";
        }
    }
}
