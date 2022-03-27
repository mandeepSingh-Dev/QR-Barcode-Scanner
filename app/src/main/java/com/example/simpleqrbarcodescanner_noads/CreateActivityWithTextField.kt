package com.example.simpleqrbarcodescanner_noads

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleqrbarcodescanner_noads.Util.Custom_Formats_duplicate
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityCreateWithTextFieldBinding
import com.google.android.gms.ads.AdRequest

class CreateActivityWithTextField : AppCompatActivity() {

    lateinit var binding:ActivityCreateWithTextFieldBinding
    private val unicodeEmoji = 0x2665
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWithTextFieldBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val adRequest = AdRequest.Builder().build()
        binding.adViewTextFieldActivity?.loadAd(adRequest)

          val typevalue = intent.getIntExtra(Intent_KEYS.VALUETYPE,0)
          val format = intent.getIntExtra(Intent_KEYS.FORMAT,0)


        when(format){
            Custom_Formats_duplicate.CODABAR -> {
                binding.textFieldLayout.hint = "Enter codetext: e.g. 0123456789"
                binding.typeValuecreateAct2.text =  "CODABAR"
                binding.instruction1TextView.text = "* Character set: numeric digits (0-9) and special characters $/-:+. \n* Codetext capacity: no restriction"
            }
            Custom_Formats_duplicate.CODE_128 ->{
                binding.textFieldLayout.hint = "Enter codetext: e.g. 0123456789"
                binding.typeValuecreateAct2.text =  "CODE_128"
                binding.instruction1TextView.text = "* Character set: all 128 characters of ASCII \n* Codetext capacity: no specific restrictions"
            }
            Custom_Formats_duplicate.CODE_39 ->{
                binding.textFieldLayout.hint ="Enter codetext: e.g. 0123456789"
                binding.typeValuecreateAct2.text =  "CODE_39"
                binding.instruction1TextView.text = "* Character set: numeric digits (0-9) and uppercase letters (A-Z) and a number of special characters (-, ., $, /, +, %, and space) \n* Codetext capacity: no specific restrictions"
            }
            Custom_Formats_duplicate.CODE_93 ->{
                binding.textFieldLayout.hint ="Enter codetext: e.g. 0123456789"
                binding.typeValuecreateAct2.text =  "CODE_93"
                binding.instruction1TextView.text = "* Character set: numeric digits (0-9) and uppercase letters (A-Z) and a number of special characters (-, ., $, /, +, %, and space) \n* Codetext capacity: no specific restrictions"
            }
            Custom_Formats_duplicate.DATA_MATRIX ->{
                binding.textFieldLayout.hint ="Enter codetext: e.g. 0123456789"
                binding.typeValuecreateAct2.text =  "DATA_MATRIX"
                binding.instruction1TextView.text = "* Character set: supports all 256 ASCII characters, all ISO characters, and all Extended Binary Coded Decimal Interchange Code (EBCDIC) characters. \n* Codetext capacity: up to 3116 numeric digits, or 2335 alpha numeric characters, or 1556 binary characters"
            }
            Custom_Formats_duplicate.EAN_13 ->{
                binding.textFieldLayout.hint ="Enter codetext: e.g. 590123412345"
                binding.typeValuecreateAct2.text =  "EAN_13"
                binding.instruction1TextView.text ="* Character set: only numeric digits (0-9). \n* Codetext capacity: exactly 12 digits + 1 check digit"
            }
            Custom_Formats_duplicate.EAN_8 ->{
                binding.textFieldLayout.hint ="Enter codetext: e.g. 9638507"
                binding.typeValuecreateAct2.text =  "EAN_8"
                binding.instruction1TextView.text = "* Character set: only numeric digits (0-9). \n* Codetext capacity: exactly 7 digits + 1 check digit"
            }
            Custom_Formats_duplicate.ITF ->{
                binding.textFieldLayout.hint ="Enter codetext: e.g. 123456"
                binding.typeValuecreateAct2.text =  "ITF"
                binding.instruction1TextView.text = "* Character set: only numeric digits (0-9).\n* Codetext capacity: 5 digits + 1 check digit"
            }
            Custom_Formats_duplicate.UPC_A ->{
                binding.textFieldLayout.hint ="Enter codetext: e.g. 0123456"
                binding.typeValuecreateAct2.text =  "UPC_A"
                binding.instruction1TextView.text = "* Character set: only numeric digits (0-9).\n* Codetext capacity: exactly 11 digits + 1 check digit"
            }
            Custom_Formats_duplicate.UPC_E ->{
                binding.textFieldLayout.hint ="Enter codetext: e.g. 0123456"
                binding.typeValuecreateAct2.text =  "UPC_E"
                binding.instruction1TextView.text = "* Character set: Supports only numeric digits (0-9). \n* Codetext capacity: exactly 7 digits + 1 check digit"
            }
            Custom_Formats_duplicate.PDF_417 ->{
                binding.textFieldLayout.hint = "Enter codetext: e.g. 0123456789"
                binding.typeValuecreateAct2.text =  "PDF_417"
                binding.instruction1TextView.text = "* Character set: all 256 ASCII characters \n* Codetext capacity: up to 800 characters"
            }
            Custom_Formats_duplicate.AZTEC ->{
                binding.textFieldLayout.hint =" Enter codetext: e.g. 0123456789"
                binding.typeValuecreateAct2.text =  "AZTEC"
                binding.instruction1TextView.text = "* Character set: all 256 ASCII characters \n* Codetext capacity: up to 3,832 numeric digits, or 3,067 alphabetic characters, or 1,914 bytes of data"
            }
            else->{
                binding.textFieldLayout.hint = "Enter text, E.g: I Love You  "+getEmojiByUnicode(unicodeEmoji)
                binding.typeValuecreateAct2.text = "Text"
                binding.instruction1TextView.text =  "* Character set: all 256 ASCII characters + Kanji \n* Codetext capacity: up to 7089 numeric characters, 4296 alphanumeric characters, 2953 bytes (binary data) or 1817 Kanji characters."
            }
        }

        binding.buttoncreateAct.setOnClickListener {
             val intent = Intent(this, MainActivity2::class.java)
            val text = binding.textFieldLayout.editText?.text.toString()
            if(text.isEmpty()){
                binding.textFieldLayout.error = "Error Content"
            }
            else{
                binding.textFieldLayout.isErrorEnabled = false
                intent.putExtra(Intent_KEYS.QRCODE, binding.textFieldLayout.editText?.text.toString())
                intent.putExtra(Intent_KEYS.FORMAT,format)
                intent.putExtra(Intent_KEYS.VALUETYPE,typevalue)

                startActivity(intent)
            }
        }

    } //end of onCreate()

    fun getEmojiByUnicode(unicode: Int): String? {
        return String(Character.toChars(unicode))
    }


}