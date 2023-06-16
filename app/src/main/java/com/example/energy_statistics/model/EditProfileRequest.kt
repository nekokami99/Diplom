package com.example.energy_statistics.model

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(
    @SerializedName("avatar") var avatar: String?,
    @SerializedName("address") var address: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("age") var age: String?,
    @SerializedName("phone_number") var phoneNumber: String?,
    @SerializedName("email") var email: String?
)
