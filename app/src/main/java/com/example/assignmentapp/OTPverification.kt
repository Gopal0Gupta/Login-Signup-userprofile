package com.example.assignmentapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.assignmentapp.utils.androidUtils
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.verifyPhoneNumber
import com.google.firebase.auth.auth
import java.nio.file.attribute.AclEntry.Builder
import java.nio.file.attribute.AclEntry.newBuilder
import java.util.concurrent.TimeUnit

class OTPverification : AppCompatActivity() {
    var selectededtpostion = 0
    var resendenabled = false
    var resendtime = 60
    lateinit var otpemail: TextView
    lateinit var otpmobile: TextView
    lateinit var resendbtn: TextView
    lateinit var otpedt1: EditText
    lateinit var otpedt2: EditText
    lateinit var otpedt3: EditText
    lateinit var otpedt4: EditText
    lateinit var verifybtn: Button
    lateinit var auth: FirebaseAuth
    lateinit var verificationCode : String
    lateinit var forceresendingtoken :PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)

        auth = FirebaseAuth.getInstance()

        otpemail = findViewById(R.id.otpemail)
        otpmobile = findViewById(R.id.otpmobile)
        resendbtn = findViewById(R.id.resendbtn)
        otpedt1 = findViewById(R.id.otpedt1)
        otpedt2 = findViewById(R.id.otpedt2)
        otpedt3 = findViewById(R.id.otpedt3)
        otpedt4 = findViewById(R.id.otpedt4)
        verifybtn = findViewById(R.id.verifybtn)

        var getemail = intent.getStringExtra("email")
        var getmobile = intent.getStringExtra("mobile")
        var fullname = intent.getStringExtra("fullname")

        otpemail.setText(getemail)
        otpmobile.setText(getmobile)

        sendOTP(otpmobile.toString(),false)

        fun showkeyboard(otpedt: EditText){
            otpedt.requestFocus()

            val inputMethodManager = otpedt.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(otpedt, InputMethodManager.SHOW_IMPLICIT)
        }

        otpedt2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if(s.length > 0){
                        if(selectededtpostion == 0){
                            selectededtpostion = 1
                            showkeyboard(otpedt2)
                        } else if(selectededtpostion == 1){
                            selectededtpostion = 2
                            showkeyboard(otpedt3)
                        } else if(selectededtpostion == 2){
                            selectededtpostion = 3
                            showkeyboard(otpedt4)
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        otpedt3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if(s.length > 0){
                        if(selectededtpostion == 0){
                            selectededtpostion = 1
                            showkeyboard(otpedt2)
                        } else if(selectededtpostion == 1){
                            selectededtpostion = 2
                            showkeyboard(otpedt3)
                        } else if(selectededtpostion == 2){
                            selectededtpostion = 3
                            showkeyboard(otpedt4)
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        otpedt4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if(s.length > 0){
                        if(selectededtpostion == 0){
                            selectededtpostion = 1
                            showkeyboard(otpedt2)
                        } else if(selectededtpostion == 1){
                            selectededtpostion = 2
                            showkeyboard(otpedt3)
                        } else if(selectededtpostion == 2){
                            selectededtpostion = 3
                            showkeyboard(otpedt4)
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        otpedt1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if(s.length > 0){
                        if(selectededtpostion == 0){
                            selectededtpostion = 1
                            showkeyboard(otpedt2)
                        } else if(selectededtpostion == 1){
                            selectededtpostion = 2
                            showkeyboard(otpedt3)
                        } else if(selectededtpostion == 2){
                            selectededtpostion = 3
                            showkeyboard(otpedt4)
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        showkeyboard(otpedt1)
        startcountdowntimer()

        resendbtn.setOnClickListener {
                startcountdowntimer()
                sendOTP(otpmobile.toString(),false)
        }

        verifybtn.setOnClickListener {
            var generateotp = otpedt1.text.toString()+otpedt2.text.toString()+otpedt3.text.toString()+otpedt4.text.toString()
            if(generateotp.length == 4){
                var credential = PhoneAuthProvider.getCredential(verificationCode,generateotp)
                signIn(credential)
            }
        }
    }
    fun startcountdowntimer(){
        resendenabled = false
        resendbtn.setTextColor(Color.parseColor("#99000000"))

        val timer = object: CountDownTimer((resendtime * 1000).toLong(), 100) {
            override fun onTick(millisUntilFinished: Long) {
                resendbtn.setText("Resend Code ("+(millisUntilFinished/1000)+")")
            }

            override fun onFinish() {
                resendenabled = true
                resendbtn.setText("Resend code")
                resendbtn.setTextColor(resources.getColor(R.color.primary))
            }
        }
        timer.start()
    }

    fun signIn(phoneAuthCredential: PhoneAuthCredential){
        auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener {
            if(it.isSuccessful){
                var intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }else{
                androidUtils.showToast(applicationContext,"OTP Verification Failed")
            }
        }
    }

    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signIn(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            androidUtils.showToast(applicationContext,"OTP Verification Failed $e")
        }
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            verificationCode = verificationId
            forceresendingtoken = token
            androidUtils.showToast(applicationContext,"OTP Sent Successfully")
        }
    }
        @SuppressLint("SuspiciousIndentation")
        fun sendOTP(phonenumber : String, resendenabled : Boolean){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phonenumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks


            if(resendenabled){
                PhoneAuthProvider.verifyPhoneNumber(options.setForceResendingToken(forceresendingtoken).build())
            }else{
                PhoneAuthProvider.verifyPhoneNumber(options.build())
            }
    }
}