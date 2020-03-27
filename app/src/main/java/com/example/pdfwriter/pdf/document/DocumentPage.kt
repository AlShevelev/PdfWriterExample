package com.example.pdfwriter.pdf.document

import com.example.pdfwriter.pdf.document_params.AreaParams
import com.example.pdfwriter.pdf.document_params.QrParams
import com.example.pdfwriter.pdf.document_params.TextLineParams
import com.example.pdfwriter.pdf.document_params.TextParagraphParams
import com.example.pdfwriter.pdf.page_size.PageSize
import java.io.Closeable

interface DocumentPage : Closeable {
    val pageSize: PageSize

    fun drawTextLine(text: String, params: TextLineParams)

    fun drawTextParagraph(text: String, params: TextParagraphParams)

    fun drawArea(params: AreaParams)

    fun drawQr(text: String, params: QrParams)
}