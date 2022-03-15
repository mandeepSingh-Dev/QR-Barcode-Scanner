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
import android.icu.util.Calendar
import android.provider.CalendarContract
import android.provider.ContactsContract
import kotlin.collections.ArrayList


class MainActivity2 : AppCompatActivity()
{
     lateinit var binding:ActivityMain2Binding
    private var qrCOde:String?=null
    private  val SEARCHBUTTON = 1
    private val CALLBUTTON = 2
    private val mPERMISSION_CODE = 100

    private  lateinit var phone:String
    private lateinit var message:String

    private lateinit var emailAddress:String
    private lateinit var emailSubject:String
    private lateinit var emailBody:String

    private lateinit var cal_title:String
    private lateinit var cal_description:String
    private lateinit var cal_location:String
    private lateinit var startDAY:String
    private lateinit var startMONTH:String
    private lateinit var startYEAR:String
    private lateinit var startHOUR:String
    private lateinit var startMINUTES:String
    private lateinit var startSECONDS:String

    private lateinit var endDAY:String
    private lateinit var endMONTH:String
    private lateinit var endYEAR:String
    private lateinit var endHOUR:String
    private lateinit var endMINUTES:String
    private lateinit var endSECONDS:String

    private lateinit var cal_end:String
    lateinit var calArlst:ArrayList<String>
    lateinit var contactArlst:ArrayList<String>




    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        //createEvent()

         qrCOde = intent.getStringExtra(Intent_KEYS.QRCODE)
        val format = intent.getIntExtra(Intent_KEYS.FORMAT, 0)
        val valueType = intent.getIntExtra(Intent_KEYS.VALUETYPE,0)
        val bundle = intent.getBundleExtra(Intent_KEYS.BUNDLE)


