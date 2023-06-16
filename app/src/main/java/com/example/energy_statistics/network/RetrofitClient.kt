package com.example.energy_statistics.network

import com.example.energy_statistics.data.Repository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(token: String?) {

    private var BASE_URL = "https://static-t5ec.onrender.com"


    private val interceptor = Interceptor() {chain->
        val request = if(token.isNullOrBlank()) {
             chain.request().newBuilder()
                .build()
        } else {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }

        chain.proceed(request)

    }
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val retrofitService: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }

}