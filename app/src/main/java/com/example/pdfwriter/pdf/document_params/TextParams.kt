package com.example.pdfwriter.pdf.document_params

import android.graphics.Color
import android.graphics.Typeface
import android.text.Layout
import androidx.annotation.ColorInt

data class TextParams(
    @ColorInt
    val color: Int = Color.BLACK,

    /**
     * In points (1/72 inch)
     */
    val size: Float,

    val typeFace: Int = Typeface.NORMAL,

    val align: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL
)