package com.retarus.fax.base.sendfax.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.utils.ValidationUtils;

import java.time.Instant;
import java.util.Objects;

import static com.retarus.fax.utils.ValidationUtils.convertInstantToIso8601String;

/**
 * @author thiagon
 * <p>
 * Subclass of Metadata, used to represent a job validation,
 * which contains the valid start/end of a fax job (in ISO 8601 format).<p>
 * If this data is not defined correctly, you will receive an API Exception error on runtime.
 */
public class JobValidation {
    /**
     * The start date of the job. In ISO 8601 format
     */
    @JsonProperty("start")
    private final String start;

    /**
     * The end date of the job. In ISO 8601 format
     */
    @JsonProperty("end")
    private final String end;

    /**
     * @param start The start date of the job. In ISO 8601 format
     * @param end   The end date of the job. In ISO 8601 format
     * @throws ApiException if the start or end date is not in ISO 8601 format
     */
    private JobValidation(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public static JobValidationBuilder builder() {
        return new JobValidationBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobValidation that = (JobValidation) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    /**
     * @return the start date of the job. In ISO 8601 format
     */
    public String getStart() {
        return start;
    }

    /**
     * @return the end date of the job. In ISO 8601 format
     */
    public String getEnd() {
        return end;
    }

    public static class JobValidationBuilder {
        private String start;
        private String end;

        private JobValidationBuilder() {
        }

        /**
         * @param start The start date of the job. In ISO 8601 format or a valid duration
         * @return the builder
         * @throws ApiException if the start date is not in ISO 8601 format or is not a valid duration
         */
        public JobValidationBuilder startTime(String start) {
            if (start != null && !ValidationUtils.isIso8601ValidDate(start) && !ValidationUtils.isStringValidDuration(start)) {
                throw new ApiException("The start date is not in ISO 8601 format or is not a valid duration");
            }
            this.start = start;
            return this;
        }

        /**
         * @param end The end date of the job. In ISO 8601 format or a valid duration
         * @return the builder
         * @throws ApiException if the end date is not in ISO 8601 format or is not a valid duration
         */
        public JobValidationBuilder expiryTime(String end) {
            if (end != null && !ValidationUtils.isIso8601ValidDate(end) && !ValidationUtils.isStringValidDuration(end)) {
                throw new ApiException("The end date is not in ISO 8601 format or is not a valid duration");
            }
            this.end = end;
            return this;
        }

        /**
         * @param start The start date of the job. Instant converted to ISO 8601 format
         * @return the builder
         */
        public JobValidationBuilder startTime(Instant start) {

            this.start = convertInstantToIso8601String(start);
            return this;
        }

        /**
         * @param end The end date of the job. Instant converted to ISO 8601 format
         * @return the builder
         */
        public JobValidationBuilder expiryTime(Instant end) {
            this.end = convertInstantToIso8601String(end);
            return this;
        }


        public JobValidation build() {
            return new JobValidation(start, end);
        }

        public String toString() {
            return "JobValidation.JobValidationBuilder(start=" + this.start + ", end=" + this.end + ")";
        }
    }
}

