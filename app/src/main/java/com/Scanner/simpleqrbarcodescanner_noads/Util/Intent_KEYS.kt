package com.Scanner.simpleqrbarcodescanner_noads.Util

object Intent_KEYS {
    const val QRCODE = "qrCode"
    const val FORMAT = "format"
    const val VALUETYPE = "valuetype"
    const val VALUETYPE_QRGENERATOR = "valuetype"
  const val BUNDLE = "Bundle"
    const val FROM_HISTORY =  "fromHistory"

    //for URL BUNDLE
  const  val URL = "URL"
    //for WIFI BUNDLE
  const val WIFINAME = "WIFINAME"
  const  val PASSWORD = "PASSWORD"
  const  val ENCRYPTION_VALUE = "ENCRYPTION_TYPE"
    //for Email BUNDLE
    const  val EMAIL_ADDRESS = "EMAIL_ADDRESS"
    const val EMAIL_SUBJECT = "EMAIL_SUBJECT"
    const  val EMAIL_BODY = "EMAIL_BODY"
    //for sms BUNDLE
    const  val SMS_PHONE = "SMS_PHONE"
    const  val MESSAGE = "MESSAGE"
    //for calender BUNDLE
    const val CAL_ARRAYLIST  = "calender_Arraylist"
    const val CONTACTS_ARRAYLIST  = "contacts_Arraylist"
    const  val URL_LIST = "url_list"
    const val WIFI_LIST = "wifi_list"
    const val EMAIL_LIST = "email_list"
    const val SMS_LIST = "sms_list"

  const val SETTINGS_SHAREDPREFERNCE = "SETTINGS_SHAREDPREFERNCE"
  const val ISAUTOMATICLAYOPEN = "Is_Automatically_Open"
  const val ISAUTOMATICLAYCOPY = "Is_Automatically_Close"
  const val ISBEEP = "Is_Beep"
  const val ISVIBRATE = "Is_Vibrate"


  //for localbroadcast between Historyactivity and adapter
    const  val INTENT_ADAPTER = "IntentAdapter"



    /* val CAL_TITLE = "title"
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


     val CAL_END = "end"*/

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