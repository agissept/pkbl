package id.agis.pkbl.model


data class User(
    val id: Int,
    val username: String,
    val email: String,
    val role: String,
    val status: Boolean
)


