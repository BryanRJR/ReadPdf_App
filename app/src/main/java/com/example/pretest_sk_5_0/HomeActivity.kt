package com.example.pretest_sk_5_0

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.pretest_sk_5_0.activity.*
import com.example.pretest_sk_5_0.databinding.ActivityHomeBinding
import com.example.pretest_sk_5_0.utils.ChargeReceiver

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val REQUEST_CODE_PERMISSION = 100

    // register receiver di main activity to receive update of broadcasts events
    lateinit var receiver: ChargeReceiver
    // for camera take result
    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }
    // gallery take result
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.ivImage.setImageURI(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // call function chargeMode
        chargeMode()

        // set button intent in Homeactivity
        binding.apply {
            btnWeb.setOnClickListener {
                val intent = Intent(this@HomeActivity, WebViewActivity::class.java)
                startActivity(intent)
            }
            btnAssets.setOnClickListener {
                val intent = Intent(this@HomeActivity, PdfAssetsActivity::class.java)
                startActivity(intent)
            }

            btnStorage.setOnClickListener {
                val intent = Intent(this@HomeActivity, PdfViewStorageActivity::class.java)
                startActivity(intent)
            }

            btnInternet.setOnClickListener {
                val intent = Intent(this@HomeActivity, PdfInternetViewActivity::class.java)
                startActivity(intent)
            }

            btnImage.setOnClickListener {
                checkingPermissions()
            }

            btnRefresh.setOnClickListener {
                startActivity(getIntent())
                overridePendingTransition(0, 0)
                finish()
            }

            btnVideo.setOnClickListener {
                val intent = Intent(this@HomeActivity, VideoActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // This is image handle code
    // fun for checking if permission is access granted
    private fun checkingPermissions() {
        if (isGranted(
                this,
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            chooseImageDialog()
        }
    }
    // fun granted executed when permission is true or granted by user
    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }
    // fun dialog permission if permission has denied
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }
    // alert dialog for choose image
    private fun chooseImageDialog() {
        AlertDialog.Builder(this)
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }
    // to open gallery in device
    private fun openGallery() {
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }
    // to open camera in device
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }
    // handle data from camera to image view
    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        binding.ivImage.setImageBitmap(bitmap)

    }
    // make broadcast for power charger
    private fun chargeMode() {
        receiver = ChargeReceiver()

        // Intent Filter to determine action power connected receive
        IntentFilter(Intent.ACTION_POWER_CONNECTED).also {
            // registering the receiver
            // it parameter which is passed in  registerReceiver() function
            registerReceiver(receiver, it)
        }
    }

    // on stop receiver
    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }


}