package com.retarus.fax.base.bulkoperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.exception.ApiException;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class FaxBulkOperationTest {

    @Test
    @DisplayName("Test builder with varargs")
    void testConstructorWithVarargs() {
        FaxBulkOperation faxBulkOperation = FaxBulkOperation.builder().action(HttpMethod.DELETE).jobIds("6789", "1011")
                .build();


        assertEquals(HttpMethod.DELETE, faxBulkOperation.getAction());
        assertEquals(Arrays.asList("6789", "1011"), faxBulkOperation.getJobIds());
    }

    @Test
    @DisplayName("Test builder with list")
    void testBuilderWithList() {
        List<String> jobIds = Arrays.asList("6789", "1011");
        FaxBulkOperation faxBulkOperation = FaxBulkOperation.builder().action(HttpMethod.GET).jobIds(jobIds).build();

        assertEquals(HttpMethod.GET, faxBulkOperation.getAction());
        assertEquals(jobIds, faxBulkOperation.getJobIds());
    }

    @Test
    @DisplayName("Test builder")
    void testBuilder() {
        List<String> jobIds = Arrays.asList("6789", "1011");
        FaxBulkOperation faxBulkOperation = FaxBulkOperation.builder()
                .action(HttpMethod.GET)
                .jobIds(jobIds)
                .build();

        assertEquals(HttpMethod.GET, faxBulkOperation.getAction());
        assertEquals(jobIds, faxBulkOperation.getJobIds());
    }

    @Test
    @DisplayName("Test builder with job ids varargs")
    void testBuilderWithJobIdsVarargs() {
        FaxBulkOperation faxBulkOperation = FaxBulkOperation.builder()

                .action(HttpMethod.GET)
                .jobIds("6789", "1011")
                .build();

        assertEquals(HttpMethod.GET, faxBulkOperation.getAction());
        assertEquals(Arrays.asList("6789", "1011"), faxBulkOperation.getJobIds());
    }

    @Test
    @DisplayName("Test builder with invalid action")
    void testBuilderWithInvalidAction() {
        Assertions.assertThrows(ApiException.class, () -> FaxBulkOperation.builder()

                .action(HttpMethod.POST)
                .jobIds("6789", "1011")
                .build());
    }


    @Test
    @DisplayName("Test sample bulk operation serialization to JSON")
    void test() throws JSONException {

        String customerNumber = "99999";
        FaxBulkOperation bulkOperationsRequest = FaxBulkOperation.builder()
                .action(HttpMethod.GET)
                .jobIds("FJJ5Y09UM505ZZBIELCRYC",
                        "FJJ5WA23UO05ZZBIEL0JY5",
                        "FJJ5WA0FWM05ZZBIELZPE4",
                        "FJJ5Y07H3805ZZBIELBAPL",
                        "FJJ5WA25VB0BRN8TPSGGVY",
                        "FJJ5WA253505ZZBIELEKRC",
                        "FJJ5WA24GI0BRN8TPSWAVM")
                .build();


        // Create an instance of the ObjectMapper class
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert the FaxBulkOperation object to JSON
        AtomicReference<String> generatedJSON = new AtomicReference<>();
        assertDoesNotThrow(() -> generatedJSON.set(objectMapper.writeValueAsString(bulkOperationsRequest)));

        // Check if the generated JSON is not null
        assertNotNull(generatedJSON.get());

        String expectedJSON = "{\n" +
                "  \"action\": \"GET\",\n" +
                "  \"jobIds\": [\n" +
                "    \"FJJ5Y09UM505ZZBIELCRYC\",\n" +
                "    \"FJJ5WA23UO05ZZBIEL0JY5\",\n" +
                "    \"FJJ5WA0FWM05ZZBIELZPE4\",\n" +
                "    \"FJJ5Y07H3805ZZBIELBAPL\",\n" +
                "    \"FJJ5WA25VB0BRN8TPSGGVY\",\n" +
                "    \"FJJ5WA253505ZZBIELEKRC\",\n" +
                "    \"FJJ5WA24GI0BRN8TPSWAVM\"\n" +
                "  ]\n" +
                "}";

        //Compare the generated JSON with the expected JSON regardless of their order and child order
        JSONAssert.assertEquals(expectedJSON, generatedJSON.get(), JSONCompareMode.LENIENT);
    }
}
