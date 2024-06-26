package com.example.testugd1

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testugd1.databinding.ActivityQrmainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


class QRMain : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityQrmainBinding

    companion object{
        private const val CAMERA_REQUEST_CODE = 100
        private const val STORAGE_REQUEST_CODE = 101

        private const val TAG = "MAIN_TAG"
    }

    private lateinit var cameraPermissions: Array<String>
    private lateinit var storagePermissions: Array<String>

    private var imageUri: Uri? = null

    private var barcodeScannerOption: BarcodeScannerOptions? = null
    private var barcodeScanner: BarcodeScanner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrmainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding
        binding.cameraBtn.setOnClickListener(this)
        binding.galleryBtn.setOnClickListener(this)
        binding.scanBtn.setOnClickListener(this)
        val fabback = findViewById<FloatingActionButton>(R.id.fab_back)
        fabback.setOnClickListener {
            val moveHome = Intent(this, HomeActivity::class.java)
            startActivity(moveHome)
        }
        cameraPermissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

//        Linear formats: Codabar, Code 39, Code 93, Code 128, EAN-8, EAN-13, ITF, UPC-A, UPC-E
//        2D formats: Aztec, Data Matrix, PDF417, QR Code

        barcodeScannerOption = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build()
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOption!!)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cameraBtn -> {
                if (checkCameraPermissions()){
                    pickImageCamera()
                }
                else{
                    requestCameraPermission()
                }
            }
            R.id.galleryBtn -> {
                if (checkStoragePermision()) {
                    pickImageGallery()
                }else {
                    requestStoragePermission()
                }
            }
            R.id.scanBtn -> {
                binding.scanBtn.showLoading()
                if (imageUri == null){
                    showToast("Pick image first")
                    binding.scanBtn.hideLoading()
                } else {
                    detectResultFromImage()
                }
            }
        }
    }

    private fun detectResultFromImage() {
        try {
            val inputImage = InputImage.fromFilePath(this, imageUri!!)

            val barcodeResult = barcodeScanner?.process(inputImage)
                ?.addOnSuccessListener { barcodes ->
                    extractbarcodeQrCodeInfo(barcodes)
                }
                ?.addOnFailureListener{ e ->
                    Log.d(TAG,"detectResultFromImage: ", e)
                    showToast("failed Scanning due to ${e.message}")
                }
        }catch (e: Exception){
            Log.d(TAG,"detectResultFromImage: ", e)
            showToast("Failed Due to ${e.message}")
        }
    }

    private fun extractbarcodeQrCodeInfo(barcodes: List<Barcode>) {
        for (barcode in barcodes) {
            val bound = barcode.boundingBox
            val corners = barcode.cornerPoints

            val rawValue = barcode.rawValue
            Log.d(TAG,"extractbarcodeQrCodeInfo: rawValue: $rawValue")


            val valueType = barcode.valueType
            when(valueType) {
                Barcode.TYPE_WIFI -> {
                    val typeWifi = barcode.wifi
                    val ssid = "${typeWifi?.ssid}"
                    val password = "${typeWifi?.password}"
                    var encryptionType = "${typeWifi?.encryptionType}"

                    if (encryptionType == "1"){
                        encryptionType = "OPEN"
                    } else if (encryptionType == "2"){
                        encryptionType = "WPA"
                    } else if (encryptionType == "3"){
                        encryptionType = "WEP"
                    }

                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_WIFI")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: ssid: $ssid")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: password: $password")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: encryptionType: $encryptionType")

                    binding.resultView.text =
                        "TYPE_WIFI \n ssid: $ssid \npassword: $password \nencryptionType: $encryptionType" +
                                "\n\nrawValue : $rawValue"

                }
                Barcode.TYPE_URL -> {
                    val typeUrl = barcode.url
                    val title = "${typeUrl?.title}"
                    val url = "${typeUrl?.url}"

                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_URL")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: title: $title")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: url: $url")

                    binding.resultView.text =
                        "TYPE_URL \ntitle: $title \nurl: $url \n\nrawValue : $rawValue"

                }
                Barcode.TYPE_EMAIL -> {
                    val typeEmail = barcode.email
                    val address = "${typeEmail?.address}"
                    val body = "${typeEmail?.body}"
                    val subject = "${typeEmail?.subject}"

                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_EMAIL")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: address: $address")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: body: $body")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: subject: $subject")

                    binding.resultView.text =
                        "TYPE_EMAIL \ntitle: $address \nurl: $body \nsubject: $subject \n\nrawValue : $rawValue"


                }
                Barcode.TYPE_CONTACT_INFO -> {
                    val typeContact = barcode.contactInfo
                    val title = "${typeContact?.title}"
                    val organisasi = "${typeContact?.organization}"
                    val name = "${typeContact?.name}"
                    val phone = "${typeContact?.phones}"

                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_CONTACT_INFO")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: Title: $title")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: Organization: $organisasi")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: nama: $name")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: Phone: $phone")

                    binding.resultView.text =
                        "TYPE_CONTACT_INFO " +
                                "\ntitle: $title " +
                                "\nnama: $name " +
                                "\norganization: $organisasi " +
                                "\nPhone : $phone"
                    "\n\nrawValue : $rawValue"

                }
                else -> {
                    binding.resultView.text =
                        "rawValue: $rawValue"
                }


            }


        }

    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data

            imageUri = data!!.data
            Log.d(TAG,"galleryActivityResultLauncher: imageUri: $imageUri")

            binding.imageTv.setImageURI(imageUri)

        }else {
            showToast("Dibatalkan ..............")
        }
    }

    private fun pickImageCamera(){
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Foto Sample")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Deskripsi Foto Sample")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        cameraActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data
            Log.d(TAG, "cameraActivityResultLauncher: imageUri: $imageUri")

            binding.imageTv.setImageURI(imageUri)
        }
    }

    private fun checkStoragePermision(): Boolean {
        val result = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        return result
    }


    private fun checkCameraPermissions(): Boolean {
        val resultcamera = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
        val resultstorage = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)

        return resultcamera && resultstorage
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE)
    }

    private fun requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermissions, STORAGE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()){
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED

                    if (cameraAccepted && storageAccepted){
                        pickImageCamera()
                    }else {
                        showToast("Camera dan storage permission are required")
                    }
                }
            }

            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()){
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (storageAccepted){
                        pickImageGallery()
                    }else
                    {
                        showToast("Storage are required...")
                    }
                }
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}