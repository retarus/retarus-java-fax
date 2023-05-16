package com.retarus.fax.base.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.retarus.fax.base.responses.status.RecipientStatus;
import com.retarus.fax.base.rest.URLProvider;
import com.retarus.fax.base.sendfax.Reference;
import com.retarus.fax.exception.ApiException;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;


/**
 * @author thiagon
 * <p>
 * Class for the report of a fax.
 * <br>The report contains the job id, the list of recipient status of the fax, the number of pages, and the reference.
 */
@JsonDeserialize(builder = FaxStatusReport.FaxStatusReportBuilder.class)
public class FaxStatusReport {

    /**
     * Constructor for the Fax Status Report class.
     *
     * @param jobId      The id of the fax job.
     * @param statusList The list of recipient status of the fax.
     * @param pages      The number of pages in the fax.
     * @param reference  The reference of the fax.
     */
    private FaxStatusReport(String jobId, List<RecipientStatus> statusList, int pages, Reference reference) {
        this.jobId = jobId;
        this.statusList = statusList;
        this.pages = pages;
        this.reference = reference;
    }

    /**
     * The id of the fax job
     */
    @NonNull
    private final String jobId;

    /**
     * The list of recipient status of the fax
     */
    private final List<RecipientStatus> statusList;

    /**
     * Number of pages in the fax
     */
    private final int pages;

    /**
     * Reference of the fax
     */
    private final Reference reference;

    private URLProvider locale;

    public static FaxStatusReportBuilder builder() {
        return new FaxStatusReportBuilder();
    }

    /**
     * @return The job id of the fax report.
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @return The list of recipient status of the fax.
     */
    public List<RecipientStatus> getStatusList() {
        return statusList;
    }

    /**
     * @return The number of pages in the fax.
     */
    public int getPages() {
        return pages;
    }

    /**
     * @return The reference of the fax.
     */
    public Reference getReference() {
        return reference;
    }

    /**
     * @return The URL provider for the locale.
     */
    public URLProvider getLocale() {
        return locale;
    }

    /**
     * @param locale The URL provider for the locale.
     */
    public void setLocale(URLProvider locale) {
        this.locale = locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaxStatusReport that = (FaxStatusReport) o;
        return pages == that.pages && jobId.equals(that.jobId) && Objects.equals(statusList, that.statusList) && Objects.equals(reference, that.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, statusList, pages, reference);
    }

    @JsonPOJOBuilder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FaxStatusReportBuilder {

        @JsonProperty("jobId")
        private String jobId;

        @JsonProperty("recipientStatus")
        private List<RecipientStatus> statusList;

        @JsonProperty("pages")
        private int pages;

        @JsonProperty("reference")
        private Reference reference;

        private FaxStatusReportBuilder() {
        }

        /**
         * @param jobId The id of the fax job.
         * @return The FaxStatusReportBuilder object.
         * The job id of the fax report cannot be null.
         * @throws ApiException if jobId is null.
         */
        public FaxStatusReportBuilder jobId(String jobId) {
            if (jobId == null) {
                throw new ApiException("Job id cannot be null.");
            }
            this.jobId = jobId;
            return this;
        }

        /**
         * @param statusList The list of recipient status of the fax.
         * @return The FaxStatusReportBuilder object.
         */
        public FaxStatusReportBuilder statusList(List<RecipientStatus> statusList) {
            this.statusList = statusList;
            return this;
        }

        /**
         * @param pages The number of pages in the fax.
         * @return The FaxStatusReportBuilder object.
         */
        public FaxStatusReportBuilder pages(int pages) {
            this.pages = pages;
            return this;
        }

        /**
         * @param reference The reference of the fax.
         * @return The FaxStatusReportBuilder object.
         */
        public FaxStatusReportBuilder reference(Reference reference) {
            this.reference = reference;
            return this;
        }

        /**
         * @return The FaxStatusReport object.
         */
        public FaxStatusReport build() {
            if (this.jobId == null) {
                throw new ApiException("jobId cannot be null");
            }
            return new FaxStatusReport(jobId, statusList, pages, reference);
        }

        public String toString() {
            return "FaxStatusReport.FaxStatusReportBuilder(jobId=" + this.jobId + ", statusList=" + this.statusList + ", pages=" + this.pages + ", reference=" + this.reference + ")";
        }
    }
}
