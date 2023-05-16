package com.retarus.fax.base.responses;

import org.apache.http.HttpStatus;

import java.util.Objects;

/**
 * @author thiagon
 * <p>
 * job request response class, used to represent a basic response from the API.
 * Contains the status code and the job id value from the response, if the status code is 200 or 201.
 * Example of successful response: {"jobId":"1234567890", statusCode:200}
 */
public class JobRequestResult {
    private final int statusCode;
    private final String jobId;

    /**
     * Constructor for the JobRequestResult class.
     *
     * @param statusCode The status code from the response.
     * @param jobId      The job id from the response.
     */
    public JobRequestResult(int statusCode, String jobId) {
        this.statusCode = statusCode;
        this.jobId = jobId;
    }

    /**
     * @return The status code from the response.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return The job id from the response.
     * If the request was a fax job creation, the response will be a fax job id if successful.
     */
    public String getJobId() {
        return jobId;
    }

    public boolean isSuccessful() {
        return statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRequestResult that = (JobRequestResult) o;
        return statusCode == that.statusCode && Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, jobId);
    }
}
