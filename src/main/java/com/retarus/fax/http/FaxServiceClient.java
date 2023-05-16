package com.retarus.fax.http;

import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.base.responses.FaxStatusReport;
import com.retarus.fax.base.rest.Region;
import com.retarus.fax.base.rest.URLProvider;
import com.retarus.fax.base.sendfax.FaxRequest;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.exception.AuthException;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author thiagon
 * <p>
 * Class used to connect the RetarusFax class to their specific request classes.
 * Also contains the connection to the HttpClient class.
 */
public class FaxServiceClient implements Fax4ApplApiClient {
    private final Credentials credentials;
    private final Fax4ApplClient aggregatorClient;
    private final URLProvider urlProvider;


    /**
     * Constructor for the FaxServiceClient class.
     *
     * @param credentials The credentials to use for the Retarus Fax account.
     * @param urlProvider The location/region of the RetarusFax account.
     */
    private FaxServiceClient(Credentials credentials, URLProvider urlProvider) {
        this.credentials = credentials;
        this.urlProvider = urlProvider;
        this.aggregatorClient = new Fax4ApplAggregatorImpl(this);
    }

    public static FaxApiClientBuilder builder() {
        return new FaxApiClientBuilder();
    }

    /**
     * Method used to send a request to the Retarus Fax API.
     *
     * @param httpMethod  The http method to use.
     * @param url         The url to send the request to.
     * @param jsonPayload The json payload to send with the request.
     * @return The response from the RetarusFax API.
     * @throws IOException If an error occurs while sending the request.
     */
    public HttpResponse sendRequest(HttpMethod httpMethod, String url, String jsonPayload) throws IOException {
        return HttpClient.sendRequest(credentials.getUsername(), credentials.getPassword(), httpMethod, url, jsonPayload);
    }

    /**
     * Method used to send a request to the RetarusFax API, without the need for a json payload.
     *
     * @param httpMethod The http method to use.
     * @param url        The url to send the request to.
     * @return The response from the RetarusFax API.
     * @throws IOException If an error occurs while sending the request.
     */
    public HttpResponse sendRequest(HttpMethod httpMethod, String url) throws IOException {
        return HttpClient.sendRequest(credentials.getUsername(), credentials.getPassword(), httpMethod, url);
    }

    @Override
    public String getUsername() {
        return credentials.getUsername();
    }

    @Override
    public String getPassword() {
        return credentials.getPassword();
    }

    @Override
    public String getCustomerNumber() {
        return credentials.getCustomerNumber();
    }

    public static synchronized FaxServiceClient initFromEnvFile() {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
        // Get values of environment variables and tries to initialize the SDK
        return FaxServiceClient.builder()
                .username(dotenv.get("RETARUS_USERNAME"))
                .password(dotenv.get("RETARUS_PASSWORD"))
                // If the environment variable is not set, the default value is used
                .customerNumber(dotenv.get("RETARUS_CUSTOMER_NUMBER", "12345"))
                // If the environment variable is not set, the default value is used
                .locale(URLProvider.getURLProviderFromString(dotenv.get("RETARUS_LOCALE", "EUROPE")))
                .build();
    }

    /**
     * Method that sends a fax request and returns the response as a JobRequestResult object.
     *
     * @param faxRequest the fax request to be sent
     * @return the response of the fax request, containing the job ID as status message (if successful) or the error message (if not successful) and the status code
     * @throws ApiException  if errors occur while generating the fax request
     * @throws AuthException if the authentication fails
     */
    @Override
    public ApiResponse<String> sendFaxRequest(FaxRequest faxRequest) {
        return aggregatorClient.sendFaxRequest(faxRequest);
    }

    /**
     * Method that sends a fax request asynchronously and returns the response as a JobRequestResult object.
     *
     * @param faxRequest the fax request to be sent
     * @return the response of the fax request, containing the job ID as status message (if successful) or the error message (if not successful) and the status code as a CompletableFuture object
     * @throws ApiException  if errors occur while generating the fax request
     * @throws AuthException if the authentication fails
     */
    @Override
    public CompletableFuture<ApiResponse<String>> sendFaxRequestAsync(FaxRequest faxRequest) {
        return aggregatorClient.sendFaxRequestAsync(faxRequest);
    }

    /**
     * Deletes the selected fax report.
     * @param faxStatusReport the fax status report to delete
     * @return the deletion report for the deleted fax report
     */
    @Override
    public ApiResponse<Optional<FaxDeletionReport>> deleteReport(FaxStatusReport faxStatusReport) {
        return aggregatorClient.deleteReport(faxStatusReport);
    }

