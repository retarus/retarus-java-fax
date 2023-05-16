package com.retarus.fax.base.sendfax.rendering;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.retarus.fax.exception.ApiException;

import java.util.Objects;

/**
 * @author thiagon
 * Settings for the overlay (e.g., stationery). A template (e.g., with letter header and footer) can be applied to all or specific pages in the fax.
 * A template consists of a one-page, black-and-white document. In order to install an overlay, the customer transfers a template to RetarusFax,
 * and the template is then saved in RetarusFax' infrastructure under a mutually agreed upon name.
 */
public class Overlay {
    /**
     * The template name, without the path information and file extension.
     */
    @JsonProperty("name")
    private final String overlayName;

    /**
     * The page in the document where the template (overlay) will be applied.
     */
    @JsonProperty("mode")
    private final OverlayMode overlayMode;

    /**
     * Default constructor.
     *
     * @param overlayName the template name, without the path information and file extension.
     * @param overlayMode the page in the document where the template (overlay) will be applied.
     */
    private Overlay(String overlayName, OverlayMode overlayMode) {
        this.overlayName = overlayName;
        this.overlayMode = overlayMode;
    }

    public static OverlayBuilder builder() {
        return new OverlayBuilder();
    }

    public String getOverlayName() {
        return overlayName;
    }

    public OverlayMode getOverlayMode() {
        return overlayMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Overlay overlay = (Overlay) o;
        return Objects.equals(overlayName, overlay.overlayName) && overlayMode == overlay.overlayMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(overlayName, overlayMode);
    }


    public static class OverlayBuilder {
        private String overlayName;
        private OverlayMode overlayMode;

        private OverlayBuilder() {
        }

        /**
         * The template name, without the path information and file extension.
         *
         * @param overlayName the template name, without the path information and file extension.
         * @return the builder
         */
        public OverlayBuilder overlayName(String overlayName) {
            if (overlayName == null) {
                throw new ApiException("overlay name must not be null");
            }
            this.overlayName = overlayName;
            return this;
        }

        /**
         * The mode of the overlay.
         *
         * @param overlayMode the page in the document where the template (overlay) will be applied.
         * @return the builder
         */
        public OverlayBuilder overlayMode(OverlayMode overlayMode) {
            if (overlayMode == null) {
                throw new ApiException("overlay mode must not be null");
            }
            this.overlayMode = overlayMode;
            return this;
        }

        public Overlay build() {
            if (overlayName == null || overlayMode == null) {
                throw new ApiException("overlay name and mode must not be null");
            }
            return new Overlay(overlayName, overlayMode);
        }

        public String toString() {
            return "Overlay.OverlayBuilder(overlayName=" + this.overlayName + ", overlayMode=" + this.overlayMode + ")";
        }
    }
}