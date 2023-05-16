package com.retarus.fax.base.sendfax.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.retarus.fax.exception.ApiException;

import java.util.Objects;

/**
 * @author thiagon
 * <p>
 * Class for the transport options of a fax.
 * Contains information on the transmission of the fax.
 * The transport options contains the csid, the isExpress, and the isDenylisted.
 */
public class TransportOptions {

    /**
     * The sender ID the received fax was sent from (max. 20 characters).
     */
    @JsonProperty("csid")
    private final String csid;

    /**
     * Flag for transmissions sent express.
     */
    @JsonProperty(value = "isExpress")
    private final boolean isExpress;

    /**
     * Flag for the use of the Robinson List (only for numbers in Germany), ECOFAX (for numbers in France), or RetarusFax' own denylist.
     */
    @JsonProperty(value = "isBlacklistEnabled")
    private final boolean isDenyListed;

    /**
     * Constructor for the transport options.
     *
     * @param csid         The sender ID the received fax was sent from (max. 20 characters).
     * @param isExpress    Flag for transmissions sent express.
     * @param isDenyListed Flag for the use of the Robinson List (only for numbers in Germany), ECOFAX (for numbers in France), or RetarusFax' own deny list.
     * @throws ApiException if the csid is longer than 20 characters.
     */
    private TransportOptions(String csid, boolean isExpress, boolean isDenyListed) {
        this.csid = csid;
        this.isExpress = isExpress;
        this.isDenyListed = isDenyListed;
    }

    public static TransportOptionsBuilder builder() {
        return new TransportOptionsBuilder();
    }

    /**
     * @return The sender ID the received fax was sent from (max. 20 characters).
     */
    public String getCsid() {
        return csid;
    }

    /**
     * @return Flag for transmissions sent express.
     */

    @JsonProperty(value = "isExpress")
    public boolean isExpress() {
        return isExpress;
    }

    /**
     * @return Flag for the use of the Robinson List (only for numbers in Germany), ECOFAX (for numbers in France), or RetarusFax' own deny list.
     */

    @JsonProperty(value = "isBlacklistEnabled")
    public boolean isDenyListed() {
        return isDenyListed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportOptions that = (TransportOptions) o;
        return isExpress == that.isExpress && isDenyListed == that.isDenyListed && Objects.equals(csid, that.csid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(csid, isExpress, isDenyListed);
    }

    public static class TransportOptionsBuilder {

        private String csid;

        private boolean isExpress;

        private boolean isDenyListed;

        private TransportOptionsBuilder() {
        }

        /**
         * @param csid The sender ID the received fax was sent from (max. 20 characters).
         * @return The transport options builder.
         * @throws ApiException if the csid is longer than 20 characters.
         * @return The transport options builder.
         */
        public TransportOptionsBuilder csid(String csid) {
            if (csid != null && csid.length() > 20) {
                throw new ApiException("The csid must be less or equal to 20 characters long. (Current length: " + csid.length() + ")");
            }
            this.csid = csid;
            return this;
        }

        /**
         * @param isExpress Flag for transmissions sent express.
         * @return The transport options builder.
         */
        public TransportOptionsBuilder isExpress(boolean isExpress) {
            this.isExpress = isExpress;
            return this;
        }

        /**
         * @param isDenyListed Flag for the use of the Robinson List (only for numbers in Germany), ECOFAX (for numbers in France), or RetarusFax' own deny list.
     *                     @return The transport options builder.
         */
        public TransportOptionsBuilder isDenyListed(boolean isDenyListed) {
            this.isDenyListed = isDenyListed;
            return this;
        }

        /**
         * @return The transport options.
         */
        public TransportOptions build() {
            return new TransportOptions(csid, isExpress, isDenyListed);
        }

        public String toString() {
            return "TransportOptions.TransportOptionsBuilder(csid=" + this.csid + ", isExpress=" + this.isExpress + ", isDenyListed=" + this.isDenyListed + ")";
        }
    }
}
