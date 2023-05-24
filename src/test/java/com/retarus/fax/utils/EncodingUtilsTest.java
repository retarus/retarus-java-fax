package com.retarus.fax.utils;

import com.retarus.fax.utils.EncodingUtils;
import org.junit.jupiter.api.Test;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EncodingUtilsTest {

    @Test
    void testEncodeCredentialsToString() {
        String username = "testuser";
        String password = "testpass";
        String expected = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        String result = EncodingUtils.encodeCredentialsToString(username, password);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void testEncodeCredentialsToStringWithEmptyStrings() {
        String username = "";
        String password = "";
        String expected = Base64.getEncoder().encodeToString(":".getBytes());
        String result = EncodingUtils.encodeCredentialsToString(username, password);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void testEncodeCredentialsToStringWithNullStrings() {
        String username = null;
        String password = null;
        String expected = Base64.getEncoder().encodeToString((null+":"+null).getBytes());
        String result = EncodingUtils.encodeCredentialsToString(username, password);
        assertNotNull(result);
        assertEquals(expected, result);
    }
}
