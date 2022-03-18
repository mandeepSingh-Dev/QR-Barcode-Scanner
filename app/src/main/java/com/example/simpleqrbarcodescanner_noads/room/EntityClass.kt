package com.example.simpleqrbarcodescanner_noads.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.mlkit.vision.barcode.common.Barcode

@Entity
class EntityClass(

    @ColumnInfo(name= "calenderArraylist")
    var calenderList:List<String>,

    @ColumnInfo(name = "Date")
    var currentTime:Long,

    @ColumnInfo(name = "TYPE_VALUE")
    var type_value:String,

    @ColumnInfo(name = "RawValue")
    var rawValue:String
    /*  //fields for TYPE_CALENDER_EVENT
    @ColumnInfo(name = "summary")
    var summary:String,
    @ColumnInfo(name = "description")
    var description:String,
    @ColumnInfo(name = "location")
    var location:String,
    @ColumnInfo(name = "startday")
    var startday:String,
    @ColumnInfo(name = "startmonth")
    var startmonth:String,
    @ColumnInfo(name = "startyear")
    var startyear:String,
    @ColumnInfo(name = "starthours")
    var starthours:String,
    @ColumnInfo(name = "startminute")
    var startminute:String,
    @ColumnInfo(name = "startsecond")
    var startsecond:String,
    @ColumnInfo(name = "endday")
    var endday:String,
    @ColumnInfo(name = "endmonth")
    var endmonth:String,
    @ColumnInfo(name = "endyear")
    var endyear:String,
    @ColumnInfo(name = "endhours")
    var endhours:String,
    @ColumnInfo(name = "endminutes")
    var endminutes:String,
    @ColumnInfo(name = "endseconds")
    var endseconds:String,*/


    )
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id:Int=0
}