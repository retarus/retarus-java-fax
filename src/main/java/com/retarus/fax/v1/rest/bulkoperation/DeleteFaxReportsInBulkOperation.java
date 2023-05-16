package com.retarus.fax.v1.rest.bulkoperation;

import com.retarus.fax.base.BulkOperation;
import com.retarus.fax.base.bulkoperation.FaxBulkOperation;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.http.Fax4ApplApiClient;
import com.retarus.fax.utils.RetarusResponseParser;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that extends the BulkOperation class and is used to delete reports in bulk.
 * The input data is a list of job ids (String) and the output data is a list of FaxDeletionReport objects.
 */
public class DeleteFaxReportsInBulkOperation extends BulkOperation<List<String>, List<FaxDeletionReport>> {

    public DeleteFaxReportsInBulkOperation(Fax4ApplApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Method that converts the HTTP response to a list of FaxDeletionReport objects.
     *
     * @param response the HTTP response
     * @return a list of FaxDeletionReport objects
     * @throws IOException if the HTTP response cannot be parsed
     */
    @Override
    public List<FaxDeletionReport> convertResponseToList(HttpResponse response) throws IOException {
        return RetarusResponseParser.parseHttpResponseToJobDeleteReportsList(response);
    }

    /**
     * Method that returns an empty list of FaxDeletionReport objects.
     *
     * @return an empty list of FaxDeletionReport objects
     */
    @Override
    public List<FaxDeletionReport> returnEmptyList() {
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
                .action(HttpMethod.DELETE)
                .jobIds(data)
                .build();
    }

}
