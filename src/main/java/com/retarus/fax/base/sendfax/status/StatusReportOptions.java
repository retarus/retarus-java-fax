package com.retarus.fax.base.sendfax.status;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * @author thiagon
 * <p>
 * Class for the status report options of a fax.
 * The status report options contains the report mail, and the status push.
 */
public class StatusReportOptions {

    /**
     * The report mail of the fax.
     */
    @JsonProperty("reportMail")
    private final ReportMail reportMail;

    /**
     * The status push of the fax.
     */
    @JsonProperty("httpStatusPush")
    private final StatusPush statusPush;

    /**
     * Constructor for the StatusReportOptions object.
     *
     * @param reportMail The report mail of the fax.
     * @param statusPush The status push of the fax.
     */
    private StatusReportOptions(ReportMail reportMail, StatusPush statusPush) {
        this.reportMail = reportMail;
        this.statusPush = statusPush;
    }


    public static StatusReportOptionsBuilder builder() {
        return new StatusReportOptionsBuilder();
    }

    public ReportMail getReportMail() {
        return reportMail;
    }

    public StatusPush getStatusPush() {
        return statusPush;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusReportOptions that = (StatusReportOptions) o;
        return Objects.equals(reportMail, that.reportMail) && Objects.equals(statusPush, that.statusPush);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportMail, statusPush);
    }

    public static class StatusReportOptionsBuilder {
        private ReportMail reportMail;
        private StatusPush statusPush;

        private StatusReportOptionsBuilder() {
        }

        /**
         * The report mail of the fax.
         *
         * @param reportMail The report mail of the fax.
         * @return StatusReportOptionsBuilder
         */
        public StatusReportOptionsBuilder reportMail(ReportMail reportMail) {
            this.reportMail = reportMail;
            return this;
        }

        /**
         * The status push of the fax.
         *
         * @param statusPush The status push of the fax.
         * @return StatusReportOptionsBuilder
         */
        public StatusReportOptionsBuilder statusPush(StatusPush statusPush) {
            this.statusPush = statusPush;
            return this;
        }

        /**
         * Builds the StatusReportOptions object.
         *
         * @return StatusReportOptions
         */
        public StatusReportOptions build() {
            return new StatusReportOptions(reportMail, statusPush);
        }

        public String toString() {
            return "StatusReportOptions.StatusReportOptionsBuilder( reportMail=" + this.reportMail + ", statusPush=" + this.statusPush + ")";
        }
    }
}
