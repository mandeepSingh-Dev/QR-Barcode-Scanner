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


    ): Parcelable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id:Int=0

    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList()!!,
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(calenderList)
        parcel.writeLong(currentTime)
        parcel.writeString(type_value)
        parcel.writeString(rawValue)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntityClass> {
        override fun createFromParcel(parcel: Parcel): EntityClass {
            return EntityClass(parcel)
        }

        override fun newArray(size: Int): Array<EntityClass?> {
            return arrayOfNulls(size)
        }
    }
}