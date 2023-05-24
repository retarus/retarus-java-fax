package com.retarus.fax.api.sendfax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retarus.fax.base.sendfax.Document;
import com.retarus.fax.base.sendfax.FaxRecipient;
import com.retarus.fax.base.sendfax.FaxRequest;
import com.retarus.fax.base.sendfax.Reference;
import com.retarus.fax.base.sendfax.metadata.Metadata;
import com.retarus.fax.base.sendfax.rendering.PaperFormat;
import com.retarus.fax.base.sendfax.rendering.PaperResolution;
import com.retarus.fax.base.sendfax.rendering.RenderingOptions;
import com.retarus.fax.base.sendfax.status.*;
import com.retarus.fax.base.sendfax.transport.TransportOptions;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FaxRequestToJSONTest {

    @Test
    @DisplayName("Test sample fax request serializes to JSON")
    void test() throws JSONException {

        FaxRequest faxRequest = FaxRequest.builder()
                .reference(Reference.builder().customerDefinedId("2015-08-07T11:04:37.057Z_customerDefinedId_testSerializesToJSON")
                        .billingCode("2015-08-07T11:04:37.057Z_billingCode_testSerializesToJSON")
                        .billingInfo("2015-08-07T11:04:37.057Z_billingInfo_testSerializesToJSON")
                        .build())
                .faxRecipients(FaxRecipient.builder().faxNumber("004989312000000000").property("key123", "value123").build(),
                        FaxRecipient.builder().faxNumber("0049893120000000002").property("key123", "value123").build())
                .documents(
                        Document.builder().filename("test-document-withReference.txt").referenceURL("http://retarus.com/junit-test").build(),
                        Document.builder().filename("test-document-inline-byte.txt").data("SGVsbG8sIHRoaXMgaXMgYSB0ZXN0aW5nIGRvY3VtZW50IGJvZHkgY3JlYXRlZCBmb3IgOTk5OTlURQ==").build())
                .transportOptions(TransportOptions.builder().csid("csid-test").isExpress(true).isDenyListed(false).build())
                .renderingOptions(RenderingOptions.builder().paperFormat(PaperFormat.A4).paperResolution(PaperResolution.HIGH).coverPageTemplate("coverpage-default.ftl.html").header("%tz=CEST Testfax: CSID: %C Empfaengernummer: %# Datum: %d.%m.%Y %H:%M %z").build())
                .statusReportOptions(
                        StatusReportOptions.builder()
                                .reportMail(ReportMail.builder().successAddress("thiago.nunes@retarus.pt").failureAddress("thiago.nunes@retarus.pt").attachedFaxImageFormat(FaxImageFormat.PDF).attachedFaxImageMode(FaxImageMode.ALWAYS).build())
                                .statusPush(StatusPush.builder().url("http://retarus.com/junit-test/testSerializesToJSON").build())
                                .build())
                .metadata(Metadata.builder().customerReference("12345").jobValidation("2015-08-07T13:04:37.107Z", "2015-08-07T14:04:37.107Z").build())
                .build();


        // Create an instance of the ObjectMapper class
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert the FaxRequest object to JSON
        AtomicReference<String> generatedJSON = new AtomicReference<>();
        assertDoesNotThrow(() -> generatedJSON.set(objectMapper.writeValueAsString(faxRequest)));

        // Check if the generated JSON is not null
        assertNotNull(generatedJSON.get());

        String expectedJSON = "{\n" +
                "\t\"reference\": {\n" +
                "\t\t\"customerDefinedId\": \"2015-08-07T11:04:37.057Z_customerDefinedId_testSerializesToJSON\",\n" +
                "\t\t\"billingCode\": \"2015-08-07T11:04:37.057Z_billingCode_testSerializesToJSON\",\n" +
                "\t\t\"billingInfo\": \"2015-08-07T11:04:37.057Z_billingInfo_testSerializesToJSON\"\n" +
                "\t},\n" +
                "\t\"recipients\": [{\n" +
                "\t\t\"number\": \"004989312000000000\",\n" +
                "\t\t\"properties\": [{\n" +
                "\t\t\t\"key\": \"key123\",\n" +
                "\t\t\t\"value\": \"value123\"\n" +
                "\t\t}]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"number\": \"0049893120000000002\",\n" +
                "\t\t\"properties\": [{\n" +
                "\t\t\t\"key\": \"key123\",\n" +
                "\t\t\t\"value\": \"value123\"\n" +
                "\t\t}]\n" +
                "\t}],\n" +
                "\t\"documents\": [{\n" +
                "\t\t\"name\": \"test-document-withReference.txt\",\n" +
                "\t\t\"charset\": \"UTF-8\",\n" +
                "\t\t\"reference\": \"http://retarus.com/junit-test\",\n" +
                "\t\t\"data\": null\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\": \"test-document-inline-byte.txt\",\n" +
                "\t\t\"charset\": \"UTF-8\",\n" +
                "\t\t\"reference\": null,\n" +
                "\t\t\"data\": \"SGVsbG8sIHRoaXMgaXMgYSB0ZXN0aW5nIGRvY3VtZW50IGJvZHkgY3JlYXRlZCBmb3IgOTk5OTlURQ==\"\n" +
                "\t}],\n" +
                "\t\"transportOptions\": {\n" +
                "\t\t\"csid\": \"csid-test\",\n" +
                "\t\t\"isExpress\": true,\n" +
                "\t\t\"isBlacklistEnabled\": false\n" +
                "\t},\n" +
                "\t\"renderingOptions\": {\n" +
                "\t\t\"paperFormat\": \"A4\",\n" +
                "\t\t\"resolution\": \"HIGH\",\n" +
                "\t\t\"coverpageTemplate\": \"coverpage-default.ftl.html\",\n" +
                "\t\t\"overlay\": null,\n" +
                "\t\t\"header\": \"%tz=CEST Testfax: CSID: %C Empfaengernummer: %# Datum: %d.%m.%Y %H:%M %z\"\n" +
                "\t},\n" +
                "\t\"statusReportOptions\": {\n" +
                "\t\t\"reportMail\": {\n" +
                "\t\t\t\"successAddress\": \"thiago.nunes@retarus.pt\",\n" +
                "\t\t\t\"failureAddress\": \"thiago.nunes@retarus.pt\",\n" +
                "\t\t\t\"attachedFaxImageMode\": \"ALWAYS\",\n" +
                "\t\t\t\"attachedFaxImageFormat\": \"PDF\"\n" +
                "\t\t},\n" +
                "\t\t\"httpStatusPush\": {\n" +
                "\t\t\t\"targetUrl\": \"http://retarus.com/junit-test/testSerializesToJSON\",\n" +
                "\t\t\t\"principal\": null,\n" +
                "\t\t\t\"credentials\": null,\n" +
                "\t\t\t\"authMethod\": \"NONE\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"meta\": {\n" +
                "\t\t\"customerReference\": \"12345\",\n" +
                "\t\t\"jobValid\": {\n" +
                "\t\t\t\"start\": \"2015-08-07T13:04:37.107Z\",\n" +
                "\t\t\t\"end\": \"2015-08-07T14:04:37.107Z\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        //Compare the generated JSON with the expected JSON regardless of their order and child order
        JSONAssert.assertEquals(expectedJSON, generatedJSON.get(), JSONCompareMode.LENIENT);
    }

}