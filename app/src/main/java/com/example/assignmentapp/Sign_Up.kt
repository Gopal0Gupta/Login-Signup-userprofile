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

        lateinit var auth: FirebaseAuth
        lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
        var storedVerificationId: String? = null
        var resendToken: PhoneAuthProvider.ForceResendingToken? = null

        auth = FirebaseAuth.getInstance()

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

        fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = task.result?.user
                    } else {
                        TODO("Not yet implemented")
                    }
                }
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
            }
        }

        signupbtn.setOnClickListener {
            var mobiletxt = mobileedt.text.toString()
            var emailtxt = emailedt.text.toString()
            var password = passwordedt.text.toString()
            var conpassword = conpasswordedt.text.toString()

            if(mobiletxt == null){
                Toast.makeText(this, "Enter Mobile", Toast.LENGTH_SHORT).show()
            }
            if(emailtxt == null){
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
            }
            if(password == null){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            }
            if(conpassword == null){
                Toast.makeText(this, "Confirm Password", Toast.LENGTH_SHORT).show()
            }
            if(fullname.text.toString() == null){
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show()
            }
            if(password != conpassword){
                Toast.makeText(this, "Password & Confirm Password should be same", Toast.LENGTH_SHORT).show()
            }

            auth.createUserWithEmailAndPassword(emailtxt, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext,
                            "Account Created Successfully",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

            var intent = Intent(this,OTPverification::class.java)
            intent.putExtra("email",emailtxt)
            intent.putExtra("mobile",mobiletxt)
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(mobiletxt)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            startActivity(intent)
        }
        loginupbtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}