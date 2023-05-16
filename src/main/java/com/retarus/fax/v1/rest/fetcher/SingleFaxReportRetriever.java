package com.retarus.fax.v1.rest.fetcher;

import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.Retriever;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.base.responses.FaxStatusReport;
import com.retarus.fax.base.rest.RequestURL;
import com.retarus.fax.base.rest.URLProvider;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.exception.AuthException;
import com.retarus.fax.utils.RetarusResponseParser;
import com.retarus.fax.http.Fax4ApplApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.Optional;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_SERVICE_UNAVAILABLE;

public class SingleFaxReportRetriever extends Retriever<String, ApiResponse<Optional<FaxStatusReport>>> {
    public SingleFaxReportRetriever(Fax4ApplApiClient apiClient, URLProvider urlProvider) {
        super(apiClient, urlProvider);
    }

    /**
     * Method that performs the fetching of a single report to an optional FaxStatusReport object.
     *
     * @param jobId the id of the report to be fetched
     * @return an optional FaxStatusReport object
     * @throws ApiException  if the request fails
     * @throws AuthException if the authentication fails
     */
    @Override
    public ApiResponse<Optional<FaxStatusReport>> get(String jobId) {
        String baseUrl = urlProvider.getFetchUrl() + RequestURL.FETCH_STATUS_REPORT_FOR_SINGLE_JOB.toString().replace(RequestURL.Constants.CUSTOMER_NUMBER_TAG, apiClient.getCustomerNumber()).replace(RequestURL.Constants.JOB_ID_TAG, jobId);

        HttpResponse response = null;
        try {
            response = apiClient.sendRequest(HttpMethod.GET, baseUrl);
        } catch (IOException e) {
            return new ApiResponse<>(Optional.empty(), SC_SERVICE_UNAVAILABLE, urlProvider);
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            try {
                return new ApiResponse<>(RetarusResponseParser.parseHttpResponseToJobGetReport(response), statusCode, urlProvider);
            } catch (IOException e) {
                return new ApiResponse<>(Optional.empty(), SC_BAD_REQUEST, urlProvider);
            }
        } else {
            return new ApiResponse<>(Optional.empty(), statusCode, urlProvider);
        }

    }
}

