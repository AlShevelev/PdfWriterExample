package com.example.pdfwriter.pdf.page_size

import android.print.PrintAttributes

interface PageSize {
    /**
     * Size of an A4 page in points
     */
    val width: Float

    /**
     * Size of an A4 page in points
     */
    val height: Float

    val pageType: PrintAttributes.MediaSize
}