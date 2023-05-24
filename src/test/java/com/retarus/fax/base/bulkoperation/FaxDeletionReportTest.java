package com.retarus.fax.base.bulkoperation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.retarus.fax.base.responses.FaxDeletionReport;
import com.retarus.fax.base.responses.Reason;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FaxDeletionReportTest {
    @Test
    void testBuilder() {
        FaxDeletionReport report = FaxDeletionReport.builder()
                .jobId("123")
                .deleted(true)
                .build();

        Assertions.assertEquals("123", report.getJobId());
        Assertions.assertTrue(report.isDeleted());
        Assertions.assertNull(report.getReason());
    }

    @Test
    void testBuilderWithReason() {
        FaxDeletionReport report = FaxDeletionReport.builder()
                .jobId("123")
                .deleted(false)
                .reason(Reason.NOT_FOUND)
                .build();

        Assertions.assertEquals("123", report.getJobId());
        Assertions.assertFalse(report.isDeleted());
        Assertions.assertEquals(Reason.NOT_FOUND, report.getReason());
    }

    @Test
    void testEqualsAndHashCode() {
        FaxDeletionReport report1 = FaxDeletionReport.builder()
                .jobId("123")
                .deleted(true)
                .build();

        FaxDeletionReport report2 = FaxDeletionReport.builder()
                .jobId("123")
                .deleted(true)
                .build();

        FaxDeletionReport report3 = FaxDeletionReport.builder()
                .jobId("456")
                .deleted(true)
                .build();

        Assertions.assertEquals(report1, report2);
        Assertions.assertNotEquals(report1, report3);
        Assertions.assertEquals(report1.hashCode(), report2.hashCode());
        Assertions.assertNotEquals(report1.hashCode(), report3.hashCode());
    }


    @Test
    @DisplayName("Test sample job delete report serialization to JSON")
    void test() throws JSONException {


        FaxDeletionReport[] expected = {
                FaxDeletionReport.builder().jobId("FJLEPVGKFXCTZ9JCNUJQAE").deleted(true).build(),
        };

        List<FaxDeletionReport> faxDeletionReports = Arrays.asList(expected);
        // Create an instance of the ObjectMapper class
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert the FaxBulkOperation object to JSON
        AtomicReference<String> generatedJSON = new AtomicReference<>();
        assertDoesNotThrow(() -> generatedJSON.set(objectMapper.writeValueAsString(faxDeletionReports)));

        JSONArray reportsArray = new JSONArray(generatedJSON.get());
        JSONObject reportsObject = new JSONObject().put("reports", reportsArray);

        // Check if the generated JSON is not null
        assertNotNull(generatedJSON.get());
        System.out.println(reportsObject.toString());

        String expectedJSON = "{\n" +
                "    \"reports\": [\n" +
                "        {\n" +
                "            \"jobId\": \"FJLEPVGKFXCTZ9JCNUJQAE\",\n" +
                "            \"deleted\": true\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //Compare the generated JSON with the expected JSON regardless of their order and child order
        System.out.println(expectedJSON.trim());
        System.out.println(reportsObject);
        JSONAssert.assertEquals(expectedJSON, reportsObject, JSONCompareMode.LENIENT);
    }
}