    /**
     * Deletes the selected fax report asynchronously.
     * @param faxStatusReport the fax status report to delete
     * @return the deletion report for the deleted fax report
     */
    @Override
    public CompletableFuture<ApiResponse<Optional<FaxDeletionReport>>> deleteReportAsync(FaxStatusReport faxStatusReport) {
        return aggregatorClient.deleteReportAsync(faxStatusReport);
    }

    /**
     * Method that returns the fax status reports for all available jobs based on the customer number.
     *
     * @return the list of fax status reports as a list of JobGetStatus objects
     * @throws ApiException  if errors occur while generating the request
     * @throws AuthException if the authentication fails
     */
    @Override
    public List<ApiResponse<List<FaxStatusReport>>> getReports() {
        return aggregatorClient.getReports();
    }

    /**
     * Method that returns the fax status reports for all available jobs based on the customer number asynchronously.
     *
     * @return the list of fax status reports as a list of JobGetStatus objects as a CompletableFuture object
     * @throws ApiException  if errors occur while generating the request
     * @throws AuthException if the authentication fails
     */
    @Override
    public CompletableFuture<List<ApiResponse<List<FaxStatusReport>>>> getReportsAsync() {
        return aggregatorClient.getReportsAsync();
    }

    /**
     * Method that deletes the fax status reports for all available completed job reports based on the customer number (max 1000 reports per request).
     * In case of more than 1000 reports, the oldest reports will be deleted first.
     * If you want to delete more than 1000 reports, you have to call this method multiple times.
     *
     * @return the list of job delete reports as a list of FaxDeletionReport objects
     * @throws ApiException  if errors occur while generating the request
     * @throws AuthException if the authentication fails
     */
    @Override
    public List<ApiResponse<List<FaxDeletionReport>>> deleteReports() {
        return aggregatorClient.deleteReports();
    }

    /**
     * Method that deletes the fax status reports for all available completed job reports based on the customer number (max 1000 reports per request) asynchronously.
     * In case of more than 1000 reports, the oldest reports will be deleted first.
     * If you want to delete more than 1000 reports, you have to call this method multiple times.
     *
     * @return the list of job delete reports as a list of FaxDeletionReport objects as a CompletableFuture object
     * @throws ApiException  if errors occur while generating the request
     * @throws AuthException if the authentication fails
     */
    @Override
    public CompletableFuture<List<ApiResponse<List<FaxDeletionReport>>>> deleteReportsAsync() {
        return aggregatorClient.deleteReportsAsync();
    }

    @Override
    public URLProvider getLocale() {
        return urlProvider;
    }

    public static class FaxApiClientBuilder {
        private String username;
        private String password;
        private String customerNumber;
        private URLProvider locale;

        FaxApiClientBuilder() {
        }

        public FaxApiClientBuilder username(String username) {
            if (isBlank(username)) {
                throw new AuthException("Username cannot be empty.");
            }
            this.username = username;
            return this;
        }

        public FaxApiClientBuilder password(String password) {
            if (isBlank(password)) {
                throw new AuthException("Password cannot be empty.");
            }
            this.password = password;
            return this;
        }

        public FaxApiClientBuilder locale(URLProvider locale) {
            this.locale = locale;
            return this;
        }

        public FaxApiClientBuilder customerNumber(String customerNumber) {
            this.customerNumber = customerNumber;
            return this;
        }

        /**
         * Method that builds the FaxServiceClient object.
         *
         * @return the FaxServiceClient object
         * @throws AuthException if the username or password is not provided
         */
        public FaxServiceClient build() {
            // If the customer number is not provided, use the default one.
            if (isBlank(customerNumber)) {
                this.customerNumber = "12345";
            }
            // If a locale  is not provided, use the default one.
            if (locale == null) {
                this.locale = Region.EUROPE;
            }

            if (isBlank(username) || isBlank(password)) {
                throw new AuthException("Username and password cannot be empty.");
            }

            Credentials credentials = new Credentials(username, password, customerNumber);

            return new FaxServiceClient(credentials, this.locale);
        }

        public String toString() {
            return "FaxServiceClient.FaxApiClientBuilder(username=" + this.username + ", password=" + this.password + ", region=" + this.locale + ", customerNumber=" + this.customerNumber + ")";
        }
    }
}
