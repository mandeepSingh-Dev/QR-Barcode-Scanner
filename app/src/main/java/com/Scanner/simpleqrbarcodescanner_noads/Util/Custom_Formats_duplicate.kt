package com.Scanner.simpleqrbarcodescanner_noads.Util


//this class is created for making duplicate formats of barcode.formats (com.google.MLKIT) but
//with name, that should be equal to BarcodeFormats enum constants
//because to create barcode we r using com.journeyApps.Barco... bla bla
// for its barcodeformats our googleMLKIT formats constants name should be match
// with journeyApps.Barc... formats in IF ELSE BLOCKS so thats why we take name from journeyApps enum class and took
//value from mlkit formats class.
object Custom_Formats_duplicate {
/*    const val  FORMAT_UNKNOWN = -1;
    const val FORMAT_ALL_FORMATS = 0;*/
    const val CODE_128 = 1;
    const val CODE_39 = 2;
    const val CODE_93 = 4;
    const val CODABAR = 8;
    const val  DATA_MATRIX = 16;
    const val  EAN_13 = 32;
    const val  EAN_8 = 64;
    const val  ITF = 128;
    const val  QR_CODE = 256;
    const val  UPC_A = 512;
    const val  UPC_E = 1024;
    const val  PDF_417 = 2048;
    const val  AZTEC = 4096;
/*    const val  TYPE_UNKNOWN = 0;
    const val  TYPE_CONTACT_INFO = 1;
    const val  TYPE_EMAIL = 2;
    const val TYPE_ISBN = 3;
    const val  TYPE_PHONE = 4;
    const val  TYPE_PRODUCT = 5;
    const val  TYPE_SMS = 6;
    const val  TYPE_TEXT = 7;
    const val TYPE_URL = 8;
    const val TYPE_WIFI = 9;
    const val TYPE_GEO = 10;
    const val TYPE_CALENDAR_EVENT = 11;
    const val TYPE_DRIVER_LICENSE = 12;*/
}