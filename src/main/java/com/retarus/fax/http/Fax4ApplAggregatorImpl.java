package com.retarus.fax.http;

import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.base.responses.FaxStatusReport;
import com.retarus.fax.base.rest.Location;
import com.retarus.fax.base.sendfax.FaxRequest;
import com.retarus.fax.v1.rest.fetcher.AvailableFaxReportsRetriever;
import com.retarus.fax.v1.rest.generator.FaxComposer;
import com.retarus.fax.RetarusFax;
import com.retarus.fax.v1.rest.deleter.OldestFaxReportsDeleter;
import com.retarus.fax.v1.rest.deleter.SingleFaxReportDeleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Fax4ApplAggregatorImpl implements Fax4ApplClient {
    private final FaxServiceClient client;
    private FaxComposer faxComposer;

    public Fax4ApplAggregatorImpl(FaxServiceClient client) {
        this.client = client;
    }


    /**
     * Sends a fax request to the fax service.
     *
     * @param faxRequest the fax request to send
     * @return the result of the fax request
     */
    @Override
    public ApiResponse<String> sendFaxRequest(FaxRequest faxRequest) {
        if (faxComposer == null) {
            faxComposer = new FaxComposer(client);
        }

        return faxComposer.compose(faxRequest);
    }

    /**
     * Sends a fax request to the fax service asynchronously.
     *
     * @param faxRequest the fax request to send
     * @return the result of the fax request
     */
    @Override
    public CompletableFuture<ApiResponse<String>> sendFaxRequestAsync(FaxRequest faxRequest) {
        if (faxComposer == null) {
            faxComposer = new FaxComposer(client);
        }

        return faxComposer.composeAsync(faxRequest);
    }

    /**
     * Deletes the selected fax report.
     * @param faxStatusReport the fax status report to delete
     * @return the deletion report for the deleted fax report
     */
    @Override
    public ApiResponse<Optional<FaxDeletionReport>> deleteReport(FaxStatusReport faxStatusReport) {
        SingleFaxReportDeleter singleFaxReportDeleter = new SingleFaxReportDeleter(client, faxStatusReport.getLocale());
        return singleFaxReportDeleter.delete(faxStatusReport.getJobId());
    }

    /**
     * Deletes the selected fax report asynchronously.
     * @param faxStatusReport the fax status report to delete
     * @return the deletion report for the deleted fax report
     */
    @Override
    public CompletableFuture<ApiResponse<Optional<FaxDeletionReport>>> deleteReportAsync(FaxStatusReport faxStatusReport) {
        SingleFaxReportDeleter singleFaxReportDeleter = new SingleFaxReportDeleter(client, faxStatusReport.getLocale());
        return singleFaxReportDeleter.deleteAsync(faxStatusReport.getJobId());
    }

    /**
     * Gets all the aggregated fax reports for a given region, fetching from each location and aggregating the results.
     * <br>The job location map is updated with the location for each job ID into the job location map, as well.
     *
     * @return all the aggregated fax reports, for all locations within a region
     */
    @Override
    public List<ApiResponse<List<FaxStatusReport>>> getReports() {
        List<ApiResponse<List<FaxStatusReport>>> apiResponses = new ArrayList<>();
        for (Location location : client.getLocale().getLocations()) {
            AvailableFaxReportsRetriever faxReportsRetriever = new AvailableFaxReportsRetriever(client, location);
            apiResponses.add(faxReportsRetriever.get(null));
        }
        return apiResponses;
    }

    /**
     * Gets all the aggregated fax reports for a given region, fetching from each location and aggregating the results asynchronously.
     * <br>The job location map is updated with the location for each job ID into the job location map, as well.
     *
     * @return all the aggregated fax reports, for all locations within a region
     */
    @Override
    public CompletableFuture<List<ApiResponse<List<FaxStatusReport>>>> getReportsAsync() {
        return CompletableFuture.supplyAsync(this::getReports, RetarusFax.getExecutorService());
    }

    /**
     * Gets all the aggregated fax reports for a given region, fetching from each location and aggregating the results.
     * <br>The job location map is updated with the location for each job ID into the job location map, as well.
     *
     * @return all the aggregated fax reports, for all locations within a region
     */
    @Override
    public List<ApiResponse<List<FaxDeletionReport>>> deleteReports() {
        List<ApiResponse<List<FaxDeletionReport>>> responses = new ArrayList<>();
        for (Location location : client.getLocale().getLocations()) {
            OldestFaxReportsDeleter oldestFaxReportsDeleter = new OldestFaxReportsDeleter(client, location);
            responses.add(oldestFaxReportsDeleter.delete(null));
        }
        return responses;
    }

    /**
     * Gets all the aggregated fax reports for a given region, fetching from each location and aggregating the results asynchronously.
     * <br>The job location map is updated with the location for each job ID into the job location map, as well.
     *
     * @return all the aggregated fax reports, for all locations within a region
     */
    @Override
    public CompletableFuture<List<ApiResponse<List<FaxDeletionReport>>>> deleteReportsAsync() {
        return CompletableFuture.supplyAsync(this::deleteReports, RetarusFax.getExecutorService());
    }
}
