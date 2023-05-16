package com.retarus.fax.v1.rest.bulkoperation;

import com.retarus.fax.base.BulkOperation;
import com.retarus.fax.base.bulkoperation.FaxBulkOperation;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.base.responses.FaxStatusReport;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.http.Fax4ApplApiClient;
import com.retarus.fax.utils.RetarusResponseParser;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that extends the BulkOperation class and is used to get reports in bulk.
 * The input data is a list of job ids (String) and the output data is a list of FaxStatusReport objects.
 */
public class GetFaxReportsInBulkOperation extends BulkOperation<List<String>, List<FaxStatusReport>> {

    public GetFaxReportsInBulkOperation(Fax4ApplApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Method that converts the HTTP response to a list of FaxStatusReport objects.
     *
     * @param response the HTTP response
     * @return a list of FaxStatusReport objects
     * @throws IOException  if the HTTP response cannot be parsed
     * @throws ApiException if errors occur while generating the request
     */
    @Override
    public List<FaxStatusReport> convertResponseToList(HttpResponse response) throws IOException {
        return RetarusResponseParser.parseHttpResponseToJobGetReportsList(response);
    }

    /**
     * Method that returns an empty list of FaxStatusReport objects.
     *
     * @return an empty list of FaxStatusReport objects
     */
    @Override
    public List<FaxStatusReport> returnEmptyList() {
        return new ArrayList<>();
    }

    /**
     * Method that generates the request to be sent to the server.
     *
     * @param data the input data, the list of job ids
     * @return the request to be sent to the server
     * @throws ApiException if errors occur while generating the request
     */
    @Override
    public FaxBulkOperation generateRequest(List<String> data) {
        return FaxBulkOperation.builder()
                .action(HttpMethod.GET)
                .jobIds(data)
                .build();
    }
}
