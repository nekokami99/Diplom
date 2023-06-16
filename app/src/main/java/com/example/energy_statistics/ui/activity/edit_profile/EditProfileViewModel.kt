package com.example.energy_statistics.ui.activity.edit_profile

import ApiResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.energy_statistics.data.Repository
import com.example.energy_statistics.data.UserResponse
import com.example.energy_statistics.model.EditProfileRequest
import com.example.energy_statistics.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel : ViewModel() {

    private val avatar = MutableLiveData<String?>()
    val address = MutableLiveData<String?>()
    val name = MutableLiveData<String?>()
    val age = MutableLiveData<String?>()
    val phoneNumber = MutableLiveData<String?>()
    val email = MutableLiveData<String?>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        avatar.postValue(Repository.instance.userInfo?.avatar)
        address.postValue(Repository.instance.userInfo?.address)
        name.postValue(Repository.instance.userInfo?.name)
        age.postValue(Repository.instance.userInfo?.age)
        phoneNumber.postValue(Repository.instance.userInfo?.phoneNumber)
        email.postValue(Repository.instance.userInfo?.email)
    }

    fun setAvatar(strAvatar: String?){
        avatar.postValue(strAvatar)
    }

    fun editProfile() {
        Log.e("TAG", "editProfile: ")
        if (name.value.isNullOrBlank()
            || age.value.isNullOrBlank()
            || phoneNumber.value.isNullOrBlank()
            || email.value.isNullOrBlank()
        ) {
            _message.postValue("Please enter full information!")
            return
        }
        val request = EditProfileRequest(
            avatar = avatar.value,
            address = address.value,
            name = name.value,
            age = age.value,
            phoneNumber = phoneNumber.value,
            email = email.value
        )

        ApiClient.editProfile(request).enqueue(object : Callback<ApiResponse<Any>> {
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    getUserInfo()
                } else {
                    _message.postValue("Unable to get data!")
                }
            }

            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                _message.postValue("Unable to get data!")
            }

        })
    }

    private fun getUserInfo() {
        ApiClient.getUserInfo().enqueue(object : Callback<ApiResponse<UserResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<UserResponse>>,
                response: Response<ApiResponse<UserResponse>>
            ) {
                if (response.isSuccessful && response.body()?.data != null) {
                    Repository.instance.userInfo = response.body()?.data!!
                    _message.postValue("Successful profile change")
                } else {
                    _message.postValue("Unable to get data!")
                }
            }

            override fun onFailure(call: Call<ApiResponse<UserResponse>>, t: Throwable) {
                _message.postValue("Unable to get data!")
            }

        })
    }
}