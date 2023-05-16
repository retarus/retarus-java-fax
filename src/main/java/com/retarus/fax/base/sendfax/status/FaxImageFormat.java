package com.retarus.fax.base.sendfax.status;

/**
 * @author thiagon
 * Determines the format in which the fax image will be attached to the email. The following formats are supported:
 * <p>
 * TIFF: Fax image is attached as TIFF
 * PDF: Fax image is atached as PDF
 * PDF_WITH_OCR: Fax image is attached as a searchable PDF file. Additional costs may occur
 */
public enum FaxImageFormat {
    TIFF, PDF, PDF_WITH_OCR
}
