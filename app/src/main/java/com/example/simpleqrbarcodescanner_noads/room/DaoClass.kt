package com.example.simpleqrbarcodescanner_noads.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable


@Dao
interface  DaoClass
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entityClass: EntityClass)

    @Query("SELECT * FROM ENTITYCLASS")
    fun query(): Flowable<List<EntityClass>>

    @Delete
    suspend fun delete(entityClass: EntityClass)

}