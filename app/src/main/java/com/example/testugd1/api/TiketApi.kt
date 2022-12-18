package com.example.testugd1.api

class TiketApi {
    companion object {
        val BASE_URL = "https://pbp-pariwisata.herokuapp.com/api/"

        val GET_ALL_URL = BASE_URL + "Tiket"
        val GET_BY_ID_URL = BASE_URL + "Tiket/"
        val ADD_URL = BASE_URL + "Tiket"
        val UPDATE_URL = BASE_URL + "Tiket/"
        val DELETE_URL = BASE_URL + "Tiket/"
    }
}