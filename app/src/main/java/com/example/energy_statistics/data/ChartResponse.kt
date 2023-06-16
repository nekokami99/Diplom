package com.example.energy_statistics.data

import com.google.gson.annotations.SerializedName

data class ChartResponse(
    @SerializedName("electric_used") var electricUsed: ElectricUsed?,
    @SerializedName("water_used") var waterUsed: WaterUsed?,
    @SerializedName("temperature") var temperature: Temperature?
)

data class ElectricUsed(
    @SerializedName("total") var total: Float?,
    @SerializedName("hours") var hours: ArrayList<Hours>?
)

data class WaterUsed (
    @SerializedName("total" ) var total : Float?,
    @SerializedName("hours" ) var hours : ArrayList<Hours>?
)

data class Temperature (
    @SerializedName("number_report" ) var numberReport : Int?,
    @SerializedName("total"         ) var total        : Float?,
    @SerializedName("hours"         ) var hours        : ArrayList<Hours>?
)

data class Hours (
    @SerializedName("hour_title"    ) var hourTitle    : Int?,
    @SerializedName("hour"          ) var hour         : Int?,
    @SerializedName("total"         ) var total        : Float?,
)