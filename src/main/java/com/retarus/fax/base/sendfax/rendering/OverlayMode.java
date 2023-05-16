package com.retarus.fax.base.sendfax.rendering;

/**
 * @author thiagon
 * <p>
 * Enum for the overlay mode, which specifies the page in the document where the template (overlay) will be applied.
 * <p>
 * Possible values are:<p>
 * ALL_PAGES: the overlay is applied to all pages<p>
 * NO_OVERLAY: no overlay is used (returns the same result as if "no overlay" had been specified in the options)<p>
 * FIRST_PAGE: the overlay is applied only to the first page (if you are using a cover page, it is considered the first page)<p>
 * LAST_PAGE: the overlay is applied only to the last page<p>
 * ALL_BUT_FIRST_PAGE: the overlay is applied to all pages except for the first (if you are using a cover page, the overlay will be applied to all other pages because the cover page is considered the first page)<p>
 * ALL_BUT_LAST_PAGE: the overlay is applied to all pages except the last one<p>
 * ALL_BUT_FIRST_AND_LAST_PAGE: the overlay is applied to all pages except for the first and the last<p>
 * (the cover page is considered the first page if this mode is used)<p>
 * FIRST_FILE: if the faxed document consists of multiple files, the overlay will only be used on the first file's pages<p>
 * (the cover page is considered not to belong to any file and does not an overlay in this mode)
 */
public enum OverlayMode {
    ALL_PAGES,
    NO_OVERLAY,
    FIRST_PAGE,
    LAST_PAGE,
    ALL_BUT_FIRST_PAGE,
    ALL_BUT_LAST_PAGE,
    ALL_BUT_FIRST_AND_LAST_PAGE,
    FIRST_FILE
}
