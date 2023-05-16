package com.retarus.fax.base.responses.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.retarus.fax.base.common.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author thiagon
 * <p>
 * Class for the recipient status of a fax that is part of a job get report.
 * Contains all the sub-objects of the recipient status.
 */
@JsonDeserialize(builder = RecipientStatus.RecipientStatusBuilder.class)
public class RecipientStatus {

    /**
     * The fax recipient's primary number (international format, e.g., +49891234678)
     */
    private final String recipientNumber;

    /**
     * Personalized properties used for the cover page.
     */
    private final List<Property> propertiesList;

    //Job status of the fax.
    /**
     * Job status of the fax.
     */
    private final String status;

    /**
     * Explanation of the status.
     */
    private final String reason;

    //Timestamp which indicates when the fax was sent (in ISO 8601 format).
    /**
     * Timestamp which indicates when the fax was sent (in ISO 8601 format).
     */
    private final String sentTimestamp;

    //Duration of the fax transmission until received by the fax recipient.
    /**
     * Duration of the fax transmission until received by the fax recipient.
     */
    private final Integer durationInSeconds;

    //The number to which the fax was sent.
    /**
     * The number to which the fax was sent.
     */
    private final String sentToNumber;

    /**
     * Fax ID which identifies the fax recipient.
     */
    private final String remoteCsid;

    /**
     * Constructor for the RecipientStatus class.
     *
     * @param recipientNumber   The fax recipient's primary number (international format, e.g., +49891234678)
     * @param propertiesList    Personalized properties used for the cover page.
     * @param status            Job status of the fax.
     * @param reason            Explanation of the status.
     * @param sentTimestamp     Timestamp which indicates when the fax was sent (in ISO 8601 format).
     * @param durationInSeconds Duration of the fax transmission until received by the fax recipient.
     * @param sentToNumber      The number to which the fax was sent.
     * @param remoteCsid        Fax ID which identifies the fax recipient.
     */
    private RecipientStatus(String recipientNumber, List<Property> propertiesList, String status, String reason, String sentTimestamp, Integer durationInSeconds, String sentToNumber, String remoteCsid) {
        this.recipientNumber = recipientNumber;
        this.propertiesList = propertiesList;
        this.status = status;
        this.reason = reason;
        this.sentTimestamp = sentTimestamp;
        this.durationInSeconds = durationInSeconds;
        this.sentToNumber = sentToNumber;
        this.remoteCsid = remoteCsid;
    }

    /**
     * @return The fax recipient's primary number (international format, e.g., +49891234678)
     */
    public String getRecipientNumber() {
        return recipientNumber;
    }

    /**
     * @return Personalized properties used for the cover page.
     */
    public List<Property> getPropertiesList() {
        return propertiesList;
    }

    /**
     * @return Job status of the fax.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return Explanation of the status.
     */
    public String getReason() {
        return reason;
    }

    /**
     * @return Timestamp which indicates when the fax was sent (in ISO 8601 format).
     */
    public String getSentTimestamp() {
        return sentTimestamp;
    }

    /**
     * @return Duration of the fax transmission until received by the fax recipient.
     */
    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * @return The number to which the fax was sent.
     */
    public String getSentToNumber() {
        return sentToNumber;
    }

    /**
     * @return Fax ID which identifies the fax recipient.
     */
    public String getRemoteCsid() {
        return remoteCsid;
    }

    public static RecipientStatusBuilder builder() {
        return new RecipientStatusBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipientStatus that = (RecipientStatus) o;
        return Objects.equals(recipientNumber, that.recipientNumber) && Objects.equals(propertiesList, that.propertiesList) && Objects.equals(status, that.status) && Objects.equals(reason, that.reason) && Objects.equals(sentTimestamp, that.sentTimestamp) && Objects.equals(durationInSeconds, that.durationInSeconds) && Objects.equals(sentToNumber, that.sentToNumber) && Objects.equals(remoteCsid, that.remoteCsid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientNumber, propertiesList, status, reason, sentTimestamp, durationInSeconds, sentToNumber, remoteCsid);
    }

    @JsonPOJOBuilder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RecipientStatusBuilder {

        @JsonProperty("number")
        private String number;

        @JsonProperty("properties")
        private List<Property> properties;

        @JsonProperty("status")
        private String status;

        @JsonProperty("reason")
        private String reason;

        @JsonProperty("sentTs")
        private String sentTs;

        @JsonProperty("durationInSecs")
        private Integer durationInSecs;

        @JsonProperty("sentToNumber")
        private String sentToNumber;

        @JsonProperty("remoteCsid")
        private String remoteCsid;

        private RecipientStatusBuilder() {
        }

        /**
         * @param number The fax recipient's primary number (international format, e.g., +49891234678)
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder number(String number) {
            this.number = number;
            return this;
        }

        /**
         * @param properties List of personalized properties used for the cover page.
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder properties(List<Property> properties) {
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
         * Instead of using the properties list, you can use this method to add a single property.
         *
         * @param property for a personalized properties used for the cover page.
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder property(Property property) {
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
         * Instead of using the properties list, you can use this method to add a single property.
         *
         * @param key   for a personalized properties used for the cover page.
         * @param value for a personalized properties used for the cover page.
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder property(String key, String value) {
            if (this.properties == null) {
                this.properties = new ArrayList<>();
            }
            this.properties.add(new Property(key, value));
            return this;
        }

        /**
         * @param status Job status of the fax.
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder status(String status) {
            this.status = status;
            return this;
        }

        /**
         * @param reason Explanation/Reason of the status.
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * @param sentTs Timestamp which indicates when the fax was sent (in ISO 8601 format).
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder sentTimestamp(String sentTs) {
            this.sentTs = sentTs;
            return this;
        }

        /**
         * @param durationInSecs Duration of the fax transmission until received by the fax recipient.
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder durationInSeconds(Integer durationInSecs) {
            this.durationInSecs = durationInSecs;
            return this;
        }

        /**
         * @param sentToNumber The number to which the fax was sent.
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder sentToNumber(String sentToNumber) {
            this.sentToNumber = sentToNumber;
            return this;
        }

        /**
         * @param remoteCsid Fax ID which identifies the fax recipient.
         * @return RecipientStatusBuilder
         */
        public RecipientStatusBuilder remoteCsid(String remoteCsid) {
            this.remoteCsid = remoteCsid;
            return this;
        }

        public RecipientStatus build() {
            return new RecipientStatus(number, properties, status, reason, sentTs, durationInSecs, sentToNumber, remoteCsid);
        }

        public String toString() {
            return "RecipientStatus.RecipientStatusBuilder(number=" + this.number + ", properties=" + this.properties + ", status=" + this.status + ", reason=" + this.reason + ", sentTs=" + this.sentTs + ", durationInSecs=" + this.durationInSecs + ", sentToNumber=" + this.sentToNumber + ", remoteCsid=" + this.remoteCsid + ")";
        }
    }
}
