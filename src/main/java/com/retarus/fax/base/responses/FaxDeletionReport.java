package com.retarus.fax.base.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.NonNull;

import java.util.Objects;

/**
 * @author thiagon
 * <p>
 * Class for the report of a fax deletion.
 * <br>If the deletion was successful, the report contains the job id and a flag indicating that the fax was deleted.
 * <br>If the deletion failed, the report contains the job id and the reason for the failure.
 */
@JsonDeserialize(builder = FaxDeletionReport.FaxDeletionReportBuilder.class)
public class FaxDeletionReport {

    /**
     * The id of the fax job
     */
    @NonNull
    private final String jobId;

    /**
     * Flag indicating whether the fax was deleted.
     */
    private final Boolean deleted;

    /**
     * Missing if deletion was successful, otherwise one of the following reason messages is returned: NOT_FOUND or INTERNAL_ERROR.
     */
    private final Reason reason;

    /**
     * Constructor for the FaxDeletionReport class.
     *
     * @param jobId   The id of the fax job.
     * @param deleted Flag indicating whether the fax was deleted.
     * @param reason  Missing if deletion was successful, otherwise one of the following reason messages is returned: NOT_FOUND or INTERNAL_ERROR.
     */
    private FaxDeletionReport(String jobId, Boolean deleted, Reason reason) {
        this.jobId = jobId;
        this.deleted = deleted;
        this.reason = reason;
    }

    public static FaxDeletionReportBuilder builder() {
        return new FaxDeletionReportBuilder();
    }

    /**
     * @return The job id of the fax report.
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @return Flag indicating whether the fax was deleted.
     */
    public Boolean isDeleted() {
        return deleted;
    }

    /**
     * @return Missing if deletion was successful, otherwise one of the following reason messages is returned: NOT_FOUND or INTERNAL_ERROR.
     */
    public Reason getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaxDeletionReport that = (FaxDeletionReport) o;
        return jobId.equals(that.jobId) && Objects.equals(deleted, that.deleted) && reason == that.reason;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, deleted, reason);
    }

    @JsonPOJOBuilder
    public static class FaxDeletionReportBuilder {

        @JsonProperty("jobId")
        private String jobId;

        @JsonProperty("deleted")
        private Boolean deleted;
        @JsonProperty("reason")
        private Reason reason;

        private FaxDeletionReportBuilder() {
        }

        /**
         * @param jobId The id of the fax job.
         * @return The FaxDeletionReportBuilder instance.
         */
        public FaxDeletionReportBuilder jobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        /**
         * @param deleted Flag indicating whether the fax was deleted.
         * @return The FaxDeletionReportBuilder instance.
         */
        public FaxDeletionReportBuilder deleted(Boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        /**
         * @param reason Missing if deletion was successful, otherwise one of the following reason messages is returned: NOT_FOUND or INTERNAL_ERROR.
         * @return The FaxDeletionReportBuilder instance.
         */
        public FaxDeletionReportBuilder reason(Reason reason) {
            this.reason = reason;
            return this;
        }

        public FaxDeletionReport build() {
            return new FaxDeletionReport(jobId, deleted, reason);
        }

        public String toString() {
            return "FaxDeletionReport.FaxDeletionReportBuilder(jobId=" + this.jobId + ", deleted=" + this.deleted + ", reason=" + this.reason + ")";
        }
    }
}
