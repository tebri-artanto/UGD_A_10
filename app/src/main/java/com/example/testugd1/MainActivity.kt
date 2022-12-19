package com.example.testugd1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testugd1.api.AkunApi
import com.example.testugd1.databinding.ActivityMainBinding
import com.example.testugd1.databinding.ActivitySignUpBinding
import com.example.testugd1.models.Akun
import com.example.testugd1.room.User
import com.example.testugd1.room.UserDB
import com.example.testugd1.room.UserDao
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.master.permissionhelper.PermissionHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.layout_loading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    val db by lazy { UserDB(this) }
    private lateinit var binding: ActivityMainBinding
    var sharedPreferences : SharedPreferences? = null
    var key = "idKey"
    var pref = "myPref"
    internal var permissionHelper: PermissionHelper? = null
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutLoading = findViewById(R.id.layout_loading)
        etUsername = findViewById(R.id.inputTextUsername)
        etPassword = findViewById(R.id.inputTextPassword)
        queue= Volley.newRequestQueue(this)
        supportActionBar?.hide()
        setText()

        permissionHelper = PermissionHelper(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.READ_EXTERNAL_STORAGE), 100)

        permissionHelper?.denied {
            if (it) {
                Log.d(TAG, "onPermissionDeniedBySystem() called")
                permissionHelper?.openAppDetailsActivity()
            } else {
                Log.d(TAG, "onPermissionDenied() called")
            }
        }

        permissionHelper?.requestIndividual {
            Log.d(TAG, "Individual Permission Granted")
        }

        permissionHelper?.requestAll {
            Log.d(TAG, "All permission granted")
        }



        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)

        binding.tvSignUp.setOnClickListener {
            val moveSignUp = Intent(this, SignUp::class.java)
            startActivity(moveSignUp)
        }
        val moveHome = Intent(this, HomeActivity::class.java)
        binding.btnLogin.setOnClickListener(View.OnClickListener {

            var checkLogin = false
            val username: String = etUsername.getText().toString()
            val password: String = etPassword.getText().toString()
            val dataTemp = Bundle()

            if (username.isEmpty()) {
                binding.inputTextUsername.setError("Username must be filled with text")
                checkLogin = false
            }
            if (password.isEmpty()) {
                binding.inputTextPassword.setError("Password must be filled with text")
                checkLogin = false
            }
            //val mBundle = intent.extras
//            CoroutineScope(Dispatchers.IO).launch {
//                if (!username.isEmpty() && !password.isEmpty()){
//                    val users = db.userDao().getUsers()
//                    //456createLogin()
//                    for (i in users) {
//                        if (username == i.username && password == i.password) {
//                            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
//                            editor.putString(key, i.id.toString())
//                            editor.apply()
//                            checkLogin = true
//                        }
//                    }
//
//                }
//                withContext(Dispatchers.Main) {
//                    if (checkLogin) {
//                        startActivity(moveHome)
//                    }
//                }
//            }
            if (!username.isEmpty() && !password.isEmpty()){
                val stringRequest: StringRequest = object : StringRequest(Method.GET, AkunApi.GET_ALL_URL,
                    Response.Listener { response ->
                        val gson = Gson()
                        var userList: Array<Akun> = gson.fromJson(response,Array<Akun>::class.java)
                        for (akun in userList){
                            if(username == akun.username){
                                if(password == akun.password){
                                    checkLogin = true
                                    dataTemp.putString("idKey",akun.username)
//                                        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)
//                                        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
//                                        akun.id?.let { it1 -> editor.putInt(key, it1) }
//                                        editor.apply()
//                                        checkLogin = true

                                    break
                                }else{

                                    FancyToast.makeText(this,"Password Salah",FancyToast.LENGTH_LONG,FancyToast.ERROR,true);
                                }
                            }

                        }

                        if(!checkLogin){

                            FancyToast.makeText(this@MainActivity, "Username salah", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                        }else{
                            FancyToast.makeText(this@MainActivity, "Berhasil Login!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()

                            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)

                            startActivity(moveHome.putExtra("idKey",dataTemp))
                        }


                    },Response.ErrorListener { error ->

                        try {
                            val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            Toast.makeText(this@MainActivity, errors.getString("Error: message"), Toast.LENGTH_SHORT).show()
                            FancyToast.makeText(this@MainActivity, errors.getString("Error: message"), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                        }catch (e: java.lang.Exception){
                            Log.d("Error di mana", e.message.toString())
                            FancyToast.makeText(this@MainActivity, e.message, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()
                        }
                    }
                ){
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "aplication/json"
                        return headers
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }
                queue!!.add(stringRequest)
            }

//            if (!checkLogin) return@OnClickListener
//            else{
//                try{
//                    createLogin()
//                }
//                catch(e : Error){
//                    inputTextUsername.setError(e.message)
//                    inputTextPassword.setError(e.message)
//                    return@OnClickListener
//                }
//            }
//            if (checkLogin == true) {
//                val moveHome = Intent(this, HomeActivity::class.java)
//                startActivity(moveHome)
//            }

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
        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            permissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    private fun createLogin() {
        setLoading(true)
        if(etUsername!!.text.toString().isEmpty()){
            Toast.makeText(this@MainActivity, "Username Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        if(etPassword!!.text.toString().isEmpty()){
            Toast.makeText(this@MainActivity, "Password Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else{
            val akun = Akun(

                etUsername!!.toString(),
                etPassword!!.toString(),
                "","",""
            )

            val stringRequest: StringRequest =
                object : StringRequest(Method.POST, AkunApi.LOGIN_URL, Response.Listener { response ->
                    val gson = Gson()
                    var akun = gson.fromJson(response, Akun::class.java)

                    if (akun != null)
                        Toast.makeText(this@MainActivity, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

//                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
//                    editor.putString("id",akun.id.toString())
//                    editor.apply()
                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()

                    setLoading(false)
                }, Response.ErrorListener { error ->
                    setLoading(false)
                    try {
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@MainActivity,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "application/json"
                        return headers
                    }

                    @Throws(AuthFailureError::class)
                    override fun getBody(): ByteArray {
                        val gson = Gson()
                        val requestBody = gson.toJson(akun)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }
            queue!!.add(stringRequest)
        }
        setLoading(false)

    }
    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layout_loading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layout_loading!!.visibility = View.INVISIBLE
        }
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