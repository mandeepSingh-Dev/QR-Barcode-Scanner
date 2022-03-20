package com.example.simpleqrbarcodescanner_noads

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleqrbarcodescanner_noads.MVVM.MyAdapter
import com.example.simpleqrbarcodescanner_noads.MVVM.MyViewModel
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityHistoryBinding
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.example.simpleqrbarcodescanner_noads.room.MainRepositry
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {

    var isSelectable = false
    lateinit var binding:ActivityHistoryBinding

    val myViewmodel: MyViewModel by viewModels()

    var adapter: MyAdapter?=null

    var layoutManager:LinearLayoutManager?=null
    var list = mutableListOf<EntityClass>()

    private var stateee:Parcelable?=null

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
          getData()
    }

    private fun getData(){
       Log.d("kdkfndknf","fdfkjd")

      // mainRepositry.getList()
       myViewmodel.listobservable.subscribeOn(Schedulers.io())
           .observeOn(Schedulers.computation())
           .map {
               list = it as MutableList<EntityClass>
           }
           .observeOn(AndroidSchedulers.mainThread())
           .doOnError {
               Log.d("dkfndk",it.message.toString())
           }
           .subscribe {
               adapter = MyAdapter(this,list.toList())
               initRecyclerView()
              /* list.forEach {
                   binding?.textTexting?.text =
                       it.rawValue }*/
           }
   }

    private fun initRecyclerView(){
       layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.layoutManager = layoutManager
       binding.historyRecyclerView.adapter = adapter

        if(stateee!=null) {
            binding.historyRecyclerView.layoutManager?.onRestoreInstanceState(stateee)
        }
      /*  adapter?.setCustomClickListenr(object:MyAdapter.CustomClickListener{
            override fun customOnClick(item: EntityClass, position: Int) {
               *//* CoroutineScope(Dispatchers.IO).launch {
                  myViewmodel.delete(list[position])
                    withContext(Dispatchers.Main) {
                        list.remove(list[position])
                        adapter?.notifyDataSetChanged()
                        adapter?.notifyItemRemoved(position)
                    }
                }*//*
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
}