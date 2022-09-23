package com.example.testugd1.roomTP

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query

@Dao
interface PariwisataDao {
    @Insert
    suspend fun addPariwisata(pariwisata: Pariwisata)
    @Update
    suspend fun updatePariwisata(pariwisata: Pariwisata)
    @Delete
    suspend fun deletePariwisata(pariwisata: Pariwisata)
    @Query("SELECT * FROM pariwisata")
    suspend fun getPariwisata() : List<Pariwisata>
    @Query("SELECT * FROM pariwisata WHERE id =:pariwisata_id")
    suspend fun getPariwisata(pariwisata_id: Int) : List<Pariwisata>
}