package com.example.simpleqrbarcodescanner_noads.MVVM

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

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