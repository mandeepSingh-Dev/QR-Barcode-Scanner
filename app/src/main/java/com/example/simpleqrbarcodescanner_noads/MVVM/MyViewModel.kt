package com.example.simpleqrbarcodescanner_noads.MVVM

import androidx.lifecycle.ViewModel
import com.example.simpleqrbarcodescanner_noads.room.EntityClass
import com.example.simpleqrbarcodescanner_noads.room.MainRepositry
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(val mainRepositry: MainRepositry): ViewModel()
{
    var listobservable:Flowable<List<EntityClass>>
    init {
        listobservable = mainRepositry.getList()
    }
    suspend fun insert(entityClass: EntityClass){
        mainRepositry.insert(entityClass)
    }
}