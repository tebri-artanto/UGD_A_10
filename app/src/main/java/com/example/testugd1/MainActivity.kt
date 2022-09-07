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

class MainActivity : AppCompatActivity() {
    private lateinit var inputusername: TextInputLayout
    private lateinit var inputpassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout
    lateinit var mBundle: Bundle
    lateinit var vUsername: String
    lateinit var vPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getBundle()
        setText()



        
        val btnSignUp: TextView = findViewById(R.id.tvSignUp)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnSignUp.setOnClickListener{
            val moveSignUp = Intent(this@MainActivity, SignUp::class.java)
            startActivity(moveSignUp)
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            inputusername = findViewById(R.id.inputUsername)
            inputpassword = findViewById(R.id.inputPassword)
            val username: String = inputusername.getEditText()?.getText().toString()
            val password: String = inputpassword.getEditText()?.getText().toString()

            //Pengecekan apakah inputan usernamme kososng
            if (username.isEmpty()){
                inputusername.setError("Username must be filled with text")
                checkLogin  = false
            }

            //Pengecekan apakah inputan password kososng
            if (password.isEmpty()){
                inputpassword.setError("Password must be filled with text")
                checkLogin  = false
            }

            //Password NPM
            if (username == mBundle.getString("username") && password == mBundle.getString("password")) checkLogin = true
            if (!checkLogin) return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })



    }

    fun getBundle() {
        mBundle = intent.getBundleExtra("signUp")!!
        vUsername = mBundle.getString("username")!!

    }

    fun setText() {
        inputusername = findViewById(R.id.inputLayoutUsername)
        inputusername.getEditText()?.setText(vUsername).toString()
    }
}