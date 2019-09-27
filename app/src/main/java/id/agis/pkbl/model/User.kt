package id.agis.pkbl.model

import com.google.gson.annotations.SerializedName

class User(
    val id: Int,
    val username: String,
    val access: Access
)

class Access(
    val id: Int,
    @SerializedName("access_name")
    val accesName: String
)