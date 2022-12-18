package com.example.testugd1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testugd1.api.AkunApi
import com.example.testugd1.api.DestinasiApi
import com.example.testugd1.databinding.ActivitySignUpBinding
import com.example.testugd1.models.Akun
import com.example.testugd1.models.Destinasi
import com.example.testugd1.room.User
import com.example.testugd1.room.UserDB
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class SignUp : AppCompatActivity() {

    //val binding: ActivitySignUpBinding
    //private lateinit var usersDb: UserDB
    val db by lazy { UserDB(this) }
    private lateinit var binding: ActivitySignUpBinding

    private val CHANNEL_ID_1 = "channerl_notification_01"
    private val notificationId1 = 101
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null
    private var etUsername: EditText? = null
    private var etPassword : EditText? = null
    private var etEmail : EditText? = null
    private var etTanggalLahir : EditText? = null
    private var etnoTelpon : EditText? = null
    private var emailFormat = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var noTelponMin = "^[+]?[0-9]{10,13}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        etUsername = findViewById(R.id.input_username)
        etPassword = findViewById(R.id.input_password)
        etEmail = findViewById(R.id.input_email)
        etTanggalLahir = findViewById(R.id.input_tanggalLahir)
        etnoTelpon = findViewById(R.id.input_noTelpon)


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

            if (email.matches(emailFormat.toRegex())){
                binding.inputLayoutEmail.setError("Email belum sesuai")
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
            if (noTelpon.matches(noTelponMin.toRegex())){
                binding.inputLayoutNoTelpon.setError("No Telpon minimal 10 digit dan maksimal 13 digit")
                checkSignUp = false
            }
            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && tanggalLahir.isNotEmpty() && noTelpon.isNotEmpty() && !email.matches(emailFormat.toRegex()) && !noTelpon.matches(noTelponMin.toRegex())) checkSignUp = true

            if (!checkSignUp) return@OnClickListener
            if(checkSignUp == true){

                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().addUser(
                        User(
                            0, username, password, email, tanggalLahir, noTelpon
                        )
                    )

                }
                createNotificationChannel()
                sendNotification1()
                createSignUp()
                mBundle.putString("username", username)
                mBundle.putString("password", password)
                val moveHome = Intent(this, MainActivity::class.java)
                moveHome.putExtras(mBundle)
                startActivity(moveHome)
            }

        })
    }
    private fun createSignUp() {
        setLoading(true)
        if(etUsername!!.text.toString().isEmpty()){
            Toast.makeText(this@SignUp, "Username Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        if(etPassword!!.text.toString().isEmpty()){
            Toast.makeText(this@SignUp, "Password Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        if(etEmail!!.text.toString().matches(emailFormat.toRegex())){
            Toast.makeText(this@SignUp, "Format Email salah!", Toast.LENGTH_SHORT).show()
        }
        if(etEmail!!.text.toString().isEmpty()){
            Toast.makeText(this@SignUp, "Email Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        if(etTanggalLahir!!.text.toString().isEmpty()){
            Toast.makeText(this@SignUp, "Tanggal Lahir Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        if(etnoTelpon!!.text.toString().isEmpty()){
            Toast.makeText(this@SignUp, "No telfon Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        if(etnoTelpon!!.text.toString().matches(noTelponMin.toRegex())){
            Toast.makeText(this@SignUp, "No telfon Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else{
            val akun = Akun(
                etUsername!!.toString(),
                etPassword!!.toString(),
                etEmail!!.toString(),
                etTanggalLahir!!.toString(),
                etnoTelpon!!.toString(),
            )

            val stringRequest: StringRequest =
                object : StringRequest(Method.POST, AkunApi.ADD_URL, Response.Listener { response ->
                    val gson = Gson()
                    var akun = gson.fromJson(response, Akun::class.java)

                    if (akun != null)
                        Toast.makeText(this@SignUp, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

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
                            this@SignUp,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@SignUp, e.message, Toast.LENGTH_SHORT).show()
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
    private fun sendNotification1() {
        val intent : Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val etTitle = "Berhasil Masuk"
        val etMessage = "Selamat anda berhasil Registrasi"
        val bigPictureBitmap = ContextCompat.getDrawable(this, R.drawable.smile)?.toBitmap()
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .setBigContentTitle(etTitle)
            .setSummaryText(etMessage)
            .bigPicture(bigPictureBitmap)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0,intent,0)

        //Showing Toast
        val broadcastIntent : Intent = Intent(this,NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", etMessage)
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_person_24)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.GREEN)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(bigPictureStyle)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1,builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)


        }
    }
}