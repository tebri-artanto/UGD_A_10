package com.example.testugd1

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testugd1.room.User

class AkunPref (application: Application): AndroidViewModel(application) {
    var sharedpreferences: SharedPreferences =
        getApplication<Application>().applicationContext.getSharedPreferences("user", Context.MODE_PRIVATE)

    private val _currentUser = MutableLiveData<User>()

    val currentUser: LiveData<User>
        get() = _currentUser

    fun getUser(){
        _currentUser.value = User(
            sharedpreferences.getInt("id", 0),
            sharedpreferences.getString("username", "")!!,
            sharedpreferences.getString("password", "")!!,
            sharedpreferences.getString("email", "")!!,
            sharedpreferences.getString("tanggal", "")!!,
            sharedpreferences.getString("telp", "")!!
        )
    }
}