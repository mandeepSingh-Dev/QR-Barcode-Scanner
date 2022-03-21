package com.example.simpleqrbarcodescanner_noads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleqrbarcodescanner_noads.CreateRecyclerView.CreateAdapter
import com.example.simpleqrbarcodescanner_noads.CreateRecyclerView.Create_Item
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityCreateBinding
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityHistoryBinding
import com.google.mlkit.vision.barcode.common.Barcode

class CreateActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.createrecyclerview.adapter

        val arrayList = ArrayList<Create_Item>()
        arrayList.add(Create_Item(R.drawable.ic_baseline_text_snippet_24,"Text"))
        arrayList.add(Create_Item(R.drawable.ic_twotone_call_24,"Phone Number"))
        arrayList.add(Create_Item(R.drawable.ic_baseline_web_24,"URL"))
        arrayList.add(Create_Item(R.drawable.ic_outline_person_add_alt_1_24,"Contact information"))
        arrayList.add(Create_Item(R.drawable.ic_sharp_wifi_24,"Wi-fi information"))
        arrayList.add(Create_Item(R.drawable.ic_baseline_email_24,"Email"))
        arrayList.add(Create_Item(R.drawable.ic_baseline_message_24,"SMS"))

          val arrayList2 = ArrayList<Create_Item>()

        arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODE_39"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODE_93"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODE_128"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"CODABAR"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"DATA_MATRIX"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"EAN_13"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"EAN_8"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"ITF"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"QR_CODE"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"UPC_A"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"UPC_E"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"PDF417"))
        arrayList2.add(Create_Item(R.drawable.ic_barcode,"AZTEC"))

        val adapter = CreateAdapter(this,arrayList)
      binding.createrecyclerview.layoutManager = LinearLayoutManager(this)
      binding.createrecyclerview.adapter = adapter

        val adapter2 = CreateAdapter(this,arrayList2)
        binding.createRecyclerView2DCodes.layoutManager = LinearLayoutManager(this)
        binding.createRecyclerView2DCodes.adapter = adapter2

    }


}