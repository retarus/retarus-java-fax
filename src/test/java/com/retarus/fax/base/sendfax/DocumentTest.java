package com.retarus.fax.base.sendfax;

import com.retarus.fax.base.sendfax.Charset;
import com.retarus.fax.base.sendfax.Document;
import com.retarus.fax.exception.ApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    //Test builder with filename and data
    @Test
    @DisplayName("Test builder with filename and data")
    void testBuilderWithFilenameAndData() {
        String filename = "test.txt";
        String data = "test";
        Document document = Document.builder()
                .filename(filename)
                .data(data)
                .build();
        assertEquals(filename, document.getFilename());
        assertEquals(data, document.getData());
    }

    //Test builder with filename, charset and data
    @Test
    @DisplayName("Test builder with filename, charset and data")
    void testBuilderWithFilenameCharsetAndData() {
        String filename = "test.txt";
        String data = "test";
        Document document = Document.builder()
                .filename(filename)
                .charset(Charset.UTF_8)
                .data(data)
                .build();
        assertEquals(filename, document.getFilename());
        assertEquals(data, document.getData());
        assertEquals(Charset.UTF_8, document.getCharset());
    }

    //Test builder with filename, charset, referenceUrl and data
    @Test
    @DisplayName("Test builder with filename, charset, referenceUrl and data")
    void testBuilderWithFilenameCharsetReferenceUrlAndData() {
        String filename = "test.txt";
        String data = "test";
        String referenceUrl = "https://www.retarus.de";
        Document document = Document.builder()
                .filename(filename)
                .charset(Charset.UTF_8)
                .referenceURL(referenceUrl)
                .data(data)
                .build();
        assertEquals(filename, document.getFilename());
        assertEquals(data, document.getData());
        assertEquals(Charset.UTF_8, document.getCharset());
        assertEquals(referenceUrl, document.getReferenceUrl());
    }


    //Test builder with file path, for file in src/main/resources/test.pdf
    @Test
    @DisplayName("Test builder with file path, for file in src/main/resources/test.pdf")
    void testBuilderWithFilePath() {
        String filename = "test.pdf";
        Document document = Document.builder()
                .filePath("src/main/resources/test.pdf")
                .build();

        assertEquals(filename, document.getFilename());
        assertNotNull(document.getData());
    }

    //Test builder with file path, for file in src/main/resources/test.txt
    @Test
    @DisplayName("Test builder with file path, for file in src/main/resources/test.txt")
    void testBuilderWithFilePathTxt() {
        String filename = "test.txt";
        Document document = Document.builder()
                .filePath("src/main/resources/test.txt")
                .build();

        assertEquals(filename, document.getFilename());
        assertNotNull(document.getData());
    }

    //Test builder with null values and make sure it throws an exception
    @Test
    @DisplayName("Test builder with null values and make sure it throws an exception")
    void testBuilderWithNullValues() {
        assertThrows(ApiException.class, () -> {
            Document.builder()
                    .filename(null)
                    .data(null)
                    .build();
        });
        assertThrows(ApiException.class, () -> {
            Document.builder()
                    .filePath(null)
                    .build();
        });
    }

    //Add more tests here
    @Test
    @DisplayName("Test builder with empty values and make sure it throws an exception")
    void testBuilderWithEmptyValues() {
        assertThrows(ApiException.class, () -> {
            Document.builder()
                    .filename("")
                    .data("")
                    .build();
        });
        assertThrows(ApiException.class, () -> {
            Document.builder()
                    .filePath("")
                    .build();
        });
    }

    @Test
    @DisplayName("Test builder withouth any values and make sure it throws an exception")
    void testBuilderWithouthAnyValues() {
        assertThrows(ApiException.class, () -> {
            Document.builder()
                    .build();
        });
    }
}