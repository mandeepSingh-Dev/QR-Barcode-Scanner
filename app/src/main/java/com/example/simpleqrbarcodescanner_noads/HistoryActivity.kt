package com.example.simpleqrbarcodescanner_noads

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleqrbarcodescanner_noads.MVVM.MyAdapter
import com.example.simpleqrbarcodescanner_noads.MVVM.MyViewModel
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityHistoryBinding
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.example.simpleqrbarcodescanner_noads.room.MainRepositry
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.FlowableSubscriber
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Subscription

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {

    lateinit var binding:ActivityHistoryBinding

    val myViewmodel: MyViewModel by viewModels()

    var adapter: MyAdapter?=null

    var layoutManager:LinearLayoutManager?=null
    var list = mutableListOf<EntityClass>()

    private var stateee:Parcelable?=null
    var disposable:Disposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        supportActionBar?.hide()


        if(savedInstanceState!=null)
        {
            stateee = savedInstanceState.getParcelable("STATE")
        }
        val anim =AnimationUtils.loadAnimation(this,R.anim.appear_anim)
        binding?.deleteButton?.animation = anim

         list = mutableListOf()

        binding.backbutton.setOnClickListener {
            finish()
        }
        adapter = MyAdapter(this@HistoryActivity,myViewmodel)
        initRecyclerView()
       // getData()
    }

    private fun getData(){

     myViewmodel.listobservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:io.reactivex.rxjava3.core.Observer<ArrayList<EntityClass>>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }
                override fun onNext(it: ArrayList<EntityClass>) {
                  Toast.makeText(this@HistoryActivity,"onnextCalled",Toast.LENGTH_SHORT).show()
                    adapter?.setDate(it)
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
                LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(Intent(Intent_KEYS.INTENT_ADAPTER))
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

    }

    override fun onResume() {
        super.onResume()
        getData()

    }



}