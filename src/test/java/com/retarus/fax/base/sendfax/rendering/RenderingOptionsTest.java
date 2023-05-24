package com.retarus.fax.base.sendfax.rendering;

import com.retarus.fax.base.sendfax.rendering.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RenderingOptionsTest {

    //Generate a test to test the builder method
    @Test
    @DisplayName("Test builder method")
    void testBuilder() {
        Overlay expectedOverlay = Overlay.builder().overlayName("template1").overlayMode(OverlayMode.ALL_PAGES).build();
        RenderingOptions renderingOptions = RenderingOptions.builder()
                .paperFormat(PaperFormat.A4)
                .paperResolution(PaperResolution.LOW)
                .coverPageTemplate("template1")
                .overlay(expectedOverlay)
                .build();
        assertNotNull(renderingOptions);
        assertEquals(PaperFormat.A4, renderingOptions.getPaperFormat());
        assertEquals(PaperResolution.LOW, renderingOptions.getPaperResolution());
        assertEquals("template1", renderingOptions.getCoverPageTemplate());
        assertEquals(expectedOverlay, renderingOptions.getOverlay());
    }

    //Generate a test to test the equals and hashcode methods
    @Test
    @DisplayName("Test equals and hashcode methods")
    void testEqualsAndHashCode() {
        Overlay overlay1 = Overlay.builder().overlayName("template1").overlayMode(OverlayMode.ALL_PAGES).build();
        Overlay overlay2 = Overlay.builder().overlayName("template1").overlayMode(OverlayMode.ALL_PAGES).build();
        Overlay overlay3 = Overlay.builder().overlayName("template2").overlayMode(OverlayMode.ALL_PAGES).build();

        RenderingOptions renderingOptions1 = RenderingOptions.builder()
                .paperFormat(PaperFormat.A4)
                .paperResolution(PaperResolution.LOW)
                .coverPageTemplate("template1")
                .overlay(overlay1)
                .build();
        RenderingOptions renderingOptions2 = RenderingOptions.builder()
                .paperFormat(PaperFormat.A4)
                .paperResolution(PaperResolution.LOW)
                .coverPageTemplate("template1")
                .overlay(overlay2)
                .build();
        RenderingOptions renderingOptions3 = RenderingOptions.builder()
                .paperFormat(PaperFormat.A4)
                .paperResolution(PaperResolution.LOW)
                .coverPageTemplate("template2")
                .overlay(overlay3)
                .build();

        assertEquals(renderingOptions1, renderingOptions2);
        assertEquals(renderingOptions1.hashCode(), renderingOptions2.hashCode());

        assertNotEquals(renderingOptions1, renderingOptions3);
        assertNotEquals(renderingOptions1.hashCode(), renderingOptions3.hashCode());
    }

    //Generate a test to test the null values
    @Test
    @DisplayName("Test null values")
    public void testNullValues() {
        RenderingOptions renderingOptions = RenderingOptions.builder().build();
        assertNull(renderingOptions.getPaperFormat());
        assertNull(renderingOptions.getPaperResolution());
        assertNull(renderingOptions.getCoverPageTemplate());
        assertNull(renderingOptions.getOverlay());

        RenderingOptions builder = RenderingOptions.builder()
                .paperFormat(null)
                .paperResolution(null)
                .coverPageTemplate(null)
                .overlay(null)
                .build();
        assertNull(builder.getPaperFormat());
        assertNull(builder.getPaperResolution());
        assertNull(builder.getCoverPageTemplate());
        assertNull(builder.getOverlay());
    }


}