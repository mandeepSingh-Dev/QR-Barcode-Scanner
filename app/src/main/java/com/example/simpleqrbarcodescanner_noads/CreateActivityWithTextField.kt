package com.example.simpleqrbarcodescanner_noads

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleqrbarcodescanner_noads.Util.Intent_KEYS
import com.example.simpleqrbarcodescanner_noads.databinding.ActivityCreateWithTextFieldBinding

class CreateActivityWithTextField : AppCompatActivity() {

    lateinit var binding:ActivityCreateWithTextFieldBinding
    val unicodeEmoji = 0x2665
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWithTextFieldBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        binding.textFieldEdittext.hint = "Enter text, E.g: I Love You  "+getEmojiByUnicode(unicodeEmoji)
          val typevalue = intent.getStringExtra(Intent_KEYS.VALUETYPE)
          val format = intent.getIntExtra(Intent_KEYS.FORMAT,0)

          binding.typeValuecreateAct2.text = typevalue


        binding.buttoncreateAct.setOnClickListener {
             val intent = Intent(this, MainActivity2::class.java)
          intent.putExtra(Intent_KEYS.QRCODE, binding.textFieldEdittext.text.toString())
          intent.putExtra(Intent_KEYS.FORMAT,format)
          intent.putExtra(Intent_KEYS.VALUETYPE_QRGENERATOR,typevalue)

          startActivity(intent)
        }
    }

    fun getEmojiByUnicode(unicode: Int): String? {
        return String(Character.toChars(unicode))
    }
}