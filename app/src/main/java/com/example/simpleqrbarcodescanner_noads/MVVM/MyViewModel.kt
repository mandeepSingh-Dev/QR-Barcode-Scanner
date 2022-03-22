package com.example.simpleqrbarcodescanner_noads.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.example.simpleqrbarcodescanner_noads.room.MainRepositry
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(val mainRepositry: MainRepositry): ViewModel()
{
    var listobservable:Observable<ArrayList<EntityClass>>
    init {
        listobservable = mainRepositry.getList()
    }
     fun insert(entityClass: EntityClass){
         viewModelScope.launch {
             mainRepositry.insert(entityClass)
         }
    }

    suspend fun delete(entityClass: EntityClass)
    {
            mainRepositry.delete(entityClass)
    }

    suspend fun getItembyId(id:Int):EntityClass
    {
        return mainRepositry.getItembyId(id)
    }
}