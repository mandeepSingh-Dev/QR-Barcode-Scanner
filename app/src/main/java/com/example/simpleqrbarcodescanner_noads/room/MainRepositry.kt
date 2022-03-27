package com.example.simpleqrbarcodescanner_noads.room

import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MainRepositry @Inject constructor(val daoClass:DaoClass)
{
    fun getList():Observable<ArrayList<EntityClass>> = daoClass.query() as Observable<ArrayList<EntityClass>>

    suspend fun insert(entityClass: EntityClass)
    {
        daoClass.insert(entityClass)
    }
    suspend fun delete(entityClass: EntityClass)
    {
         daoClass.delete(entityClass)
    }
    suspend fun getItembyId(id:Int):EntityClass
    {
        return daoClass.findItembyId(id)
    }
}