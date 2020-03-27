package com.example.pdfwriter.pdf.document

import java.io.Closeable
import java.io.OutputStream

interface Document : Closeable {
    fun addPage(): DocumentPage

    fun writeTo(out: OutputStream)
}