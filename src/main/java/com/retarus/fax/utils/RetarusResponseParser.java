package com.retarus.fax.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.base.responses.FaxStatusReport;
import com.retarus.fax.base.responses.Reason;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RetarusResponseParser {

    private RetarusResponseParser() {
        // private constructor to prevent instantiation
    }

    //Tag used to fetch the reports from the HttpResponse
    public static final String REPORTS_TAG = "reports";

    /**
     * Parse the HTTP response to an optional FaxStatusReport object.
     *
     * @param httpResponse the HTTP response
     * @return an optional FaxStatusReport object
     * @throws IOException if the HTTP response cannot be parsed
     */
    public static Optional<FaxStatusReport> parseHttpResponseToJobGetReport(HttpResponse httpResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        // Read the HTTP response body as a string
        HttpEntity entity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(entity);

        // Parse the JSON string to a RecipientStatus object
        FaxStatusReport faxStatusReport = objectMapper.readValue(responseBody, FaxStatusReport.class);

        return Optional.ofNullable(faxStatusReport);
    }

    /**
     * Parse the HTTP response in String to a list of FaxStatusReport objects.
     *
     * @param httpResponse the HTTP response
     * @return a list of FaxStatusReport objects
     * @throws IOException if the HTTP response cannot be parsed
     */
    //Parse the HTTP response to a FaxStatusReport list object.
    public static List<FaxStatusReport> parseHttpResponseToJobGetReportsList(HttpResponse httpResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        // Read the HTTP response body as a string
        HttpEntity entity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(entity);
        JsonNode jsonNode = objectMapper.readTree(responseBody); // parse the JSON string into a Jackson JsonNode
        JsonNode reportsNode = jsonNode.get(REPORTS_TAG); // get the "reports" array from the JsonNode
        return objectMapper.convertValue(reportsNode, new TypeReference<List<FaxStatusReport>>() {
        });
    }

    /**
     * Parse the HTTP response in String to a list of FaxDeletionReport objects.
     *
     * @param httpResponse the HTTP response
     * @return a list of FaxDeletionReport objects
     * @throws IOException if the HTTP response cannot be parsed
     */
    public static List<FaxDeletionReport> parseHttpResponseToJobDeleteReportsList(HttpResponse httpResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        // Read the HTTP response body as a string
        HttpEntity entity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(entity);

        JsonNode jsonNode = objectMapper.readTree(responseBody); // parse the JSON string into a Jackson JsonNode
        JsonNode reportsNode = jsonNode.get(REPORTS_TAG); // get the "reports" array from the JsonNode
        return objectMapper.convertValue(reportsNode, new TypeReference<List<FaxDeletionReport>>() {
        });
    }

    /**
     * Parse the HTTP response to an optional FaxDeletionReport object.
     *
     * @param httpResponse the HTTP response
     * @return an optional FaxDeletionReport object
     */
    public static FaxDeletionReport parseHttpResponseToJobDeleteStatus(HttpResponse httpResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
            // Read the HTTP response body as a string
            HttpEntity entity = httpResponse.getEntity();
            String responseBody = EntityUtils.toString(entity);

            // Parse the JSON string to a FaxDeletionReport object
            return objectMapper.readValue(responseBody, FaxDeletionReport.class);
        } catch (IOException e) {
            return FaxDeletionReport.builder().deleted(false).reason(Reason.INTERNAL_ERROR).build();
        }


    }


}
