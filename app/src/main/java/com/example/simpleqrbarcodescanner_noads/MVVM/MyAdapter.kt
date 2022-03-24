package com.example.simpleqrbarcodescanner_noads.MVVM

import android.annotation.SuppressLint
import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleqrbarcodescanner_noads.MainActivity2
import com.example.simpleqrbarcodescanner_noads.R
import com.example.simpleqrbarcodescanner_noads.Util.Custom_Formats_duplicate
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter(val context: Context, val myviewmodel: MyViewModel, val selectButton: ImageView) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var customClickListener: CustomClickListener? = null
    var isSelectable = false
    var isSelected = true
    var pos = 0

    // var arrayList = ArrayList<Int>()
   // var arrayList2 = ArrayList<EntityClass>()
    var receiver: BroadcastReceiver? = null
    var localbroadcatMnaager: LocalBroadcastManager? = null

    /**------------------*/
    var arrayListtty: ArrayList<EntityClass> = ArrayList<EntityClass>()
    var tvEmpty: TextView? = null
    var isEnable = false
    var isSelectAll = false
   // var selectList: ArrayList<EntityClass> = ArrayList()

    /**-------------------*/
    var selectedList = ArrayList<EntityClass>()


    lateinit var selectOnClickListener: SelectOnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false)

        /**--------GEEKS FOR GEEKS APPROACH---------*/
        // myViewmodel2 = ViewModelProviders.of(activity as HistoryActivity).get(MyViewmodel2::class.java)
        /**--------GEEKS FOR GEEKS APPROACH---------*/
        /*    HistoryActivity().setSelectOnClickListener(object: HistoryActivity.SelectOnClickListenerr {
                override fun selectOnClick(isSelect: Boolean) {
                    if(isSelect)
                    {
                        Toast.makeText(context,"SELECTED",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Log.d("not sleected","NOT SELECETD")
                    }
                }
            })*/
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entityItem = arrayListtty.get(position)

        // setDataintoVIEW(entityItem.type_value.toInt(),entityItem.format,entityItem, holder)

        holder.bind(entityItem)
        // holder.getItem(entityItem)

    /*    holder.itemView.setOnClickListener {
            Log.d("soljdsdfs", entityItem.type_value)
            val intent = Intent(context, MainActivity2::class.java)
            intent.putExtra(Intent_KEYS.QRCODE, entityItem.rawValue)
            intent.putExtra(Intent_KEYS.FORMAT, entityItem.format)
            intent.putExtra(Intent_KEYS.VALUETYPE, entityItem.type_value.toInt())
            intent.putExtra(Intent_KEYS.FROM_HISTORY, true)
            intent.putExtra(Intent_KEYS.BUNDLE, setBundle(entityItem))
            Log.d("fihfedf", entityItem.type_value)
            context.startActivity(intent)
        }*/

        var popmenu = PopupMenu(context, holder.popupMenu)
        popmenu.inflate(R.menu.select_menu)
        holder.popupMenu?.setOnClickListener {

            popmenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.menu_delete) {
                    //  customClickListener?.customOnClick(arrayList.get(holder.adapterPosition),holder.adapterPosition)
                    CoroutineScope(Dispatchers.Default).launch {
                        val itemm =
                            myviewmodel.getItembyId(arrayListtty.get(holder.adapterPosition).id)
                        myviewmodel.delete(itemm)
                        withContext(Dispatchers.Main) {
                            arrayListtty.removeAt(holder.adapterPosition)
//                        notifyItemRemoved(holder.adapterPosition)
//                        notifyDataSetChanged()
                            // notifyItemRangeChanged(holder.adapterPosition,arrayListtty.size)
                            // notifyItemRangeChanged(holder.adapterPosition, arrayListtty?.size!!); // notifyDataSetChanged()
                            // Toast.makeText(context, "DELETE", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "SELECTEDTOAST", Toast.LENGTH_SHORT).show()
                }
                return@setOnMenuItemClickListener true
            }
            popmenu.show()
        }

        Log.d("fp[fgdjifgvd", entityItem.isSelected.toString())
       /* selectButton.setOnClickListener {
            Log.d("fhdfgb", "gihd")
            holder.cardView?.setBackgroundColor(Color.RED)
        }*/


        /*       *----------GEEKS FOR GEEKS APPROACH-----------
       holder.itemView.setOnLongClickListener {
           if(!isEnable)
           {
              var callback = object: ActionMode.Callback{
                   override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                      val menuInflater =mode?.menuInflater
                       menuInflater?.inflate(R.menu.select_menu,menu)
                    return true
                   }

                   override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                          isEnable = true

                       clickItem(holder)

                       myViewmodel2.getText().observe(activity as LifecycleOwner, androidx.lifecycle.Observer {
                           mode?.setTitle(String.format("%s Selected",it))
                       })
                    return true
                    }

                   override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

                       val id = item?.itemId

                       when(id){
                           R.id.menu_delete ->{
                               selectList.forEach {
                                   arrayList.remove(it)
                               }
                               if(arrayList.size==0)
                               {
                                   Toast.makeText(context,"EMPTY",Toast.LENGTH_LONG).show()
                               }
                               mode?.finish()
                           }
                           R.id.menu_select_all ->{
                               if(selectList.size == arrayList.size)
                               {
                                   isSelectAll = false
                                   selectList.clear()
                               }
                               else{
                                   isSelectAll = true
                                   Toast.makeText(context,"isSelectTRUE",Toast.LENGTH_SHORT).show()
                                   selectList.clear()
                                   selectList.addAll(arrayList)
                               }
                               myViewmodel2.setText(selectList.size.toString())
                               notifyDataSetChanged()
                           }

                       }
                       return true
                   }

                   override fun onDestroyActionMode(mode: ActionMode?) {
                       isEnable = false

                       isSelectAll = false

                       selectList.clear()

                       notifyDataSetChanged()
                   }
               }// onCreateActionMode finished
               (it.context as AppCompatActivity).startActionMode(callback)

           }
           else{
               clickItem(holder)
           }
           return@setOnLongClickListener true
       }
       holder.itemView.setOnClickListener {
           if(isEnable)
           {
               clickItem(holder)
           }
           else{
               Toast.makeText(activity,"You Clicked"+arrayList.get(holder.getAdapterPosition()),
                   Toast.LENGTH_SHORT).show();
           }
       }
               if(isSelectAll)
               {
                   holder.checkbox?.visibility = View.VISIBLE
                   holder.itemView?.setBackgroundColor(Color.LTGRAY)
               }
               else{
                   holder.checkbox?.visibility = View.GONE
                   holder.itemView?.setBackgroundColor(Color.WHITE)
               }

               arrayList.forEach {
                   var item = it
                   selectList.forEach {
                       if(it.id == item.id)
                       {

                           holder.checkbox?.visibility = View.VISIBLE
                           holder.itemView?.setBackgroundColor(Color.LTGRAY)
                       }                }

                   }
               *----------GEEKS FOR GEEKS APPROACH-----------
               *-------MY APPROACH---------------
               holder.cardView?.setOnLongClickListener {

       //            if(!arrayList.contains(holder.adapterPosition)) {
                   if(!arrayList.contains(entityItem.id)) {
                       isSelectable = true
                       holder.cardView?.setCardBackgroundColor(Color.RED)
       //                arrayList.add(holder.adapterPosition)
                       arrayList.add(entityItem.id)
                       arrayList2.add(entityItem)
                       deleteButton.visibility = View.VISIBLE
                       layout.setBackgroundColor(Color.parseColor("#8B0000"))
                   }
                   return@setOnLongClickListener true
               }
               holder.cardView?.setOnClickListener {
                   if (isSelectable) {
       //                    if (!(arrayList.contains(position))) {
                       if (!(arrayList.contains(entityItem.id))) {
                           holder.cardView?.setCardBackgroundColor(Color.RED)
       //                        arrayList.add(holder.adapterPosition)
                               arrayList.add(entityItem.id)
                               arrayList2.add(entityItem)

                           }
       //                else if (arrayList.contains(position)) {
                       else if (arrayList.contains(entityItem.id)) {
                           holder.cardView?.setCardBackgroundColor(Color.WHITE)
       //                        arrayList.remove(holder.adapterPosition)
                               arrayList.remove(entityItem.id)
                               arrayList2.remove(entityItem)

                               if(arrayList.isEmpty())
                               {
                               isSelectable = false
                                   deleteButton.visibility = View.GONE
                                   layout.setBackgroundColor(Color.parseColor("#1A3E42"))
                               }
                               }
                   }
                   else{
                       Log.d("soljdsdfs",entityItem.type_value)
                       val intent = Intent(context,MainActivity2::class.java)
                       intent.putExtra(Intent_KEYS.QRCODE, entityItem.rawValue)
                       intent.putExtra(Intent_KEYS.FORMAT, entityItem.format)
                       intent.putExtra(Intent_KEYS.VALUETYPE, entityItem.type_value.toInt())
                       intent.putExtra(Intent_KEYS.BUNDLE,setBundle(entityItem))
                       Log.d("fihfedf",entityItem.type_value)
                       context.startActivity(intent)
                   }
               }
               deleteButton.setOnClickListener {
                   val dialogueBuilder = MaterialAlertDialogBuilder(context, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                   dialogueBuilder.setTitle("Delete")
                       .setIcon(R.drawable.ic_round_delete_outline_24)
                       .setTitle("Delete selected items?")
                       .setPositiveButton("Confirm",DialogInterface.OnClickListener { dialog, which ->
                           Toast.makeText(context,arrayList2.size.toString(),Toast.LENGTH_SHORT).show()
                       })
                   dialogueBuilder.show()

               }
                receiver =  object:BroadcastReceiver(){
                   override fun onReceive(context: Context?, intent: Intent?) {
                       arrayList2.removeAll(arrayList2)
                       arrayList.removeAll(arrayList)
                       deleteButton.visibility = View.GONE
                       layout.setBackgroundColor(Color.parseColor("#1A3E42"))
                       holder.cardView?.setCardBackgroundColor(Color.WHITE)
                   }
               }
                  localbroadcatMnaager = LocalBroadcastManager.getInstance(context)
               localbroadcatMnaager?.registerReceiver(receiver!!, IntentFilter(Intent_KEYS.INTENT_ADAPTER))
               *-------MY APPROACH---------------*/

    }

    override fun getItemCount(): Int {
        return arrayListtty.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var typeTextView: TextView? = null
        var nameInfoText: TextView? = null
        var currentDateTEXT: TextView? = null
        var logo: ImageView? = null
        var cardView: CardView? = null
        var popupMenu: ImageView? = null


        init {
            typeTextView = itemView.findViewById(R.id.TypeValueTEXT)
            nameInfoText = itemView.findViewById(R.id.nameInfoText)
            currentDateTEXT = itemView.findViewById(R.id.currentDateTEXT)
            logo = itemView.findViewById(R.id.logo)
            cardView = itemView.findViewById(R.id.parentcardView)
            popupMenu = itemView.findViewById(R.id.popupMenu)
        }

        fun bind(entityItem: EntityClass) {
            setDataintoVIEW(entityItem.type_value.toInt(), entityItem.format, entityItem, this)

            if (!entityItem.isSelected) cardView?.setCardBackgroundColor(Color.RED)
            else cardView?.setCardBackgroundColor(Color.GREEN)

            /**-----------------------------------------------*/
                cardView?.setOnLongClickListener {
                    if (!entityItem.isSelected) {
                        cardView?.setCardBackgroundColor(Color.GREEN)
                        entityItem.isSelected = true
                        selectedList.add(entityItem)
                    } else {
                        cardView?.setCardBackgroundColor(Color.RED)
                        entityItem.isSelected = false
                        selectedList.remove(entityItem)
                    }

                    Toast.makeText(context, selectedList.size.toString() + "    dfhgd", Toast.LENGTH_SHORT).show()
                    return@setOnLongClickListener true
                }

            selectButton.setOnClickListener {
                Log.d("dkfhdfd","sfjsd")

                if(selectedList.size<arrayListtty.size)
                {
                    //selectedList.clear()
                     arrayListtty.forEach {
                         it.isSelected =  false
                     }
                    Log.d("fkhdfjd","if block")
                    arrayListtty.forEach {
                        clickItem(this,it)
                    }
                }
                else{
                    Log.d("fkhdfjd","else block")
                    arrayListtty.forEach {
                        it.isSelected = true
                    }
                    arrayListtty.forEach {
                        clickItem(this,it)
                    }

                   // notifyDataSetChanged()
                }
                Log.d("difgdhfb","${selectedList.size.toString()}____${arrayListtty.size.toString()}")
            }


        }

    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    fun setDataintoVIEW(typeValue: Int, format: Int, item: EntityClass, holder: MyViewHolder) {

        Log.d("dfhdf", item.type_value)

        when (format) {
            Custom_Formats_duplicate.CODABAR -> {
                holder.typeTextView?.text = "CODABAR"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.CODE_128 -> {
                holder.typeTextView?.text = "CODE_128"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.CODE_39 -> {
                holder.typeTextView?.text = "CODE_39"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.CODE_93 -> {
                holder.typeTextView?.text = "CODE_93"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.DATA_MATRIX -> {
                holder.typeTextView?.text = "DATA_MATRIX"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.EAN_13 -> {
                holder.typeTextView?.text = "EAN_13"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.EAN_8 -> {
                holder.typeTextView?.text = "EAN_8"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.ITF -> {
                holder.typeTextView?.text = "ITF"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.UPC_A -> {
                holder.typeTextView?.text = "UPC_A"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.UPC_E -> {
                holder.typeTextView?.text = "UPC_E"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.PDF_417 -> {
                holder.typeTextView?.text = "PDF_417"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            Custom_Formats_duplicate.AZTEC -> {
                holder.typeTextView?.text = "AZTEC"
                holder.nameInfoText?.text = item.rawValue
                holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                holder.logo?.setImageDrawable(
                    context.resources.getDrawable(
                        R.drawable.ic_barcode,
                        null
                    )
                )
            }
            else -> {
                when (typeValue) {
                    Barcode.TYPE_PRODUCT -> {
                        holder.typeTextView?.text = "Product"
                        holder.nameInfoText?.text = item.rawValue
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_production_quantity_limits_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_TEXT -> {
                        holder.typeTextView?.text = "Text"
                        holder.nameInfoText?.text = item.rawValue
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_text_snippet_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_CONTACT_INFO -> {
                        holder.typeTextView?.text = "Contact Information"
                        holder.nameInfoText?.text = item.rawValue
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_outline_person_add_alt_1_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_ISBN -> {
                        holder.typeTextView?.text = "ID Book"
                        holder.nameInfoText?.text = item.rawValue
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_menu_book_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_PHONE -> {
                        holder.typeTextView?.text = "Phone"
                        holder.nameInfoText?.text = item.rawValue
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_twotone_call_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_URL -> {
                        holder.typeTextView?.text = "Url"
                        holder.nameInfoText?.text = item.calenderList[0]
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_web_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_GEO -> {
                        holder.typeTextView?.text = "Location"
                        holder.nameInfoText?.text = item.rawValue
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_location_on_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_CALENDAR_EVENT -> {
                        holder.typeTextView?.text = "Event"
                        holder.nameInfoText?.text = item.calenderList[0]
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_calendar_month_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_UNKNOWN -> {
                        holder.typeTextView?.text = "Unknown"
                        holder.nameInfoText?.text = item.rawValue
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_question_mark_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_EMAIL -> {
                        holder.typeTextView?.text = "Email"
                        holder.nameInfoText?.text = item.calenderList[0]
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_email_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_SMS -> {
                        holder.typeTextView?.text = "SMS"
                        holder.nameInfoText?.text = item.calenderList[0]
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_message_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_WIFI -> {
                        holder.typeTextView?.text = "Wifi"
                        holder.nameInfoText?.text = item.calenderList[0]
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_sharp_wifi_24,
                                null
                            )
                        )
                    }
                    Barcode.TYPE_DRIVER_LICENSE -> {
                        holder.typeTextView?.text = "Driving Licence"
                        holder.nameInfoText?.text = item.rawValue
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_document_scanner_24,
                                null
                            )
                        )
                    }
                    else -> {
                        holder.typeTextView?.text = "Unknown"
                        holder.nameInfoText?.text = item.rawValue
                        holder.currentDateTEXT?.text = convertoDate(item.currentTime)
                        holder.logo?.setImageDrawable(
                            context.resources.getDrawable(
                                R.drawable.ic_baseline_question_mark_24,
                                null
                            )
                        )
                    }
                }
            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun convertoDate(milliseconds: Long): String =
        SimpleDateFormat("dd-MM-yyyy  h:mm a").format(Date(milliseconds))

    interface CustomClickListener {
        fun customOnClick(item: EntityClass, position: Int)
    }

    fun setCustomClickListenr(customClickListener: CustomClickListener) {
        this.customClickListener = customClickListener
    }

    /*fun getList(): ArrayList<EntityClass> {
        return arrayList2
    }*/

    //TODO(set data to send in mainActivity exactly in that pattern which is used in mainActivity for sending data to mainActity2 )
    fun setBundle(item: EntityClass): Bundle {

        val valueType = item.type_value.toInt()
        var bundle = Bundle()
        val arraylisttt = ArrayList<String>()

        when (valueType) {
            Barcode.TYPE_URL -> {
                // bundle.putString(Intent_KEYS.URL,barcode?.url?.url)
                arraylisttt.add(item.rawValue)

                arraylisttt.let { bundle.putStringArrayList(Intent_KEYS.URL_LIST, it) }
            }
            Barcode.TYPE_WIFI -> {
/*
                bundle.putString(Intent_KEYS.WIFINAME,list.calenderList[0])
                bundle.putString(Intent_KEYS.PASSWORD,list.calenderList[1])
                list.calenderList[1].let {
                    bundle.putInt(Intent_KEYS.ENCRYPTION_VALUE, it)
                }*/
                arraylisttt.add(item.calenderList[0])
                arraylisttt.add(item.calenderList[1])
                arraylisttt.add(item.calenderList[2].toString())

                arraylisttt.let { bundle.putStringArrayList(Intent_KEYS.WIFI_LIST, it) }
            }
            Barcode.TYPE_EMAIL -> {
                /*  bundle.putString(Intent_KEYS.EMAIL_ADDRESS,barcode?.email?.address)
                  bundle.putString(Intent_KEYS.EMAIL_SUBJECT,barcode?.email?.subject)
                  bundle.putString(Intent_KEYS.EMAIL_BODY,barcode?.email?.body)
  */
                arraylisttt.add(item.calenderList[0])
                arraylisttt.add(item.calenderList[1])
                arraylisttt.add(item.calenderList[2])

                arraylisttt.let { bundle.putStringArrayList(Intent_KEYS.EMAIL_LIST, it) }


            }
            Barcode.TYPE_SMS -> {
                /*  bundle.putString(Intent_KEYS.SMS_PHONE,barcode?.sms?.phoneNumber)
                  bundle.putString(Intent_KEYS.MESSAGE,barcode?.sms?.message)
  */
                arraylisttt.add(item.calenderList[0])
                arraylisttt.add(item.calenderList[1])

                arraylisttt.let { bundle.putStringArrayList(Intent_KEYS.SMS_LIST, it) }
            }
            Barcode.TYPE_CALENDAR_EVENT -> {
                arraylisttt.add(item.calenderList[0])
                arraylisttt.add(item.calenderList[1])
                arraylisttt.add(item.calenderList[2])

                arraylisttt.add(item.calenderList[3])
                arraylisttt.add(item.calenderList[4])
                arraylisttt.add(item.calenderList[5])
                arraylisttt.add(item.calenderList[6])
                arraylisttt.add(item.calenderList[7])
                arraylisttt.add(item.calenderList[8])

                arraylisttt.add(item.calenderList[9])
                arraylisttt.add(item.calenderList[10])
                arraylisttt.add(item.calenderList[11])
                arraylisttt.add(item.calenderList[12])
                arraylisttt.add(item.calenderList[13])
                arraylisttt.add(item.calenderList[14])

                arraylisttt.let { bundle.putStringArrayList(Intent_KEYS.CAL_ARRAYLIST, it) }
            }
            Barcode.TYPE_CONTACT_INFO -> {
                try {
                    arraylisttt.add(item.calenderList[0])
                    arraylisttt.add(item.calenderList[1])
                    arraylisttt.add(item.calenderList[2])
                    var dfhdj4 = ""
                    item.calenderList[3].forEach { dfhdj4 += it.toString() }
                    arraylisttt.add(dfhdj4)
                    arraylisttt.add(item.calenderList[4])
                    arraylisttt.add(item.calenderList[5])
                    arraylisttt.add(item.calenderList[6])
                    arraylisttt.add(item.calenderList[7])

                    arraylisttt.let {
                        bundle.putStringArrayList(
                            Intent_KEYS.CONTACTS_ARRAYLIST,
                            it
                        )
                    }
                } catch (e: Exception) {
                }
            }
            //doesnot suppported
            Barcode.TYPE_DRIVER_LICENSE -> {
                /*  bundle.putString(Intent_KEYS.LICENCE_NUMBER,barcode?.driverLicense?.licenseNumber)
                  bundle.putString(Intent_KEYS.LICENCE_FIRSTNAME,barcode?.driverLicense?.firstName)
                  bundle.putString(Intent_KEYS.LICENCE_MIDDLENAME,barcode?.driverLicense?.middleName)
                  bundle.putString(Intent_KEYS.LICENCE_LASTNAME,barcode?.driverLicense?.lastName)
                  bundle.putString(Intent_KEYS.LICENCE_DOB,barcode?.driverLicense?.birthDate)
                  bundle.putString(Intent_KEYS.LICENCE_EXPDATE,barcode?.driverLicense?.expiryDate)
                  bundle.putString(Intent_KEYS.LICENCE_ISSUEDATE,barcode?.driverLicense?.issueDate)
                  bundle.putString(Intent_KEYS.LICENCE_DOCTYPE,barcode?.driverLicense?.documentType)
                  bundle.putString(Intent_KEYS.LICENCE_GENDER,barcode?.driverLicense?.gender)
                  bundle.putString(Intent_KEYS.LICENCE_ISSUCOUNTRY,barcode?.driverLicense?.issuingCountry)
                  bundle.putString(Intent_KEYS.LICENCE_ADSTREET,barcode?.driverLicense?.addressStreet)
                  bundle.putString(Intent_KEYS.LICENCE_ADSTATE,barcode?.driverLicense?.addressState)
                  bundle.putString(Intent_KEYS.LICENCE_ADCITY,barcode?.driverLicense?.addressCity)
                  bundle.putString(Intent_KEYS.LICENCE_ADZIP,barcode?.driverLicense?.addressZip)
      */
            }
            else -> bundle.putString(Intent_KEYS.VOID, "VOID")
        }
        return bundle
    }

    interface SelectOnClickListener {
        fun selectOnClick(isSelect: Boolean)
    }

    fun setSelectOnClickListenr(selectOnClickListener: SelectOnClickListener) {
        this.selectOnClickListener = selectOnClickListener
    }

    /**GEEKS FOR GEEKS APPROACH METHOD*/
     fun clickItem(holder: MyViewHolder,item:EntityClass)
     {
         if (!item.isSelected) {
             holder?.cardView?.setCardBackgroundColor(Color.GREEN)
             item.isSelected = true
             selectedList.add(item)
         } else {
             holder?.cardView?.setCardBackgroundColor(Color.RED)
             item.isSelected = false
             selectedList.remove(item)
         }
        /* if(holder.checkbox?.visibility == View.GONE)
         {
             holder.checkbox?.visibility = View.VISIBLE
             //Log.d("fdmfd",holder.itemtoString())

             holder.itemView.setBackgroundColor(Color.LTGRAY)

             selectList.add(item)
         }
         else{
             holder.checkbox?.visibility = View.GONE

             holder.itemView.setBackgroundColor(Color.WHITE)

             selectList.remove(item)
         }

         myViewmodel2.setText(selectList.size.toString())*/
         notifyDataSetChanged()
     }

    fun setDate(listy: ArrayList<EntityClass>) {
        arrayListtty = listy
        notifyDataSetChanged()
    }

}