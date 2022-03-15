package com.example.simpleqrbarcodescanner_noads.Util

object Intent_KEYS {
    const val QRCODE = "qrCode"
    const val FORMAT = "format"
    const val VALUETYPE = "valuetype"
    const val BUNDLE = "Bundle"

    //for URL BUNDLE
    val URL = "URL"
    //for WIFI BUNDLE
    val WIFINAME = "WIFINAME"
    val PASSWORD = "PASSWORD"
    val ENCRYPTION_VALUE = "ENCRYPTION_TYPE"
    //for Email BUNDLE
    val EMAIL_ADDRESS = "EMAIL_ADDRESS"
    val EMAIL_SUBJECT = "EMAIL_SUBJECT"
    val EMAIL_BODY = "EMAIL_BODY"
    //for sms BUNDLE
    val SMS_PHONE = "SMS_PHONE"
    val MESSAGE = "MESSAGE"
    //for calender BUNDLE
    val CAL_TITLE = "title"
    val CAL_DESCRIPTION= "description"
    val CAL_LOCATION_CAL = "location"
    val START_DAY = "startday"
    val START_MONTH = "startmonth"
    val START_YEAR = "startyear"
    val START_HOUR = "starthour"
    val START_MINUTES = "startminute"
    val START_SECONDS = "startsecond"

    val END_DAY = "endday"
    val END_MONTH = "endmonth"
    val END_YEAR = "endyear"
    val END_HOUR = "endhour"
    val END_MINUTES = "endminute"
    val END_SECONDS = "endsecond"


    val CAL_END = "end"

    //for licence BUNDLE
    val LICENCE_NUMBER = "LICENCE_NUMBER"
    val LICENCE_FIRSTNAME = "LICENCE_FIRSTNAME"
    val LICENCE_MIDDLENAME = "LICENCE_MIDDLENAME"
    val LICENCE_LASTNAME = "LICENCE_LASTNAME"
    val LICENCE_DOB = "LICENCE_DOB"
    val LICENCE_EXPDATE = "LICENCE_EXPDATE"
    val LICENCE_ISSUEDATE = "LICENCE_ISSUEDATE"
    val LICENCE_DOCTYPE = "LICENCE_DOCTYPE"
    val LICENCE_GENDER = "LICENCE_GENDER"
    val LICENCE_ISSUCOUNTRY = "LICENCE_ISSUCOUNTRY"
    val LICENCE_ADSTREET = "LICENCE_ADSTREET"
    val LICENCE_ADSTATE = "LICENCE_ADSTATE"
    val LICENCE_ADCITY = "LICENCE_ADCITY"
    val LICENCE_ADZIP = "LICENCE_ADZIP"
    //for VOID BUNDLE
    val VOID = "VOID"
}