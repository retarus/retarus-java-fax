package com.retarus.fax.utils;

import com.retarus.fax.exception.ApiException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;


public class FileUtils {

    private FileUtils() {
    }

    /**
     * Read a file and convert it to a base64 string.
     *
     * @param file the file to read
     * @return the base64 string
     */
    public static String readFileAndConvertToBase64(File file) {
        try {
            byte[] fileData = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileData);
        } catch (IOException e) {
            throw new ApiException("Error reading file", e);
        }
    }
}
