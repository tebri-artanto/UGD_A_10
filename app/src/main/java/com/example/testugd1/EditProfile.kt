package com.example.testugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testugd1.databinding.ActivityEditProfileBinding
import com.example.testugd1.databinding.ActivitySignUpBinding
import com.example.testugd1.databinding.FragmentProfileBinding
import com.example.testugd1.room.User
import com.example.testugd1.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfile : AppCompatActivity() {
    val db by lazy { UserDB(this) }

    private val key = "idKey"
    private val mypref= "myPref"

    private lateinit var binding: ActivityEditProfileBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences(mypref, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(key, "")!!.toInt()

        //createNotificationChannel()

        CoroutineScope(Dispatchers.IO).launch {
            val user = db?.userDao()?.getUser(id)?.get(0)
            withContext(Dispatchers.Main) {
                binding.inputUsername.setText(user?.username)
                binding.inputPassword.setText(user?.password)
                binding.inputEmail.setText(user?.email)
                binding.inputTanggalLahir.setText(user?.tanggalLahir)
                binding.inputNoTelp.setText(user?.noTelpon)
            }

        }

        binding.buttonSave.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                val loggedUasers: List<User> = db.userDao().getUser(id)
                val logged = loggedUasers[0]
                db.userDao().updateUser(
                    User(
                        id,
                        binding.inputUsername.text.toString(),
                        binding.inputEmail.text.toString(),
                        binding.inputPassword.text.toString(),
                        binding.inputTanggalLahir.text.toString(),
                        binding.inputNoTelp.text.toString()
                    )
                )
            }
            finish()
            //bikinnya disini yang nanti notifnya bakal muncul
            //sendNotification1()
            val intent = Intent(this, HomeActivity::class.java)
            val bundle = Bundle()
            bundle.putString("key", "filled")
            intent.putExtra("keyBundle", bundle)
            startActivity(intent)

        }

    }
//        binding.buttonSave.setOnClickListener(View.OnClickListener {
//            var checkSignUp = false
//            val mBundle = Bundle()
//            val username: String = binding.inputUsername.text.toString()
//            val password: String = binding.inputPassword.text.toString()
//            val email: String = binding.inputEmail.text.toString()
//            val tanggalLahir: String = binding.inputTanggalLahir.text.toString()
//            val noTelpon: String = binding.inputNoTelp.text.toString()
//            if (username.isEmpty()){
//                binding.inputLayoutUsername.setError("Username must be filled with text")
//                checkSignUp = false
//            }
//
//            if (password.isEmpty()){
//                binding.inputLayoutPassword.setError("Password must be filled with text")
//                checkSignUp = false
//            }
//
//            if (email.isEmpty()){
//                binding.inputLayoutEmail.setError("Email must be filled with text")
//                checkSignUp = false
//            }
//
//            if (tanggalLahir.isEmpty()){
//                binding.inputLayoutTanggalLahir.setError("Tanggal Lahir must be filled with text")
//                checkSignUp = false
//            }
//
//            if (noTelpon.isEmpty()){
//                binding.inputLayoutNoTelpon.setError("No Telpon must be filled with text")
//                checkSignUp = false
//            }
//            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && tanggalLahir.isNotEmpty() && noTelpon.isNotEmpty()) checkSignUp = true
//
//            if (!checkSignUp) return@OnClickListener
//            if(checkSignUp == true){
//                CoroutineScope(Dispatchers.IO).launch {
//                    db?.userDao()?.updateUser(
//                        User(
//                            0, username, password, email, tanggalLahir, noTelpon
//                        )
//                    )
//                    finish()
//                }
//
//                val moveHome = Intent(this, HomeActivity::class.java)
//                moveHome.putExtras(mBundle)
//                startActivity(moveHome)
//            }
//
//        })

}