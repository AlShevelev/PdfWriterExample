package com.example.pdfwriter.page_rendering

import java.util.*

data class PageData(
    val userName: String,
    val userId: String,

    val createDate: Date,

    val phoneNumber: String,

    val password: String,
    val activeKey: String,
    val ownerKey: String,

    val qrText: String
)