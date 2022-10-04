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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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

    private val CHANNEL_ID_1 = "channerl_notification_01"
    private val notificationId1 = 101

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

                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().addUser(
                        User(
                            0, username, password, email, tanggalLahir, noTelpon
                        )
                    )

                }
                createNotificationChannel()
                sendNotification1()
                mBundle.putString("username", username)
                mBundle.putString("password", password)
                val moveHome = Intent(this, MainActivity::class.java)
                moveHome.putExtras(mBundle)
                startActivity(moveHome)
            }

        })
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