package com.example.simpleqrbarcodescanner_noads.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class EntityClass(

    @ColumnInfo(name= "calenderArraylist")
    var calenderList:List<String>,

    @ColumnInfo(name = "Date")
    var currentTime:Long,

    @ColumnInfo(name = "TYPE_VALUE")
    var type_value:String,

    @ColumnInfo(name = "RawValue")
    var rawValue:String,

    @ColumnInfo(name = "Format")
    var format:Int,


    //TODO create one more entity for imageDrawable for typeValues

    )
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id:Int=0


}