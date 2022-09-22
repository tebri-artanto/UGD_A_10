package com.example.testugd1.roomTP

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pariwisata (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nama: String,
    val lokasi: String,
    val harga: String,
    val tipe: String,
)