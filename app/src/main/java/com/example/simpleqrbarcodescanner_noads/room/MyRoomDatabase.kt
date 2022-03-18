package com.example.simpleqrbarcodescanner_noads.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(entities = arrayOf(EntityClass::class), version = 4)
@TypeConverters(Converterss::class)
abstract class MyRoomDatabase: RoomDatabase()
{
    companion object {
        private val databaseName = "BarcodeRoomDatabase"
         var instance: MyRoomDatabase?=null

        fun getInstance(context: Context): MyRoomDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, MyRoomDatabase::class.java, databaseName)
                        .fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }

    abstract fun getDao():DaoClass
}