package com.example.simpleqrbarcodescanner_noads.room

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MainRepositry @Inject constructor(val daoClass:DaoClass)
{
    fun getList():Flowable<List<EntityClass>> = daoClass.query()

    suspend fun insert(entityClass: EntityClass)
    {
        daoClass.insert(entityClass)
    }
}