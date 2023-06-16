package com.example.energy_statistics.ui.activity.home

import ApiResponse
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.energy_statistics.Utils
import com.example.energy_statistics.data.ChartResponse
import com.example.energy_statistics.data.HistoryResponse
import com.example.energy_statistics.data.Repository
import com.example.energy_statistics.data.UserResponse
import com.example.energy_statistics.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success


    private val _chartToday = MutableLiveData<ChartResponse>()
    val chartToday: MutableLiveData<ChartResponse> = _chartToday

    private val _chartYesterday = MutableLiveData<ChartResponse>()
    val chartYesterday: MutableLiveData<ChartResponse> = _chartYesterday

    private val _historyDay = MutableLiveData<List<HistoryResponse>>()
    val historyDay: MutableLiveData<List<HistoryResponse>> = _historyDay

    private val _historyMonth = MutableLiveData<List<HistoryResponse>>()
    val historyMonth: MutableLiveData<List<HistoryResponse>> = _historyMonth

    private val _userInfo = MutableLiveData<UserResponse>()
    val userInfo: MutableLiveData<UserResponse> = _userInfo

    init {
        getChartToDay()
        getHistoryDay()
        getUserInfo()
        getHistoryMonth()
    }

    fun getChartToDay() {
        ApiClient.getChartToday().enqueue(object : Callback<ApiResponse<ChartResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<ChartResponse>>,
                response: Response<ApiResponse<ChartResponse>>
            ) {
                if (response.isSuccessful && response.body()?.data != null) {
                    _success.postValue(true)
                    _chartToday.postValue(response.body()?.data!!)
                } else {
                    _message.postValue("Unable to get data!1")
                    _success.postValue(false)
                }
            }

            override fun onFailure(call: Call<ApiResponse<ChartResponse>>, t: Throwable) {
                t.printStackTrace()
                _message.postValue("Unable to get data!")
                _success.postValue(false)
            }

        })
    }

    fun getChartYesterday() {
        ApiClient.getChartYesterday().enqueue(object : Callback<ApiResponse<ChartResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<ChartResponse>>,
                response: Response<ApiResponse<ChartResponse>>
            ) {
                if (response.isSuccessful && response.body()?.data != null) {
                    _success.postValue(true)
                    _chartYesterday.postValue(response.body()?.data!!)
                } else {
                    _message.postValue("Unable to get data!")
                    _success.postValue(false)
                }
            }

            override fun onFailure(call: Call<ApiResponse<ChartResponse>>, t: Throwable) {
                t.printStackTrace()
                _message.postValue("Unable to get data!")
                _success.postValue(false)
            }

        })
    }

    fun getHistoryDay() {
        ApiClient.getHistoryDay().enqueue(object : Callback<ApiResponse<List<HistoryResponse>>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ApiResponse<List<HistoryResponse>>>,
                response: Response<ApiResponse<List<HistoryResponse>>>
            ) {
                if (response.isSuccessful && response.body()?.data != null) {
                    _success.postValue(true)
                    try {
                        val historyDay = response.body()?.data!!
                            .sortedByDescending {
                                it.dateReport?.let { date ->
                                    Utils.convertStringToDate(date, "dd.MM.yyyy")
                                }
                            }
                        _historyDay.postValue(historyDay)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        _historyDay.postValue(response.body()?.data!!)
                    }

                } else {
                    _message.postValue("Unable to get data!")
                    _success.postValue(false)
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<HistoryResponse>>>, t: Throwable) {
                t.printStackTrace()
                _message.postValue("Unable to get data!")
                _success.postValue(false)
            }

        })
    }

    fun getHistoryMonth() {
        ApiClient.getHistoryMonth().enqueue(object : Callback<ApiResponse<List<HistoryResponse>>> {
            override fun onResponse(
                call: Call<ApiResponse<List<HistoryResponse>>>,
                response: Response<ApiResponse<List<HistoryResponse>>>
            ) {
                if (response.isSuccessful && response.body()?.data != null) {
                    _success.postValue(true)
                    val historyMonth = response.body()?.data!!
                        .sortedByDescending {
                            it.dateReport
                        }
                    _historyMonth.postValue(historyMonth)
                } else {
                    _message.postValue("Unable to get data!")
                    _success.postValue(false)
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<HistoryResponse>>>, t: Throwable) {
                t.printStackTrace()
                _message.postValue("Unable to get data!")
                _success.postValue(false)
            }

        })
    }

    fun getUserInfo() {
        ApiClient.getUserInfo().enqueue(object : Callback<ApiResponse<UserResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<UserResponse>>,
                response: Response<ApiResponse<UserResponse>>
            ) {
                if (response.isSuccessful && response.body()?.data != null) {
                    _success.postValue(true)
                    Repository.instance.userInfo = response.body()?.data!!
                } else {
                    _message.postValue("Unable to get data!")
                    _success.postValue(false)
                }
            }

            override fun onFailure(call: Call<ApiResponse<UserResponse>>, t: Throwable) {
                _message.postValue("Unable to get data!")
                _success.postValue(false)
            }

        })
    }
}