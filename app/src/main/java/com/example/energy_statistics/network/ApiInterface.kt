package com.example.energy_statistics.network

import ApiResponse
import androidx.lifecycle.LiveData
import com.example.energy_statistics.data.ChartResponse
import com.example.energy_statistics.data.HistoryResponse
import com.example.energy_statistics.data.UserResponse
import com.example.energy_statistics.model.EditProfileRequest
import com.example.energy_statistics.model.LoginRequest
import com.example.energy_statistics.model.LoginResponse
import com.example.energy_statistics.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiInterface {

    @POST("/login")
    fun callLogin(@Body body: LoginRequest): Call<ApiResponse<LoginResponse>>

    @POST("register")
    fun register(@Body request: RegisterRequest): Call<ApiResponse<Any>>

    @GET("/user")
    fun getUser(): Call<ApiResponse<UserResponse>>

    @PUT("/user")
    fun editProfile(@Body request: EditProfileRequest): Call<ApiResponse<Any>>

    @PUT("/user/change-password")
    fun changePass(@Header("Authorization") accessToken: String?): LiveData<Any>

    @GET("/consumption-history")
    fun getHistory(
        @Query("date_type") dateType: String?
    ): Call<ApiResponse<List<HistoryResponse>>>

    @GET("/consumption-history")
    fun getChart(
        @Query("date_type") dateType: String?
    ): Call<ApiResponse<ChartResponse>>

}