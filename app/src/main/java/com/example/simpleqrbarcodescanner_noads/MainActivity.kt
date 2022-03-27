package com.example.simpleqrbarcodescanner_noads

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.simpleqrbarcodescanner_noads.ScannerUtil.QRCodeFoundListener
import com.example.simpleqrbarcodescanner_noads.ScannerUtil.ScannerBarcode
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityMainBinding
import com.google.android.gms.ads.MobileAds
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.common.Barcode
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private val PERMISSION_REQUEST_CAMERA = 1

    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
    var cameraProvider:ProcessCameraProvider?=null
  //  var imageAnalysis:ImageAnalysis?=null
    private var binding:ActivityMainBinding?=null

    private var qrCode: String? = null
    lateinit var camera:Camera
    var isFlashGlow = false

    val vibrator:Vibrator by lazy {
         getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    val mediaplayer:MediaPlayer by lazy {
        MediaPlayer.create(this@MainActivity,R.raw.beepsound)
    }
    val sharedPreferences:SharedPreferences by lazy {
        getSharedPreferences(Intent_KEYS.SETTINGS_SHAREDPREFERNCE,Context.MODE_PRIVATE)
    }
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)

        MobileAds.initialize(this) {}


        supportActionBar?.hide()
        binding?.scanImage?.setOnClickListener {
            Toast.makeText(this,"fgdf",Toast.LENGTH_SHORT).show()
            launcher.launch("image/*")
        }


      /*  mainRepositry.getList().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.forEach {
                    Log.d("'fgidhfgd",it.rawValue)
                    Toast.makeText(this,it.rawValue,Toast.LENGTH_SHORT).show()
                    binding?.textTexting?.text = it.rawValue
                }
            }*/
      //  binding?.scanImage?.setOnClickListener {
        /*    CoroutineScope(Dispatchers.IO).launch {
                mainRepositry.insert(
                    EntityClass(
                        ArrayList<String>(),
                        System.currentTimeMillis(),
                        "hds${count++}",
                        "isdgggggfhs${count++}"
                               )
                )
            }*/
         //   }
          /*  .subscribe(object:io.reactivex.rxjava3.core.SingleObserver<List<EntityClass>>{
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}

                override fun onSuccess(t: List<EntityClass>) {
                }
            })*/




      //  CoroutineScope(Dispatchers.IO).launch {
         // MyRoomDatabase.getInstance(this@MainActivity).getDao().query().get(0).calenderList.forEach {
              // Log.d("f777udjf",it.toString())
          // }
      //  }
      /*  binding?.button?.setOnClickListener {
          *//*  val intent = Intent(applicationContext,MainActivity2::class.java)
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())*//*
        }*/


        //blink()
      //  binding?.button?.animation  = AnimationUtils.loadAnimation(this,R.anim.anim)



       ObjectAnimator.ofFloat(binding?.movingView,"translationY",binding?.viewBox?.layoutParams?.height?.toFloat()?.minus(20f)!!).apply {
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
         //   reverse()
            duration = 800
            start()
        }
       // ObjectAnimator.
       /* var animatorr= ObjectAnimator()
        animatorr.duration =1000
        animatorr.repeatCount == ValueAnimator.INFINITE*/


