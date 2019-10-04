package id.agis.pkbl.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val userId: Int,

    val username: String,

    val access: Access,

    val token: String
)

data class Access(
    val id: Int,
    @SerializedName("access_name")
    val accessName: String
)

