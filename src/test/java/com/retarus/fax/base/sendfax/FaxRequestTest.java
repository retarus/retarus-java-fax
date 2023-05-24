package com.retarus.fax.base.sendfax;

import com.retarus.fax.base.sendfax.Document;
import com.retarus.fax.base.sendfax.FaxRecipient;
import com.retarus.fax.base.sendfax.FaxRequest;
import com.retarus.fax.base.sendfax.Reference;
import com.retarus.fax.base.sendfax.metadata.Metadata;
import com.retarus.fax.base.sendfax.rendering.OverlayMode;
import com.retarus.fax.base.sendfax.rendering.RenderingOptions;
import com.retarus.fax.base.sendfax.status.*;
import com.retarus.fax.base.sendfax.transport.TransportOptions;
import com.retarus.fax.exception.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class FaxRequestTest {

    //Test a fax request with all the options and possible objects filled
    @Test
    @DisplayName("Test a fax request with all the options and possible objects filled")
    void testFaxRequestWithAllOptions() {
        //Generate a fax recipient with a fax number
        FaxRecipient faxRecipient = FaxRecipient.builder()
                .faxNumber("faxNumber")
                .build();

        //Generate a metadata object with a valid jobvalidation date
        Metadata metadata = Metadata.builder()
                .customerReference("customerReference")
                .jobValidation(Instant.now(), Instant.now())
                .build();

        //Generate the rendering options with overlay
        RenderingOptions renderingOptions = RenderingOptions.builder()
                .overlay("overlayName", OverlayMode.ALL_PAGES)
                .build();

        //Generate the report mail object with all fields
        ReportMail reportMail = ReportMail.builder()
                .successAddress("mail")
                .failureAddress("mail2")
                .attachedFaxImageMode(FaxImageMode.SUCCESS_ONLY)
                .attachedFaxImageFormat(FaxImageFormat.PDF)
                .build();
        //Generate a valid status push object
        StatusPush statusPush = StatusPush.builder()
                .url("url")
                .username("username")
                .password("password")
                .build();

        //Generate the Status Report options with the report mail and status push objects
        StatusReportOptions statusReportOptions = StatusReportOptions.builder()
                .reportMail(reportMail)
                .statusPush(statusPush)
                .build();

        //Generate valid transport options object with all fields
        TransportOptions transportOptions = TransportOptions.builder()
                .csid("csid")
                .isExpress(true)
                .isDenyListed(true)
                .build();

        //Generate a valid document object with all fields
        Document document = Document.builder()
                .filePath("src/main/resources/test.txt")
                .build();

        //Generate a reference object with all fields
        Reference reference = Reference.builder()
                .customerDefinedId("customerDefinedId")
                .billingCode("billingCode")
                .billingInfo("billingInfo")
                .build();

        //Generate a fax request object with all the objects and options
        FaxRequest faxRequest = FaxRequest.builder()
                .faxRecipient(faxRecipient)
                .metadata(metadata)
                .renderingOptions(renderingOptions)
                .statusReportOptions(statusReportOptions)
                .transportOptions(transportOptions)
                .document(document)
                .reference(reference)
                .build();

        //Check if the fax request object has all the fields filled

        assertTrue(faxRequest.getFaxRecipients().contains(faxRecipient));
        assertEquals(metadata, faxRequest.getMetadata());
        assertEquals(renderingOptions, faxRequest.getRenderingOptions());
        assertEquals(statusReportOptions, faxRequest.getStatusReportOptions());
        assertEquals(transportOptions, faxRequest.getTransportOptions());
        assertTrue(faxRequest.getDocuments().contains(document));
        assertEquals(reference, faxRequest.getReference());
    }

    //Test a fax request with only the required fields filled
    @Test
    @DisplayName("Test a fax request with only the required fields filled")
    void testFaxRequestWithOnlyRequiredFields() {

        //Generate a fax recipient with a fax number
        FaxRecipient faxRecipient = FaxRecipient.builder()
                .faxNumber("faxNumber")
                .build();

        //Generate a fax request object with only the required fields
        FaxRequest faxRequest = FaxRequest.builder()
                .faxRecipient(faxRecipient)
                .build();
        Instant.now().plus(1, ChronoUnit.DAYS);

        //Check if the fax request object has all the fields filled
        assertTrue(faxRequest.getFaxRecipients().contains(faxRecipient));
        assertNull(faxRequest.getMetadata());
        assertNull(faxRequest.getRenderingOptions());
        assertNull(faxRequest.getStatusReportOptions());
        assertNull(faxRequest.getTransportOptions());
        assertNull(faxRequest.getDocuments());
        assertNull(faxRequest.getReference());
    }

    //Test an empty fax request and validate that it throws an exception
    @Test
    @DisplayName("Test an empty fax request and validate that it throws an exception")
    void testEmptyFaxRequest() {
        Assertions.assertThrows(ApiException.class, () -> FaxRequest.builder()
                .build());
    }
}