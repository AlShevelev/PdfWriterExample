package com.example.pdfwriter.pdf.document

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.print.PrintAttributes
import android.print.pdf.PrintedPdfDocument
import com.example.pdfwriter.pdf.page_size.PageSize
import java.io.OutputStream

class DocumentImpl private constructor(context: Context, private val pageSize: PageSize): Document {
    companion object {
        fun create(context: Context, pageSize: PageSize): Document = DocumentImpl(context, pageSize)
    }

    private val doc: PdfDocument

    private var pageIndex = 0

    init {
        val printAttributes = PrintAttributes.Builder()
            .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
            .setMediaSize(pageSize.pageType)
            .setResolution(PrintAttributes.Resolution("res1", "300_DPI", 300, 300))
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
            .build()

        doc = PrintedPdfDocument(context, printAttributes)
    }

    override fun addPage(): DocumentPage =
        DocumentPageImpl(pageSize, pageIndex++, doc)

    override fun writeTo(out: OutputStream) = doc.writeTo(out)

    override fun close() {
        doc.close()
    }
}