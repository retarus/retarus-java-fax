import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.base.responses.FaxStatusReport;
import com.retarus.fax.base.rest.Region;
import com.retarus.fax.http.FaxServiceClient;
import org.apache.http.HttpStatus;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GetFaxReportAndDeleteExample {

    public static void main(String[] args) {
        FaxServiceClient client = initializeFaxServiceClient();

        //Please note that all operations below may throw an ApiException or an AuthException
        //But only the first one is handled in this example

        //Get the fax report for all fax jobs (up to 1000)
        List<ApiResponse<List<FaxStatusReport>>> allReportsResponseList = client.getReports();
        allReportsResponseList.stream()
                .filter(response -> response.getStatusCode() == HttpStatus.SC_OK) // Filter the responses for the API calls that were successful
                .map(ApiResponse::getValue) // Map the responses to the Optional<FaxStatusReport> objects
                .flatMap(Collection::stream) // Flattens list of lists
                .collect(Collectors.toList()).stream() // Collect the results and stream them
                .filter(report -> report.getPages() >= 5) // Filter the reports for the ones that have more than 5 pages
                .collect(Collectors.toList()); // Collect the results

        //Filter the responses for the API calls that were successful and aggregate the responses to a list of FaxStatusReport objects
        List<FaxStatusReport> availableFaxReports = allReportsResponseList.stream()
                .filter(response -> response.getStatusCode() == HttpStatus.SC_OK) // Filter the responses for the API calls that were successful
                .map(ApiResponse::getValue) // Map the responses to the Optional<FaxStatusReport> objects
                .flatMap(Collection::stream) // Flattens list of lists
                .collect(Collectors.toList()); // Collect the results

        //Do something with the status reports, like, for example, print the job id and the customer defined id of each report
        //And delete the reports afterward

        availableFaxReports.forEach(faxStatusReport -> {
            System.out.println("The fax job with ID " + faxStatusReport.getJobId()
                    + " has the customer defined ID " + faxStatusReport.getReference().getCustomerDefinedId()
                    + " and the billing code is " + faxStatusReport.getReference().getBillingCode());
            client.deleteReport(faxStatusReport);
        });

        //Or you can delete all reports at once
        //Delete the fax reports for all fax jobs (up to 1000)
        List<ApiResponse<List<FaxDeletionReport>>> allReportsList = client.deleteReports();
        //Do something with the deletion reports, like, for example, count how many were deleted and how many were not deleted
        for (ApiResponse<List<FaxDeletionReport>> response : allReportsList) {
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                List<FaxDeletionReport> faxDeletionReports = response.getValue();
                faxDeletionReports.stream().filter(faxDeletionReport -> !faxDeletionReport.isDeleted()).forEach(faxDeletionReport -> {
                    System.out.println("The fax job with ID " + faxDeletionReport.getJobId()
                            + " was not deleted because " + faxDeletionReport.getReason());
                });
                long deleted = faxDeletionReports.stream().filter(FaxDeletionReport::isDeleted).count();
                long notDeleted = faxDeletionReports.stream().filter(faxDeletionReport -> !faxDeletionReport.isDeleted()).count();
                System.out.println("Deleted " + deleted + " reports and not deleted " + notDeleted
                        + " reports out of " + allReportsList.size() + " reports");
            }
        }
        client.deleteReports();
        CompletableFuture<List<ApiResponse<List<FaxDeletionReport>>>> deleteReportsAsync = client.deleteReportsAsync();


        List<ApiResponse<List<FaxStatusReport>>> reports = client.getReports();
        reports.stream()
                .filter(response -> response.getStatusCode() == HttpStatus.SC_OK) // Filter the responses for the API calls that were successful
                .map(ApiResponse::getValue) // Map the responses to the Optional<FaxStatusReport> objects
                .flatMap(Collection::stream) // Flattens list of lists
                .collect(Collectors.toList()).stream() // Collect the results and stream them
                .forEach(report -> {
                    System.out.println(report.getJobId());
                    client.deleteReport(report);
                });
    }

    private static FaxServiceClient initializeFaxServiceClient() {

        //Initialize the client, username and password are mandatory, region and customer number are optional
        FaxServiceClient serviceClient = FaxServiceClient.builder()
                .username("YOUR_USERNAME")
                .password("YOUR_PASSWORD")
                //Optional
                .locale(Region.EUROPE)
                //Optional
                .customerNumber("YOUR_CUSTOMER_NUMBER")
                .build();

        //Optional: Initialize with .env file
        FaxServiceClient serviceClient1 = FaxServiceClient.initFromEnvFile();

        //Return the desired client
        return serviceClient;

    }

}