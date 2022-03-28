package com.Scanner.simpleqrbarcodescanner_noads.room

import androidx.room.*
import io.reactivex.rxjava3.core.Observable


@Dao
interface  DaoClass
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entityClass: EntityClass)

    @Query("SELECT * FROM ENTITYCLASS")
    fun query(): Observable<List<EntityClass>>

    @Delete
    suspend fun delete(entityClass: EntityClass)

    @Query("SELECT * FROM ENTITYCLASS WHERE id LIKE :idd")
    fun findItembyId(idd:Int):EntityClass

}