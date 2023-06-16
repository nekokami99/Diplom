package com.example.energy_statistics.network

import ApiResponse
import com.example.energy_statistics.App
import com.example.energy_statistics.AppConstant
import com.example.energy_statistics.data.ChartResponse
import com.example.energy_statistics.data.HistoryResponse
import com.example.energy_statistics.data.UserResponse
import com.example.energy_statistics.model.EditProfileRequest
import com.example.energy_statistics.model.LoginRequest
import com.example.energy_statistics.model.LoginResponse
import com.example.energy_statistics.model.RegisterRequest
import retrofit2.Call

class ApiClient {
    companion object {
        fun login(email: String, password: String): Call<ApiResponse<LoginResponse>> {
            return App.instance.retrofitService.callLogin(LoginRequest(email, password))!!
        }

        fun registerAccount(request: RegisterRequest): Call<ApiResponse<Any>> {
            return App.instance.retrofitService.register(request)
        }

        fun getChartToday(): Call<ApiResponse<ChartResponse>> {
            return App.instance.retrofitService.getChart(
                AppConstant.DateType.TODAY.type
            )
        }

        fun getChartYesterday(): Call<ApiResponse<ChartResponse>> {
            return App.instance.retrofitService.getChart(
                AppConstant.DateType.YESTERDAY.type
            )
        }

        fun getHistoryDay(): Call<ApiResponse<List<HistoryResponse>>> {
            return App.instance.retrofitService.getHistory(AppConstant.DateType.DAY.type)
        }

        fun getHistoryMonth(): Call<ApiResponse<List<HistoryResponse>>> {
            return App.instance.retrofitService.getHistory(AppConstant.DateType.MONTH.type)
        }

        fun getUserInfo(): Call<ApiResponse<UserResponse>> {
            return App.instance.retrofitService.getUser()
        }

        fun editProfile(request: EditProfileRequest): Call<ApiResponse<Any>> {
            return App.instance.retrofitService.editProfile(request)
        }
    }
}