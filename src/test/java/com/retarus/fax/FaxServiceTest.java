package com.retarus.fax;

import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.common.Property;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.base.responses.FaxStatusReport;
import com.retarus.fax.base.rest.Location;
import com.retarus.fax.base.rest.Region;
import com.retarus.fax.base.rest.URLProvider;
import com.retarus.fax.base.sendfax.*;
import com.retarus.fax.base.sendfax.metadata.Metadata;
import com.retarus.fax.base.sendfax.rendering.PaperFormat;
import com.retarus.fax.base.sendfax.rendering.PaperResolution;
import com.retarus.fax.base.sendfax.rendering.RenderingOptions;
import com.retarus.fax.base.sendfax.status.*;
import com.retarus.fax.base.sendfax.transport.TransportOptions;
import com.retarus.fax.exception.AuthException;
import com.retarus.fax.http.FaxServiceClient;
import com.retarus.fax.http.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FaxServiceTest {

    private static final Reference REFERENCE = Reference.builder()
            .customerDefinedId("2018-08-7T11:04:37.057Z_customerDefinedId")
            .billingCode("2018-08-7T11:04:37.057Z_billingCode").billingInfo("2018-08-7T11:04:37.057Z_billingInfo")
            .build();
    private static final FaxRecipient FAX_RECIPIENT = FaxRecipient.builder().faxNumber("+4900000000")
            .properties(Arrays.asList(
                    new Property("FromName", "Sales Engineering Department"),
                    new Property("FromCompanyName", "retarus, Inc."),
                    new Property("FromTelNum", "1-855-462-0839"),
                    new Property("ToName", "Matthias"),
                    new Property("ToCompanyName", "ABC Company"),
                    new Property("ToFaxNum", "1-202-827-2391"),
                    new Property("SubjectTitle", "Test Fax using REST API"),
                    new Property("SubjectText", "This is a test fax for Matthias")
            )).build();
    private static final RenderingOptions RENDERING_OPTIONS = RenderingOptions.builder().paperFormat(PaperFormat.A4)
            .paperResolution(PaperResolution.HIGH).coverPageTemplate("coverpage-default.ftl.html")
            .header("%tz=CEST Testfax: CSID: %C Empfaengernummer: %# Datum: %d.%m.%Y %H:%M%z").build();
    private static final ReportMail REPORT_MAIL = ReportMail.builder().successAddress("thiago.nunes@retarus.pt")
            .failureAddress("thiago.nunes@retarus.pt").attachedFaxImageMode(FaxImageMode.SUCCESS_ONLY)
            .attachedFaxImageFormat(FaxImageFormat.PDF).build();
    private static final StatusPush STATUS_PUSH = StatusPush.builder().url("https://www.retarus.com")
            .authenticationMethod(AuthenticationMethod.NONE).username("username").password("password").build();
    private static final StatusReportOptions STATUS_REPORT_OPTIONS = StatusReportOptions.builder()
            .reportMail(REPORT_MAIL).build();

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
    private static final Metadata METADATA = Metadata.builder()
            .customerReference("2018-08-7T11:04:37.057Z_customerReference")
            .jobValidation(formatter.format(Instant.now()), formatter.format(Instant.now().plus(1, ChronoUnit.DAYS)))
            .build();
    private static final TransportOptions TRANSPORT_OPTIONS = TransportOptions.builder().csid("test_csid")
            .isExpress(false).isDenyListed(false).build();

    private static FaxServiceClient faxClient;
    private static URLProvider urlProviderMock;

    @BeforeAll
    static void setUp() {
        urlProviderMock = mock(URLProvider.class);

        when(urlProviderMock.getLocations()).thenReturn(new Location[]{Location.MUNICH});

        faxClient = FaxServiceClient.builder()
                .username("username")
                .password("password")
                //Optional
                .locale(urlProviderMock)
                //Optional
                .customerNumber("12345")
                .build();
    }

    @Test
    @DisplayName("This test will send a fax request and get a response with the given jobId.")
    @Order(1)
    void testSendFaxRequestAndGetReport() throws IOException {
        FaxRequest faxRequest = FaxRequest.builder()
                .reference(REFERENCE)
                .faxRecipient(FAX_RECIPIENT)
                .document(Document.builder().filePath("src/main/resources/test.txt").charset(Charset.UTF_8).build())
                .renderingOptions(RENDERING_OPTIONS)
                .statusReportOptions(STATUS_REPORT_OPTIONS)
//                .metadata(METADATA)
//                .transportOptions(TRANSPORT_OPTIONS)
                .build();

        mockClientAndResponse(new String(Files.readAllBytes(Paths.get("src/main/resources/send_fax_response.json")), StandardCharsets.UTF_8));

        ApiResponse<String> jobRequestResult = faxClient.sendFaxRequest(faxRequest);
        assertEquals(HttpStatus.SC_OK, jobRequestResult.getStatusCode());
        assertFalse(jobRequestResult.getValue().isEmpty());
        assertEquals("FJLI3102O5BEPYOTB5LPC9", jobRequestResult.getValue());
    }


    @Test
    @DisplayName("This test will send a fax request async and get a response with the given jobId.")
    @Order(2)
    void testSendFaxRequestAsyncAndGetReport() throws ExecutionException, InterruptedException, IOException {

        FaxRequest faxRequest = FaxRequest.builder()
                .reference(REFERENCE)
                .faxRecipient(FAX_RECIPIENT)
                .document(Document.builder().filePath("src/main/resources/test.pdf").build())
                .renderingOptions(RENDERING_OPTIONS)
                .statusReportOptions(STATUS_REPORT_OPTIONS)
                .build();

        mockClientAndResponse(new String(Files.readAllBytes(Paths.get("src/main/resources/send_fax_response.json")), StandardCharsets.UTF_8));

        CompletableFuture<ApiResponse<String>> asyncResponse = faxClient.sendFaxRequestAsync(faxRequest);
        asyncResponse.whenComplete((apiResponse, throwable) -> {
            assertNotNull(apiResponse);
            assertEquals(HttpStatus.SC_OK, apiResponse.getStatusCode());
            assertFalse(apiResponse.getValue().isEmpty());
            assertEquals("FJLI3102O5BEPYOTB5LPC9", apiResponse.getValue());
        });
    }

    @Test
    @DisplayName("This test deletes the available status reports for the given customer number.")
    void testDeletingAvailableStatusReportsForCustomer() throws IOException {

        mockClientAndResponse(new String(Files.readAllBytes(Paths.get("src/main/resources/delete_reports_response.json")), StandardCharsets.UTF_8));


        List<ApiResponse<List<FaxDeletionReport>>> apiResponseList = faxClient.deleteReports();
        assertNotNull(apiResponseList);
        assertTrue(apiResponseList.size() > 0);
        apiResponseList.forEach(apiResponse -> {
            assertEquals(HttpStatus.SC_OK, apiResponse.getStatusCode());
            assertNotNull(apiResponse.getValue());
            apiResponse.getValue().forEach(faxDeletionReport -> {
                assertNotNull(faxDeletionReport.getJobId());
                assertTrue(faxDeletionReport.isDeleted());
                assertNull(faxDeletionReport.getReason());
                assertEquals("FJLI3102O5BEPYOTB5LPC9", faxDeletionReport.getJobId());
            });
        });
    }


    @Test
    @DisplayName("This test gets all available status reports for the given customer number.")
    void testFetchingAllReportsForThisAccount() throws IOException {

        mockClientAndResponse(new String(Files.readAllBytes(Paths.get("src/main/resources/get_fax_reports_response.json")), StandardCharsets.UTF_8));

        List<ApiResponse<List<FaxStatusReport>>> apiResponseList = faxClient.getReports();
        assertNotNull(apiResponseList);
        assertTrue(apiResponseList.size() > 0);
        apiResponseList.forEach(apiResponse -> {
            assertEquals(HttpStatus.SC_OK, apiResponse.getStatusCode());
            assertNotNull(apiResponse.getValue());
            apiResponse.getValue().forEach(report -> {
                assertNotNull(report.getJobId());
                assertNotNull(report.getReference());
                assertTrue(report.getPages() > 0);
                assertNotNull(report.getStatusList());
                report.getStatusList().forEach(recipientStatus -> {
                    assertNotNull(recipientStatus.getRecipientNumber());
                    assertNotNull(recipientStatus.getStatus());
                    assertNotNull(recipientStatus.getPropertiesList());
                });
                assertEquals("FJLI3102O5BEPYOTB5LPC9", report.getJobId());
            });
        });
    }

    @Test
    @DisplayName("This test checks if the init method throws an exception if the required parameters are missing.")
    void testInit() {
        // Test missing username parameter
        assertThrows(AuthException.class, () -> {
            FaxServiceClient.builder()
                    .username(null)
                    .password("test_password")
                    .customerNumber("test_customer_number")
                    .locale(Region.EUROPE)
                    .build();
        });

        // Test missing password parameter
        assertThrows(AuthException.class, () -> {
            FaxServiceClient.builder()
                    .username("test_username")
                    .password(null)
                    .customerNumber("test_customer_number")
                    .locale(Region.EUROPE)
                    .build();
        });

        // Test missing customer number parameter
        assertDoesNotThrow(() -> {
            FaxServiceClient.builder()
                    .username("test_username")
                    .password("test_password")
                    .locale(Region.EUROPE)
                    .build();
        });

        // Test missing region parameter
        assertDoesNotThrow(() -> {

            FaxServiceClient.builder()
                    .username("test_username")
                    .password("test_password")
                    .customerNumber("test_customer_number")
                    .build();
        });
    }

    private static void mockClientAndResponse(String response) throws IOException {

        //Mock the HttpClient and HttpResponse
        CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);

        HttpEntity httpEntity = new StringEntity(response, StandardCharsets.UTF_8);
        StatusLine statusLine = mock(StatusLine.class);
        when(mockHttpResponse.getEntity()).thenReturn(httpEntity);
        when(mockHttpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);

        when(mockHttpClient.execute(any(HttpUriRequest.class))).thenReturn(mockHttpResponse);

        String testUrl = "http://localhost:9001";

        when(urlProviderMock.getSendUrl()).thenReturn(testUrl);

        HttpClient.setHttpClient(mockHttpClient);
    }

}