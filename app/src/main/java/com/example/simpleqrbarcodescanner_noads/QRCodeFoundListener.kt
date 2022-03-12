package com.example.simpleqrbarcodescanner_noads

import android.graphics.Bitmap
import android.os.Bundle
import com.google.mlkit.vision.barcode.common.Barcode

interface QRCodeFoundListener {
    fun onQRCodeFound(qrCode: String?)
    fun onqrCodeNotFound()
    fun onQRFormat(qrCode:String?,format:Int?,valueType:Int?,bundle:Bundle?)


}