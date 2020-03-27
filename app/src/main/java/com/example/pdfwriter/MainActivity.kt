package com.example.pdfwriter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfwriter.page_rendering.PageData
import com.example.pdfwriter.page_rendering.PageRenderer
import com.example.pdfwriter.pdf.document.DocumentImpl
import com.example.pdfwriter.pdf.page_size.A4PageSize
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class MainActivity : AppCompatActivity() {
    private var downloadPath: String = "/storage/emulated/0/Download"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveLocalButton.setOnClickListener {
            val fileName = "${Date().time}.pdf"
            createAndSaveLocal(File(downloadPath, fileName))
            fileNameLabel.text = fileName
        }
    }

    private fun createAndSaveLocal(localFile: File) {
        val pageInfo = A4PageSize()

        val pageData = PageData(
            userName = "fasdscdsacsd3",
            userId = "tst5utqchjpd",
            createDate = Date(),
            phoneNumber = "+19854344433",
            password = "P5JPDqyFMs3cjHApwmCStBiCow6DLQRHABKo1GZNmNFQm92WGfHR",
            activeKey = "5HqxtFTMWiiSF7eU5idX5Kujj5ggUa5tHiRVwAxHHuJLv7gUjFa",
            ownerKey = "5K2QS5FTZ3rXSNyeXrgm443imZASVfUuQ36sXJBUnHr3nBRbb8M",
            qrText = "Phew! We now know the basics of what StaticLayout is and how we can use it to draw multiline text to Canvas."
        )

        DocumentImpl.create(this, pageInfo).use { doc ->
            doc.addPage().use { page ->
                val renderer = PageRenderer(this)
                renderer.render(page, pageData)
            }

            FileOutputStream(localFile).use { stream ->
                doc.writeTo(stream)
            }
        }
    }
}