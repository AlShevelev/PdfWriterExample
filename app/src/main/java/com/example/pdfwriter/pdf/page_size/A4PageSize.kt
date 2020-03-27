package com.example.pdfwriter.pdf.page_size

import android.print.PrintAttributes

class A4PageSize: PageSize {
    override val width: Float = 595f
    override val height: Float = 842f

    override val pageType = PrintAttributes.MediaSize.ISO_A4
}