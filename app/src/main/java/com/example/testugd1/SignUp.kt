package com.example.testugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class SignUp : AppCompatActivity() {
    private lateinit var inputUsername:TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputTangalLahir: TextInputLayout
    private lateinit var inputNoTelpon: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputTangalLahir = findViewById(R.id.inputLayoutTanggalLahir)
        inputNoTelpon = findViewById(R.id.inputLayoutNoTelpon)
        val btnSignUp2: Button = findViewById(R.id.btnSignUp)

        btnSignUp2.setOnClickListener(View.OnClickListener {
            var checkSignUp = false
            val mBundle = Bundle()

            var username: String = inputUsername.getEditText()?.getText().toString()
            var password: String = inputUsername.getEditText()?.getText().toString()
            var email: String = inputUsername.getEditText()?.getText().toString()
            var tanggalLahir: String = inputUsername.getEditText()?.getText().toString()
            var noTelpon: String = inputUsername.getEditText()?.getText().toString()

            if (username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkSignUp = false
            }

            if (password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkSignUp = false
            }

            if (email.isEmpty()){
                inputEmail.setError("Email must be filled with text")
                checkSignUp = false
            }

            if (tanggalLahir.isEmpty()){
                inputTangalLahir.setError("Tanggal Lahir must be filled with text")
                checkSignUp = false
                }

            if (noTelpon.isEmpty()){
                inputNoTelpon.setError("No Telpon must be filled with text")
            }

            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && tanggalLahir.isNotEmpty() && noTelpon.isNotEmpty()) {
                checkSignUp = true
            }
            if (!checkSignUp) return@OnClickListener
            if(checkSignUp == true){
                mBundle.putString("username", inputUsername.getEditText()?.getText().toString())
                mBundle.putString("password", inputPassword.getEditText()?.getText().toString())
                val moveHome = Intent(this, MainActivity::class.java)
                moveHome.putExtras(mBundle)
                startActivity(moveHome)
            }

        })


    }
}