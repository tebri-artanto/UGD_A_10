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
    private lateinit var signUpLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputTangalLahir = findViewById(R.id.inputLayoutTanggalLahir)
        inputNoTelpon = findViewById(R.id.inputLayoutNoTelpon)
        signUpLayout = findViewById(R.id.singUpLayout)

        val btnSignUp2: Button = findViewById(R.id.btnSignUp)

        btnSignUp2.setOnClickListener(View.OnClickListener {
            var checkSignUp = false

            val mBundle = Bundle()
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()
            val email: String = inputEmail.getEditText()?.getText().toString()
            val tanggalLahir: String = inputTangalLahir.getEditText()?.getText().toString()
            val noTelpon: String = inputNoTelpon.getEditText()?.getText().toString()


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
                checkSignUp = false
            }
            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && tanggalLahir.isNotEmpty() && noTelpon.isNotEmpty()) checkSignUp = true

            if (!checkSignUp) return@OnClickListener
            if(checkSignUp == true){
                mBundle.putString("username", username)
                mBundle.putString("password", password)
                val moveHome = Intent(this@SignUp, MainActivity::class.java)
                moveHome.putExtras(mBundle)
                startActivity(moveHome)
            }

        })


    }
}