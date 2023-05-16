package com.retarus.fax.http;

import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.base.responses.FaxStatusReport;
import com.retarus.fax.base.sendfax.FaxRequest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

interface Fax4ApplClient {

    ApiResponse<String> sendFaxRequest(FaxRequest faxRequest);

    CompletableFuture<ApiResponse<String>> sendFaxRequestAsync(FaxRequest faxRequest);

    ApiResponse<Optional<FaxDeletionReport>> deleteReport(FaxStatusReport faxStatusReport);

    CompletableFuture<ApiResponse<Optional<FaxDeletionReport>>> deleteReportAsync(FaxStatusReport faxStatusReport);

    List<ApiResponse<List<FaxStatusReport>>> getReports();

    CompletableFuture<List<ApiResponse<List<FaxStatusReport>>>> getReportsAsync();

    List<ApiResponse<List<FaxDeletionReport>>> deleteReports();

    CompletableFuture<List<ApiResponse<List<FaxDeletionReport>>>> deleteReportsAsync();


}
