package com.example.simpleqrbarcodescanner_noads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityHistoryBinding
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.example.simpleqrbarcodescanner_noads.room.MainRepositry
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {

    lateinit var binding:ActivityHistoryBinding
    @Inject
    lateinit var mainRepositry:MainRepositry

    var adapter:MyAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        getData()
    }

   fun getData(){
       var list = mutableListOf<EntityClass>()
       mainRepositry.getList()
           .subscribeOn(Schedulers.io())
           .observeOn(Schedulers.computation())
           .map {
               list = it as MutableList<EntityClass>
           }
           .observeOn(AndroidSchedulers.mainThread())
           .doOnError {
               Log.d("dkfndk",it.message.toString())
           }.subscribe {

               adapter = MyAdapter(this,list.toList())
               initRecyclerView()
              /* list.forEach {
                   binding?.textTexting?.text =
                       it.rawValue }*/
           }
   }

    fun initRecyclerView(){
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
       binding.historyRecyclerView.adapter = adapter
    }
}