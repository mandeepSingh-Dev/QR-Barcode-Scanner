package com.example.simpleqrbarcodescanner_noads.CreateRecyclerView

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleqrbarcodescanner_noads.MVVM.MyAdapter
import com.example.simpleqrbarcodescanner_noads.MainActivity2
import com.example.simpleqrbarcodescanner_noads.R
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.zxing.BarcodeFormat

class CreateAdapter(val context: Context, val arrayList:ArrayList<Create_Item>): RecyclerView.Adapter<CreateAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
              var iconLogo:ImageView?=null
              var typeValue: TextView?=null
        init {
            iconLogo = itemView.findViewById(R.id.iconLogo)
            typeValue = itemView.findViewById(R.id.typeValueTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         val view =  LayoutInflater.from(context).inflate(R.layout.create_item_layout,parent,false)
             return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = arrayList.get(position)
        holder.iconLogo?.setImageResource(item.iconId)
        holder.typeValue?.text = item.typeValue

        val intent = Intent(context,MainActivity2::class.java)
        intent.putExtra(Intent_KEYS.QRCODE,"9650226920")
        intent.putExtra(Intent_KEYS.FORMAT, Barcode.FORMAT_QR_CODE)

        context.startActivity(intent)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}