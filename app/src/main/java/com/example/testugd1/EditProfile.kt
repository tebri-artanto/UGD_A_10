package com.example.testugd1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testugd1.databinding.ActivityEditProfileBinding
import com.example.testugd1.databinding.ActivitySignUpBinding
import com.example.testugd1.databinding.FragmentProfileBinding
import com.example.testugd1.room.User
import com.example.testugd1.room.UserDB
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfile : AppCompatActivity() {
    val db by lazy { UserDB(this) }

    private val key = "idKey"
    private val mypref= "myPref"

    private lateinit var binding: ActivityEditProfileBinding
    private var CHANNEL_ID_1 = "channel_notiification_01"
    private var notificationId1 = 101
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createNotificationChannel()

        binding!!.buttonSave.setOnClickListener{
            sendNotification1()
        }

        sharedPreferences = getSharedPreferences(mypref, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(key, "")!!.toInt()

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

            val intent = Intent(this, HomeActivity::class.java)
            val bundle = Bundle()
            bundle.putString("key", "filled")
            intent.putExtra("keyBundle", bundle)
            startActivity(intent)

        }

    }

    //membuat notif
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification1(){

        val intent: Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent: Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", binding?.buttonSave?.text.toString())
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentTitle(binding?.inputEmail?.text.toString())
            .setContentText("Profile Change Successful")
            //bigtext
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA...........")
            )
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
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