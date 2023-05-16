package com.retarus.fax.base.rest;

import static com.retarus.fax.base.rest.RequestURL.Constants.*;

/**
 * @author thiagon
 * <p>
 * Enum class for the API request URLs.
 * This class contains the base URL for the API, and the URL for each request.
 * The URL for each request is built by replacing the tags with the customer number and, if applicable, the job id.
 */
public enum RequestURL {
    SEND_FAX_REQUEST(BASE_URL),
    FETCH_STATUS_REPORT_FOR_SINGLE_JOB(REPORTS_FOR_SINGLE_JOB_URL),
    DELETE_STATUS_REPORT_FOR_SINGLE_JOB(REPORTS_FOR_SINGLE_JOB_URL),
    FETCH_REPORTS_FOR_ACCOUNT(REPORTS_URL),
    PERFORM_BULK_OPERATION_ON_STATUS_REPORT(REPORTS_URL),
    DELETE_REPORTS_FOR_ACCOUNT(REPORTS_URL);

    private final String url;

    RequestURL(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }


    public static class Constants {
        private Constants() {
        }

        public static final String CUSTOMER_NUMBER_TAG = "{custNr}";
        public static final String JOB_ID_TAG = "{jobId}";
        protected static final String BASE_URL = "/" + CUSTOMER_NUMBER_TAG + "/fax";

        protected static final String REPORTS_URL = BASE_URL + "/reports";

        protected static final String REPORTS_FOR_SINGLE_JOB_URL = REPORTS_URL + "/" + JOB_ID_TAG;

    }
}
