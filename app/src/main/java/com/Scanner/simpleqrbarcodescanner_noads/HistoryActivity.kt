package com.Scanner.simpleqrbarcodescanner_noads

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.Scanner.simpleqrbarcodescanner_noads.MVVM.MyAdapter
import com.Scanner.simpleqrbarcodescanner_noads.MVVM.MyViewModel
import com.Scanner.simpleqrbarcodescanner_noads.Util.OnBackButtonCustomListener
import com.Scanner.simpleqrbarcodescanner_noads.databinding.ActivityHistoryBinding
import com.Scanner.simpleqrbarcodescanner_noads.room.EntityClass
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {

    lateinit var binding:ActivityHistoryBinding

    val myViewmodel: MyViewModel by viewModels()

    var adapter: MyAdapter?=null

    var layoutManager:LinearLayoutManager?=null
    var list = mutableListOf<EntityClass>()

    private var stateee:Parcelable?=null
    var disposable:Disposable?=null

    lateinit var onBackButtonCustomListener:OnBackButtonCustomListener

    //lateinit  var selectOnClickListener:SelectOnClickListenerr

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        supportActionBar?.hide()

     /*   val adRequest = AdRequest.Builder().build()
        binding.adViewHistoryActiivty?.loadAd(adRequest)*/

        if(savedInstanceState!=null)
        {
            stateee = savedInstanceState.getParcelable("STATE")
        }


         list = mutableListOf()

        binding.backbutton.setOnClickListener {
            finish()
           // onBackPressed()
        }
        adapter = MyAdapter(this@HistoryActivity,myViewmodel,binding.selectAllButton,binding.deleteButton,binding.emptylayout)
        initRecyclerView()
       // getData()
        binding.selectAllButton.setOnClickListener {
            //selectOnClickListener.selectOnClick(true)
        }
    }//end of onCreate

    fun getData(){

     myViewmodel.listobservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:io.reactivex.rxjava3.core.Observer<ArrayList<EntityClass>>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }
                override fun onNext(it: ArrayList<EntityClass>) {
                    if(!it.isEmpty()) {
                        binding.historyRecyclerView.visibility = View.VISIBLE
                        binding.emptylayout.visibility = View.GONE
                        adapter?.setDate(it)
                    }
                    else{
                        binding.historyRecyclerView.visibility = View.GONE
                        binding.emptylayout.visibility = View.VISIBLE

                    }
                }
                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            }
            )


    /*   disposable = myViewmodel.listobservable.subscribeOn(Schedulers.io())
           .observeOn(Schedulers.computation())
           .map {
               list = it as MutableList<EntityClass>
           }
           .observeOn(AndroidSchedulers.mainThread())
           .doOnError {
               Log.d("dkfndk",it.message.toString())
           }.subscribe {
              adapter = MyAdapter(this,list.toList(),myViewmodel)
               initRecyclerView()
          }*/

   }

    private fun initRecyclerView(){
       layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.layoutManager = layoutManager
       binding.historyRecyclerView.adapter = adapter

        if(stateee!=null) {
            binding.historyRecyclerView.layoutManager?.onRestoreInstanceState(stateee)
        }
        adapter?.setCustomClickListenr(object:MyAdapter.CustomClickListener {
            override fun customOnClick(item: EntityClass, position: Int) {

              /*  myViewmodel.delete(item)
                list.removeAt(position)
                adapter?.notifyItemRemoved(position)*/
               // adapter?.notifyItemRangeChanged(position,list.size)


           /*     CoroutineScope(Dispatchers.IO).launch {
                    myViewmodel.delete(list[position])
                    withContext(Dispatchers.Main) {
                        list.remove(list[position])
                        adapter?.notifyDataSetChanged()
                        adapter?.notifyItemRemoved(position)
                    }
                }*/
            }
        })

            /*
               *//* if(binding.deleteButton.visibility == View.VISIBLE) {
                    binding.deleteButton.visibility = View.GONE
                }else{
                    binding.deleteButton.visibility = View.VISIBLE
                }*//*
              //  isSelectable = true

            }
        })*/
    }





    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("STATE",binding.historyRecyclerView.layoutManager?.onSaveInstanceState())
    }

    override fun onBackPressed() {
       // super.onBackPressed()
        //checking items are selected or not by checking size of arraylist of selected items
        val list = adapter?.getList()
              list?.size?.let {
            if (it > 0) {
              //  adapter?.selectedList?.clear()
            onBackButtonCustomListener.onBackClick(true)
            } else {
                super.onBackPressed()
               // finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if(disposable!=null) {
             disposable?.dispose()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onResume() {
        super.onResume()
        getData()

    }
    interface SelectOnClickListenerr {
        fun selectOnClick(con:Boolean)
    }
   @JvmName("setSelectOnClickListener1")
    fun setSelectOnClickListener(selectOnClickListenerr: SelectOnClickListenerr){
      //  this.selectOnClickListener = selectOnClickListenerr
    }

    fun selectOnBackButtonClickListener(onBackButtonCustomListener: OnBackButtonCustomListener)
    {
        this.onBackButtonCustomListener = onBackButtonCustomListener
    }

}