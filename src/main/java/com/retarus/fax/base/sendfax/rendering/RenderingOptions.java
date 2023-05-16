package com.retarus.fax.base.sendfax.rendering;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * @author thiagon<br>
 * Contains instructions for the rendering of the fax.<br>
 * The rendering options are used to specify the paper format, resolution, cover page, overlay, and header of the fax.<br>
 * The rendering options are optional. If they are not specified, the default values are used.
 */
public class RenderingOptions {

    /**
     * Specifies the paper format of the fax, e.g. A4, Letter.
     */
    @JsonProperty("paperFormat")
    private final PaperFormat paperFormat;

    /**
     * Specifies the resolution of the fax, e.g. LOW, HIGH.
     */
    @JsonProperty("resolution")
    private final PaperResolution paperResolution;

    /**
     * The name of the cover page's template; e.g., coverpage-default.ftl.html.
     */
    @JsonProperty("coverpageTemplate")
    private final String coverPageTemplate;

    /**
     * Settings for the overlay (e.g., stationery). A template (e.g., with letter header and footer) can be applied to all or specific pages in the fax.
     */
    @JsonProperty("overlay")
    private final Overlay overlay;

    /**
     * Specifies the content of the header of the fax, example: %tz=CEST Testfax: CSID: %C Recipient number: %# Date: %d.%m.%Y %H:%M %z.
     */
    @JsonProperty("header")
    private final String header;

    /**
     * Constructor.
     *
     * @param paperFormat       the paper format of the fax, e.g. A4, Letter.
     * @param paperResolution   the resolution of the fax, e.g. LOW, HIGH.
     * @param coverPageTemplate the name of the cover page's template; e.g., coverpage-default.ftl.html.
     * @param overlay           the overlay settings.
     * @param header            the content of the header of the fax, example: %tz=CEST Testfax: CSID: %C Recipient number: %# Date: %d.%m.%Y %H:%M %z.
     */
    private RenderingOptions(PaperFormat paperFormat, PaperResolution paperResolution, String coverPageTemplate, Overlay overlay, String header) {
        this.paperFormat = paperFormat;
        this.paperResolution = paperResolution;
        this.coverPageTemplate = coverPageTemplate;
        this.overlay = overlay;
        this.header = header;
    }

    public static RenderingOptionsBuilder builder() {
        return new RenderingOptionsBuilder();
    }

    public PaperFormat getPaperFormat() {
        return paperFormat;
    }

    public PaperResolution getPaperResolution() {
        return paperResolution;
    }

    public String getCoverPageTemplate() {
        return coverPageTemplate;
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public String getHeader() {
        return header;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RenderingOptions that = (RenderingOptions) o;
        return paperFormat == that.paperFormat && paperResolution == that.paperResolution && Objects.equals(coverPageTemplate, that.coverPageTemplate) && Objects.equals(overlay, that.overlay) && Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperFormat, paperResolution, coverPageTemplate, overlay, header);
    }

    public static class RenderingOptionsBuilder {
        private PaperFormat paperFormat;
        private PaperResolution paperResolution;
        private String coverPageTemplate;
        private Overlay overlay;
        private String header;

        private RenderingOptionsBuilder() {
        }

        /**
         * Set paper format.
         *
         * @param paperFormat the paper format of the fax, e.g. A4, Letter.
         * @return the rendering options builder.
         * @see PaperFormat for possible values.
         */
        public RenderingOptionsBuilder paperFormat(PaperFormat paperFormat) {
            this.paperFormat = paperFormat;
            return this;
        }

        /**
         * Set paper resolution.
         *
         * @param paperResolution the resolution of the fax, e.g. LOW, HIGH.
         * @return the rendering options builder.
         * @see PaperResolution for possible values.
         */
        public RenderingOptionsBuilder paperResolution(PaperResolution paperResolution) {
            this.paperResolution = paperResolution;
            return this;
        }

        /**
         * Set cover page template.
         *
         * @param coverPageTemplate the name of the cover page's template; e.g., coverpage-default.ftl.html.
         * @return the rendering options builder.
         */
        public RenderingOptionsBuilder coverPageTemplate(String coverPageTemplate) {
            this.coverPageTemplate = coverPageTemplate;
            return this;
        }

        /**
         * Set overlay.
         *
         * @param overlay the overlay settings.
         * @return the rendering options builder.
         * @see Overlay for possible values.
         */
        public RenderingOptionsBuilder overlay(Overlay overlay) {
            this.overlay = overlay;
            return this;
        }

        /**
         * Set overlay.
         *
         * @param overlayName the name of the overlay template.
         * @param overlayMode the overlay mode.
         * @return the rendering options builder.
         * @see Overlay for possible values.
         */
        public RenderingOptionsBuilder overlay(String overlayName, OverlayMode overlayMode) {
            this.overlay = Overlay.builder().overlayName(overlayName).overlayMode(overlayMode).build();
            return this;
        }

        /**
         * Set header.
         *
         * @param header the content of the header of the fax, example: %tz=CEST Testfax: CSID: %C Recipient number: %# Date: %d.%m.%Y %H:%M %z.
         * @return the rendering options builder.
         */
        public RenderingOptionsBuilder header(String header) {
            this.header = header;
            return this;
        }

        public RenderingOptions build() {
            return new RenderingOptions(paperFormat, paperResolution, coverPageTemplate, overlay, header);
        }

        public String toString() {
            return "RenderingOptions.RenderingOptionsBuilder(paperFormat=" + this.paperFormat + ", paperResolution=" + this.paperResolution + ", coverPageTemplate=" + this.coverPageTemplate + ", overlay=" + this.overlay + ", header=" + this.header + ")";
        }
    }
}