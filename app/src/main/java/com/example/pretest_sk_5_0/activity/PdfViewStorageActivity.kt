package com.example.pretest_sk_5_0.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pretest_sk_5_0.R
import com.example.pretest_sk_5_0.databinding.ActivityPdfViewStorageBinding
import com.github.barteksc.pdfviewer.PDFView

class PdfViewStorageActivity : AppCompatActivity() {
    lateinit var pdfView: PDFView
    val PDF_SELECTION_CODE = 99

    private lateinit var binding: ActivityPdfViewStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // binding to make view in xml
        pdfView = binding.pdfStorage
        selectPdfFromStorage()
    }
    // select pdf in storage handphone
    private fun selectPdfFromStorage(){
        Toast.makeText(this, "Select Pdf File", Toast.LENGTH_SHORT).show()
        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(browseStorage, "Select Pdf"), PDF_SELECTION_CODE)
    }
    // it will show pdf file we choose in storage
    fun showPdfFromUri(uri: Uri?) {
        binding.pdfStorage.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()
    }
    // result function
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PDF_SELECTION_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPdf = data.data
            showPdfFromUri(selectedPdf)
        }
    }
}