package com.example.simpleqrbarcodescanner_noads.MVVM

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleqrbarcodescanner_noads.R
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.google.mlkit.vision.barcode.common.Barcode
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter(val context:Context,val list:List<EntityClass>):RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view =  LayoutInflater.from(context).inflate(R.layout.item_history,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         val entityItem = list.get(position)

        try {
            setDataintoVIEW(entityItem, holder)
            }catch (e:Exception){}
    }

    override fun getItemCount(): Int {
            return list.size
    }

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        var typeTextView: TextView?=null
        var nameInfoText:TextView?=null
        var currentDateTEXT:TextView?=null
        var logo:ImageView?=null

        init {
            typeTextView =  itemView.findViewById(R.id.TypeValueTEXT)
            nameInfoText = itemView.findViewById(R.id.nameInfoText)
            currentDateTEXT = itemView.findViewById(R.id.currentDateTEXT)
            logo = itemView.findViewById(R.id.logo)
        }
    }
     @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
     fun setDataintoVIEW(item:EntityClass, holder: MyViewHolder):String{
         val typeValue = item.type_value.toInt()

         return   when(typeValue)
           {
                   Barcode.TYPE_PRODUCT -> {
                       holder.typeTextView?.text = "Product"
                       holder.nameInfoText?.text = item.rawValue
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_production_quantity_limits_24,null))
                       "Product"
                   }
                   Barcode.TYPE_TEXT-> {
                       holder.typeTextView?.text = "Text"
                       holder.nameInfoText?.text = item.rawValue
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_text_snippet_24,null))
                       "Text"
                   }
                   Barcode.TYPE_CONTACT_INFO-> {
                       holder.typeTextView?.text = "Contact Information"
                       holder.nameInfoText?.text = item.calenderList[0]
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_outline_person_add_alt_1_24,null))
                       "Contact Information"
                   }
                   Barcode.TYPE_ISBN-> {
                       holder.typeTextView?.text = "ID Book"
                       holder.nameInfoText?.text = item.rawValue
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_menu_book_24,null))
                       "ID Book"
                   }
                   Barcode.TYPE_PHONE-> {
                       holder.typeTextView?.text = "Phone"
                       holder.nameInfoText?.text = item.rawValue
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_twotone_call_24,null))
                       "Phone"
                   }
                   Barcode.TYPE_URL-> {
                       holder.typeTextView?.text = "Url"
                       holder.nameInfoText?.text = item?.calenderList[0]
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_web_24,null))
                       "Url"
                   }
                   Barcode.TYPE_GEO-> {
                       holder.typeTextView?.text = "Location"
                       holder.nameInfoText?.text = item.rawValue
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_location_on_24,null))
                       "Location"
                   }
                   Barcode.TYPE_CALENDAR_EVENT-> {
                       holder.typeTextView?.text = "Event"
                       holder.nameInfoText?.text = item.calenderList[0]
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_calendar_month_24,null))
                       "Event"
                   }
                   Barcode.TYPE_UNKNOWN-> {
                       holder.typeTextView?.text = "Unknown"
                       holder.nameInfoText?.text = item.rawValue
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_question_mark_24,null))
                       "Unknown"
                   }
                   Barcode.TYPE_EMAIL-> {
                       holder.typeTextView?.text = "Email"
                       holder.nameInfoText?.text = item.calenderList[0]
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_email_24,null))
                       "Email"
                   }
                   Barcode.TYPE_SMS-> {
                       holder.typeTextView?.text = "SMS"
                       holder.nameInfoText?.text = item.calenderList[0]
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_message_24,null))

                       "SMS"
                   }
                   Barcode.TYPE_WIFI-> {
                       holder.typeTextView?.text = "Wifi"
                       holder.nameInfoText?.text = item.calenderList[0]
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_sharp_wifi_24,null))
                       "Wifi"
                   }
                   Barcode.TYPE_DRIVER_LICENSE-> {
                       holder.typeTextView?.text = "Driving Licence"
                       holder.nameInfoText?.text = item.rawValue
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_document_scanner_24,null))
                       "Driving Licence"
                   }
                   else ->{
                       holder.typeTextView?.text = "Driving Licence"
                       holder.nameInfoText?.text = item.rawValue
                       holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                       holder.logo?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_baseline_question_mark_24,null))
                       "Unknown"
                   }
         }
     }

    @SuppressLint("SimpleDateFormat")
    fun convertoDate(milliseconds: Long): String =
        SimpleDateFormat("dd-MM-yyyy  h:mm a").format(Date(milliseconds))
}