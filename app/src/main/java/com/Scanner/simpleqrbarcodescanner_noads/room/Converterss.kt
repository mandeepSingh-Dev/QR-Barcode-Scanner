package com.Scanner.simpleqrbarcodescanner_noads.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converterss
{

    @TypeConverter
    fun fromList(djfd:List<String>):String = Gson().toJson(djfd)
    @TypeConverter
    fun toList(value:String):List<String>
    {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value,listType)
    }

@TypeConverter
fun fromlong(date:Long):String = date.toString()
@TypeConverter
fun toLong(value:String):Long
    {
        return value.toLong()
    }
}