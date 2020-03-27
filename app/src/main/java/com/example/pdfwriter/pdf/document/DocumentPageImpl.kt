package com.example.pdfwriter.pdf.document

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.example.pdfwriter.pdf.document_params.*
import com.example.pdfwriter.pdf.page_size.PageSize
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlin.math.roundToInt


class DocumentPageImpl(override val pageSize: PageSize, index: Int, private val doc: PdfDocument): DocumentPage {

    private val textPaints = mutableMapOf<TextParams, TextPaint>()
    private val areaPaints = mutableMapOf<Int, Paint>()

    private val page: PdfDocument.Page =
        PdfDocument.PageInfo
            .Builder(pageSize.width.roundToInt(), pageSize.height.roundToInt(), index)
            .create()
            .let { doc.startPage(it) }

    override fun drawTextLine(text: String, params: TextLineParams) =
        page.canvas.drawText(text, params.left, params.top+params.textParams.size/2, getTextPaint(params.textParams))

    override fun drawTextParagraph(text: String, params: TextParagraphParams) {
        val layout = createStaticLayout(text, params)

        page.canvas.save()
        page.canvas.translate(params.left, params.top)
        layout.draw(page.canvas)
        page.canvas.restore()
    }

    override fun drawArea(params: AreaParams) =
        page.canvas.drawRoundRect(
            params.left,
            params.top,
            params.left + params.width,
            params.top + params.height,
            params.cornerRadius,
            params.cornerRadius,
            getAreaPaint(params))

    override fun drawQr(text: String, params: QrParams) =
        page.canvas.drawBitmap(createQrAsBitmap(text, params.size), params.left, params.top, null)

    override fun close() {
        doc.finishPage(page)
    }

    private fun createTextPaint(params: TextParams) =
        TextPaint()
            .apply {
                color = params.color
                typeface = Typeface.create(Typeface.DEFAULT, params.typeFace)
                textSize = params.size
                textAlign = params.align.toPaint()
            }

    private fun getTextPaint(params: TextParams) =
        textPaints[params] ?: createTextPaint(params).apply { textPaints[params] = this }

    private fun createAreaPaint(params: AreaParams) = Paint().apply { color = params.fillColor }

    private fun getAreaPaint(params: AreaParams): Paint =
        areaPaints[params.fillColor] ?: createAreaPaint(params).apply { areaPaints[params.fillColor] = this }

    @Suppress("DEPRECATION")
    private fun createStaticLayout(text: String, params: TextParagraphParams): StaticLayout {
        val paint = getTextPaint(params.textParams)
        return if(Build.VERSION.SDK_INT >= 23) {
            StaticLayout.Builder
                .obtain(text, 0, text.length, paint, params.width.roundToInt())
                .setLineSpacing(1f, 1.4f)
                .build()
        } else {
            StaticLayout(text, paint, params.width.roundToInt(), params.textParams.align, 1.4f, 1f, true)
        }
    }

    private fun Layout.Alignment.toPaint() =
        when(this) {
            Layout.Alignment.ALIGN_NORMAL -> Paint.Align.LEFT
            Layout.Alignment.ALIGN_CENTER -> Paint.Align.CENTER
            Layout.Alignment.ALIGN_OPPOSITE -> Paint.Align.RIGHT
        }

    private fun createQrAsBitmap(text: String, size: Float): Bitmap {
        val writer = QRCodeWriter()

        val encodingHints = mutableMapOf<EncodeHintType, Any?>(
            EncodeHintType.CHARACTER_SET to "UTF-8",
            EncodeHintType.MARGIN to 0
        )

        val encoding = writer.encode(text, BarcodeFormat.QR_CODE, size.roundToInt(), size.roundToInt(), encodingHints)

        val bitmap = Bitmap.createBitmap(size.roundToInt(), size.roundToInt(), Bitmap.Config.ARGB_8888)

        for (i in 0 until size.toInt()) {
            for (j in 0 until size.toInt()) {
                bitmap.setPixel(i, j, if (encoding[i, j]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap

    }
}