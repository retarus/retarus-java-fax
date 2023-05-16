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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;

/**
 * Class that extends the Retriever class and is used to fetch the available reports.
 * The output data is a list of FaxStatusReport objects.
 */
public class AvailableFaxReportsRetriever extends Retriever<Void, ApiResponse<List<FaxStatusReport>>> {

    public AvailableFaxReportsRetriever(Fax4ApplApiClient apiClient, URLProvider urlProvider) {
        super(apiClient, urlProvider);
    }

    /**
     * Method that performs the fetching of the available reports.
     *
     * @return a list of FaxStatusReport objects or an empty list for the available reports
     * @throws AuthException if the authentication fails while attempting to fetch the available reports
     * @throws ApiException  if an error occurs while attempting to fetch the available reports
     */
    @Override
    public ApiResponse<List<FaxStatusReport>> get(Void data) {
        String baseUrl = urlProvider.getFetchUrl() + RequestURL.FETCH_REPORTS_FOR_ACCOUNT.toString().replace(RequestURL.Constants.CUSTOMER_NUMBER_TAG, apiClient.getCustomerNumber());

        HttpResponse response = null;
        try {
            response = apiClient.sendRequest(HttpMethod.GET, baseUrl);
        } catch (IOException e) {
            return new ApiResponse<>(new ArrayList<>(), SC_SERVICE_UNAVAILABLE, apiClient.getLocale());
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == SC_OK) {
            try {
                List<FaxStatusReport> faxStatusReports = RetarusResponseParser.parseHttpResponseToJobGetReportsList(response);
                faxStatusReports.forEach(faxStatusReport -> faxStatusReport.setLocale(urlProvider.getLocale()));
                return new ApiResponse<>(faxStatusReports, statusCode, urlProvider);
            } catch (IOException e) {
                return new ApiResponse<>(new ArrayList<>(), SC_BAD_REQUEST, apiClient.getLocale());
            }
        } else {
            return new ApiResponse<>(new ArrayList<>(), statusCode, urlProvider);
        }
    }
}

