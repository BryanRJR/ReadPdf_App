package com.example.pretest_sk_5_0.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pretest_sk_5_0.R
import com.example.pretest_sk_5_0.databinding.ActivityPdfAssetsBinding
import com.example.pretest_sk_5_0.utils.Utils

class PdfAssetsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfAssetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfAssetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // call the function showpdfassets
        showPdfFromAssets(Utils.getPdfNameFromAssets())
    }
    // take file pdf from assets then open it
    private fun showPdfFromAssets(pdfName: String) {
        binding.pdfAssets.fromAsset(pdfName)
            .password(null) // tampilkan password jika ada password
            .defaultPage(0) // set the default page to open
            .onPageError { page, _ ->
                Toast.makeText(
                    this@PdfAssetsActivity,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }

}