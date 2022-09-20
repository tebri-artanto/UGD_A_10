package com.example.testugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {
    private lateinit var inputusername: TextInputEditText
    private lateinit var inputpassword: TextInputEditText
    private lateinit var mainLayout: ConstraintLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        getBundle()
        setText()

        val btnSignUp: TextView = findViewById(R.id.tvSignUp)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        inputusername = findViewById(R.id.inputTextUsername)
        inputpassword = findViewById(R.id.inputTextPassword)
        mainLayout = findViewById(R.id.mainLayout)

        btnSignUp.setOnClickListener {
            val moveSignUp = Intent(this@MainActivity, SignUp::class.java)
            startActivity(moveSignUp)
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = inputusername.getText().toString()
            val password: String = inputpassword.getText().toString()

            if (username.isEmpty()) {
                inputusername.setError("Username must be filled with text")
                checkLogin = false
            }
            if (password.isEmpty()) {
                inputpassword.setError("Password must be filled with text")
                checkLogin = false
            }
            val mBundle = intent.extras
            if (mBundle != null) {
                if (username == mBundle.getString("username") && password == mBundle.getString("password")) checkLogin =
                    true

            }
            if (!checkLogin) return@OnClickListener
            if (checkLogin == true) {
                val moveHome = Intent(this, HomeActivity::class.java)
                startActivity(moveHome)
            }


        })
    }




    fun getBundle(){
        inputusername = findViewById(R.id.inputTextUsername)
        inputpassword = findViewById(R.id.inputTextPassword)
    }
    fun setText() {

        val mBundle = intent.extras
        if (mBundle != null){
            inputusername.setText(mBundle.getString("username"))
            inputpassword.setText(mBundle.getString("password"))
        }
    }
}