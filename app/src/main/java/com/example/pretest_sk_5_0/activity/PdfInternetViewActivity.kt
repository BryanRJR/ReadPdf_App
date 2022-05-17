package com.example.pretest_sk_5_0.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.pretest_sk_5_0.R
import com.example.pretest_sk_5_0.databinding.ActivityPdfAssetsBinding
import com.example.pretest_sk_5_0.databinding.ActivityPdfInternetViewBinding
import com.example.pretest_sk_5_0.utils.Utils
import java.io.File

class PdfInternetViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfInternetViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfInternetViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // call the view of progress bar and get pdf file
        binding.progBar.visibility = View.VISIBLE
        val fileName = "myFile.pdf"
        downloadPdfInternet(
            Utils.getPdfUrl(),
            Utils.getRootDirPath(this),
            fileName
        )
    }
    // this is for download pdf file
    // and condition to get error and complete
    private fun downloadPdfInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Toast.makeText(this@PdfInternetViewActivity, getString(R.string.toast_download_complete), Toast.LENGTH_LONG)
                        .show()
                    val downloadedFile = File(dirPath, fileName)
                    binding.progBar.visibility = View.GONE
                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: Error?) {
                    Toast.makeText(
                        this@PdfInternetViewActivity,
                        getString(R.string.toast_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }
    // when done then show file pdf
    private fun showPdfFromFile(file: File) {
        binding.pdfInternet.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
                Toast.makeText(
                    this@PdfInternetViewActivity,
                    getString(R.string.toast_error_page), Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }
}