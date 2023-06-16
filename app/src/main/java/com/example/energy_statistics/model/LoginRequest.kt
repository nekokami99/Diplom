package com.example.energy_statistics.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
        @SerializedName("email")
        var email: String?,
        @SerializedName("password")
        var password: String?
)
