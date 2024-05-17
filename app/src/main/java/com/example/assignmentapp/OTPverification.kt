package com.example.assignmentapp

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
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)

        lateinit var auth: FirebaseAuth
        lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
        var storedVerificationId: String? = null
        var resendToken: PhoneAuthProvider.ForceResendingToken? = null

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

        otpemail.setText(getemail)
        otpmobile.setText(getmobile)

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
            if(resendenabled){
                startcountdowntimer()
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(getmobile.toString())
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }

        fun verifyPhoneNumberWithCode(verificationId: String, code: String) {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            signInWithPhoneAuthCredential(credential)
        }

        verifybtn.setOnClickListener {
            var generateotp = otpedt1.text.toString()+otpedt2.text.toString()+otpedt3.text.toString()+otpedt4.text.toString()
            if(generateotp.length == 4){
                if (storedVerificationId != null && generateotp.isNotEmpty()) {
                    verifyPhoneNumberWithCode(storedVerificationId!!, generateotp)
                    var intent = Intent(this,LoginActivity::class.java)
                    //intent.putExtra("email",emailtxt)
                    //intent.putExtra("mobile",mobiletxt)
                    startActivity(intent)
                }
            }
        }

        fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
            if(keyCode == KeyEvent.KEYCODE_DEL){
                if(selectededtpostion == 3){
                    selectededtpostion = 2
                    showkeyboard(otpedt3)
                }
                else if(selectededtpostion == 2){
                    selectededtpostion = 1
                    showkeyboard(otpedt2)
                }
                else if(selectededtpostion == 1){
                    selectededtpostion = 0
                    showkeyboard(otpedt1)
                }
                return true
            }
            else{
                return super.onKeyUp(keyCode, event)
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
}