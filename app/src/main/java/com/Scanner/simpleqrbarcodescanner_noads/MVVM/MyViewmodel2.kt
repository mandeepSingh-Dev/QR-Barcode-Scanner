package com.Scanner.simpleqrbarcodescanner_noads.MVVM

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewmodel2:ViewModel()
{
    var mutableLivedata = MutableLiveData<String>()

    fun setText(text:String)
    {
        mutableLivedata.value = text
    }

    fun getText():MutableLiveData<String>
    {
        return mutableLivedata
    }
}