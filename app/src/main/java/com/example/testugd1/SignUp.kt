package com.example.testugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.testugd1.databinding.ActivitySignUpBinding
import com.example.testugd1.room.User
import com.example.testugd1.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUp : AppCompatActivity() {

    //val binding: ActivitySignUpBinding
    //private lateinit var usersDb: UserDB
    val db by lazy { UserDB(this) }
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSignUp.setOnClickListener(View.OnClickListener {
            var checkSignUp = false
            val mBundle = Bundle()
            val username: String = binding.inputLayoutUsername.getEditText()?.getText().toString()
            val password: String = binding.inputLayoutPassword.getEditText()?.getText().toString()
            val email: String = binding.inputLayoutEmail.getEditText()?.getText().toString()
            val tanggalLahir: String = binding.inputLayoutTanggalLahir.getEditText()?.getText().toString()
            val noTelpon: String = binding.inputLayoutNoTelpon.getEditText()?.getText().toString()
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
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().addUser(
                        User(
                            0, username, password, email, tanggalLahir, noTelpon
                        )
                    )
                    finish()
                }

                val moveHome = Intent(this, MainActivity::class.java)
                moveHome.putExtras(mBundle)
                startActivity(moveHome)
            }

        })
    }
}