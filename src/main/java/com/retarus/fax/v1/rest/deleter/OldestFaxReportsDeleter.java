package com.retarus.fax.v1.rest.deleter;

import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.Deleter;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.base.rest.RequestURL;
import com.retarus.fax.base.rest.URLProvider;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.http.Fax4ApplApiClient;
import com.retarus.fax.utils.RetarusResponseParser;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;

/**
 * Class that extends the Deleter class and is used to delete the oldest reports.
 * The output data is a list of FaxDeletionReport objects.
 */
public class OldestFaxReportsDeleter extends Deleter<Void, ApiResponse<List<FaxDeletionReport>>> {

    public OldestFaxReportsDeleter(Fax4ApplApiClient apiClient, URLProvider urlProvider) {
        super(apiClient, urlProvider);
    }

    /**
     * Method that performs the deletion.
     *
     * @param data the input data, null
     * @return a list of FaxDeletionReport objects or an empty list for the deleted reports
     * @throws ApiException if errors occur while generating the request
     */
    @Override
    public ApiResponse<List<FaxDeletionReport>> delete(Void data) {
        String baseUrl = urlProvider.getFetchUrl() + RequestURL.DELETE_REPORTS_FOR_ACCOUNT.toString().replace(RequestURL.Constants.CUSTOMER_NUMBER_TAG, apiClient.getCustomerNumber());

        HttpResponse response;
        try {
            response = apiClient.sendRequest(HttpMethod.DELETE, baseUrl);
        } catch (IOException e) {
            return new ApiResponse<>(new ArrayList<>(), SC_SERVICE_UNAVAILABLE, apiClient.getLocale());
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == SC_OK) {
            try {
                return new ApiResponse<>(RetarusResponseParser.parseHttpResponseToJobDeleteReportsList(response), statusCode, apiClient.getLocale());
            } catch (IOException e) {
                return new ApiResponse<>(new ArrayList<>(), SC_BAD_REQUEST, apiClient.getLocale());
            }
        } else {
            return new ApiResponse<>(new ArrayList<>(), statusCode, apiClient.getLocale());
        }
    }
}
