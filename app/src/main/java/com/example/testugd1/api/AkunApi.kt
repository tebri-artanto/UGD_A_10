package com.example.testugd1.api

class AkunApi {
    companion object {
        val BASE_URL = "https://pbp-pariwisata.herokuapp.com/api/"

        val GET_ALL_URL = BASE_URL + "user"
        val GET_BY_ID_URL = BASE_URL + "user/"
        val ADD_URL = BASE_URL + "register"
        val UPDATE_URL = BASE_URL + "user/"
        val DELETE_URL = BASE_URL + "user/"
        val LOGIN_URL = BASE_URL + "login"
    }
}