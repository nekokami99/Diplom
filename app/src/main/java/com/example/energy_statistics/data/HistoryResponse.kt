package com.example.energy_statistics.data

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("electric_used") var electricUsed: Int?,
    @SerializedName("water_used") var waterUsed: Int?,
    @SerializedName("temperature") var temperature: Int?,
    @SerializedName("date_report") var dateReport: String?
)