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

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputTangalLahir = findViewById(R.id.inputLayoutTanggalLahir)
        inputNoTelpon = findViewById(R.id.inputLayoutNoTelpon)

        val btnSignUp: Button = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener(View.OnClickListener {
        var checkSignUp = false
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

        if (username.isEmpty()){
            inputUsername.setError("Username must be filled with text")
            checkSignUp = false
        }

        if (username.isEmpty()){
            inputUsername.setError("Username must be filled with text")
            checkSignUp = false
            }
        })

        setContentView(R.layout.activity_sign_up)
    }
}