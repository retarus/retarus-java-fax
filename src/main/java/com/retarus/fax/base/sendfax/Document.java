package com.retarus.fax.base.sendfax;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.utils.FileUtils;

import java.io.File;
import java.util.Objects;

import static com.retarus.fax.utils.FileUtils.readFileAndConvertToBase64;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Class for the document of a fax request.
 * <br>The document contains the filename, the character encoding of plain text documents (*.txt),
 * the reference URL, and the data.
 * <br>Data and additional information on sent documents; if there is no document data and no cover page, the service will not accept the job.
 * <br>The data (document data) must be encoded in Base64, if no reference URL is provided, the data is used.
 * <br>You can read a file path using the filePath method, and it is automatically converted to Base64 and set as data.
 * <p>
 * <br>Please note: either reference or data can be used in a single document, but not both at the same time, if both are provided, the reference is used, please use only one at the time.
 * <br>Please note: The maximum possible length of a file name is 32 characters. Allowed characters are: a-zA-Z0-9-_. , and no whitespaces, slashes, or other special characters are permitted.
 */
public class Document {

    //The allowed characters in the filename
    private static final String ALLOWED_REGEX = "^[a-zA-Z0-9-_.]+$";

    /**
     * The filename of the document.
     * The maximum possible length of a file name is 32 characters. Allowed characters are: a-zA-Z0-9-_. , and no whitespaces, slashes, or other special characters are permitted.
     */
    @JsonProperty("name")
    private final String filename;

    /**
     * The character encoding of plain text documents (*.txt).
     */
    @JsonProperty("charset")
    private final Charset charset;

    /**
     * A URL pointing to the document to be transmitted, the URL must be accessible from the RetarusFax infrastructure.
     */
    @JsonProperty("reference")
    private final String referenceUrl;

    /**
     * The document data.
     * The data (document data) must be encoded in Base64, if no reference URL is provided, the data is used.
     */
    @JsonProperty("data")
    private final String data;

    /**
     * Constructor for the document.
     *
     * @param filename     The filename of the document.
     *                     The maximum possible length of a file name is 32 characters. Allowed characters are: a-zA-Z0-9-_. , and no whitespaces, slashes, or other special characters are permitted.
     * @param charset      The character encoding of plain text documents (*.txt).
     * @param referenceUrl A URL pointing to the document to be transmitted, the URL must be accessible from the RetarusFax infrastructure.
     * @param data         The document data.
     *                     The data (document data) must be encoded in Base64, if no reference URL is provided, the data is used.
     * @throws ApiException if the filename is longer than 32 characters or contains invalid characters.
     */
    private Document(String filename, Charset charset, String referenceUrl, String data) {
        this.filename = filename;
        this.charset = charset;
        this.referenceUrl = referenceUrl;
        this.data = data;
    }

    public static DocumentBuilder builder() {
        return new DocumentBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return filename.equals(document.filename) && charset == document.charset && Objects.equals(referenceUrl, document.referenceUrl) && Objects.equals(data, document.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, charset, referenceUrl, data);
    }

    public String getFilename() {
        return this.filename;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getReferenceUrl() {
        return this.referenceUrl;
    }

    public String getData() {
        return this.data;
    }

    public static class DocumentBuilder {
        private String filename;
        private Charset charset;
        private String referenceUrl;
        private String data;

        private DocumentBuilder() {
        }

        /**
         * @param filename The filename of the document.
         *                 The maximum possible length of a file name is 32 characters. Allowed characters are: a-zA-Z0-9-_. , and no whitespaces, slashes, or other special characters are permitted.
         * @throws ApiException if the filename is not valid or the length is greater than 32 characters.
         * @return The document builder.
         */
        public DocumentBuilder filename(String filename) {
            //Validate the allowed characters in the filename and the length of the filename
            if (filename == null || (!filename.matches(ALLOWED_REGEX) || filename.length() > 32)) {
                throw new ApiException("The filename must be less or equal to 32 characters long and only contain the following characters: a-zA-Z0-9-_.");
            }
            this.filename = filename;
            return this;
        }

        /**
         * @param charset The character encoding of plain text documents (*.txt).
         * @return The document builder.
         */
        public DocumentBuilder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        /**
         * @param referenceUrl A URL pointing to the document to be transmitted, the URL must be accessible from the RetarusFax infrastructure.
         * @return The document builder.
         */
        public DocumentBuilder referenceURL(String referenceUrl) {
            this.referenceUrl = referenceUrl;
            return this;
        }

        /**
         * @param data The document data.
         *             The data (document data) must be encoded in Base64, if no reference URL is provided, the data is used.
         * @return The document builder.
         */
        public DocumentBuilder data(String data) {
            this.data = data;
            return this;
        }

        /**
         * Reads the file from the given path and sets the data of the document, as well as the filename.
         *
         * @param filePath The path to the file to be sent.
         * @throws ApiException if the file cannot be read or the filename is not valid
         * @return The document builder.
         */
        public DocumentBuilder filePath(String filePath) {
            if (isBlank(filePath)) {
                throw new ApiException("The file path must not be null or empty.");
            }
            File file = new File(filePath);
            filename(file.getName());
            data(readFileAndConvertToBase64(file));
            return this;
        }

        /**
         * @return a new Document object
         */
        public Document build() {
            if (isBlank(this.filename)) {
                throw new ApiException("The filename must not be null or empty.");
            } else if (isBlank(this.data) && isBlank(this.referenceUrl)) {
                throw new ApiException("Either the data or the reference URL must not be null or empty.");
            } else if (this.charset == null && this.filename.endsWith(".txt")) {
                this.charset = Charset.defaultCharset();
            }

            return new Document(filename, charset, referenceUrl, data);
        }

        public String toString() {
            return "Document.DocumentBuilder(filename=" + this.filename + ", charset=" + this.charset + ", referenceUrl=" + this.referenceUrl + ", data=" + this.data + ")";
        }
    }
}
