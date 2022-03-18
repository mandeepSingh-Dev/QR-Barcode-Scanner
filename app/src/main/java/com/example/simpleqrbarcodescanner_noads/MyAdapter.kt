package com.example.simpleqrbarcodescanner_noads

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.google.mlkit.vision.barcode.common.Barcode

class MyAdapter(val context:Context,val list:List<EntityClass>):RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view =  LayoutInflater.from(context).inflate(R.layout.item_history,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         val typeValue = list.get(position).type_value
Log.d("fjisdfhsd",typeValue)
        holder.typeTextView?.text = getTypeValueasString(typeValue.toInt()) }

    override fun getItemCount(): Int {
            return list.size
    }

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        var typeTextView: TextView?=null
        init {
            typeTextView =  itemView.findViewById(R.id.TypeValueTEXT)
        }
    }
     fun getTypeValueasString(typeValue:Int):String{
         return   when(typeValue)
           {
             Barcode.TYPE_PRODUCT -> "Product"
             Barcode.TYPE_TEXT-> "Text"
             Barcode.TYPE_CONTACT_INFO-> "Contact Information"
             Barcode.TYPE_ISBN-> "ID Book"
             Barcode.TYPE_PHONE-> "Phone"
             Barcode.TYPE_URL-> "Url"
             Barcode.TYPE_GEO-> "Location"
             Barcode.TYPE_CALENDAR_EVENT-> "Event"
             Barcode.TYPE_UNKNOWN-> "Unknown"
             Barcode.TYPE_EMAIL-> "Email"
             Barcode.TYPE_SMS-> "SMS"
             Barcode.TYPE_WIFI-> "Wifi"
             Barcode.TYPE_DRIVER_LICENSE-> "Driving Licence"
             else -> "Unknown"
         }
     }
}