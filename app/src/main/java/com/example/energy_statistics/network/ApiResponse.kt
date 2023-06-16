import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
        @SerializedName("status")
        var status: Int?,
        @SerializedName("message")
        var message: String?,
        @SerializedName("data")
        var data: T?

)