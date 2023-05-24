package com.retarus.fax.base.sendfax.rendering;

import com.retarus.fax.base.sendfax.rendering.Overlay;
import com.retarus.fax.base.sendfax.rendering.OverlayMode;
import com.retarus.fax.exception.ApiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OverlayTest {
    @Test
    void testBuilder() {
        Overlay overlay = Overlay.builder()
                .overlayName("template1")
                .overlayMode(OverlayMode.ALL_PAGES)
                .build();
        assertNotNull(overlay);
        assertEquals("template1", overlay.getOverlayName());
        assertEquals(OverlayMode.ALL_PAGES, overlay.getOverlayMode());
    }

    @Test
    void testEqualsAndHashCode() {
        Overlay overlay1 = Overlay.builder().overlayName("template1").overlayMode(OverlayMode.ALL_PAGES).build();
        Overlay overlay2 = Overlay.builder().overlayName("template1").overlayMode(OverlayMode.ALL_PAGES).build();
        Overlay overlay3 = Overlay.builder().overlayName("template2").overlayMode(OverlayMode.ALL_PAGES).build();

        assertEquals(overlay1, overlay2);
        assertEquals(overlay1.hashCode(), overlay2.hashCode());

        assertNotEquals(overlay1, overlay3);
        assertNotEquals(overlay1.hashCode(), overlay3.hashCode());
    }

    @Test
    public void testNullValues() {
        assertThrows(ApiException.class, () -> Overlay.builder().build());


        assertThrows(ApiException.class, () -> Overlay.builder()
                .overlayName(null)
                .overlayMode(null)
                .build());
    }

    @Test
    public void testDifferentOverlayModes() {
        Overlay overlay1 = Overlay.builder().overlayName("template").overlayMode(OverlayMode.ALL_PAGES).build();
        Overlay overlay2 = Overlay.builder().overlayName("template").overlayMode(OverlayMode.NO_OVERLAY).build();
        Overlay overlay3 = Overlay.builder().overlayName("template").overlayMode(OverlayMode.FIRST_PAGE).build();
        Overlay overlay4 = Overlay.builder().overlayName("template").overlayMode(OverlayMode.LAST_PAGE).build();
        Overlay overlay5 = Overlay.builder().overlayName("template").overlayMode(OverlayMode.ALL_BUT_FIRST_PAGE).build();
        Overlay overlay6 = Overlay.builder().overlayName("template").overlayMode(OverlayMode.ALL_BUT_LAST_PAGE).build();
        Overlay overlay7 = Overlay.builder().overlayName("template").overlayMode(OverlayMode.ALL_BUT_FIRST_AND_LAST_PAGE).build();
        Overlay overlay8 = Overlay.builder().overlayName("template").overlayMode(OverlayMode.FIRST_FILE).build();

        assertEquals(OverlayMode.ALL_PAGES, overlay1.getOverlayMode());
        assertEquals(OverlayMode.NO_OVERLAY, overlay2.getOverlayMode());
        assertEquals(OverlayMode.FIRST_PAGE, overlay3.getOverlayMode());
        assertEquals(OverlayMode.LAST_PAGE, overlay4.getOverlayMode());
        assertEquals(OverlayMode.ALL_BUT_FIRST_PAGE, overlay5.getOverlayMode());
        assertEquals(OverlayMode.ALL_BUT_LAST_PAGE, overlay6.getOverlayMode());
        assertEquals(OverlayMode.ALL_BUT_FIRST_AND_LAST_PAGE, overlay7.getOverlayMode());
        assertEquals(OverlayMode.FIRST_FILE, overlay8.getOverlayMode());
    }
}