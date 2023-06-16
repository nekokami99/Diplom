package com.example.energy_statistics

import com.google.gson.annotations.SerializedName

object AppConstant {
    enum class DateType constructor(val type: String) {
        @SerializedName("today")
        TODAY("today"),
        @SerializedName("yesterday")
        YESTERDAY("yesterday"),
        @SerializedName("day")
        DAY("day"),
        @SerializedName("month")
        MONTH("month")
    }
}