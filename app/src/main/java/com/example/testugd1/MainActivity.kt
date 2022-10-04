package com.example.testugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.testugd1.databinding.ActivityMainBinding
import com.example.testugd1.databinding.ActivitySignUpBinding
import com.example.testugd1.room.User
import com.example.testugd1.room.UserDB
import com.example.testugd1.room.UserDao
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private lateinit var binding: ActivityMainBinding
    var sharedPreferences : SharedPreferences? = null
    var key = "idKey"
    var pref = "myPref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setText()

        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)

        binding.tvSignUp.setOnClickListener {
            val moveSignUp = Intent(this, SignUp::class.java)
            startActivity(moveSignUp)
        }
        val moveHome = Intent(this, HomeActivity::class.java)
        binding.btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = binding.inputTextUsername.getText().toString()
            val password: String = binding.inputTextPassword.getText().toString()

            if (username.isEmpty()) {
                binding.inputTextUsername.setError("Username must be filled with text")
                checkLogin = false
            }
            if (password.isEmpty()) {
                binding.inputTextPassword.setError("Password must be filled with text")
                checkLogin = false
            }
            //val mBundle = intent.extras
            CoroutineScope(Dispatchers.IO).launch {
                if (!username.isEmpty() && !password.isEmpty()){
                    val users = db.userDao().getUsers()

                    for (i in users) {
                        if (username == i.username && password == i.password) {
                            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                            editor.putString(key, i.id.toString())
                            editor.apply()
                            checkLogin = true
                        }
                    }

                }
                withContext(Dispatchers.Main) {
                    if (checkLogin) {
                        startActivity(moveHome)
                    }
                }
            }

            if (!checkLogin) return@OnClickListener
            if (checkLogin == true) {
                val moveHome = Intent(this, HomeActivity::class.java)
                startActivity(moveHome)
            }

//
//            val mBundle = intent.extras
//            if (mBundle != null) {
//                if (username == mBundle.getString("username") && password == mBundle.getString("password")) checkLogin =
//                    true
//
//            }
//            if(checkLogin==true){
//                val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
//                startActivity(moveHome)
//            }
//            if (!checkLogin) return@OnClickListener


        })
    }

//    inner class NoteViewHolder( val view: View) : RecyclerView.ViewHolder(view)

//    override fun onBindViewHolder(holder: NoteViewHolder, position: Int){
//        val note = notes[position]
//        holder.view.text_title.text = note.title
//        holder.view.text_title.setOnClickListener{
//            listener.onClick(note)
//        }
//        holder.view.icon_edit.setOnClickListener {
//            listener.onUpdate(note)
//        }
//        holder.view.icon_delete.setOnClickListener {
//            listener.onDelete(note)
//        }
//    }

    fun setText() {
        var inputusername = binding.inputTextUsername
        var inputpassword = binding.inputTextPassword
        val mBundle = intent.extras
        if (mBundle != null){
            inputusername.setText(mBundle.getString("username"))
            inputpassword.setText(mBundle.getString("password"))
        }
    }
}