package com.example.assignmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import java.util.concurrent.TimeUnit

class Sign_Up : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var passwordshowing = false
        var conpasswordshowing = false

        var passwordicon = findViewById<ImageView>(R.id.passwordicon)
        var passwordedt = findViewById<EditText>(R.id.passwordedt)
        var conpasswordicon = findViewById<ImageView>(R.id.conpasswordicon)
        var conpasswordedt = findViewById<EditText>(R.id.conpasswordedt)
        var signupbtn = findViewById<Button>(R.id.signupbtn)
        var mobileedt = findViewById<EditText>(R.id.mobileedt)
        var emailedt = findViewById<EditText>(R.id.emailedt)
        var loginupbtn = findViewById<TextView>(R.id.loginupbtn)
        var fullname = findViewById<EditText>(R.id.fullnameedt)

        passwordicon.setOnClickListener {
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

        conpasswordicon.setOnClickListener {
            if(conpasswordshowing){
                conpasswordshowing = false
                conpasswordedt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                conpasswordicon.setImageResource(R.drawable.show_password)
            }
            else{
                conpasswordshowing = true
                conpasswordedt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                conpasswordicon.setImageResource(R.drawable.hide_password)
            }
            conpasswordedt.setSelection(conpasswordedt.length())
        }

        signupbtn.setOnClickListener {
            var mobiletxt = mobileedt.text.toString()
            var emailtxt = emailedt.text.toString()
            var password = passwordedt.text.toString()
            var conpassword = conpasswordedt.text.toString()
            var fullname = fullname.text.toString()

            if (mobiletxt.isEmpty() || emailtxt.isEmpty() || password.isEmpty() || conpassword.isEmpty() || fullname.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
            }

            var intent = Intent(this,OTPverification::class.java)
            intent.putExtra("email",emailtxt)
            intent.putExtra("mobile",mobiletxt)
            intent.putExtra("fullname",fullname)
            startActivity(intent)
        }
        loginupbtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}