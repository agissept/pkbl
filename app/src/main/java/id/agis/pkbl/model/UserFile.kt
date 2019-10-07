package id.agis.pkbl.model

import android.net.Uri

data class UserFile(
    val uri: Uri,
    val name: String?,
    val type: String?
)