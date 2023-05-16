package com.retarus.fax.base.sendfax;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.retarus.fax.base.sendfax.metadata.Metadata;
import com.retarus.fax.exception.ApiException;
import com.retarus.fax.base.sendfax.rendering.RenderingOptions;
import com.retarus.fax.base.sendfax.status.StatusReportOptions;
import com.retarus.fax.base.sendfax.transport.TransportOptions;

import java.util.*;

/**
 * @author thiagon
 * <p>
 * Class used to represent a fax request.
 * <br>The fax request contains the customer number,
 * the documents, the fax recipients, the transport options, the rendering options, the status report options, the metadata, and the reference.
 * <p>
 * This object is used to create a fax job request. The customer number is required, as well as the fax recipients.
 */
public class FaxRequest {

    /**
     * The documents of the fax.
     */
    @JsonProperty("documents")
    private final List<Document> documents;

    /**
     * The fax recipients of the fax.
     */
    @JsonProperty("recipients")
    private final List<FaxRecipient> faxRecipients;

    /**
     * The transport options of the fax.
     */
    @JsonProperty("transportOptions")
    private final TransportOptions transportOptions;

    /**
     * The rendering options of the fax.
     */
    @JsonProperty("renderingOptions")
    private final RenderingOptions renderingOptions;

    /**
     * The status report options of the fax.
     */
    @JsonProperty("statusReportOptions")
    private final StatusReportOptions statusReportOptions;

    /**
     * The metadata of the fax.
     */
    @JsonProperty("meta")
    private final Metadata metadata;

    /**
     * The reference of the fax.
     */
    @JsonProperty("reference")
    private final Reference reference;

    /**
     * Constructor for FaxRequest
     *
     * @param documents           The documents of the fax.
     * @param faxRecipients       The fax recipients of the fax.
     * @param transportOptions    The transport options of the fax.
     * @param renderingOptions    The rendering options of the fax.
     * @param statusReportOptions The status report options of the fax.
     * @param metadata            The metadata of the fax.
     * @param reference           The reference of the fax.
     */
    private FaxRequest(List<Document> documents, List<FaxRecipient> faxRecipients, TransportOptions transportOptions, RenderingOptions renderingOptions, StatusReportOptions statusReportOptions, Metadata metadata, Reference reference) {
        this.documents = documents;
        this.faxRecipients = faxRecipients;
        this.transportOptions = transportOptions;
        this.renderingOptions = renderingOptions;
        this.statusReportOptions = statusReportOptions;
        this.metadata = metadata;
        this.reference = reference;
    }

    public static FaxRequestBuilder builder() {
        return new FaxRequestBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaxRequest that = (FaxRequest) o;
        return Objects.equals(documents, that.documents) && Objects.equals(faxRecipients, that.faxRecipients) && Objects.equals(transportOptions, that.transportOptions) && Objects.equals(renderingOptions, that.renderingOptions) && Objects.equals(statusReportOptions, that.statusReportOptions) && Objects.equals(metadata, that.metadata) && Objects.equals(reference, that.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documents, faxRecipients, transportOptions, renderingOptions, statusReportOptions, metadata, reference);
    }

    public List<Document> getDocuments() {
        return this.documents;
    }

    public List<FaxRecipient> getFaxRecipients() {
        return this.faxRecipients;
    }

    public TransportOptions getTransportOptions() {
        return this.transportOptions;
    }

    public RenderingOptions getRenderingOptions() {
        return this.renderingOptions;
    }

