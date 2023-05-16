package com.retarus.fax.base.sendfax;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.retarus.fax.exception.ApiException;

import java.util.Objects;

/**
 * @author thiagon
 * <p>
 * Class for the reference of a fax.
 * The reference is a set of values that can be used to identify the fax.
 */
@JsonDeserialize(builder = Reference.ReferenceBuilder.class)
public class Reference {

    /**
     * The customer defined id of the fax. (max. 256 characters)
     */
    @JsonProperty("customerDefinedId")
    private final String customerDefinedId;

    /**
     * Information on the cost center; format is arbitrary (max. 80 characters)
     */
    @JsonProperty("billingCode")
    private final String billingCode;

    /**
     * Additional data for internal customer accounting. (max. 80 characters)
     */
    @JsonProperty("billingInfo")
    private final String billingInfo;

    /**
     * Constructor for the reference.
     *
     * @param customerDefinedId The customer defined id of the fax. (max. 256 characters)
     * @param billingCode       Information on the cost center; format is arbitrary (max. 80 characters)
     * @param billingInfo       Additional data for internal customer accounting. (max. 80 characters)
     * @throws ApiException if the customer defined id, the billing code, or the billing info is longer than 256, 80, or 80 characters respectively.
     */
    private Reference(String customerDefinedId, String billingCode, String billingInfo) {
        this.customerDefinedId = customerDefinedId;
        this.billingCode = billingCode;
        this.billingInfo = billingInfo;
    }

    public static ReferenceBuilder builder() {
        return new ReferenceBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return Objects.equals(customerDefinedId, reference.customerDefinedId) && Objects.equals(billingCode, reference.billingCode) && Objects.equals(billingInfo, reference.billingInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerDefinedId, billingCode, billingInfo);
    }

    public String getCustomerDefinedId() {
        return this.customerDefinedId;
    }

    public String getBillingCode() {
        return this.billingCode;
    }

    public String getBillingInfo() {
        return this.billingInfo;
    }

    @JsonPOJOBuilder
    public static class ReferenceBuilder {
        @JsonProperty("customerDefinedId")
        private String customerDefinedId;

        @JsonProperty("billingCode")
        private String billingCode;

        @JsonProperty("billingInfo")
        private String billingInfo;

        private ReferenceBuilder() {
        }

        /**
         * @param customerDefinedId The customer defined id of the fax. (max. 256 characters)
         * @throws ApiException if the customer defined id is longer than 256 characters
         * @return the reference builder
         */
        public ReferenceBuilder customerDefinedId(String customerDefinedId) {
            if (this.customerDefinedId != null && this.customerDefinedId.length() > 256) {
                throw new ApiException("The customer defined id of the fax must be less than or equal to 256 characters.");
            }
            this.customerDefinedId = customerDefinedId;
            return this;
        }

        /**
         * @param billingCode Information on the cost center; format is arbitrary (max. 80 characters)
         * @throws ApiException if the billing code is longer than 80 characters.
         * @return the reference builder
         */
        public ReferenceBuilder billingCode(String billingCode) {
            if (this.billingCode != null && this.billingCode.length() > 80) {
                throw new ApiException("The billing code of the fax must be less than or equal to 80 characters.");
            }
            this.billingCode = billingCode;
            return this;
        }

        /**
         * @param billingInfo Additional data for internal customer accounting. (max. 80 characters)
         * @throws ApiException if the billing info is longer than 80 characters.
         * @return the reference builder
         */
        public ReferenceBuilder billingInfo(String billingInfo) {
            if (this.billingInfo != null && this.billingInfo.length() > 80) {
                throw new ApiException("The billing info of the fax must be less than or equal to 80 characters.");
            }
            this.billingInfo = billingInfo;
            return this;
        }

        /**
         * @return the reference
         * @throws ApiException if the customer defined id, the billing code, or the billing info is longer than 256, 80, or 80 characters respectively.
         */
        public Reference build() {
            return new Reference(customerDefinedId, billingCode, billingInfo);
        }

        public String toString() {
            return "Reference.ReferenceBuilder(customerDefinedId=" + this.customerDefinedId + ", billingCode=" + this.billingCode + ", billingInfo=" + this.billingInfo + ")";
        }
    }
}
