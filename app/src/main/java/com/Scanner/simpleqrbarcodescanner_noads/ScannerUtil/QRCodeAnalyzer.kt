package com.example.simpleqrbarcodescanner_noads

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Bundle
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.Scanner.simpleqrbarcodescanner_noads.ScannerUtil.QRCodeFoundListener
import com.Scanner.simpleqrbarcodescanner_noads.ScannerUtil.ScannerBarcode
import com.Scanner.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.io.ByteArrayOutputStream


class QRCodeImageAnalyzer(private val listener: QRCodeFoundListener) : ImageAnalysis.Analyzer
{
    var inputImage:InputImage?=null
    var rawValue:String?=null
    var format:Int?=null
    var valueType:Int?=null
    var barcode:Barcode?=null
    lateinit var bundle:Bundle
    lateinit var calArrayList:ArrayList<String>
    lateinit var contactArrayList:ArrayList<String>

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy)
    {

        bundle = Bundle()
        ScannerBarcode(listener).scanBarcode1(image)

        /* val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC)
            .build()*/
       /* val mediaImage = image.image
        if (mediaImage != null) {
            inputImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

            val scanner = BarcodeScanning.getClient()

            scanner.process(inputImage!!).addOnSuccessListener { barcodes ->

                barcodes.firstOrNull().let { barcode ->
                            rawValue = barcode?.rawValue
                            format = barcode?.format
                            valueType = barcode?.valueType

                            barcode?.let { listener.onBarcode(it) }
                            this.barcode = barcode
                    }
                    rawValue?.let {
                        listener.onQRCodeFound(it)
                        listener.onQRFormat(it,format,valueType,setBundle(valueType,barcode))


                        *//* when(valueType){
                             Barcode.TYPE_WIFI->{

                             }
                             else->{Log.d("fkghyhdfgfv","fidhfd")}
                         }*//*
                    }
                    image.close()
               // provider.unbindAll()
                }.addOnFailureListener {
                    Log.d("dfgdfgdfdfderg",it.cause.toString())
                    listener.onqrCodeNotFound()
                    image.close()
                }.addOnCompleteListener {
                    Log.d("fidhfd","bhsdj")
            }.addOnCanceledListener {
                Log.d("fidhfd","bhsdj")

            }

        }*/

    }

    fun getBitmap(image:ImageProxy){
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = uBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)
        //U and V are swapped
        yBuffer[nv21, 0, ySize]
        vBuffer[nv21, ySize, vSize]
        uBuffer[nv21, ySize + vSize, uSize]

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 75, out)

        val byteArray = out.toByteArray()
        var bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }

    fun setBundle(valueType:Int?,barcode: Barcode?):Bundle{

        var bundle=Bundle()
        calArrayList = ArrayList()
        when(valueType){
            Barcode.TYPE_URL -> {
                bundle.putString(Intent_KEYS.URL,barcode?.url?.url)
                calArrayList.add(barcode?.url?.url.toString())

                calArrayList?.let { bundle?.putStringArrayList(Intent_KEYS.URL_LIST, calArrayList) }
            }
            Barcode.TYPE_WIFI -> {

                bundle.putString(Intent_KEYS.WIFINAME,barcode?.wifi?.ssid.toString())
                bundle.putString(Intent_KEYS.PASSWORD,barcode?.wifi?.password.toString())
                barcode?.wifi?.encryptionType?.let {
                    bundle.putInt(Intent_KEYS.ENCRYPTION_VALUE, it)
                }
                calArrayList.add(barcode?.wifi?.ssid.toString())
                calArrayList.add(barcode?.wifi?.password.toString())
                calArrayList.add(barcode?.wifi?.encryptionType.toString())

                calArrayList?.let { bundle?.putStringArrayList(Intent_KEYS.WIFI_LIST, calArrayList) }
            }
            Barcode.TYPE_EMAIL -> {
                bundle.putString(Intent_KEYS.EMAIL_ADDRESS,barcode?.email?.address)
                bundle.putString(Intent_KEYS.EMAIL_SUBJECT,barcode?.email?.subject)
                bundle.putString(Intent_KEYS.EMAIL_BODY,barcode?.email?.body)

                calArrayList.add(barcode?.email?.address.toString())
                calArrayList.add(barcode?.email?.subject.toString())
                calArrayList.add(barcode?.email?.body.toString())

                calArrayList?.let { bundle?.putStringArrayList(Intent_KEYS.EMAIL_LIST, calArrayList) }


            }
            Barcode.TYPE_SMS  ->{
                bundle.putString(Intent_KEYS.SMS_PHONE,barcode?.sms?.phoneNumber)
                bundle.putString(Intent_KEYS.MESSAGE,barcode?.sms?.message)

                calArrayList.add(barcode?.sms?.phoneNumber.toString())
                calArrayList.add(barcode?.sms?.message.toString())

                calArrayList?.let { bundle?.putStringArrayList(Intent_KEYS.SMS_LIST, calArrayList) }
            }
            Barcode.TYPE_CALENDAR_EVENT ->{
                calArrayList.add(barcode?.calendarEvent?.summary.toString())
                calArrayList.add(barcode?.calendarEvent?.description.toString())
                calArrayList.add(barcode?.calendarEvent?.location.toString())

                calArrayList.add(barcode?.calendarEvent?.start?.day.toString())
                calArrayList.add(barcode?.calendarEvent?.start?.month.toString())
                calArrayList.add(barcode?.calendarEvent?.start?.year.toString())
                calArrayList.add(barcode?.calendarEvent?.start?.hours.toString())
                calArrayList.add(barcode?.calendarEvent?.start?.minutes.toString())
                calArrayList.add(barcode?.calendarEvent?.start?.seconds.toString())

                calArrayList.add(barcode?.calendarEvent?.end?.day.toString())
                calArrayList.add(barcode?.calendarEvent?.end?.month.toString())
                calArrayList.add(barcode?.calendarEvent?.end?.year.toString())
                calArrayList.add(barcode?.calendarEvent?.end?.hours.toString())
                calArrayList.add(barcode?.calendarEvent?.end?.minutes.toString())
                calArrayList.add(barcode?.calendarEvent?.end?.seconds.toString())

                calArrayList.let { bundle.putStringArrayList(Intent_KEYS.CAL_ARRAYLIST, calArrayList) }
            }
            Barcode.TYPE_CONTACT_INFO ->{
               try {
                   calArrayList.add(barcode?.contactInfo?.phones?.get(0)?.number.toString())
                   calArrayList.add(barcode?.contactInfo?.addresses?.get(0)?.addressLines?.get(0).toString())
                   calArrayList.add(barcode?.contactInfo?.emails?.get(0)?.address.toString())
                   var dfhdj4 = ""
                   barcode?.contactInfo?.organization?.forEach { dfhdj4 += it.toString() }
                   calArrayList.add(dfhdj4)
                   calArrayList.add(barcode?.contactInfo?.name?.formattedName.toString())
                   calArrayList.add(barcode?.contactInfo?.title.toString())
                   calArrayList.add(barcode?.contactInfo?.urls?.get(0).toString())
                   calArrayList.add(barcode?.contactInfo?.phones?.get(0)?.type.toString())

                   calArrayList.let { bundle.putStringArrayList(Intent_KEYS.CONTACTS_ARRAYLIST, it) }
               }catch (e:Exception){}

            }
            //doesnot suppported
            Barcode.TYPE_DRIVER_LICENSE ->{
              /*  bundle.putString(Intent_KEYS.LICENCE_NUMBER,barcode?.driverLicense?.licenseNumber)
                bundle.putString(Intent_KEYS.LICENCE_FIRSTNAME,barcode?.driverLicense?.firstName)
                bundle.putString(Intent_KEYS.LICENCE_MIDDLENAME,barcode?.driverLicense?.middleName)
                bundle.putString(Intent_KEYS.LICENCE_LASTNAME,barcode?.driverLicense?.lastName)
                bundle.putString(Intent_KEYS.LICENCE_DOB,barcode?.driverLicense?.birthDate)
                bundle.putString(Intent_KEYS.LICENCE_EXPDATE,barcode?.driverLicense?.expiryDate)
                bundle.putString(Intent_KEYS.LICENCE_ISSUEDATE,barcode?.driverLicense?.issueDate)
                bundle.putString(Intent_KEYS.LICENCE_DOCTYPE,barcode?.driverLicense?.documentType)
                bundle.putString(Intent_KEYS.LICENCE_GENDER,barcode?.driverLicense?.gender)
                bundle.putString(Intent_KEYS.LICENCE_ISSUCOUNTRY,barcode?.driverLicense?.issuingCountry)
                bundle.putString(Intent_KEYS.LICENCE_ADSTREET,barcode?.driverLicense?.addressStreet)
                bundle.putString(Intent_KEYS.LICENCE_ADSTATE,barcode?.driverLicense?.addressState)
                bundle.putString(Intent_KEYS.LICENCE_ADCITY,barcode?.driverLicense?.addressCity)
                bundle.putString(Intent_KEYS.LICENCE_ADZIP,barcode?.driverLicense?.addressZip)
     */       }
            else-> bundle.putString(Intent_KEYS.VOID,"VOID")
       }
        return bundle
    }

}