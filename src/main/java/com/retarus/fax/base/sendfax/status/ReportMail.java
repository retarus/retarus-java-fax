package com.retarus.fax.base.sendfax.status;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * @author thiagon
 * <p>
 * Settings for the report mail. The report mail is sent to the specified addresses when the fax job is completed.<br>
 * The report mail contains the fax job status and, if requested, the fax image.<br>
 * Users can receive notifications for each fax job as soon as it is completed via HTTP POST or email.<br>
 * Separate email addresses can be specified for delivery and failed delivery confirmations.<br>
 * If an email address is deleted for either confirmation type, no notification email will be sent for that type.<br>
 * The report emails' format is determined by a customizable UTF-8 encoded template that includes relevant data(Job ID, job status, and details on the recipients).<br>
 * The fax image can also be attached to the report in the chosen format.
 */
public class ReportMail {

    /**
     * The email address to which the report mail is sent when the fax job is completed successfully.
     */
    @JsonProperty("successAddress")
    private final String successAddress;

    /**
     * The email address to which the report mail is sent when the fax job is completed with errors.
     */
    @JsonProperty("failureAddress")
    private final String failureAddress;

    /**
     * The fax image mode determines when the fax image will be attached to the email.
     */
    @JsonProperty("attachedFaxImageMode")
    private final FaxImageMode attachedFaxImageMode;

    /**
     * The fax image format determines the format in which the fax image will be attached to the email.
     */
    @JsonProperty("attachedFaxImageFormat")
    private final FaxImageFormat attachedFaxImageFormat;

    /**
     * Constructor for ReportMail
     *
     * @param successAddress         The email address to which the report mail is sent when the fax job is completed successfully.
     * @param failureAddress         The email address to which the report mail is sent when the fax job is completed with errors.
     * @param attachedFaxImageMode   The fax image mode determines when the fax image will be attached to the email.
     * @param attachedFaxImageFormat The fax image format determines the format in which the fax image will be attached to the email.
     */
    private ReportMail(String successAddress, String failureAddress, FaxImageMode attachedFaxImageMode, FaxImageFormat attachedFaxImageFormat) {
        this.successAddress = successAddress;
        this.failureAddress = failureAddress;
        this.attachedFaxImageMode = attachedFaxImageMode;
        this.attachedFaxImageFormat = attachedFaxImageFormat;
    }

    public static ReportMailBuilder builder() {
        return new ReportMailBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportMail that = (ReportMail) o;
        return Objects.equals(successAddress, that.successAddress) && Objects.equals(failureAddress, that.failureAddress) && attachedFaxImageMode == that.attachedFaxImageMode && attachedFaxImageFormat == that.attachedFaxImageFormat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(successAddress, failureAddress, attachedFaxImageMode, attachedFaxImageFormat);
    }

    /**
     * @return the success address of the report mail
     */
    public String getSuccessAddress() {
        return successAddress;
    }

    /**
     * @return the failure address of the report mail
     */
    public String getFailureAddress() {
        return failureAddress;
    }

    /**
     * @return the attached fax image mode of the report mail
     */
    public FaxImageMode getAttachedFaxImageMode() {
        return attachedFaxImageMode;
    }

    /**
     * @return the attached fax image format of the report mail
     */
    public FaxImageFormat getAttachedFaxImageFormat() {
        return attachedFaxImageFormat;
    }

    public static class ReportMailBuilder {
        private String successAddress;
        private String failureAddress;
        private FaxImageMode attachedFaxImageMode;
        private FaxImageFormat attachedFaxImageFormat;

        ReportMailBuilder() {
        }

        /**
         * @param successAddress The email address to which the report mail is sent when the fax job is completed successfully.
         * @return the report mail builder object
         */
        public ReportMailBuilder successAddress(String successAddress) {
            this.successAddress = successAddress;
            return this;
        }

        /**
         * @param failureAddress The email address to which the report mail is sent when the fax job is completed with errors.
         * @return the report mail builder object
         */
        public ReportMailBuilder failureAddress(String failureAddress) {
            this.failureAddress = failureAddress;
            return this;
        }

        /**
         * @param attachedFaxImageMode The fax image mode determines when the fax image will be attached to the email.
         * @return the report mail builder object
         */
        public ReportMailBuilder attachedFaxImageMode(FaxImageMode attachedFaxImageMode) {
            this.attachedFaxImageMode = attachedFaxImageMode;
            return this;
        }

        /**
         * @param attachedFaxImageFormat The fax image format determines the format in which the fax image will be attached to the email.
         * @return the report mail builder object
         */
        public ReportMailBuilder attachedFaxImageFormat(FaxImageFormat attachedFaxImageFormat) {
            this.attachedFaxImageFormat = attachedFaxImageFormat;
            return this;
        }

        /**
         * @return the report mail object
         */
        public ReportMail build() {
            return new ReportMail(successAddress, failureAddress, attachedFaxImageMode, attachedFaxImageFormat);
        }

        public String toString() {
            return "ReportMail.ReportMailBuilder(successAddress=" + this.successAddress + ", failureAddress=" + this.failureAddress + ", attachedFaxImageMode=" + this.attachedFaxImageMode + ", attachedFaxImageFormat=" + this.attachedFaxImageFormat + ")";
        }
    }
}