package com.example.testugd1.roomTP

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testugd1.EditActivity
import com.example.testugd1.homeMenu.homeFragment

@Database(
    entities = [Pariwisata::class],
    version = 1
)
abstract class PariwisataDB: RoomDatabase() {
    abstract fun pariwisataDao() : PariwisataDao

    companion object {
        @Volatile private var instance : PariwisataDB? = null
        private val LOCK = Any()
        operator fun invoke(context: homeFragment) = instance ?:
        synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PariwisataDB::class.java,
                "TempatPariwisata.db"
            ).build()
    }
}