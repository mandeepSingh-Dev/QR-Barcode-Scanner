package com.example.simpleqrbarcodescanner_noads

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.ImageFormat.*
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.google.zxing.*
import java.io.ByteArrayOutputStream


class QRCodeImageAnalyzer(private val listener: QRCodeFoundListener) : ImageAnalysis.Analyzer
{
    var inputImage:InputImage?=null
    var rawValue:String?=null
    var format:Int?=null
    var valueType:Int?=null
    var barcode:Barcode?=null
    lateinit var bundle:Bundle
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy)
    {

        bundle = Bundle()

        /* val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC)
            .build()*/
        val mediaImage = image.image
        if (mediaImage != null) {
            inputImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

            val scanner = BarcodeScanning.getClient()

            scanner.process(inputImage!!).addOnSuccessListener { barcodes ->
                Log.d("dgfnd","fg")

                barcodes.firstOrNull().let { barcode ->
                            rawValue = barcode?.rawValue
                            format = barcode?.format
                            valueType = barcode?.valueType
                            this.barcode = barcode
                    Log.d("fknf",valueType.toString())
                    }
                    rawValue?.let {
                        listener.onQRCodeFound(it)
                        listener.onQRFormat(it,format,valueType,setBundle(valueType,barcode))
                        Log.d("dfkndf",barcode?.boundingBox?.top.toString())
                        Log.d("dfkndf",barcode?.boundingBox?.bottom.toString())
                        Log.d("dfkndf",barcode?.boundingBox?.left.toString())
                        Log.d("dfkndf",barcode?.boundingBox?.right.toString())
                        Log.d("dfkndf",barcode?.boundingBox?.width().toString())
                        Log.d("dfkndf",barcode?.boundingBox?.height().toString())
                        Log.d("dfkndf",barcode?.cornerPoints?.get(0)?.x.toString())
                        Log.d("dfkndf",barcode?.cornerPoints?.get(0)?.y.toString())


                        /* when(valueType){
                             Barcode.TYPE_WIFI->{

                             }
                             else->{Log.d("fkghyhdfgfv","fidhfd")}
                         }*/
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

        }


        /*    if (image.format == YUV_420_888 || image.format == YUV_422_888 || image.format == YUV_444_888)
        {
            val byteBuffer = image.planes[0]?.buffer
            val imageData = ByteArray(byteBuffer?.capacity()!!)
            byteBuffer.get(imageData)
            val source = PlanarYUVLuminanceSource(imageData, image.width, image.height, 0, 0, image.width, image.height, false)
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

            try {
                val result = QRCodeMultiReader().decode(binaryBitmap)
                listener.onQRCodeFound(result.getText())
                Log.d("dsdsdsdss",result.text+"s")
            } catch (e: FormatException) {
                Log.d("dflmd",e.message.toString())
                listener.onqrCodeNotFound()
            } catch (e: ChecksumException) {
                Log.d("dflssssmd",e.message.toString())
                listener.onqrCodeNotFound()
            } catch (e: NotFoundException) {
                Log.d("dflffffmd",e.cause.toString())
                listener.onqrCodeNotFound()
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
        when(valueType){
            Barcode.TYPE_URL -> bundle.putString(Intent_KEYS.URL,barcode?.url?.url)
            Barcode.TYPE_WIFI -> {
                bundle.putString(Intent_KEYS.WIFINAME,barcode?.wifi?.ssid.toString())
                bundle.putString(Intent_KEYS.PASSWORD,barcode?.wifi?.password.toString())
                barcode?.wifi?.encryptionType?.let { bundle.putInt(Intent_KEYS.ENCRYPTION_VALUE, it) }
            }
            Barcode.TYPE_EMAIL -> {
                bundle.putString(Intent_KEYS.EMAIL_ADDRESS,barcode?.email?.address)
                bundle.putString(Intent_KEYS.EMAIL_SUBJECT,barcode?.email?.subject)
                bundle.putString(Intent_KEYS.EMAIL_BODY,barcode?.email?.body)
            }
            Barcode.TYPE_SMS  ->{
                bundle.putString(Intent_KEYS.SMS_PHONE,barcode?.sms?.phoneNumber)
                bundle.putString(Intent_KEYS.MESSAGE,barcode?.sms?.message)
            }
            //doesnot suppported
            Barcode.TYPE_DRIVER_LICENSE ->{
                bundle.putString(Intent_KEYS.LICENCE_NUMBER,barcode?.driverLicense?.licenseNumber)
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
            }
            else-> bundle.putString(Intent_KEYS.VOID,"VOID")
       }
        return bundle
    }

}