    public StatusReportOptions getStatusReportOptions() {
        return this.statusReportOptions;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public Reference getReference() {
        return this.reference;
    }

    public static class FaxRequestBuilder {
        private List<Document> documents;
        private List<FaxRecipient> faxRecipients;
        private TransportOptions transportOptions;
        private RenderingOptions renderingOptions;
        private StatusReportOptions statusReportOptions;
        private Metadata metadata;
        private Reference reference;

        private FaxRequestBuilder() {
        }

        /**
         * The list of documents of the fax.
         *
         * @param documents The documents list of the fax.
         * @return The builder.
         */
        public FaxRequestBuilder documents(Collection<Document> documents) {
            if (this.documents == null) {
                this.documents = new ArrayList<>();
            } else if (documents == null) {
                return this;
            }
            this.documents.addAll(documents);
            return this;
        }

        /**
         * Add an array of documents to the fax.
         *
         * @param documents The documents array to be added to the fax.
         * @return The builder.
         */
        public FaxRequestBuilder documents(Document... documents) {
            if (this.documents == null) {
                this.documents = new ArrayList<>();
            } else if (documents == null) {
                return this;
            }
            this.documents.addAll(Arrays.asList(documents));
            return this;
        }

        /**
         * A single document to be added to the fax.
         *
         * @param document The document to be sent in the fax.
         * @return The builder.
         */
        public FaxRequestBuilder document(Document document) {
            if (this.documents == null) {
                this.documents = new ArrayList<>();
            } else if (document == null) {
                return this;
            }
            this.documents.add(document);
            return this;
        }

        /**
         * The list of fax recipients of the fax.
         *
         * @param faxRecipients The fax recipients list of the fax.
         * @return The builder.
         */
        public FaxRequestBuilder faxRecipients(List<FaxRecipient> faxRecipients) {
            if (this.faxRecipients == null) {
                this.faxRecipients = new ArrayList<>();
            } else if (faxRecipients == null) {
                return this;
            }
            this.faxRecipients.addAll(faxRecipients);
            return this;
        }

        /**
         * An array of fax recipients to be added to the fax.
         *
         * @param faxRecipients The fax recipients array of the fax.
         * @return The builder.
         */
        public FaxRequestBuilder faxRecipients(FaxRecipient... faxRecipients) {
            if (this.faxRecipients == null) {
                this.faxRecipients = new ArrayList<>();
            } else if (faxRecipients == null) {
                return this;
            }
            this.faxRecipients.addAll(Arrays.asList(faxRecipients));
            return this;
        }

        /**
         * Add a single fax recipient to the fax.
         *
         * @param faxRecipient The fax recipient to be added to the fax.
         * @return The builder.
         */
        public FaxRequestBuilder faxRecipient(FaxRecipient faxRecipient) {
            if (this.faxRecipients == null) {
                this.faxRecipients = new ArrayList<>();
            } else if (faxRecipient == null) {
                return this;
            }
            this.faxRecipients.add(faxRecipient);
            return this;
        }

        /**
         * A single fax recipient to be added to the fax.
         *
         * @param faxRecipient The fax recipients array of the fax.
         * @return The builder.
         */
        public FaxRequestBuilder faxRecipients(FaxRecipient faxRecipient) {
            if (this.faxRecipients == null) {
                this.faxRecipients = new ArrayList<>();
            } else if (faxRecipient == null) {
                return this;
            }
            this.faxRecipients.add(faxRecipient);
            return this;
        }

        /**
         * A single fax recipient to be added to the fax with only the fax recipient number, without properties.
         *
         * @param faxRecipientNumber The fax recipient number to be added to the fax request.
         * @return The builder.
         */
        public FaxRequestBuilder faxRecipientNumber(String faxRecipientNumber) {
            if (this.faxRecipients == null) {
                this.faxRecipients = new ArrayList<>();
            } else if (faxRecipientNumber == null) {
                return this;
            }
            this.faxRecipients.add(FaxRecipient.builder().faxNumber(faxRecipientNumber).build());
            return this;
        }

        /**
         * An array of fax recipient numbers to be added to the fax with only the fax recipient number, without properties.
         *
         * @param faxRecipientNumbers The fax recipient numbers to be added to the fax request.
         * @return The builder.
         */
        public FaxRequestBuilder faxRecipientNumber(String... faxRecipientNumbers) {
            if (this.faxRecipients == null) {
                this.faxRecipients = new ArrayList<>();
            } else if (faxRecipientNumbers == null) {
                return this;
            }

            for (String faxRecipientNumber : faxRecipientNumbers) {
                FaxRecipient recipient = FaxRecipient.builder().faxNumber(faxRecipientNumber).build();
                this.faxRecipients.add(recipient);
            }
            return this;
        }

        /**
         * The transport options of the fax.
         *
         * @param transportOptions The transport options of the fax.
         * @return The builder.
         */
        public FaxRequestBuilder transportOptions(TransportOptions transportOptions) {
            this.transportOptions = transportOptions;
            return this;
        }

        /**
         * The rendering options of the fax.
         *
         * @param renderingOptions The rendering options of the fax.
         * @return The builder.
         */
        public FaxRequestBuilder renderingOptions(RenderingOptions renderingOptions) {
            this.renderingOptions = renderingOptions;
            return this;
        }

        /**
         * The status report options of the fax.
         *
         * @param statusReportOptions The status report options of the fax.
         * @return The builder.
         */
        public FaxRequestBuilder statusReportOptions(StatusReportOptions statusReportOptions) {
            this.statusReportOptions = statusReportOptions;
            return this;
        }

        /**
         * The metadata of the fax.
         *
         * @param metaData The metadata of the fax.
         * @return The builder.
         */
        public FaxRequestBuilder metadata(Metadata metaData) {
            this.metadata = metaData;
            return this;
        }

        /**
         * The reference of the fax.
         *
         * @param reference The reference of the fax.
         * @return The builder.
         */
        public FaxRequestBuilder reference(Reference reference) {
            this.reference = reference;
            return this;
        }

        /**
         * Build the fax request.
         *
         * @return The fax request.
         * @throws ApiException if the fax recipients list is empty.
         */
        public FaxRequest build() {
            if (faxRecipients == null || faxRecipients.isEmpty()) {
                throw new ApiException("Fax recipients list is empty.");
            }
            return new FaxRequest(documents, faxRecipients, transportOptions, renderingOptions, statusReportOptions, metadata, reference);
        }

        public String toString() {
            return "FaxRequest.FaxRequestBuilder(Documents=" + this.documents + ", faxRecipients=" + this.faxRecipients + ", transportOptions=" + this.transportOptions + ", renderingOptions=" + this.renderingOptions + ", statusReportOptions=" + this.statusReportOptions + ", metaData=" + this.metadata + ", reference=" + this.reference + ")";
        }
    }
}