package com.example.assignmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var passwordshowing = false

        var signupbtn = findViewById<TextView>(R.id.signupbtn)
        var passwordicon = findViewById<ImageView>(R.id.passwordicon)
        var passwordedt = findViewById<EditText>(R.id.passwordedt)

        signupbtn.setOnClickListener{
            startActivity(Intent(this,Sign_Up::class.java))
        }

        passwordicon.setOnClickListener{
            if(passwordshowing){
                passwordshowing = false
                passwordedt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordicon.setImageResource(R.drawable.show_password)
            }
            else{
                passwordshowing = true
                passwordedt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordicon.setImageResource(R.drawable.hide_password)
            }
            passwordedt.setSelection(passwordedt.length())
        }



    }
}