package com.example.testugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
<<<<<<< Updated upstream
import androidx.core.view.get
import com.example.testugd1.databinding.ActivitySignUpBinding
=======
import com.example.testugd1.room.User
import com.example.testugd1.room.UserDB
>>>>>>> Stashed changes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    val db by lazy { UserDB(this) }
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

<<<<<<< Updated upstream
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)






=======
        setupListener()

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputTangalLahir = findViewById(R.id.inputLayoutTanggalLahir)
        inputNoTelpon = findViewById(R.id.inputLayoutNoTelpon)
        signUpLayout = findViewById(R.id.singUpLayout)
>>>>>>> Stashed changes

        val btnSignUp2: Button = findViewById(R.id.btnSignUp)

        btnSignUp2.setOnClickListener(View.OnClickListener {
            var checkSignUp = false

            val mBundle = Bundle()
            val username: String = binding.inputLayoutUsername.toString()
            val password: String = binding.inputLayoutPassword.toString()
            val email: String = binding.inputLayoutEmail.toString()
            val tanggalLahir: String = binding.inputLayoutTanggalLahir.toString()
            val noTelpon: String = binding.inputLayoutNoTelpon.toString()


            if (username.isEmpty()){
                binding.inputLayoutUsername.setError("Username must be filled with text")
                checkSignUp = false
            }

            if (password.isEmpty()){
                binding.inputLayoutPassword.setError("Password must be filled with text")
                checkSignUp = false
            }

            if (email.isEmpty()){
                binding.inputLayoutEmail.setError("Email must be filled with text")
                checkSignUp = false
            }

            if (tanggalLahir.isEmpty()){
                binding.inputLayoutTanggalLahir.setError("Tanggal Lahir must be filled with text")
                checkSignUp = false
                }

            if (noTelpon.isEmpty()){
                binding.inputLayoutNoTelpon.setError("No Telpon must be filled with text")
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

    private fun setupListener() {
        btnSignUp.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addUser(
                    User(
                        0,
                        inputUsername.getEditText()?.getText().toString(),
                        inputPassword.getEditText()?.getText().toString(),
                        inputEmail.getEditText()?.getText().toString(),
                        inputTangalLahir.getEditText()?.getText().toString(),
                        inputNoTelpon.getEditText()?.getText().toString()
                    )
                )
                finish()
            }
        }
    }
}