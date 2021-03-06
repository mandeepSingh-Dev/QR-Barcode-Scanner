package com.Scanner.simpleqrbarcodescanner_noads

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.Scanner.simpleqrbarcodescanner_noads.Util.Custom_Formats_duplicate
import com.Scanner.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.Scanner.simpleqrbarcodescanner_noads.MVVM.MyViewModel
import com.Scanner.simpleqrbarcodescanner_noads.databinding.ActivityMain2Binding
import com.Scanner.simpleqrbarcodescanner_noads.room.EntityClass
import com.google.android.gms.ads.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity()
{
    lateinit var binding: ActivityMain2Binding
    private var rawQrCOde:String?=null
    private var fromHistoryPage:Boolean?=null

    private val mCALLPERMISSION_CODE = 100
    private val mWRITEPERMISSION_CODE = 1

    private  lateinit var phone:String
    private lateinit var message:String

    private lateinit var emailAddress:String
    private lateinit var emailSubject:String
    private lateinit var emailBody:String

     var bitmap:Bitmap?=null

    lateinit var calArlst:ArrayList<String>
    lateinit var contactArlst:ArrayList<String>

    lateinit var arrayList:ArrayList<String>
    var dialogue:AlertDialog?=null

    val myiewmodel:MyViewModel by viewModels()

    lateinit var sharedPreferences: SharedPreferences
  //  private var mInterstitialAd: InterstitialAd? = null




    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.backbutton3?.setOnClickListener {
            finish()
          /*  if(mInterstitialAd!=null) mInterstitialAd?.show(this)*/
        }
     /*   val adRequestBanner = AdRequest.Builder().build()
        binding.adViewMain2Actiivty.loadAd(adRequestBanner)
        binding.adViewMain2Actiivty.adListener = object:AdListener(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                binding.adViewMain2Actiivty.loadAd(adRequestBanner)
            }
        }
        var adRequestInterstitial = AdRequest.Builder().build()
            initializeAd(adRequestInterstitial)*/

        rawQrCOde = intent.getStringExtra(Intent_KEYS.QRCODE)
        val format = intent.getIntExtra(Intent_KEYS.FORMAT,0)
        val valueType = intent.getIntExtra(Intent_KEYS.VALUETYPE,0)
        val bundle = intent.getBundleExtra(Intent_KEYS.BUNDLE)
        //var typeValue_QRGEN = intent.getStringExtra(Intent_KEYS.VALUETYPE_QRGENERATOR)
        fromHistoryPage = intent.getBooleanExtra(Intent_KEYS.FROM_HISTORY,false)

        arrayList = ArrayList()

        //getting sharerdprefenrce for setting activity
        sharedPreferences=  getSharedPreferences(Intent_KEYS.SETTINGS_SHAREDPREFERNCE,Context.MODE_PRIVATE)
        //for automatically open the link
        if(sharedPreferences.getString(Intent_KEYS.ISAUTOMATICLAYOPEN,"false").equals("true"))
        {
            automaticallyOpenLink(rawQrCOde!!,valueType,format)
        }
        //for automatically copy text
        if(sharedPreferences.getString(Intent_KEYS.ISAUTOMATICLAYCOPY,"false").equals("true"))
        {
            copyText(rawQrCOde!!)
        }



        //when block for setting data acc. to Valuetype and to store in room also
        // but for only metoined below types
        //and for other types we store from another 2nd when block



        //when block to set type of barcode in top textview  acc. to valueType
        // and to store data also in room
       /* try{
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
                    *//**------------------*//*
                    contactArlst =  bundle?.getStringArrayList(Intent_KEYS.CONTACTS_ARRAYLIST)  as ArrayList<String>
                    val name = contactArlst[4]
                    val phone = contactArlst[0]
                    val email = contactArlst[2]
                    val organisation = contactArlst[3]
                    val title = contactArlst[5]
                    val address = contactArlst[1]
                    val url = contactArlst[6]
                    binding.result.text = "Name: $name \n Phone: $phone \n Email: $email \n Organisation: $organisation \n Title: $title \n Adress: $address \n Url: $url"
                    minsert( Barcode.TYPE_CONTACT_INFO,format,contactArlst)
                    *//**------------------*//*

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
                    *//**--------------------*//*
                    calArlst = bundle?.getStringArrayList(Intent_KEYS.CAL_ARRAYLIST) as ArrayList<String>
                    calArlst?.let{arrayList.addAll(it)}


                    if(arrayList[6].toInt()<10){ arrayList[6] = "0${arrayList[6]}" }
                    if(arrayList[7].toInt()<10){  arrayList[7] = "0${arrayList[7]}" }
                    if(arrayList[8].toInt()<10){  arrayList[8] = "0${arrayList[8]}" }

                    if(arrayList[12].toInt()<10){ arrayList[12] = "0${arrayList[12]}" }
                    if(arrayList[13].toInt()<10){ arrayList[13] = "0${arrayList[13]}" }
                    if(arrayList[14].toInt()<10){ arrayList[14] = "0${arrayList[14]}"}

                    binding.result.text = "Summary: ${arrayList[0]}\nDescription: ${arrayList[1]}\nLocation: ${arrayList[2]}\nStart Time: ${arrayList[3]}-${arrayList[4]}-${arrayList[5]}   ${arrayList[6]}:${arrayList[7]}:${arrayList[8]}\nEnd Time: ${arrayList[9]}-${arrayList[10]}-${arrayList[11]}   ${arrayList[12]}:${arrayList[13]}:${arrayList[14]}"
                    calArlst?.let{minsert( Barcode.TYPE_CALENDAR_EVENT,format,it)}
                    *//**--------------------*//*
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
                    *//**--------------------------*//*
                    bundle?.getStringArrayList(Intent_KEYS.EMAIL_LIST)?.let { arrayList.addAll(it) }
                    emailAddress =  arrayList[0]
                    emailSubject =  arrayList[1]
                    emailBody =  arrayList[2]


*//*
                    CoroutineScope(Dispatchers.IO).launch {
                        MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                            .insert(EntityClass(arrayList,System.currentTimeMillis(),Barcode.TYPE_EMAIL.toString(),rawQrCOde.toString()))
                    }
*//*
                    binding.result.text = "Mail to :$emailAddress\nSubject : $emailSubject\nBody: $emailBody"
                    minsert(Barcode.TYPE_EMAIL,format,arrayList)

                    *//**----------------*//*
                    "Email"}
                Barcode.TYPE_SMS->{
                    binding.searchButton.text = "Create message"
                    binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_message_24,null),null,null,null)
                    binding.copybutton.text = "Copy"
                    binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                    binding.addContactsButton.visibility = View.GONE
                    //   binding.copyMainButton.visibility = View.GONE
                    *//**--------------------------*//*
                    bundle?.getStringArrayList(Intent_KEYS.SMS_LIST)?.let { arrayList.addAll(it) }
                    phone = arrayList[0]
                    message = arrayList[1]


*//*
                    CoroutineScope(Dispatchers.IO).launch {
                        MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                            .insert(EntityClass(arrayList,System.currentTimeMillis(),Barcode.TYPE_SMS.toString(),rawQrCOde.toString()))
                    }
*//*
                    binding.result.text = "SMS to: $phone\nMessage: $message"
                    minsert(Barcode.TYPE_SMS,format,arrayList)
                    *//**-----------------------*//*
                    "SMS"}
                Barcode.TYPE_WIFI->{
                    binding.searchButton.text = "Connect"
                    binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_sharp_wifi_24,null),null,null,null)
                    binding.copybutton.text = "Copy"
                    binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                    binding.addContactsButton.visibility = View.GONE
                    //  binding.copyMainButton.visibility = View.GONE
                    *//**---------------------------*//*
                    bundle?.getStringArrayList(Intent_KEYS.WIFI_LIST)?.let { arrayList.addAll(it) }
                    val wifiName = arrayList[0]
                    val password = arrayList[1]
                    val encryptionValue = arrayList[2].toInt()
                    val encryption = if(encryptionValue==2){"WPA/WPA2"}
                    else if(encryptionValue==3){"WEP"}
                    else{"None"}


*//*
                    CoroutineScope(Dispatchers.IO).launch {
                        MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                            .insert(EntityClass(arrayList,System.currentTimeMillis(),Barcode.TYPE_WIFI.toString(),rawQrCOde.toString()))
                    }
*//*
                    binding.result.text = "Wifi Name:$wifiName\nPassword: $password\n$encryption"
                    minsert(Barcode.TYPE_WIFI,format,arrayList)
                    *//**---------------------------*//*
                    "Wi-fi"}
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

                    *//**--------------------------------*//*
                    binding.result.text = rawQrCOde
                    minsert( Barcode.TYPE_UNKNOWN,format,arrayList)
                    *//**--------------------------------*//*

                    "Unknown"}
            }
            binding.typeTextView.text = textType
        }catch (e:Exception){}*/

        try {
             bitmap = qrGenerate(rawQrCOde, format,valueType,bundle)
            binding.codeImage.setImageBitmap(bitmap)
            binding.saveButton.visibility = View.VISIBLE

            binding.saveButton.setOnClickListener {
                if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.P) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        bitmap?.let{saveCodeImage(it)}
                    } else {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), mWRITEPERMISSION_CODE)
                    }
                }else{
                    bitmap?.let{saveCodeImage(it)}
                }
            }
        }catch (e:Exception){
             binding.saveButton.visibility = View.GONE
        }

        try{
            binding.searchButton.setOnClickListener {
                val buttonText = binding.searchButton.text
                when(buttonText) {
                    "Search" ->  rawQrCOde?.let { onSearch(it, valueType) }
                    "Copy" ->  rawQrCOde?.let { copyText(it) }
                    "Add to contacts"-> addToContacts(contactArlst)
                    "Call"-> if(ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),mCALLPERMISSION_CODE)
                    }else{
                        rawQrCOde?.let { call(it) }
                    }
                    "Add event"->createEvent(calArlst)
                    "Send email"->sendEmail(emailAddress,emailSubject,emailBody)
                    "Create message"->createMessage(phone,message)
                    "Connect"-> connectToWifi()
                }
            }
            binding.copybutton.setOnClickListener {
             val buttontext = binding.copybutton.text.toString()

                when(buttontext) {
                    "Copy" ->  rawQrCOde?.let { copyText(it) }
                   // "Call" ->   rawQrCOde?.let { call(it)}
                }
            }
            binding.shareButton.setOnClickListener {
                rawQrCOde?.let { shareText(it) }
            }
            binding.addContactsButton?.setOnClickListener {
                addToContacts(contactArlst)
            }
        }catch (e:Exception){}
    }//end of onCreate()

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    fun qrGenerate(qrcode:String?, format:Int, TypeValue:Int?,bundle:Bundle?): Bitmap {
        val imageWidth =  binding.codeImage.layoutParams?.width
        val bitmap =  when(format){
            Custom_Formats_duplicate.CODABAR -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "CODABAR"
                minsert( -1,format!!,arrayList)

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
                minsert( -1,format!!,arrayList)

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
                minsert( -1,format!!,arrayList)

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
                minsert( -1,format!!,arrayList)

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
                minsert( -1,format!!,arrayList)

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
                minsert( -1,format!!,arrayList)

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
                minsert( -1,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.EAN_8,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.ITF -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "ITF"
                minsert( -1,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.ITF,imageWidth!!, 220)
            }
            Custom_Formats_duplicate.UPC_A -> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE
                binding.result.text = rawQrCOde
                binding.typeTextView.text = "UPC_A"
                minsert( -1,format!!,arrayList)

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
                minsert( -1,format!!,arrayList)

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
                minsert( -1,format!!,arrayList)

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
                minsert( -1,format!!,arrayList)

                binding.codeImage.layoutParams.height = 280
                BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.AZTEC,imageWidth!!, 220)
            }
            else ->  {

                val bitmapp =   when(TypeValue) {
                    Barcode.TYPE_PRODUCT-> {
                        binding.searchButton.text = "Search"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_search_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "Product"

//                binding.copyMainButton.visibility = View.GONE
                        minsert( Barcode.TYPE_PRODUCT,format,arrayList)
                        binding.result.text = rawQrCOde

                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_TEXT->{

                        binding.searchButton.text = "Copy"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                        binding.copybutton.visibility = View.GONE
                        binding.addContactsButton.visibility = View.GONE
                        binding.result.text = rawQrCOde
                        binding.typeTextView.text = "Text"
                        minsert( Barcode.TYPE_TEXT,format!!,arrayList)

                      //  val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_CONTACT_INFO->{
                        binding.searchButton.text = "Add to contacts"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_outline_person_add_alt_1_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.typeTextView.text = "Contact Information"
                        binding.addContactsButton.visibility = View.GONE
                        //    binding.copyMainButton.visibility = View.GONE
                        /**------------------*/
                        contactArlst =  bundle?.getStringArrayList(Intent_KEYS.CONTACTS_ARRAYLIST)  as ArrayList<String>
                        val name = contactArlst[4]
                        val phone = contactArlst[0]
                        val email = contactArlst[2]
                        val organisation = contactArlst[3]
                        val title = contactArlst[5]
                        val address = contactArlst[1]
                        val url = contactArlst[6]
                        binding.result.text = "Name: $name \nPhone: $phone \nEmail: $email \nOrganisation: $organisation \nTitle: $title \nAdress: $address \nUrl: $url"
                        minsert( Barcode.TYPE_CONTACT_INFO,format,contactArlst)

                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_ISBN->  {
                        binding.searchButton.text = "Search"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_search_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "ID Book"
                        //    binding.copyMainButton.visibility = View.GONE
                        binding.result.text = rawQrCOde
                        minsert( Barcode.TYPE_ISBN,format,arrayList)
                      //  val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_PHONE-> {
                        binding.searchButton.text = "Call"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_twotone_call_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.addContactsButton.visibility = View.GONE
//  binding.copyMainButton.visibility = View.GONE
                        binding.result.text = rawQrCOde
                        binding.typeTextView.text = "Phone"
                        minsert( Barcode.TYPE_PHONE,format!!,arrayList)

                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.PHONE,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_URL->{
                        binding.searchButton.text = "Search"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_search_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.addContactsButton.visibility = View.GONE
                        //  binding.copyMainButton.visibility = View.GONE
                        binding.result.text = rawQrCOde
                        binding.typeTextView.text = "Url"

                        bundle?.getStringArrayList(Intent_KEYS.URL_LIST)?.let { arrayList.addAll(it) }
                        minsert( Barcode.TYPE_URL,format,arrayList)

                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_GEO-> {
                        binding.searchButton.text = "Copy"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                        binding.copybutton.visibility = View.GONE
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "Location"
                        binding.result.text = rawQrCOde
                        minsert( Barcode.TYPE_GEO,format,arrayList)

                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.LOCATION,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_CALENDAR_EVENT->{
                        binding.searchButton.text = "Add event"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_calendar_month_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "Event"
                        //  binding.copyMainButton.visibility = View.GONE
                        /**--------------------*/
                        calArlst = bundle?.getStringArrayList(Intent_KEYS.CAL_ARRAYLIST) as ArrayList<String>
                        calArlst?.let{arrayList.addAll(it)}


                        if(arrayList[6].toInt()<10){ arrayList[6] = "0${arrayList[6]}" }
                        if(arrayList[7].toInt()<10){  arrayList[7] = "0${arrayList[7]}" }
                        if(arrayList[8].toInt()<10){  arrayList[8] = "0${arrayList[8]}" }

                        if(arrayList[12].toInt()<10){ arrayList[12] = "0${arrayList[12]}" }
                        if(arrayList[13].toInt()<10){ arrayList[13] = "0${arrayList[13]}" }
                        if(arrayList[14].toInt()<10){ arrayList[14] = "0${arrayList[14]}"}

                        binding.result.text = "Summary: ${arrayList[0]}\nDescription: ${arrayList[1]}\nLocation: ${arrayList[2]}\nStart Time: ${arrayList[3]}-${arrayList[4]}-${arrayList[5]}   ${arrayList[6]}:${arrayList[7]}:${arrayList[8]}\nEnd Time: ${arrayList[9]}-${arrayList[10]}-${arrayList[11]}   ${arrayList[12]}:${arrayList[13]}:${arrayList[14]}"
                        calArlst?.let{minsert( Barcode.TYPE_CALENDAR_EVENT,format,it)}
                        /**--------------------*/
                       // val encoder =  QRGEncoder(qrcode,null,null,470)
                       // BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                        //encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_UNKNOWN ->{
                        binding.searchButton.text = "Search"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_email_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "Unknown"
                        //  binding.copyMainButton.visibility = View.GONE
                        binding.result.text = rawQrCOde
                        //minsert( Barcode.TYPE_UNKNOWN,format,arrayList)
                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_EMAIL->{
                        binding.searchButton.text = "Send email"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_email_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "Email"
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

                      //  val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.EMAIL,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_SMS->{
                        binding.searchButton.text = "Create message"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_message_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "SMS"
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

                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.SMS,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_WIFI->{
                        binding.searchButton.text = "Connect"
                        binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_sharp_wifi_24,null),null,null,null)
                        binding.copybutton.text = "Copy"
                        binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "Wi-fi"
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

                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                      //  Log.d("dfnndb", TypeValue.toString()+"else block")
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    Barcode.TYPE_DRIVER_LICENSE->{
                        binding.searchButton.visibility = View.GONE
                        binding.copybutton.text = "Copy"
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "Driving Licence"
                        binding.result.text = rawQrCOde
                        minsert( Barcode.TYPE_DRIVER_LICENSE,format,arrayList)

                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                       // encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                    else->{
                        binding.searchButton.visibility= View.GONE
                        binding.copybutton.visibility = View.GONE
                        binding.shareButton.visibility = View.GONE
                        binding.addContactsButton.visibility = View.GONE
                        binding.typeTextView.text = "Unknown"

                        /**--------------------------------*/
                        binding.result.text = rawQrCOde
                        minsert( Barcode.TYPE_UNKNOWN,format,arrayList)

                       // val encoder =  QRGEncoder(qrcode,null,QRGContents.Type.TEXT,470)
                      //  encoder.encodeAsBitmap()
                        BarcodeEncoder().encodeBitmap(qrcode,BarcodeFormat.QR_CODE,imageWidth!!,470)
                    }
                }
                bitmapp
                /* binding.codeImage.layoutParams.height = 470
                 BarcodeEncoder().encodeBitmap(qrcode, BarcodeFormat.QR_CODE,imageWidth!!,430)
 */
            }

        }
        // binding?.imageView?.setImageBitmap(bitmap)
        return bitmap
    }

    private fun onSearch(text:String,valueType: Int){
       try {
           val intent = Intent(Intent.ACTION_VIEW)
           val ISBN_baseurl = "https://www.google.com/search?q="
           when (valueType) {
               Barcode.TYPE_URL -> {
                   binding.searchButton.text = "Search"
                   intent.data = Uri.parse(text)
                   startActivity(intent)
               }
               Barcode.TYPE_ISBN, Barcode.TYPE_PRODUCT -> {
                   binding.searchButton.text = "Search"
                   intent.data = Uri.parse(ISBN_baseurl + text)
                   startActivity(intent)
               }
               Barcode.TYPE_TEXT -> copyText(text)


           }
       }catch (e:Exception){}

    }

    private fun copyText(text: String){
       try{
           val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(text,text)
        clipboard.setPrimaryClip(clip)
        Snackbar.make(binding.copybutton,"Text copied",2000).show()
       }catch (e:Exception){}
    }
    private fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,text)
        intent.type = "text/plain"

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }
    private fun call(phnNumber:String){
       try {
           val intent = Intent(Intent.ACTION_CALL)
           intent.data = Uri.parse(/*"tel:$*/phnNumber)
           val dialogBuidler = MaterialAlertDialogBuilder(
               this,
               com.google.android.material.R.style.Theme_MaterialComponents_Dialog
           )
               .setIcon(resources.getDrawable(R.drawable.ic_twotone_call_24, null))
               .setMessage("Calling to: $rawQrCOde")
               .setTitle("Dial")
               .setCancelable(true)
               .setBackground(resources.getDrawable(R.drawable.dialog_background, null))
               .setPositiveButton("Call", DialogInterface.OnClickListener { dialog, which ->
                   startActivity(intent)
                   dialog.dismiss()
               })
               .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, which ->

               })
           dialogue = dialogBuidler.show()
       }catch (e:Exception){}

    }
    private fun connectToWifi(){
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        startActivity(intent)
    }
    private fun createMessage(phnNumber:String,message:String) {
        try{
            val smsIntent = Intent(Intent.ACTION_SENDTO)
        smsIntent.type = "vnd.android-dir/mms-sms"
        smsIntent.data = Uri.parse("sms:$phnNumber")
        smsIntent.putExtra("sms_body",message)
        startActivity(smsIntent)
        }catch (e:Exception){}
    }
    private fun sendEmail(email:String,subject:String,text:String){
       try{ val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL,email)
        intent.putExtra(Intent.EXTRA_SUBJECT,subject)
        intent.putExtra(Intent.EXTRA_TEXT,text)
        intent.setType("message/rfc822")

        startActivity(Intent.createChooser(intent,"Choose ab Email"))
       }catch (e:Exception){}
    }
    private fun createEvent(list:ArrayList<String>){
try{
        val i = Intent(Intent.ACTION_EDIT)
        i.type = "vnd.android.cursor.item/event"
        i.putExtra(CalendarContract.Events.TITLE, list[0])
        i.putExtra(CalendarContract.Events.DESCRIPTION, list[1])
        i.putExtra(CalendarContract.Events.EVENT_LOCATION, list[2])
        startActivity(i)
}catch (e:Exception){}
    }
    private fun addToContacts(list:ArrayList<String>) {
       try{ val intent = Intent(ContactsContract.Intents.Insert.ACTION)
        intent.type = ContactsContract.RawContacts.CONTENT_TYPE
        intent.putExtra(ContactsContract.Intents.Insert.NAME,list[4])
        intent.putExtra(ContactsContract.Intents.Insert.PHONE,list[0])
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL,list[2])
        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE,list[5])
        intent.putExtra(ContactsContract.CommonDataKinds.Organization.DISPLAY_NAME,list[3])

        startActivity(intent)
       }catch (e:Exception){}
    }
    private fun saveCodeImage(bitmap :Bitmap){
        try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val imageName = System.currentTimeMillis().toString()

            val contentValues = ContentValues()
            contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, imageName)
            contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png")
            contentValues.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,Environment.DIRECTORY_PICTURES + "/MyQRAPP")
          //  contentValues.put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MyQRAPP")

            val insertedUri = contentResolver?.insert(contentUri, contentValues)

            val outstream = insertedUri?.let { contentResolver?.openOutputStream(it) }

            val con = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outstream)
            if (con) {
                Snackbar.make(binding.saveButton, "Image saved.\nPath: ${Environment.DIRECTORY_PICTURES}/$imageName", 4000).show()
            }

        }catch (e:java.lang.Exception){
        }
    }
    fun automaticallyOpenLink(text:String,valueType:Int,format:Int){
        try{
            if(text!=null)
            {
                val intent = Intent(Intent.ACTION_VIEW)
                val ISBN_baseurl = "https://www.google.com/search?q="
                if(format!=Barcode.FORMAT_QR_CODE)
                {
                }else {
                    when (valueType) {
                        Barcode.TYPE_URL -> {
                            binding.searchButton.text = "Search"
                            intent.data = Uri.parse(text)
                            startActivity(intent)
                        }
                        Barcode.TYPE_ISBN, Barcode.TYPE_PRODUCT -> {
                            binding.searchButton.text = "Search"
                            intent.data = Uri.parse(ISBN_baseurl + text)
                            startActivity(intent)
                        }
                        else -> {}
                    }
                }
            }
        }catch (e:Exception){}
    }

  /*  private fun initializeAd(adRequest:AdRequest){
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                initializeAd(adRequest)
                mInterstitialAd = null
            }
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                mInterstitialAd?.show(this@MainActivity2)
            }
        })
    }*/

    fun minsert(typeValue:Int,format:Int,list:ArrayList<String>){

       try{
           if(!fromHistoryPage!!) {
               myiewmodel.insert(EntityClass(list, System.currentTimeMillis(), typeValue.toString(), rawQrCOde.toString(), format))
           }
       }catch (e:Exception){}
        /* CoroutineScope(Dispatchers.IO).launch {
             MyRoomDatabase.getInstance(this@MainActivity2).getDao()
                 .insert(EntityClass(list,System.currentTimeMillis(),typeValue.toString(),rawQrCOde.toString(),format))
         }*/
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
try {
    when (requestCode) {
        mCALLPERMISSION_CODE -> {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                rawQrCOde?.let { call(it) }
            }
        }
        mWRITEPERMISSION_CODE -> {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bitmap?.let { saveCodeImage(it) }
            }
        }
    }
}catch (e:Exception){}
    }

    override fun onDestroy() {
        super.onDestroy()
       try {
           if (dialogue != null) {
               dialogue?.cancel()
               dialogue = null
           }
       }catch (e:Exception){}

    }

    override fun onBackPressed() {
        super.onBackPressed()
       /* if(mInterstitialAd!=null) {
            mInterstitialAd?.show(this)
        }*/

    }
}