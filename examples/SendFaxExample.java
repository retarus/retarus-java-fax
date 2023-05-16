import com.retarus.fax.base.ApiResponse;
import com.retarus.fax.base.rest.Region;
import com.retarus.fax.base.sendfax.rendering.PaperFormat;
import com.retarus.fax.base.sendfax.rendering.PaperResolution;
import com.retarus.fax.base.sendfax.rendering.RenderingOptions;
import com.retarus.fax.http.FaxServiceClient;
import org.apache.http.HttpStatus;

import java.util.Collections;

public class SendFaxExample {

    public static void main(String[] args) {
        //Initialize the client
        FaxServiceClient client = initializeFaxServiceClient();

        //Generate the classes required to send a fax

        //Reference
        Reference reference = Reference.builder()
                .customerDefinedId("customerDefinedId")
                .billingCode("billingCode")
                .billingInfo("billingInfo")
                .build();

        //Fax recipient and properties
        FaxRecipient faxRecipient = FaxRecipient.builder()
                .faxNumber("+49123456789")
                .property("FromName", "Name")
                .property("FromCompanyName", "Company Name")
                .property("FromTelNum", "1234567890")
                .property("ToName", "Luke Skywalker")
                .property("ToCompanyName", "Jedi Order")
                .property("ToFaxNum", "1234567890")
                .property("SubjectTitle", "Test Fax using the RetarusFax SDK")
                .property("SubjectText", "This is a test fax using the Retarus Fax SDK")
                .build();

        //Document to be sent
        Document document = Document.builder()
                .filename("foobar.txt")
                .data("JVBERi0xLjcKCjQgMCBvYmoKKElkZW50aXR5KQplbmRvYmoKNSAwIG9iagooQWR")
                .charset(Charset.UTF_8)
                .build();
        //Rendering options
        RenderingOptions renderingOptions = RenderingOptions.builder()
                .paperFormat(PaperFormat.A4)
                .paperResolution(PaperResolution.HIGH)
                .coverPageTemplate("coverpage-default.ftl.html")
                .header("Header")
                .build();

        //Report mail addresses and options for the status report
        ReportMail reportMail = ReportMail.builder()
                .successAddress("email@email.com")
                .failureAddress("email@email.com")
                .attachedFaxImageMode(FaxImageMode.SUCCESS_ONLY)
                .attachedFaxImageFormat(FaxImageFormat.PDF)
                .build();

        //Status push options
        StatusPush statusPush = StatusPush.builder()
                .authenticationMethod(AuthenticationMethod.NONE)
                .username("USERNAME")
                .password("PASSWORD")
                .url("http://URL.com")
                .build();

        //Status report options
        StatusReportOptions statusReportOptions = StatusReportOptions.builder()
                .reportMail(reportMail)
                .statusPush(statusPush)
                .build();

        //Generate the fax request object using the desired parameters and options
        FaxRequest faxRequest = FaxRequest.builder()
                .faxRecipients(Collections.singletonList(faxRecipient))
                .documents(Collections.singletonList(document))
                .renderingOptions(renderingOptions)
                .statusReportOptions(statusReportOptions)
                .reference(reference)
                .build();

        //Make a fax request
        ApiResponse<String> apiResponse;
        //Syncronous request
        apiResponse = client.sendFaxRequest(faxRequest);
        //Or async request
        // apiResponse = client.sendFaxRequestAsync(faxRequest);
        if (apiResponse.getStatusCode() == HttpStatus.SC_OK
                || apiResponse.getStatusCode() == HttpStatus.SC_ACCEPTED) {
            //Get the job id from the response
            String jobId = apiResponse.getValue();

            //The fax was successfully sent
            System.out.println("Fax successfully sent with resulting job id: " + jobId);
        } else {
            //The fax was not sent
            System.out.println("Fax was not sent with status code: " + apiResponse.getStatusCode());
        }

    }

    private static FaxServiceClient initializeFaxServiceClient() {
        //Initialize the client, username and password are mandatory, region and customer number are optional
        return FaxServiceClient.builder()
                .username("YOUR_USERNAME")
                .password("YOUR_PASSWORD")
                //Optional
                .locale(Region.EUROPE)
                //Optional
                .customerNumber("YOUR_CUSTOMER_NUMBER")
                .build();

    }
}