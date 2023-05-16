package com.retarus.fax.v1.rest.deleter;

import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.Deleter;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.base.responses.Reason;
import com.retarus.fax.base.rest.RequestURL;
import com.retarus.fax.base.rest.URLProvider;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.exception.AuthException;
import com.retarus.fax.http.Fax4ApplApiClient;
import com.retarus.fax.utils.RetarusResponseParser;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.Optional;

import static org.apache.http.HttpStatus.*;

/**
 * Class that extends the Deleter class and is used to delete a single report.
 * The output data is an optional FaxDeletionReport object.
 */
public class SingleFaxReportDeleter extends Deleter<String, ApiResponse<Optional<FaxDeletionReport>>> {

    public SingleFaxReportDeleter(Fax4ApplApiClient apiClient, URLProvider urlProvider) {
        super(apiClient, urlProvider);
    }

    /**
     * Method that performs the deletion.
     *
     * @param jobId the id of the report to be deleted
     * @return an optional FaxDeletionReport object
     * @throws ApiException  if the request fails
     * @throws AuthException if the authentication fails
     */
    @Override
    public ApiResponse<Optional<FaxDeletionReport>> delete(String jobId) {

        String baseUrl = urlProvider.getFetchUrl() + RequestURL.DELETE_STATUS_REPORT_FOR_SINGLE_JOB.toString().replace(RequestURL.Constants.CUSTOMER_NUMBER_TAG, apiClient.getCustomerNumber()).replace(RequestURL.Constants.JOB_ID_TAG, jobId);

        HttpResponse response;
        try {
            response = apiClient.sendRequest(HttpMethod.DELETE, baseUrl);
        } catch (IOException e) {
            return new ApiResponse<>(Optional.empty(), SC_SERVICE_UNAVAILABLE, urlProvider);
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == SC_OK) {
            FaxDeletionReport faxDeletionReport = RetarusResponseParser.parseHttpResponseToJobDeleteStatus(response);
            if (faxDeletionReport.getReason() == Reason.INTERNAL_ERROR) {
                return new ApiResponse<>(Optional.of(faxDeletionReport), HttpStatus.SC_INTERNAL_SERVER_ERROR, urlProvider);
            } else {
                return new ApiResponse<>(Optional.of(faxDeletionReport), statusCode, urlProvider);
            }
        } else if (statusCode == SC_NOT_FOUND || statusCode == SC_BAD_REQUEST) {
            return new ApiResponse<>(Optional.of(FaxDeletionReport.builder().jobId(jobId).deleted(Boolean.FALSE).reason(Reason.NOT_FOUND).build()), statusCode, urlProvider);
        } else {
            return new ApiResponse<>(Optional.empty(), statusCode, urlProvider);
        }
    }
}
