package com.example.energy_statistics.data

import com.example.energy_statistics.App
import com.example.energy_statistics.data.SharedPref.Companion.KEY_USER

class Repository {

    val shared by lazy { SharedPref(App.instance)}
    val accessToken = shared.getAccessTokenValue(KEY_USER)
    var userInfo: UserResponse? = null
    companion object {
        val instance = Repository()
    }
}