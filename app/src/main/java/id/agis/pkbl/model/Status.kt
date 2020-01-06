package id.agis.pkbl.model

data class Status(
    val status: Boolean,
    val auth: Boolean,
    val code: Int,
    val message: String
)