/*var bitmap = BarcodeEncoder().encodeBitmap("9788192107554", BarcodeFormat.AZTEC,200,200)

binding?.imageView?.setImageBitmap(bitmap)*/



        //animatorr.target =binding?.button

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        requestCamera()



        /*binding?.button?.setOnClickListener {
            if(qrCode!=null)
            Toast.makeText(this,qrCode+"sd",Toast.LENGTH_LONG).show()
        }*/

        binding?.bottomView?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.create-> startActivity(Intent(this,
                    CreateActivity::class.java)/*,ActivityOptions.makeSceneTransitionAnimation(this).toBundle()*/)
                R.id.history2 -> startActivity(Intent(this, HistoryActivity::class.java))
                R.id.setting -> startActivity(Intent(this, SettingsActivity::class.java))
                else->{
                    Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
                }

            }
            return@setOnItemSelectedListener true
        }

    }//end of onCreate()
    private fun requestCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
            }
        }
    }//end of requestCamera

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }//end of onRequetPermiss...

    private fun startCamera()
    {
        cameraProviderFuture!!.addListener({
            try {
                 cameraProvider = cameraProviderFuture!!.get()
                cameraProvider?.let { bindCameraPreview(it) }

            } catch (e: Exception) {
              //  Toast.makeText(this, "Error starting camera " + e.message, Toast.LENGTH_SHORT).show()
            } catch (e: InterruptedException) {
               // Toast.makeText(this, "Error starting camera " + e.message, Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))

    }

    fun bindCameraPreview(cameraProvider: ProcessCameraProvider)
    {
      //  binding?.activityMainPreviewView?.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        binding?.activityMainPreviewView?.implementationMode = PreviewView.ImplementationMode.PERFORMANCE

        val preview = Preview.Builder().build()

        val cameraSelector = CameraSelector.Builder()
          .requireLensFacing(CameraSelector.LENS_FACING_BACK)
          .build()

        preview.setSurfaceProvider(binding?.activityMainPreviewView?.surfaceProvider)


        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280,720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()


        imageAnalysis?.setAnalyzer(ContextCompat.getMainExecutor(this),QRCodeImageAnalyzer(object :
            QRCodeFoundListener {
                override fun onQRCodeFound(qrCode: String?) {
                    Log.d("djfnd", qrCode + "d")
                   // binding?.button?.text = qrCode
                    this@MainActivity.qrCode = qrCode
                }
                override fun onqrCodeNotFound() {}
                override fun onQRFormat(qrCode: String?, format: Int?, valueType: Int?, bundle:Bundle?) {
                    if(sharedPreferences?.getString(Intent_KEYS.ISBEEP,"false").equals("true"))
                    { mediaplayer.start() }
                    if(sharedPreferences?.getString(Intent_KEYS.ISVIBRATE,"false").equals("true"))
                    { vibrate()}

                    val intent = Intent(applicationContext, MainActivity2::class.java)
                    intent.putExtra(Intent_KEYS.QRCODE, qrCode)
                    intent.putExtra(Intent_KEYS.FORMAT, format)
                    intent.putExtra(Intent_KEYS.VALUETYPE, valueType)
                    intent.putExtra(Intent_KEYS.BUNDLE,bundle)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle())

                   // var effectbivrate=VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                   // Vibrator().vibrate(effectbivrate)
                    //here we r doing clearAnalyzer and unBindAll because camera ImageAnalyzer taking
                    //images continuosuly and if we dont use  clearAnalyzer and Unbind
                    //then qrcode scans mulitple code and open activity multiple times
                    //thats why i need to use clearAnalysis and unBindAll for only scan one barcode at
                    // a time--->

                    imageAnalysis?.clearAnalyzer()
                    cameraProvider.unbindAll()
                    //then we need to start the Camera again when we come back to camera activity.
                    //requestCamera()

                    //  qrGenerate(qrCode,format)
                }
                override fun onBarcode(barcode: Barcode) {}
            })
        )
        camera = cameraProvider.bindToLifecycle(this,cameraSelector,imageAnalysis,preview)

        /**-------Configuring Flash---------------------------------*/
        if(camera.cameraInfo.hasFlashUnit()){
               binding?.flashButton?.visibility = View.VISIBLE
               binding?.flashButton?.setOnClickListener {
                   camera.cameraControl.enableTorch(!isFlashGlow)
               }
           }
         camera.cameraInfo.torchState.observe(this, androidx.lifecycle.Observer {
             isFlashGlow = when (it) {
                 TorchState.ON -> true
                 else -> false
             }
         })
        /**-------Configuring Flash---------------------------------*/

      //  camera.cameraControl.enableTorch(true)

    }
    fun vibrate()
    {
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(500)
        }

    }
    override fun onResume() {
        super.onResume()
        Log.d("dfsdfsdfs","onResume")
        requestCamera()
    }



    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent(),ActivityResultCallback{

        if(it!=null) {
            val inputStream = contentResolver?.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            var scanner = ScannerBarcode(object : QRCodeFoundListener {
                override fun onQRCodeFound(qrCode: String?) {
                    Log.d("djfnd", qrCode + "d")
                    // binding?.button?.text = qrCode

                    this@MainActivity.qrCode = qrCode
                }

                override fun onqrCodeNotFound() {}
                override fun onQRFormat(qrCode: String?, format: Int?, valueType: Int?, bundle: Bundle?) {
                    if(sharedPreferences?.getString(Intent_KEYS.ISBEEP,"false").equals("true"))
                    { mediaplayer.start() }
                    if(sharedPreferences?.getString(Intent_KEYS.ISVIBRATE,"false").equals("true"))
                    { vibrate()}

                    val intent = Intent(applicationContext, MainActivity2::class.java)
                    intent.putExtra(Intent_KEYS.QRCODE, qrCode)
                    intent.putExtra(Intent_KEYS.FORMAT, format)
                    intent.putExtra(Intent_KEYS.VALUETYPE, valueType)
                    intent.putExtra(Intent_KEYS.BUNDLE, bundle)
                    startActivity(
                        intent,
                        ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle()
                    )

                    // var effectbivrate=VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                    // Vibrator().vibrate(effectbivrate)
                    //here we r doing clearAnalyzer and unBindAll because camera ImageAnalyzer taking
                    //images continuosuly and if we dont use  clearAnalyzer and Unbind
                    //then qrcode scans mulitple code and open activity multiple times
                    //thats why i need to use clearAnalysis and unBindAll for only scan one barcode at
                    // a time--->
                    // imageAnalysis?.clearAnalyzer()
                    // cameraProvider.unbindAll()
                    //then we need to start the Camera again when we come back to camera activity.
                    //requestCamera()

                    //  qrGenerate(qrCode,format)
                }

                override fun onBarcode(barcode: Barcode) {}
            })
            scanner.scanBarcode2(bitmap)
        }
    })

    override fun onStop() {
        super.onStop()
        cameraProvider?.unbindAll()
    }
    override fun onDestroy() {
        super.onDestroy()
        cameraProvider?.unbindAll()


        if(mediaplayer!=null) mediaplayer.release()

    }

    override fun onPause() {
        super.onPause()
        Log.d("dkfjdfn","onPause called")
    }
}
