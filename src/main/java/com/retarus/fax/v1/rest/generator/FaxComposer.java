package com.retarus.fax.v1.rest.generator;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.Composer;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.base.rest.RequestURL;
import com.retarus.fax.base.rest.URLProvider;
import com.retarus.fax.base.sendfax.FaxRequest;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.exception.AuthException;
import com.retarus.fax.http.Fax4ApplApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.http.HttpStatus.*;

/**
 * FaxComposer class to generate the fax request to be sent to the server.
 *
 * @author thiagon<br>
 * <br>
 * The input is a FaxRequest object and the output is a JobRequestResult object.
 * The basic response object contains the status code and the job id of the fax request if the status code is 200 or 201.
 */
public class FaxComposer extends Composer<FaxRequest, ApiResponse<String>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FaxComposer(Fax4ApplApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Generates the fax request to be sent to the server.
     *
     * @param faxRequest the fax request to be sent to the server
     * @return the response from the server
     * @throws ApiException  if errors occur while generating the fax request or while reading the response
     * @throws AuthException if the authentication fails
     */
    @Override
    public ApiResponse<String> compose(FaxRequest faxRequest) {
        URLProvider urlProvider = apiClient.getLocale();
        String baseUrl = urlProvider.getSendUrl() + RequestURL.SEND_FAX_REQUEST.toString()
                .replace("{custNr}", apiClient.getCustomerNumber());
        String jsonPayload;

        try {
            jsonPayload = objectMapper.writeValueAsString(faxRequest);
        } catch (JsonProcessingException e) {
            return new ApiResponse<>("", SC_BAD_REQUEST, urlProvider);
        }

        HttpResponse response;
        try {
            response = apiClient.sendRequest(HttpMethod.POST, baseUrl, jsonPayload);
        } catch (IOException e) {
            return new ApiResponse<>("", SC_SERVICE_UNAVAILABLE, urlProvider);
        }
        try {
            String responseBody = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == SC_OK || statusCode == SC_CREATED) {
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode jobIdNode = rootNode.get("jobId");
                if (jobIdNode != null && !isBlank(jobIdNode.asText())) {
                    String jobId = jobIdNode.asText();
                    return new ApiResponse<>(jobId, statusCode, urlProvider);
                } else {
                    return new ApiResponse<>("", statusCode, urlProvider);
                }
            } else {
                return new ApiResponse<>("", statusCode, urlProvider);
            }
        } catch (IOException e) {
            return new ApiResponse<>("", SC_BAD_REQUEST, urlProvider);
        }
    }
}
