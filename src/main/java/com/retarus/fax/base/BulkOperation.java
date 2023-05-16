package com.retarus.fax.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retarus.fax.base.rest.RequestURL;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.exception.AuthException;
import com.retarus.fax.RetarusFax;
import com.retarus.fax.base.bulkoperation.FaxBulkOperation;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.http.Fax4ApplApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * @param <V> The type of the data to be sent to the server.
 * @param <T> The type of the response from the server.
 * @author thiagon<p>
 * Abstract class for the bulk operations. *
 */
public abstract class BulkOperation<V, T> {

    protected Fax4ApplApiClient apiClient;

    protected BulkOperation(Fax4ApplApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Base method for the bulk operations to be performed asynchronously.
     *
     * @param data the data to be sent to the server
     * @return a CompletableFuture object
     */
    public CompletableFuture<T> performAsync(V data) {
        return CompletableFuture.supplyAsync(() -> perform(data), RetarusFax.getExecutorService());
    }

    /**
     * Base method to convert the response to a list of objects.
     *
     * @param response the response from the server
     * @return a list of objects
     * @throws IOException if the response cannot be converted to a list of objects
     */
    public abstract T convertResponseToList(HttpResponse response) throws IOException;

    public abstract T returnEmptyList();

    /**
     * Base method to generate the request to be sent to the server.
     *
     * @param data the data to be sent to the server
     * @return the request to be sent to the server
     */
    public abstract FaxBulkOperation generateRequest(V data);

    /**
     * Base method to perform the bulk operation.
     *
     * @param data the data to be sent to the server
     * @return the response from the server
     * @throws ApiException  if errors occur while performing the bulk operation
     * @throws AuthException if the authentication fails
     */
    public T perform(V data) {

        ObjectMapper objectMapper = new ObjectMapper();

        String baseUrl = apiClient.getLocale()
                .getFetchUrl() + RequestURL.PERFORM_BULK_OPERATION_ON_STATUS_REPORT.toString()
                .replace(RequestURL.Constants.CUSTOMER_NUMBER_TAG, apiClient.getCustomerNumber());
        FaxBulkOperation faxBulkOperation = generateRequest(data);

        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(faxBulkOperation);
        } catch (JsonProcessingException e) {
            throw new ApiException(e.getMessage(), e);
        }

        HttpResponse response;
        try {
            response = apiClient.sendRequest(HttpMethod.POST, baseUrl, jsonPayload);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == SC_OK) {
                return convertResponseToList(response);
            } else if (statusCode == SC_NOT_FOUND) {
                return returnEmptyList();
            } else if (statusCode == HttpStatus.SC_UNAUTHORIZED || statusCode == HttpStatus.SC_BAD_REQUEST) {
                throw new AuthException("Authentication failed.", new Throwable());
            } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                throw new ApiException("Internal Server Error.", new Throwable(), baseUrl, jsonPayload, response);
            } else if (statusCode == HttpStatus.SC_SERVICE_UNAVAILABLE) {
                throw new ApiException("Service Unavailable.", new Throwable(), baseUrl, jsonPayload, response);
            } else {
                throw new ApiException("Cannot perform the desired bulk operation", new Throwable(), baseUrl, jsonPayload, response);
            }
        } catch (IOException e) {
            throw new ApiException(e.getMessage(), e, baseUrl, jsonPayload, null);
        }


    }
}
