package com.example.pdfwriter.utils

inline fun <T, R> scope(receiver: T, block: (T) -> R): R {
    return block(receiver)
}
