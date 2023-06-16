package com.example.energy_statistics.ui.activity.signup

import ApiResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.energy_statistics.model.RegisterRequest
import com.example.energy_statistics.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val pass = MutableLiveData<String>()
    val confirmPass = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val age = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val address = MutableLiveData<String>()

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun register() {
        if (
            name.value.isNullOrEmpty()
            || pass.value.isNullOrEmpty()
            || age.value.isNullOrEmpty()
            || phoneNumber.value.isNullOrEmpty()
            || email.value.isNullOrEmpty()
        ) {
            _showProgress.postValue(false)
            _success.postValue(false)
            _message.postValue("Please enter full information!")
            return
        } else {

        }
        if (pass.value != confirmPass.value) {
            _showProgress.postValue(false)
            _success.postValue(false)
            _message.postValue("Password does not match!")
            return
        }
        _showProgress.postValue(true)
        val request = RegisterRequest(
            avatar = null,
            username = name.value ?: "",
            password = pass.value ?: "",
            confirmPassword = confirmPass.value ?: "",
            name = name.value ?: "",
            age = age.value ?: "",
            phoneNumber = phoneNumber.value ?: "",
            email = email.value ?: ""

        )
        ApiClient.registerAccount(request).enqueue(object : Callback<ApiResponse<Any>> {
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                if (response.isSuccessful) {
                    _success.postValue(true)
                    response.body()?.message?.let {
                        _message.postValue(it)
                    }

                } else {
                    _success.postValue(false)
                    _message.postValue("Signup failed!")
                }
                _showProgress.postValue(false)
            }

            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                _showProgress.postValue(false)
                _message.postValue("Signup failed!")
                _success.postValue(false)
            }

        })
    }

}