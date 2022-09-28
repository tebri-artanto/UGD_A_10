package com.example.testugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.testugd1.room.UserDB
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private lateinit var inputusername: TextInputEditText
    private lateinit var inputpassword: TextInputEditText
    private lateinit var mainLayout: ConstraintLayout
    var sharedPreferences : SharedPreferences? = null

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
        var key = "idKey"
        var pref = "myPref"
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)

        btnSignUp.setOnClickListener {
            val moveSignUp = Intent(this, SignUp::class.java)
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

            CoroutineScope(Dispatchers.IO).launch {
                if (!username.isEmpty() && !password.isEmpty()) {
                    val user = db.userDao().getUsers()
                    for (i in user) {
                        if (username == i.username) {
                            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                            editor.putString(key, i.id.toString())
                            editor.apply()
                        }
                    }

                }
            }
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