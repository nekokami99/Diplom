package com.example.energy_statistics

import android.app.Application
import com.example.energy_statistics.data.Repository
import com.example.energy_statistics.network.ApiInterface
import com.example.energy_statistics.network.RetrofitClient

class App : Application() {

    lateinit var retrofitService : ApiInterface


    override fun onCreate() {
        super.onCreate()
        instance = this
        retrofitService = RetrofitClient(Repository.instance.accessToken).retrofitService
    }

    companion object {
        lateinit var instance: App
    }
}