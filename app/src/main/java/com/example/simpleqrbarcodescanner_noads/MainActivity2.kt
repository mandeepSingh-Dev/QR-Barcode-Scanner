package com.example.simpleqrbarcodescanner_noads

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.simpleqrbarcodescanner_noads.Util.Custom_Formats_duplicate
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityMain2Binding
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityMainBinding
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.ByteArrayOutputStream

 class MainActivity2 : AppCompatActivity()
{
     lateinit var binding:ActivityMain2Binding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val qrCOde = intent.getStringExtra(Intent_KEYS.QRCODE)
        val format = intent.getIntExtra(Intent_KEYS.FORMAT, 0)
        val valueType = intent.getIntExtra(Intent_KEYS.VALUETYPE,0)
        val bundle = intent.getBundleExtra(Intent_KEYS.BUNDLE)

        Log.d("fkvnfjvb",valueType.toString())

        when(valueType){
            //doesnot suppported
              /*  Barcode.TYPE_PRODUCT-> binding?.typeTextView.text = "Product"
                Barcode.TYPE_TEXT->  binding?.typeTextView.text = "Text"
                Barcode.TYPE_CONTACT_INFO->  binding?.typeTextView.text = "Contact"
                Barcode.TYPE_ISBN->  binding?.typeTextView.text = "ISBN"
            Barcode.TYPE_PHONE->  binding?.typeTextView.text = "Phone"
            Barcode.TYPE_URL->  binding?.typeTextView.text = "Url"
            Barcode.TYPE_GEO->  binding?.typeTextView.text = "Location"
            Barcode.TYPE_CALENDAR_EVENT->  binding?.typeTextView.text = "Calendar"*/
            Barcode.TYPE_DRIVER_LICENSE->{
                binding?.typeTextView.text = "Driving Licence"
                val licencenumber = bundle?.getString("LICENCE_NUMBER")
                binding.result.text = licencenumber
            }
                Barcode.TYPE_WIFI ->{
                    binding?.typeTextView.text = "Wifi"
                val wifiName = bundle?.getString(Intent_KEYS.WIFINAME)
                val password = bundle?.getString(Intent_KEYS.PASSWORD)
                val encryptionValue = bundle?.getInt(Intent_KEYS.ENCRYPTION_VALUE)
                val encryption = if(encryptionValue==2){"WPA/WPA2"}
                                else if(encryptionValue==3){"WEP"}
                                else{"None"}
                binding.result.text = "Wifi Name:$wifiName\nPassword: $password\n$encryption"
            }
                Barcode.TYPE_SMS->{
                    binding?.typeTextView.text = "SMS"
                val phone = bundle?.getString(Intent_KEYS.SMS_PHONE)
               val message =  bundle?.getString(Intent_KEYS.MESSAGE)
                binding.result.text = "SMS to: $phone\nMessage: $message"
            }
                Barcode.TYPE_EMAIL->{
                    binding?.typeTextView.text = "Email"
                val emailAddress =  bundle?.getString(Intent_KEYS.EMAIL_ADDRESS)
                val emailSubject =  bundle?.getString(Intent_KEYS.EMAIL_SUBJECT)
                val emailBody =  bundle?.getString(Intent_KEYS.EMAIL_BODY)
                binding.result.text = "Mail to :$emailAddress\nSubject : $emailSubject\nBody: $emailBody"
            }
            else ->{
                binding?.typeTextView.text = "Void"
                binding.result.text = qrCOde
            }
        }
        val bitmap = qrGenerate(qrCOde, format)
        binding.codeImage.setImageBitmap(bitmap)

    }

    fun qrGenerate(qrcode:String?,format:Int?): Bitmap {
        val imageWidth =  binding.codeImage.layoutParams?.width
        val bitmap =  when(format){
            Custom_Formats_duplicate.CODABAR -> {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.CODABAR, imageWidth!!, 220)

            }
            Custom_Formats_duplicate.CODE_128 -> {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.CODE_128, imageWidth!!, 220)
            }
            Custom_Formats_duplicate.CODE_39 -> {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.CODE_39,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.CODE_93 -> {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.CODE_93,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.DATA_MATRIX -> {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.DATA_MATRIX,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.EAN_13 ->  {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.EAN_13,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.EAN_8 ->  {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.EAN_8,imageWidth!!, 220)
            } Custom_Formats_duplicate.ITF -> {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.ITF,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.QR_CODE ->  {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 470
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.QR_CODE,imageWidth!!,430)

            }
            Custom_Formats_duplicate.UPC_A -> {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.UPC_A,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.UPC_E ->  {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.UPC_E,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.PDF_417 ->  {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.PDF_417,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.AZTEC ->  {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.AZTEC,imageWidth!!, 220)
            }
            else ->  {
                Log.d("dfnndb", format.toString())
                binding.codeImage?.layoutParams.height = 280
                BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_image_24)
            }

        }
        // binding?.imageView?.setImageBitmap(bitmap)
        return bitmap
    }

}