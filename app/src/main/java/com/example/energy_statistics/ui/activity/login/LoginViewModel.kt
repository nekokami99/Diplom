package com.example.energy_statistics.ui.activity.login

import ApiResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energy_statistics.App
import com.example.energy_statistics.data.Repository
import com.example.energy_statistics.data.SharedPref.Companion.KEY_USER
import com.example.energy_statistics.model.LoginResponse
import com.example.energy_statistics.network.ApiClient
import com.example.energy_statistics.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _startSignup = MutableLiveData(false)
    val startSignup: LiveData<Boolean> = _startSignup

    fun login() {
        if (email.value.isNullOrBlank() || password.value.isNullOrBlank()) {
            _error.postValue("Email or Password is blank")
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                ApiClient.login(email.value!!, password.value!!)
                    .enqueue(object : Callback<ApiResponse<LoginResponse>> {

                        override fun onResponse(
                            call: Call<ApiResponse<LoginResponse>>,
                            response: Response<ApiResponse<LoginResponse>>
                        ) {
                            Log.e("TAG", "onResponse: ${response.body()}")
                            if (response.isSuccessful && response.body() != null && response.body()?.data?.token != null) {
                                val data = response.body()?.data
                                val accessToken = data?.token
                                if (!accessToken.isNullOrBlank()) {
                                    Repository.instance.shared?.setAccessTokenValue(
                                        KEY_USER,
                                        accessToken
                                    )
                                    val retrofit = RetrofitClient(accessToken)
                                    App.instance.retrofitService = retrofit.retrofitService
                                    _success.postValue("Login success")
                                }
                            } else {
                                _error.postValue("Login fail!")
                            }
                        }

                        override fun onFailure(
                            call: Call<ApiResponse<LoginResponse>>,
                            t: Throwable
                        ) {
                            Log.e("TAG", "onFailure: $t")
                        }
                    })
            }

        }
    }

    fun startSignUp() {
        _startSignup.postValue(true)
    }
}