package com.example.energy_statistics.model

import com.google.gson.annotations.SerializedName


data class LoginResponse(
        @SerializedName("user")
        var user: User?,
        @SerializedName("token")
        var token: String?
)

data class User(
        @SerializedName("_id")
        var id: String?,
        @SerializedName("email")
        var email: String?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("age")
        var age: String?,
        @SerializedName("address")
        var address: String?,
        @SerializedName("avatar")
        var avatar: String?,
        @SerializedName("phone_number")
        var phoneNumber: String?
)