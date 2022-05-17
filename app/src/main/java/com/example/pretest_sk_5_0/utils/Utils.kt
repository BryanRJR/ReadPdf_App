package com.example.pretest_sk_5_0.utils

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File

object Utils {
    // Mendapatkan Pdf Url
    fun getPdfUrl(): String {
        return "https://kotlinlang.org/assets/kotlin-media-kit.pdf"
    }
    // Mendapat File Dari Assets Local
    fun getPdfNameFromAssets(): String {
        return "Example_PDF_Kotlin_Wikipedia.pdf"
    }
    // Root File Location
    fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }
}