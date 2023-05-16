package com.retarus.fax.base.sendfax;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author thiagon
 * <br>
 * Character encoding of plain text documents (*.txt).
 * <br>
 * <p>Possible values are:
 * <p>US_ASCII: US-ASCII</p>
 * <p>UTF_8: UTF-8</p>
 * <p>UTF_16: UTF-16</p>
 * <p>UTF_16BE: UTF-16BE</p>
 * <p>UTF_16LE: UTF-16LE</p>
 * <p>ISO_8859_1: ISO-8859-1</p>
 * <p>WINDOWS_1252: WINDOWS-1252</p>
 */
public enum Charset {
    US_ASCII("US-ASCII"),
    UTF_8("UTF-8"),
    UTF_16("UTF-16"),
    UTF_16BE("UTF-16BE"),
    UTF_16LE("UTF-16LE"),
    ISO_8859_1("ISO-8859-1"),
    WINDOWS_1252("WINDOWS-1252");

    private final String charsetString;

    Charset(String charsetString) {
        this.charsetString = charsetString;
    }

    /**
     * Returns the default charset.
     *
     * @return the default charset, UTF-8
     */
    public static Charset defaultCharset() {
        return UTF_8;
    }

    @JsonValue
    public String getCharsetString() {
        return charsetString;
    }
}
