package com.example.simpleqrbarcodescanner_noads

import android.os.Bundle
import android.view.LayoutInflater
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleqrbarcodescanner_noads.CreateRecyclerView.CreateAdapter
import com.example.simpleqrbarcodescanner_noads.CreateRecyclerView.Create_Item
import com.example.simpleqrbarcodescanner_noads.Util.Custom_Formats_duplicate
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityCreateBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.encoder.QRCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.annotation.concurrent.Immutable

class CreateActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            val arrayList = ArrayList<Create_Item>()
            arrayList.add(Create_Item(R.drawable.ic_baseline_text_snippet_24, "Text",0))
            arrayList.add(Create_Item(R.drawable.ic_twotone_call_24, "Phone Number",0))
            arrayList.add(Create_Item(R.drawable.ic_baseline_web_24, "URL",0))
            arrayList.add(Create_Item(R.drawable.ic_outline_person_add_alt_1_24, "Contact information",0))
            arrayList.add(Create_Item(R.drawable.ic_sharp_wifi_24, "Wi-fi information",0))
            arrayList.add(Create_Item(R.drawable.ic_baseline_email_24, "Email",0))
            arrayList.add(Create_Item(R.drawable.ic_baseline_message_24, "SMS",0))

            val arrayList2 = ArrayList<Create_Item>()

            arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODE_39" ,Custom_Formats_duplicate.CODE_39))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODE_93" ,Custom_Formats_duplicate.CODE_93))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODE_128",Custom_Formats_duplicate.CODE_128))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODABAR",Custom_Formats_duplicate.CODABAR))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"DATA_MATRIX",Custom_Formats_duplicate.DATA_MATRIX))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"EAN_13",Custom_Formats_duplicate.EAN_13))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"EAN_8",Custom_Formats_duplicate.EAN_8))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"ITF",Custom_Formats_duplicate.ITF))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"QR_CODE",Custom_Formats_duplicate.QR_CODE))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"UPC_A",Custom_Formats_duplicate.UPC_A))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"UPC_E",Custom_Formats_duplicate.UPC_E))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"PDF_417",Custom_Formats_duplicate.PDF_417))
            arrayList2.add(Create_Item(R.drawable.ic_barcode,"AZTEC",Custom_Formats_duplicate.AZTEC))

            withContext(Dispatchers.Main) {
                val adapter = CreateAdapter(this@CreateActivity, arrayList)
                binding.createrecyclerview.layoutManager = LinearLayoutManager(this@CreateActivity)
                binding.createrecyclerview.adapter = adapter

                val adapter2 = CreateAdapter(this@CreateActivity, arrayList2)
                binding.createRecyclerView2DCodes.layoutManager = LinearLayoutManager(this@CreateActivity)
                binding.createRecyclerView2DCodes.adapter = adapter2
            }
        }


    }//end of onCreate


}