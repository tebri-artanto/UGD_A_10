package com.example.testugd1.room

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testugd1.MainActivity
import com.example.testugd1.SignUp
import com.example.testugd1.profileFragment

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDB: RoomDatabase() {
    abstract fun userDao() : UserDao
    companion object {
        @Volatile private var instance : UserDB? = null
        private val LOCK = Any()
        operator fun invoke(context: FragmentActivity) = instance ?:
        synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDB::class.java,
                "userPariwisata.db"
            ).build()
    }
}