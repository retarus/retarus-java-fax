package com.retarus.fax.base.sendfax.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.retarus.fax.exception.ApiException;

import java.time.Instant;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author thiagon
 * <p>
 * Metadata object that is used to add additional information to a fax job request.
 * <br>With this object you can add a customer reference and a job validation object,
 * which contains the valid start/end of a fax job (in ISO 8601 format).
 * <br>If this data is not defined correctly, you will receive an API Exception error on runtime.
 */
public class Metadata {
    /**
     * The customer reference of the fax job
     */
    @JsonProperty("customerReference")
    private final String customerReference;

    /**
     * The job validation object
     */
    @JsonProperty("jobValid")
    private final JobValidation jobValidation;

    /**
     * @param customerReference The customer reference of the fax job
     * @param jobValidation     The job validation object
     * @throws ApiException if the start or end date is not in ISO 8601 format
     */
    private Metadata(String customerReference, JobValidation jobValidation) {
        this.customerReference = customerReference;
        this.jobValidation = jobValidation;
    }

    public static MetaDataBuilder builder() {
        return new MetaDataBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metaData = (Metadata) o;
        return Objects.equals(customerReference, metaData.customerReference) && Objects.equals(jobValidation, metaData.jobValidation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerReference, jobValidation);
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public JobValidation getJobValidation() {
        return jobValidation;
    }

    public static class MetaDataBuilder {
        private String customerReference;
        private JobValidation jobValidation;

        private MetaDataBuilder() {
        }

        /**
         * @param customerReference The customer reference of the fax job
         * @return The MetaDataBuilder object
         */
        public MetaDataBuilder customerReference(String customerReference) {
            if (isBlank(customerReference)) {
                throw new ApiException("Customer reference cannot be null or empty");
            }
            this.customerReference = customerReference;
            return this;
        }

        /**
         * @param jobValidation The job validation object
         * @return The MetaDataBuilder object
         */
        public MetaDataBuilder jobValidation(JobValidation jobValidation) {
            this.jobValidation = jobValidation;
            return this;
        }

        /**
         * @param start The start date of the job. In ISO 8601 format, or a valid duration string
         * @param end   The end date of the job. In ISO 8601 format, or a valid duration string
         * @return The MetaDataBuilder object
         */
        public MetaDataBuilder jobValidation(String start, String end) {
            this.jobValidation = JobValidation.builder().startTime(start).expiryTime(end).build();
            return this;
        }

        public MetaDataBuilder jobValidation(Instant start, Instant end) {
            this.jobValidation = JobValidation.builder().startTime(start).expiryTime(end).build();
            return this;
        }

        /**
         * Builds the Metadata object
         *
         * @return The Metadata object
         */
        public Metadata build() {
            if (isBlank(customerReference)) {
                throw new ApiException("Customer reference cannot be null or empty");
            }
            return new Metadata(customerReference, jobValidation);
        }

        public String toString() {
            return "Metadata.MetaDataBuilder(customerReference=" + this.customerReference + ", jobValidation=" + this.jobValidation + ")";
        }
    }
}
