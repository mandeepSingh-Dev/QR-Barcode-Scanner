package com.example.simpleqrbarcodescanner_noads

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.simpleqrbarcodescanner_noads.Util.Custom_Formats_duplicate
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityMain2Binding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import android.content.Intent
import android.provider.CalendarContract
import android.provider.ContactsContract
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.simpleqrbarcodescanner_noads.MVVM.MyViewModel
import com.example.simpleqrbarcodescanner_noads.MVVM.MyViewmodel2
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.example.simpleqrbarcodescanner_noads.room.MyRoomDatabase
import com.google.zxing.oned.EAN13Reader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity()
{
     lateinit var binding:ActivityMain2Binding
    private var rawQrCOde:String?=null
    private var fromHistoryPage:Boolean?=null

    private val mPERMISSION_CODE = 100

    private  lateinit var phone:String
    private lateinit var message:String

    private lateinit var emailAddress:String
    private lateinit var emailSubject:String
    private lateinit var emailBody:String

    lateinit var calArlst:ArrayList<String>
    lateinit var contactArlst:ArrayList<String>

    lateinit var arrayList:ArrayList<String>
    var dialogue:AlertDialog?=null

    val myiewmodel:MyViewModel by viewModels()




    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

         rawQrCOde = intent.getStringExtra(Intent_KEYS.QRCODE)
        val format = intent.getIntExtra(Intent_KEYS.FORMAT,0)
        val valueType = intent.getIntExtra(Intent_KEYS.VALUETYPE,0)
        val bundle = intent.getBundleExtra(Intent_KEYS.BUNDLE)
        val typeValue_QRGEN = intent.getStringExtra(Intent_KEYS.VALUETYPE_QRGENERATOR)
        fromHistoryPage = intent.getBooleanExtra(Intent_KEYS.FROM_HISTORY,false)

        Log.d("dgfknfgd","format$format $typeValue_QRGEN  $valueType")
        arrayList = ArrayList()


        //when block for setting data acc. to Valuetype and to store in room also
        // but for only metoined below types
        //and for other types we store from another 2nd when block



        //when block to set type of barcode in top textview  acc. to valueType
        // and to store data also in room
        try{
        val textType =  when(valueType){
            Barcode.TYPE_PRODUCT-> {
                binding.searchButton.text = "Search"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_search_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
//                binding.copyMainButton.visibility = View.GONE
               minsert( Barcode.TYPE_PRODUCT,format,arrayList)
                binding.result.text = rawQrCOde

                "Product"}
            Barcode.TYPE_TEXT->  {
                Log.d("uidfh","ELSE_TEXTpehle WALA")

                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                minsert( Barcode.TYPE_TEXT,format,arrayList)

                "Text"}
            Barcode.TYPE_CONTACT_INFO->  {
                binding.searchButton.text = "Add to contacts"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_outline_person_add_alt_1_24,null),null,null,null)
                binding.copybutton.text = "Call"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_twotone_call_24,null),null,null)
                //    binding.copyMainButton.visibility = View.GONE
              /**------------------*/
               val list =  bundle?.getStringArrayList(Intent_KEYS.CONTACTS_ARRAYLIST)/*?.let {
                    *//*contactArlst.addAll(it)*//*
                    arrayList.addAll(it)}*/
                Log.d("fjhife",list?.size.toString())
                //Toast.makeText(this,arrayList[0],Toast.LENGTH_SHORT).show()
                /* CoroutineScope(Dispatchers.IO).launch {
                     MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                         .insert(EntityClass(contactArlst,System.currentTimeMillis(),Barcode.TYPE_CONTACT_INFO.toString(),rawQrCOde.toString()))
                 }*/
                binding.result.text = rawQrCOde
                minsert( Barcode.TYPE_CONTACT_INFO,format,arrayList)
                /**------------------*/

                "Contact Information"}
            Barcode.TYPE_ISBN->  {
                binding.searchButton.text = "Search"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_search_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //    binding.copyMainButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                minsert( Barcode.TYPE_ISBN,format,arrayList)

                "ID Book"}
            Barcode.TYPE_PHONE->  {
                binding.searchButton.text = "Call"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_twotone_call_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.text = "Add contact"
//  binding.copyMainButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                minsert( Barcode.TYPE_PHONE,format,arrayList)
                "Phone"}
            Barcode.TYPE_URL->  {
                binding.searchButton.text = "Search"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_search_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //  binding.copyMainButton.visibility = View.GONE
                binding.result.text = rawQrCOde

                bundle?.getStringArrayList(Intent_KEYS.URL_LIST)?.let { arrayList.addAll(it) }
                minsert( Barcode.TYPE_URL,format,arrayList)
                "Url"}
            Barcode.TYPE_GEO-> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                minsert( Barcode.TYPE_GEO,format,arrayList)

                "Location"}
            Barcode.TYPE_CALENDAR_EVENT->  {
                binding.searchButton.text = "Add event"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_calendar_month_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //  binding.copyMainButton.visibility = View.GONE
                /**--------------------*/
               val list = bundle?.getStringArrayList(Intent_KEYS.CAL_ARRAYLIST)
                list?.let{arrayList.addAll(it)}


                if(arrayList[6].toInt()<10){ arrayList[6] = "0${arrayList[6]}" }
                if(arrayList[7].toInt()<10){  arrayList[7] = "0${arrayList[7]}" }
                if(arrayList[8].toInt()<10){  arrayList[8] = "0${arrayList[8]}" }

                if(arrayList[12].toInt()<10){ arrayList[12] = "0${arrayList[12]}" }
                if(arrayList[13].toInt()<10){ arrayList[13] = "0${arrayList[13]}" }
                if(arrayList[14].toInt()<10){ arrayList[14] = "0${arrayList[14]}"}

                binding.result.text = "Summary: ${arrayList[0]}\nDescription: ${arrayList[1]}\nLocation: ${arrayList[2]}\nStart Time: ${arrayList[3]}-${arrayList[4]}-${arrayList[5]}   ${arrayList[6]}:${arrayList[7]}:${arrayList[8]}\nEnd Time: ${arrayList[9]}-${arrayList[10]}-${arrayList[11]}   ${arrayList[12]}:${arrayList[13]}:${arrayList[14]}"
/*
                    CoroutineScope(Dispatchers.IO).launch {
                        MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                            .insert(EntityClass(calArlst,System.currentTimeMillis(),Barcode.TYPE_CALENDAR_EVENT.toString(),rawQrCOde.toString()))
                    }

*/              list?.let{minsert( Barcode.TYPE_CALENDAR_EVENT,format,it)}
                /**--------------------*/
                "Event"}
            Barcode.TYPE_UNKNOWN ->{
                binding.searchButton.text = "Search"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_email_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //  binding.copyMainButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                //minsert( Barcode.TYPE_UNKNOWN,format,arrayList)

                "Unknown"}
            Barcode.TYPE_EMAIL -> {
                binding.searchButton.text = "Send email"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_email_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //   binding.copyMainButton.visibility = View.GONE
                /**--------------------------*/
                bundle?.getStringArrayList(Intent_KEYS.EMAIL_LIST)?.let { arrayList.addAll(it) }
                emailAddress =  arrayList[0]
                emailSubject =  arrayList[1]
                emailBody =  arrayList[2]


/*
                    CoroutineScope(Dispatchers.IO).launch {
                        MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                            .insert(EntityClass(arrayList,System.currentTimeMillis(),Barcode.TYPE_EMAIL.toString(),rawQrCOde.toString()))
                    }
*/
                binding.result.text = "Mail to :$emailAddress\nSubject : $emailSubject\nBody: $emailBody"
                minsert(Barcode.TYPE_EMAIL,format,arrayList)

                /**----------------*/
                "Email"}
            Barcode.TYPE_SMS->{
                binding.searchButton.text = "Create message"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_message_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //   binding.copyMainButton.visibility = View.GONE
         /**--------------------------*/
                bundle?.getStringArrayList(Intent_KEYS.SMS_LIST)?.let { arrayList.addAll(it) }
                phone = arrayList[0]
                message = arrayList[1]


/*
                    CoroutineScope(Dispatchers.IO).launch {
                        MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                            .insert(EntityClass(arrayList,System.currentTimeMillis(),Barcode.TYPE_SMS.toString(),rawQrCOde.toString()))
                    }
*/
                binding.result.text = "SMS to: $phone\nMessage: $message"
                minsert(Barcode.TYPE_SMS,format,arrayList)
                /**-----------------------*/
                "SMS"}
            Barcode.TYPE_WIFI->{
                binding.searchButton.text = "Connect"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_sharp_wifi_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //  binding.copyMainButton.visibility = View.GONE
                /**---------------------------*/
                bundle?.getStringArrayList(Intent_KEYS.WIFI_LIST)?.let { arrayList.addAll(it) }
                val wifiName = arrayList[0]
                val password = arrayList[1]
                val encryptionValue = arrayList[2].toInt()
                val encryption = if(encryptionValue==2){"WPA/WPA2"}
                else if(encryptionValue==3){"WEP"}
                else{"None"}


/*
                    CoroutineScope(Dispatchers.IO).launch {
                        MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                            .insert(EntityClass(arrayList,System.currentTimeMillis(),Barcode.TYPE_WIFI.toString(),rawQrCOde.toString()))
                    }
*/
                binding.result.text = "Wifi Name:$wifiName\nPassword: $password\n$encryption"
                minsert(Barcode.TYPE_WIFI,format,arrayList)
                /**---------------------------*/
                "Wifi"}
            Barcode.TYPE_DRIVER_LICENSE->{
                binding.searchButton.visibility = View.GONE
                binding.copybutton.text = "Copy"
                binding.addContactsButton.visibility = View.GONE
                               binding.result.text = rawQrCOde
                minsert( Barcode.TYPE_DRIVER_LICENSE,format,arrayList)
                "Driving Licence"}
           else -> {
               binding.searchButton.visibility= View.GONE
               binding.copybutton.visibility = View.GONE
               binding.shareButton.visibility = View.GONE
               binding.addContactsButton.visibility = View.GONE

               /**--------------------------------*/
               binding.result.text = rawQrCOde
               minsert( Barcode.TYPE_UNKNOWN,format,arrayList)
               /**--------------------------------*/

               "Unknown"}
        }
        binding.typeTextView.text = textType
        }catch (e:Exception){}

      //  try {
            val bitmap = qrGenerate(rawQrCOde, format,typeValue_QRGEN!!)
            binding.codeImage.setImageBitmap(bitmap)
      //  }catch (e:Exception){}

        try{
        binding.searchButton.setOnClickListener {
            val buttonText = binding.searchButton.text
            when(buttonText) {
                "Search" ->  rawQrCOde?.let { onSearch(it, valueType) }
                "Copy" ->  rawQrCOde?.let { copyText(it) }
                "Add to contacts"-> addToContacts(contactArlst)
                "Call"-> if(ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),mPERMISSION_CODE)
                }else{rawQrCOde?.let { call(it) } }
                "Add event"->createEvent(calArlst)
                "Send email"->sendEmail(emailAddress,emailSubject,emailBody)
                "Create message"->createMessage(phone,message)
                "Connect"-> connectToWifi()
            }
        }
        binding.copybutton.setOnClickListener {
            rawQrCOde?.let { copyText(it) }
        }
        binding.shareButton.setOnClickListener {
            rawQrCOde?.let { shareText(it) }
        }
        }catch (e:Exception){}
    }//end of onCreate()

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    fun qrGenerate(qrcode:String?, format:Int?, TypeValstr:String): Bitmap {
        val imageWidth =  binding.codeImage.layoutParams?.width
        val bitmap =  when(format){
            Custom_Formats_duplicate.CODABAR -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "CODABAR"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.CODABAR, imageWidth!!, 220)

            }
            Custom_Formats_duplicate.CODE_128 -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "CODE_128"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.CODE_128, imageWidth!!, 220)
            }
            Custom_Formats_duplicate.CODE_39 -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "CODE_39"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.CODE_39,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.CODE_93 -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "CODE_93"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.CODE_93,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.DATA_MATRIX -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "DATA_MATRIX"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.DATA_MATRIX,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.EAN_13 ->  {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "EAN_13"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.EAN_13,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.EAN_8 ->  {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "EAN_8"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.EAN_8,imageWidth!!, 220)
            } Custom_Formats_duplicate.ITF -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "ITF"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.ITF,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.QR_CODE ->  {
                Log.d("uidfh","QR_CODE")
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "QR_CODE"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 470
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.QR_CODE,imageWidth!!,430)

            }
            Custom_Formats_duplicate.UPC_A -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "UPC_A"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.UPC_A,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.UPC_E ->  {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "UPC_E"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.UPC_E,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.PDF_417 ->  {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "PDF_417"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.PDF_417,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.AZTEC ->  {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "AZTEC"
                minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.AZTEC,imageWidth!!, 220)
            }
            else ->  {
              val bitmapp =   when(TypeValstr) {
                    "Text"->{
                        Log.d("uidfh","ELSE_TEXTWALA")

                        binding.searchButton.text = "Copy"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                        binding.copybutton.visibility = View.GONE
                        binding.addContactsButton.visibility = View.GONE
                        binding.result.text = rawQrCOde
                        binding.typeTextView.text = "Text"
                        minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                        val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                        encoder.encodeAsBitmap()}
                    "Phone Number"->{
                        Log.d("dfnndb", TypeValstr.toString()+"else block")
                        val encoder =  QRGEncoder("9654980621",null,QRGContents.Type.PHONE,470)
                        encoder.encodeAsBitmap()
                    }
                    "URL"->{ val encoder =  QRGEncoder("9654980621",null,QRGContents.URL_KEY,470)
                        Log.d("dfnndb", TypeValstr.toString()+"else block")
                        encoder.encodeAsBitmap()}
                    "Contact information"->{ val encoder =  QRGEncoder("9654980621",null,QRGContents.Type.CONTACT,170)
                        Log.d("dfnndb", TypeValstr.toString()+"else block")
                       try {
                           binding.codeImage.setImageBitmap(encoder.encodeAsBitmap())
                       }catch (e:Exception){}
                        encoder.encodeAsBitmap()
                    }
                    "Wi-fi information"->{ val encoder =  QRGEncoder("9654980621",null,QRGContents.Type.TEXT,470)
                        Log.d("dfnndb", TypeValstr.toString()+"else block")
                        encoder.encodeAsBitmap()}
                    "Email"->{ val encoder =  QRGEncoder("9654980621",null,QRGContents.Type.EMAIL,470)
                        Log.d("dfnndb", TypeValstr.toString()+"else block")
                        encoder.encodeAsBitmap()}
                    "SMS"->{ val encoder =  QRGEncoder("9654980621",null,QRGContents.Type.SMS,470)
                        Log.d("dfnndb", TypeValstr.toString()+"else block")
                        encoder.encodeAsBitmap()}
                    else->{
                        val encoder =  QRGEncoder("9654980621",null,QRGContents.Type.TEXT,470)
                        Log.d("dfnndb", TypeValstr.toString()+"else block")
                        encoder.encodeAsBitmap()}
                }
                bitmapp
            }

        }
        // binding?.imageView?.setImageBitmap(bitmap)
        return bitmap
    }

   private fun onSearch(text:String,valueType: Int){
       val intent = Intent(Intent.ACTION_VIEW)
       val ISBN_baseurl = "https://www.google.com/search?q="
         when(valueType){
           Barcode.TYPE_URL -> {
               binding.searchButton.text = "Search"
               intent.data = Uri.parse(text)
               startActivity(intent)
           }
           Barcode.TYPE_ISBN,Barcode.TYPE_PRODUCT ->{
               binding.searchButton.text = "Search"
               intent.data = Uri.parse(ISBN_baseurl+text)
               startActivity(intent)
           }
             Barcode.TYPE_TEXT-> copyText(text)


       }


   }

    private fun copyText(text: String){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(text,text)
        clipboard.setPrimaryClip(clip)
        Snackbar.make(binding.copybutton,"Text copied",2000).show()
    }
    private fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,text)
        intent.type = "text/plain"

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }
    private fun call(phnNumber:String){
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse(/*"tel:$*/phnNumber)
       val dialogBuidler = MaterialAlertDialogBuilder(applicationContext, com.google.android.material.R.style.MaterialAlertDialog_MaterialComponents)
            .setIcon(resources.getDrawable(R.drawable.ic_twotone_call_24,null))
            .setMessage("Calling to: $rawQrCOde")
            .setTitle("Dial")
            .setCancelable(true)
            .setBackground(resources.getDrawable(R.drawable.dialog_background,null))
            .setPositiveButton("Call", DialogInterface.OnClickListener { dialog, which ->
                startActivity(intent)
                dialog.dismiss()
            })
            .setNegativeButton("cancel",DialogInterface.OnClickListener { dialog, which ->

            })
         dialogue = dialogBuidler.show()


    }
    private fun connectToWifi(){
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        startActivity(intent)
    }
    private fun createMessage(phnNumber:String,message:String) {
        val smsIntent = Intent(Intent.ACTION_SENDTO)
        smsIntent.type = "vnd.android-dir/mms-sms"
        smsIntent.data = Uri.parse("sms:$phnNumber")
        smsIntent.putExtra("sms_body",message)
        startActivity(smsIntent)
    }
    private fun sendEmail(email:String,subject:String,text:String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL,email)
        intent.putExtra(Intent.EXTRA_SUBJECT,subject)
        intent.putExtra(Intent.EXTRA_TEXT,text)
        intent.setType("message/rfc822")

        startActivity(Intent.createChooser(intent,"Choose ab Email"))

    }
    private fun createEvent(list:ArrayList<String>){

        val i = Intent(Intent.ACTION_EDIT)
        i.type = "vnd.android.cursor.item/event"
        i.putExtra(CalendarContract.Events.TITLE, list[0])
        i.putExtra(CalendarContract.Events.DESCRIPTION, list[1])
        i.putExtra(CalendarContract.Events.EVENT_LOCATION, list[2])
        startActivity(i)
    }
    private fun addToContacts(list:ArrayList<String>) {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION)
        intent.type = ContactsContract.RawContacts.CONTENT_TYPE
        intent.putExtra(ContactsContract.Intents.Insert.NAME,list[4])
        intent.putExtra(ContactsContract.Intents.Insert.PHONE,list[0])
        startActivity(intent)

    }

    fun minsert(typeValue:Int,format:Int,list:ArrayList<String>){

        if(!fromHistoryPage!!) {
            myiewmodel.insert(EntityClass(list, System.currentTimeMillis(), typeValue.toString(), rawQrCOde.toString(), format))
        }
       /* CoroutineScope(Dispatchers.IO).launch {
            MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                .insert(EntityClass(list,System.currentTimeMillis(),typeValue.toString(),rawQrCOde.toString(),format))
        }*/
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            mPERMISSION_CODE ->{
                if(permissions.isNotEmpty() && permissions[0].equals(Manifest.permission.CALL_PHONE))
                {
                     rawQrCOde?.let { call(it) }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(dialogue!=null)
        {
            dialogue?.cancel()
            dialogue = null
        }

    }
}