        //when block for setting data acc. to Valuetype
        when(valueType){
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
                 phone = bundle?.getString(Intent_KEYS.SMS_PHONE).toString()
                 message = bundle?.getString(Intent_KEYS.MESSAGE).toString()
                binding.result.text = "SMS to: $phone\nMessage: $message"
            }
                Barcode.TYPE_EMAIL->{
                    binding?.typeTextView.text = "Email"
                 emailAddress =  bundle?.getString(Intent_KEYS.EMAIL_ADDRESS).toString()
                 emailSubject =  bundle?.getString(Intent_KEYS.EMAIL_SUBJECT).toString()
                 emailBody =  bundle?.getString(Intent_KEYS.EMAIL_BODY).toString()
                binding.result.text = "Mail to :$emailAddress\nSubject : $emailSubject\nBody: $emailBody"
            }
                Barcode.TYPE_CALENDAR_EVENT ->{
                binding?.typeTextView.text = "Event"
                calArlst = ArrayList()
                bundle?.getStringArrayList(Intent_KEYS.CAL_ARRAYLIST)?.let {
                    calArlst.addAll(it) }


                if(calArlst[6].toInt()<10){ calArlst[6] = "0${calArlst[6]}" }
                if(calArlst[7].toInt()<10){  calArlst[7] = "0${calArlst[7]}" }
                if(calArlst[8].toInt()<10){  calArlst[8] = "0${calArlst[8]}" }

                if(calArlst[12].toInt()<10){  calArlst[12] = "0${calArlst[12]}" }
                if(calArlst[13].toInt()<10){  calArlst[13] = "0${calArlst[13]}" }
                if(calArlst[14].toInt()<10){  calArlst[14] = "0${calArlst[14]}"}

                binding.result.text = "Summary: ${calArlst[0]}\nDescription: ${calArlst[1]}\nLocation: ${calArlst[2]}\nStart Time: ${calArlst[3]}-${calArlst[4]}-${calArlst[5]}   ${calArlst[6]}:${calArlst[7]}:${calArlst[8]}\nEnd Time: ${calArlst[9]}-${calArlst[10]}-${calArlst[11]}   ${calArlst[12]}:${calArlst[13]}:${calArlst[14]}"
            }
                Barcode.TYPE_CONTACT_INFO ->{
                    contactArlst = ArrayList()
                    bundle?.getStringArrayList(Intent_KEYS.CONTACTS_ARRAYLIST)?.let {
                        contactArlst.addAll(it) }
                    binding.result.text = qrCOde
                }
            else ->{
                binding?.typeTextView.text = "Void"
                binding.result.text = qrCOde
            }
        }
        val bitmap = qrGenerate(qrCOde, format)
        binding.codeImage.setImageBitmap(bitmap)

        //when block to set type of barcode acc. to valueType
        val textType =  when(valueType){
            Barcode.TYPE_PRODUCT-> {
                binding.searchButton.text = "Search"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_search_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
//                binding.copyMainButton.visibility = View.GONE

                "Product"}
            Barcode.TYPE_TEXT->  {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE

                "Text"}
            Barcode.TYPE_CONTACT_INFO->  {
                binding.searchButton.text = "Add to contacts"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_outline_person_add_alt_1_24,null),null,null,null)
                binding.copybutton.text = "Call"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_twotone_call_24,null),null,null)
                //    binding.copyMainButton.visibility = View.GONE

                "Contact Information"}
            Barcode.TYPE_ISBN->  {
                binding.searchButton.text = "Search"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_search_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //    binding.copyMainButton.visibility = View.GONE

                "ID Book"}
            Barcode.TYPE_PHONE->  {
                binding.searchButton.text = "Call"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_twotone_call_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.text = "Add contact"
                //  binding.copyMainButton.visibility = View.GONE
                "Phone"}
            Barcode.TYPE_URL->  {
                binding.searchButton.text = "Search"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_search_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //  binding.copyMainButton.visibility = View.GONE
                "Url"}
            Barcode.TYPE_GEO-> {
                binding.searchButton.text = "Copy"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null,null)
                binding.copybutton.visibility = View.GONE
                binding.addContactsButton.visibility = View.GONE

                "Location"}
            Barcode.TYPE_CALENDAR_EVENT->  {
                binding.searchButton.text = "Add event"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_calendar_month_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //  binding.copyMainButton.visibility = View.GONE

                "Event"}
            Barcode.TYPE_UNKNOWN ->{
                binding.searchButton.text = "Search"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_email_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //  binding.copyMainButton.visibility = View.GONE

                "Unknown"}
            Barcode.TYPE_EMAIL -> {
                binding.searchButton.text = "Send email"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_email_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //   binding.copyMainButton.visibility = View.GONE

                "Email"}
            Barcode.TYPE_SMS->{
                binding.searchButton.text = "Create message"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_baseline_message_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //   binding.copyMainButton.visibility = View.GONE

                "SMS"}
            Barcode.TYPE_WIFI->{
                binding.searchButton.text = "Connect"
                binding.searchButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_sharp_wifi_24,null),null,null,null)
                binding.copybutton.text = "Copy"
                binding.copybutton.setCompoundDrawablesWithIntrinsicBounds(null,resources.getDrawable(R.drawable.ic_baseline_content_copy_24,null),null,null)
                binding.addContactsButton.visibility = View.GONE
                //  binding.copyMainButton.visibility = View.GONE

                "Wifi"}
            Barcode.TYPE_DRIVER_LICENSE->{
                binding.searchButton.visibility = View.GONE
                binding.copybutton.text = "Copy"
                binding.addContactsButton.visibility = View.GONE
                "Licence"}
           else -> {
               binding.searchButton.visibility= View.GONE
               binding.copybutton.visibility = View.GONE
               binding.shareButton.visibility = View.GONE
               binding.addContactsButton.visibility = View.GONE
               "Unknown"}
        }
        binding.typeTextView.text = textType

        binding.searchButton.setOnClickListener {
            val buttonText = binding.searchButton.text
            when(buttonText) {
                "Search" ->  qrCOde?.let { onSearch(it, valueType) }
                "Copy" ->  qrCOde?.let { copyText(it) }
                "Add to contacts"-> addToContacts(contactArlst)
                "Call"-> if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),mPERMISSION_CODE)
                }else{qrCOde?.let { call(it) } }
                "Add event"->createEvent(calArlst)
                "Send email"->sendEmail(emailAddress,emailSubject,emailBody)
                "Create message"->createMessage(phone,message)
                "Connect"-> connectToWifi()
            }
        }
        binding.copybutton.setOnClickListener {
            qrCOde?.let { copyText(it) }
        }
        binding.shareButton.setOnClickListener {
            qrCOde?.let { shareText(it) }
        }
    }//end of onCreate()

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
        MaterialAlertDialogBuilder(this, com.google.android.material.R.style.MaterialAlertDialog_MaterialComponents)
            .setIcon(resources.getDrawable(R.drawable.ic_twotone_call_24,null))
            .setMessage("Calling to: $qrCOde")
            .setTitle("Dial")
            .setCancelable(true)
            .setBackground(resources.getDrawable(R.drawable.dialog_background,null))
            .setPositiveButton("Call", DialogInterface.OnClickListener { dialog, which ->
                startActivity(intent)
                dialog.dismiss()
            })
            .setNegativeButton("cancel",DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                dialog.cancel()
            })
            .show()

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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            mPERMISSION_CODE ->{
                if(permissions.isNotEmpty() && permissions[0].equals(Manifest.permission.CALL_PHONE))
                {
                     qrCOde?.let { call(it) }
                }
            }
        }
    }
}