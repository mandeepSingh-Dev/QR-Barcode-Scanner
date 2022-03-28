package com.Scanner.simpleqrbarcodescanner_noads

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.Scanner.simpleqrbarcodescanner_noads.CreateRecyclerView.CreateAdapter
import com.Scanner.simpleqrbarcodescanner_noads.CreateRecyclerView.Create_Item
import com.Scanner.simpleqrbarcodescanner_noads.Util.Custom_Formats_duplicate
import com.Scanner.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.Scanner.simpleqrbarcodescanner_noads.databinding.ActivityCreateBinding

import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        supportActionBar?.hide()

     /*   val adRequest = AdRequest.Builder().build()
        binding.adViewCreateActiivty?.loadAd(adRequest)
        binding.adViewCreateActiivty?.adListener = object: AdListener() {
            override fun onAdLoaded() {
            // binding.adViewlinearlayout?.visibility = View.VISIBLE
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }*/

        CoroutineScope(Dispatchers.IO).launch {
          /*  val arrayList = ArrayList<Create_Item>()
            arrayList.add(Create_Item(R.drawable.ic_baseline_text_snippet_24, "Text",0,Barcode.TYPE_TEXT))
            arrayList.add(Create_Item(R.drawable.ic_twotone_call_24, "Phone Number",0,Barcode.TYPE_PHONE))
            arrayList.add(Create_Item(R.drawable.ic_baseline_web_24, "URL",0,Barcode.TYPE_URL))
            arrayList.add(Create_Item(R.drawable.ic_outline_person_add_alt_1_24, "Contact information",0,Barcode.TYPE_CONTACT_INFO))
            arrayList.add(Create_Item(R.drawable.ic_sharp_wifi_24, "Wi-fi information",0,Barcode.TYPE_WIFI))
            arrayList.add(Create_Item(R.drawable.ic_baseline_email_24, "Email",0,Barcode.TYPE_EMAIL))
            arrayList.add(Create_Item(R.drawable.ic_baseline_message_24, "SMS",0,Barcode.TYPE_SMS))
*/
            val arrayList2 = ArrayList<Create_Item>()

            arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODE_39" ,Custom_Formats_duplicate.CODE_39,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODE_93" ,Custom_Formats_duplicate.CODE_93,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODE_128",Custom_Formats_duplicate.CODE_128,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODABAR",Custom_Formats_duplicate.CODABAR,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"DATA_MATRIX",Custom_Formats_duplicate.DATA_MATRIX,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"EAN_13",Custom_Formats_duplicate.EAN_13,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"EAN_8",Custom_Formats_duplicate.EAN_8,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"ITF",Custom_Formats_duplicate.ITF,Barcode.TYPE_UNKNOWN))
         //   arrayList2.add(Create_Item(R.drawable.ic_barcode,"QR_CODE",Custom_Formats_duplicate.QR_CODE))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"UPC_A",Custom_Formats_duplicate.UPC_A,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"UPC_E",Custom_Formats_duplicate.UPC_E,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"PDF_417",Custom_Formats_duplicate.PDF_417,Barcode.TYPE_UNKNOWN))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"AZTEC",Custom_Formats_duplicate.AZTEC,Barcode.TYPE_UNKNOWN))

            withContext(Dispatchers.Main) {
              /*  val adapter = CreateAdapter(this@CreateActivity, arrayList)
                binding.createrecyclerview.layoutManager = LinearLayoutManager(this@CreateActivity)
                binding.createrecyclerview.adapter = adapter
*/
                val adapter2 = CreateAdapter(this@CreateActivity, arrayList2)
                binding.createRecyclerView2DCodes.layoutManager = LinearLayoutManager(this@CreateActivity)
                binding.createRecyclerView2DCodes.adapter = adapter2
            }
        }
        binding.createQRCODEButton.setOnClickListener {
          /*  val intent = Intent(context, MainActivity2::class.java)
            intent.putExtra(Intent_KEYS.QRCODE, )
            intent.putExtra(Intent_KEYS.FORMAT, item.format)
            intent.putExtra(Intent_KEYS.VALUETYPE_QRGENERATOR,item.typeValue)

            context.startActivity(intent)*/
            val intent = Intent(this,
                com.Scanner.simpleqrbarcodescanner_noads.CreateActivityWithTextField::class.java)
            intent.putExtra(Intent_KEYS.VALUETYPE,Barcode.TYPE_TEXT)
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        binding.backbutton2.setOnClickListener {
            onBackPressed()
        }



      // mAdView =  findViewById<AdView>(R.id.adVieww)
       // mAdView.loadAd(adRequest)

     /*   mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            super.onAdLoaded()
                Toast.makeText(this@CreateActivity,"Loaded",Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }*/
       // val adview=  AdView(this)


    }//end of onCreate


}