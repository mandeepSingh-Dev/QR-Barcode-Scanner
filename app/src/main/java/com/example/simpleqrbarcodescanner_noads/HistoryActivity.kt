package com.example.simpleqrbarcodescanner_noads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.example.simpleqrbarcodescanner_noads.room.MainRepositry
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HistoryActivity : AppCompatActivity() {

    @Inject
    lateinit var mainRepositry:MainRepositry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
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
               list.forEach {
                   binding?.textTexting?.text =
                       it.rawValue }
           }
   }
}