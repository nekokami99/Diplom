package com.example.energy_statistics.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("avatar") var avatar: String?,
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String,
    @SerializedName("confirm_password") var confirmPassword: String,
    @SerializedName("name") var name: String,
    @SerializedName("age") var age: String,
    @SerializedName("phone_number") var phoneNumber: String,
    @SerializedName("email") var email: String
)