package com.example.energy_statistics.data

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("email") var email: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("age") var age: String?,
    @SerializedName("created_at") var createdAt: String?,
    @SerializedName("updated_at") var updatedAt: String?,
    @SerializedName("avatar") var avatar: String?,
    @SerializedName("phone_number") var phoneNumber: String?,
    @SerializedName("address") var address: String?

)